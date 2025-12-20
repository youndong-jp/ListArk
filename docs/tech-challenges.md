# Technical Challenges

## 0. 문서 목적

이 문서는 ListArk 프로젝트를 개발하면서 마주한 **핵심 기술적 문제와 그 해결 과정** 을 정리합니다.

ListArk는 Lost Ark Open API라는 **외부 의존성이 크고, 구조가 복잡한 시스템** 을 기반으로 합니다.
따라서 단순한 기능 구현보다 다음과 같은 **설계 관점의 문제 해결**이 중요했습니다.

- 외부 API 구조 변화 대응
- 장애 및 실패 상황 대응
- 테스트 가능성 확보

본 문서는
“무엇을 구현했는가”가 아니라
**“왜 그렇게 설계했는가”** 에 초점을 맞춥니다.

## 1. 동적 Tooltip 파싱 문제

**문제 상황:**

Lost Ark API의 Tooltip 데이터는 **HTML** 태그가 섞인 **JSON** 문자열 형태로 제공됩니다.
또한 장비·아바타·아크그리드 등 카테고리별로 Element 순서와 구조가 모두 달라 정적인 구조 가정이나 하드코딩이 불가능했습니다.
```json
{
  "Element_001": "<FONT COLOR='#FFD200'>힘 +2.00%</FONT>",
  "Element_005": {
    "type": "ItemPartBox",
    "value": {
      "Element_000": "<FONT COLOR='#A9D0F5'>기본 효과</FONT>",
      "Element_001": "힘 +2.00%"
    }
  }
}
```

**해결 과정:**
1. **정규식으로 HTML 태그 제거**: `<[^>]*>` 패턴 매칭
2. **동적 Element 순회**: Element 키를 고정하지 않고 전체 순회하며, `type` 값을 기준으로 파싱 로직 분리 
3. **Parser 추상화**: 각 카테고리별로 Parser 분리
    - `EquipmentTooltipParser`
    - `AvatarTooltipParser`
    - `ArkGridTooltipParser` (가장 복잡)

**결과:**
- Tooltip 구조 변경 시에도 Mapper 수정 없이 대응 가능
- Parser 분리로 코드 복잡도 감소
- 단일 책임 원칙(SRP)에 맞는 구조 확립

**배운 점:**

하드코딩은 단기적으로 빠르지만,
외부 API처럼 구조 변경 가능성이 높은 영역에서는
동적 처리와 추상화가 장기적인 유지보수에 훨씬 유리하다는 것을 배웠습니다.

---

## 2. NPE(NullPointerException) 방어

**문제 상황:**
Lost Ark API가 간헐적으로 `null` 값을 반환했습니다.
- 장비 미착용 캐릭터: `ArmoryEquipment: null`
- 아바타 미착용: `ArmoryAvatar: null`
- 보석 미장착: `Gems: null`
```java
// 기존 코드 - NPE 위험
String type = raw.getType();
List<Equipment> items = raw.getArmoryEquipment();
String name = items.get(0).getName();
```

**해결 방법:**
`NullSafe` 유틸리티 클래스 작성
```java
public class NullSafe {
    /**
     * Supplier로 null 안전 호출
     */
    public static <T> T get(Supplier<T> supplier, T defaultValue) {
        try {
            T value = supplier.get();
            return value != null ? value : defaultValue;
        } catch (NullPointerException e) {
            return defaultValue;
        }
    }
    
    /**
     * 리스트 null 안전 처리
     */
    public static <T> List<T> list(List<T> list) {
        return list != null ? list : List.of();
    }
}
```

**사용 예시:**
```java
// NPE 안전한 코드
String type = NullSafe.get(raw::getType, "");
List<Equipment> items = NullSafe.list(raw.getArmoryEquipment());
```
**설계 의도:**
null 체크 로직이 Mapper마다 중복되는 것을 방지하고,
Raw → Tidy 변환 과정에서 일관된 방어 전략을 적용하기 위함입니다.

**“왜 Reactive Retry인가”**
Reactive 환경에서 Thread.sleep 기반 재시도는
Thread 점유 및 확장성 문제를 유발할 수 있기 때문에,
비동기 흐름을 유지할 수 있는 retryWhen 방식을 선택했습니다.


**결과:**
- 외부 API의 null 응답으로 인한 NPE 제거
- Mapper/Service 전반에 방어적 프로그래밍 적용
- null 처리 로직 중복 제거


**배운 점:**
외부 API 연동 시 **“null은 예외가 아니라 기본 상태”** 로 가정하고 설계해야 한다는 것을 배웠습니다.

---

## 3. API 재시도 전략 (Resilience)

**문제 상황**

Lost Ark Open API는 간헐적으로 다음과 같은 일시적 장애를 반환했습니다.

- `503 Service Unavailable`
- `504 Gateway Timeout`
- 네트워크 타임아웃 / 연결 오류

- 즉시 에러를 반환할 경우 사용자 경험이 크게 저하되었습니다.

---

**해결 방법**

Spring WebClient + Reactor 환경에 맞춰  
`retryWhen` 기반의 **Reactive Retry 전략**을 구현했습니다.

- Exponential Backoff 적용
- Jitter(랜덤 지연) 적용으로 서버 과부하 방지
- Retry 가능한 예외만 선별
- API 성격에 따라 Retry 정책 분리

```java
public class RetryUtils {
   public static <T> Function<Mono<T>, Mono<T>> retry3() {
      return mono -> mono.retryWhen(
              Retry.backoff(3, Duration.ofMillis(200))
                      .jitter(0.75)
                      .filter(RetryUtils::isRetryableException)
                      .onRetryExhaustedThrow((spec, signal) ->
                              new ExternalApiException("Lost Ark API unavailable after retries")
                      )
      );
   }

   private static boolean isRetryableException(Throwable ex) {
      return ex instanceof WebClientResponseException.ServiceUnavailable || // 503
              ex instanceof WebClientResponseException.GatewayTimeout ||     // 504
              ex instanceof WebClientRequestException;                       // network error
   }
}
```
해당 Retry 로직은 WebClientHelper에서 공통으로 적용되어,
모든 외부 API 호출이 일관된 정책으로 보호됩니다.

.bodyToMono(responseType)
.transform(RetryUtils.retry3());

**설계 의도:**
Retry는 예외 처리의 연장이 아니라,
외부 시스템과 통신할 때 반드시 고려해야 할 기본 책임으로 판단했습니다.

**결과**
- 일시적인 API 장애 상황에서도 자동 재시도로 성공 확률 향상
- 네트워크 장애로 인한 불필요한 실패 감소
- Thread.sleep 기반 동기 재시도 제거
- Non-blocking Reactive 환경 유지
- 테스트 환경에서도 안정적인 동작 보장

**배운 점**
- 외부 API 연동 시 “실패는 예외가 아니라 전제 조건”으로 두고 설계해야 함
- Reactive 환경에서는 retryWhen 기반 재시도가 안정성과 확장성 면에서 적합함
---
# 핵심 요약

- Tooltip 파싱은 동적 구조 + 전용 Parser 분리로 해결
- 외부 데이터는 항상 null-safe하게 처리
- 외부 API 장애는 복원력(Resilience) 관점에서 설계

## 4. 참고 문서

- 아키텍쳐 설계 : [Architecture](architecture.md)
- 데이터 흐름 : [Data Flow](data-flow.md)
- API 상세 : [Api Details](api-detail.md) 
- 테스트 전략 : [Testing Strategy](testing.md)