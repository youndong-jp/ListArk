# Testing Strategy

## 0. 문서 목적

이 문서는 ListArk 프로젝트의 **테스트 전략과 설계 의도**를 설명합니다.

ListArk는 Lost Ark Open API라는
- 외부 의존성
- 네트워크 불안정성
- 복잡한 데이터 구조

를 전제로 하는 시스템입니다.

따라서 테스트의 핵심 목표를  
**“기능 검증”보다 “안정성 · 재현성 · 신뢰성”** 에 두었습니다.

본 문서는  
“무엇을 테스트했는가”가 아니라  
**“왜 이런 테스트 전략을 선택했는가”** 에 초점을 둡니다.
---
## 1. 테스트 목표

### 핵심 목표

- 외부 API 의존성 제거
- Mapper / Parser 로직 안정성 확보
- 테스트 실패 원인을 코드로 한정

### 배경

Lost Ark Open API는 다음과 같은 특성을 가집니다.

- 응답 구조가 매우 복잡함
- 간헐적인 장애 발생
- API Key 의존
- 테스트 환경에 따라 결과가 달라짐

실제 API 호출 기반 테스트는  
**신뢰할 수 있는 테스트 전략이 아니라고 판단**했습니다.

---

## 2. 테스트 구조
```
src/test/java/
├── integration/
│   ├── mapper/
│   │   ├── equipment/          # 장비 Mapper 테스트
│   │   ├── avatar/             # 아바타 Mapper 테스트
│   │   ├── engraving/          # 각인 Mapper 테스트
│   │   └── arkgrid/            # 아크그리드 Mapper 테스트
│   └── armory/
│       └── ArmoryIntegrationTest  # 오류/예외 흐름 검증용 통합 테스트 
└── support/
    ├── BaseIntegrationTest        # 테스트 베이스
    └── MockLostArkServer          # WireMock 서버
```

### 구조 설계 의도

- Mapper / Parser 단위 테스트 집중
- 복잡한 데이터 변환 로직을 가장 먼저 검증
- Service / Controller 테스트는 점진적 확장
---
## 3. WireMock 사용 이유

### 문제 상황

실제 Lost Ark API를 호출하는 테스트는 다음 문제를 가졌습니다.

- 느림 (네트워크 I/O, 평균 1~2초)
- 불안정 (API 장애 시 테스트 실패)
- API Key 관리 필요
- 테스트 결과 재현 어려움

---

### 해결 방법: WireMock 도입

Lost Ark API를 대체하는 Mock 서버를 구성했습니다.

```java
@Component
public class MockLostArkServer {
    private WireMockServer wireMockServer;

    public void stubArmory(String characterName, String jsonResponse) {
        stubFor(get(urlEqualTo("/armories/characters/" + characterName))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(jsonResponse)
                )
        );
    }
}
```
### 효과 
- 테스트 속도 대폭 개선
- API Key 불필요
- 외부 장애와 무관한 안정적인 테스트 환경 확보
---
## 4. 통합 테스트 범위에 대한 판단

본 프로젝트에서는  
Armory 전체 정상 흐름을 통합 테스트로 보장하는 전략을 선택하지 않았습니다.

그 이유는 다음과 같습니다.

- 정상 응답 시 로직의 핵심 복잡도는  
  Service가 아니라 Mapper / TooltipParser에 있음
- 외부 API의 정상 응답은  
  데이터 구조에 대한 신뢰보다 **변환 로직의 정확성**이 중요함
- 정상 플로우를 통합 테스트로 검증할 경우  
  테스트 비용 대비 검증 가치가 낮다고 판단

따라서 테스트 전략을 다음과 같이 분리했습니다.

- **정상 데이터 흐름**  
  → Mapper / TooltipParser 단위 테스트로 검증
- **오류 및 예외 흐름**  
  → 통합 테스트로 전체 계층 연결만 확인

---
## 5. 통합 테스트의 실제 역할

통합 테스트는  
**정상 데이터 변환을 검증하기 위한 용도**가 아니라,  
다음 항목을 확인하는 데에만 사용됩니다.

- 외부 API 오류가 도메인 예외로 변환되는지
- GlobalExceptionHandler가 올바른 응답을 반환하는지
- Client → Service → Controller 연결이 끊어지지 않았는지

즉, 통합 테스트의 목적은  
**“데이터가 맞게 변환되었는가”가 아니라  
“오류가 시스템 전체를 통해 올바르게 전달되는가”** 입니다.
---
## 6. 테스트 전략 요약

| 영역 | 테스트 방식 | 이유 |
|----|----|----|
| Mapper | 단위 테스트 | 복잡한 변환 로직 집중 검증 |
| TooltipParser | 단위 테스트 | 동적 구조 파싱 안정성 확보 |
| Service | 최소 검증 | 오케스트레이션 역할 |
| Controller | 최소 검증 | HTTP 연결 확인 |
| 오류 흐름 | 통합 테스트 | 예외 변환 및 응답 구조 검증 |

이 전략을 통해  
테스트는 많지 않지만,  
**실패 가능성이 가장 높은 지점을 확실하게 검증**합니다.
---
## 7. 배운 점

- 모든 정상 흐름을 통합 테스트로 검증하는 것이  
  항상 좋은 전략은 아니다
- 복잡도가 높은 지점을 정확히 선택해 테스트하는 것이 중요하다
- 테스트는 “얼마나 많이 작성했는가”보다  
  **“어디를 검증했는가”가 더 중요하다**

## 8. 참고 문서 

- 아키텍쳐 설계 : [Architecture](architecture.md)
- 데이터 흐름 : [Data Flow](data-flow.md)
- API 상세 : [Api Details](api-detail.md) 
- 기술적 설계 의도: [Technical Challenges](tech-challenges.md)