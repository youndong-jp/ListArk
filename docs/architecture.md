# ListArk Architecture

## 0. 문서 목적

이 문서는 ListArk 프로젝트의 아키텍처 설계 의도와  
Raw → Tidy 데이터 변환 구조, 외부 API 대응 전략을 설명합니다.

ListArk는 Lost Ark Open API의 복잡한 Raw 데이터를  
프론트엔드에서 바로 사용할 수 있는 형태로 가공하는 API 래퍼 서비스입니다.

---

## 1. 전체 시스템 구조

### 전체 흐름
```
Client (React - 예정)
↓
Controller
(HTTP 요청/응답)
↓
Service
(Raw → Tidy 오케스트레이션)
↓
API Client
(Lost Ark API 의미 단위)
↓
WebClientHelper
(공통 HTTP 처리)
↓
WebClient
(HTTP 통신)
↓
Lost Ark Open API
```

### 구조 요약

- Controller는 HTTP 요청/응답만 담당
- Service는 비즈니스 흐름과 변환 조합을 담당
- Client는 외부 API 호출 의미를 캡슐화
- Mapper / Parser는 Service 내부에서 조합되어 사용

---

## 2. Client 계층을 분리한 이유

API Client 계층은 Lost Ark API의 **엔드포인트 의미를 표현하기 위한 계층**입니다.

HTTP 구현(WebClient, Retry, Error Handling)과  
비즈니스 로직(Service)을 분리하기 위해 도입했습니다.

이를 통해 Service는

- **“어떤 데이터를 조합할지”**에만 집중하고
- **“어떻게 HTTP로 가져오는지”**는 Client 계층에 위임합니다.

> API Client 계층은 **Raw DTO만 반환**하며  
> Raw → Tidy 변환은 **Service 내부에서 Mapper를 통해 수행**됩니다.

---

## 3. 계층별 책임

| 계층 | 책임 |
|------|------|
| Controller | HTTP 요청/응답 처리 |
| Service | 비즈니스 로직 조합, Raw → Tidy 오케스트레이션 |
| Client | 외부 API 호출, Retry, Error Handling |
| Mapper | Raw DTO → Tidy DTO 변환 |
| Parser | Tooltip / 문자열 파싱 |
| Util | 공통 유틸리티 (NullSafe, Retry 등) |

### Mapper / Parser 보충 설명

Mapper와 Parser는 독립적인 비즈니스 흐름을 가지지 않습니다.  
Service 계층에 의해 조합되어 Raw → Tidy 변환을 수행합니다.

- Service: 전체 변환 흐름 오케스트레이션
- Mapper: 구조적 DTO 변환 책임
- Parser: Tooltip과 같은 복잡한 문자열 파싱 전용

---

## 4. Raw → Tidy 변환 구조

1. Service가 Client를 통해 Raw DTO 수신
2. Service가 도메인별 Mapper를 순차 호출
3. Mapper 내부에서 필요 시 Parser 호출
4. 모든 결과를 `ArmoryTidyDto`로 조합

### Mapper 분리 이유

Lost Ark Armory 데이터는 카테고리별 구조와 책임이 명확히 다르기 때문에  
**도메인 단위 Mapper 분리**를 선택했습니다.

- 변경 영향 범위 최소화
- 테스트 단위 명확화
- 신규 카테고리 추가 시 확장 용이

---

## 5. 예외 처리 전략

외부 API 에러 및 비즈니스 예외는 모두 **도메인 예외**로 변환됩니다.

### 처리 흐름

```
LostArk API
↓
WebClientErrorHandler
↓
Domain Exception
↓
Domain Exception
↓
ErrorResponse
```

### 특징

- HTTP 상태 코드는 Client 계층에서 변환
- Controller는 예외를 직접 처리하지 않음
- 모든 에러는 공통 ErrorResponse 형식으로 응답

---

## 6. Retry / Resilience 설계

외부 API는 항상 불안정하다는 전제하에 설계했습니다.

### Retry 전략

- Exponential Backoff 적용
- Jitter 적용으로 서버 과부하 방지
- API 성격에 따라 Retry 정책 분리
    - `retry3` : 핵심 API
    - `retry1` : 가벼운 API
    - `noRetry` : 재시도 부적합 API

### 장점

- 일시적 장애 자동 복구
- Non-blocking Reactive 환경 유지
- 테스트 환경에서도 안정적인 동작

---

## 7. 설계 원칙

### 1. 단일 책임 원칙 (SRP)

- Controller: HTTP만
- Service: 비즈니스 흐름만
- Mapper: DTO 변환만
- Parser: 문자열 파싱만

### 2. 관심사 분리

- HTTP 구현과 비즈니스 로직 분리
- 외부 API 의존성 최소화

### 3. 외부 API는 항상 불안정

- NullSafe 유틸 적용
- Retry 로직 기본 적용
- WireMock 기반 테스트 환경 구성
---

## 데이터 흐름 문서

요청부터 응답까지의 실제 처리 흐름은 아래 문서에 정리되어 있습니다.

**[ListArk Data Flow (Raw → Tidy)](data-flow.md)**



