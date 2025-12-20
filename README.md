# ListArk

> 프론트엔드 개발자를 위한 로스트아크 캐릭터 정보 API 래퍼 서비스

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)

---

## 목차

- [프로젝트 소개](#프로젝트-소개)
- [주요 기능](#주요-기능)
- [기술 스택](#기술-스택)
- [아키텍처](#아키텍처)
- [기술적 챌린지(요약)](#기술적-챌린지-요약)
- [시작하기](#시작하기)
- [API 문서](#api-문서)
- [테스트(요약)](#테스트-요약)
- [향후 계획](#향후-계획)
- [회고](#회고)

---

## 프로젝트 소개

### 개발 동기

**"개발자는 무언가를 만들어야 한다"**

이론만 공부하다 "난 뭘 공부하고 있는 거지?"라는 생각이 들었습니다.
실제로 동작하는 무언가를 만들어야 진짜 개발자라는 생각에 프로젝트를 시작했습니다.

단순한 CRUD가 아닌, **내가 좋아하는 것**과 **실제로 사용할 수 있는 것**을 만들기로 결심했습니다.

### 왜 ListArk인가?

- **개인적 관심사**: 평소 즐기는 로스트아크 게임 데이터 활용
- **실전 경험**: 학습 + 포트폴리오 + 서비스 운영을 한 번에
- **실사용자 확보**: 친구들에게 실제 배포하여 피드백 수집
- **레퍼런스**: [로아와](https://loawa.com/) 같은 실서비스를 목표로

---

## 주요 기능

### 1. 캐릭터 정보 조회
```bash
GET /api/characters/{characterName}/armory
```

**제공 정보:**
- 프로필 (레벨, 아이템 레벨, 서버, 클래스)
- 장비 (무기, 방어구, 악세서리)
- 아바타 (외형 정보)
- 각인 (활성화된 각인 정보)
- 스킬 (스킬 빌드 정보)
- 보석 (보석 정보 )
- 카드 (장착 카드, 세트 효과)
- 컬렉션 (모험물 현황)
- 아크 패시브 (아크 패시브 정보)
- 아크 그리드 (아크 그리드  정보)

### 2. Raw → Tidy 데이터 변환

Lost Ark API의 복잡한 Raw 데이터를 프론트엔드 친화적인 구조로 변환합니다.

**Before (Raw API):**
```json
{
  "ArkPassiveEffects": [
    {
      "AbilityStoneLevel": 2,
      "Grade": "유물",
      "Level": 0,
      "Name": "각성",
      "Description": "각성기의 재사용 대기시간이 <FONT COLOR='#99ff99'>51.50%</FONT> 감소하고..."
    }
  ]
}
```

**After (Tidy API):**
```json
{
  "success": true,
  "data": {
    "engravings": [
      {
        "name": "각성",
        "level": 2,
        "stoneLevel": 2,
        "grade": "유물",
        "description": "각성기의 재사용 대기시간이 51.50% 감소하고..."
      }
    ]
  }
}
```

**주요 변환 작업:**
- HTML 태그 제거 (`<FONT>`, `<BR>` 등)
- 중첩된 JSON 구조 단순화
- 프론트엔드에서 사용하기 쉬운 필드명으로 변경
- null 안전 처리

### 3. Swagger API 문서 자동 생성

- 모든 API 엔드포인트 자동 문서화
- Request/Response 예시 포함
- 브라우저에서 직접 테스트 가능

---

## 기술 스택

### Backend

| 기술              | 버전 | 사용 계기 & 배운 점 |
|-----------------|------|---------------------|
| **Java**        | 17 | Java를 주력 언어로 학습 중이었고, LTS 버전이라 학습·운영 모두 안정적. Stream API, Record 등 모던 Java 기능 활용 |
| **Spring Boot** | 3.x | Controller–Service–Mapper 구조를 직접 설계하며 DI/IoC 개념을 체득 |
| **Maven**       | 3.8+ | Spring Initializr 기본 설정으로 시작. 의존성 관리와 테스트 실행(`mvn test`), 커버리지 리포트(`mvn jacoco:report`)를 직접 사용하며 빌드 도구의 역할 이해 |
| WebClient       | - | 외부 API 호출 전용으로 사용. 내부 구조는 MVC 유지|
| **Lombok**      | - | 반복적인 Getter/Setter 작성에 지쳐서 도입. `@Data`, `@Builder`로 코드량 50% 감소 |

### Documentation

| 기술 | 사용 계기 & 배운 점 |
|------|---------------------|
| **SpringDoc OpenAPI** | API 문서를 수동으로 관리하는 번거로움 해소. Annotation만으로 자동 문서화되는 편리함 경험 |
| **Swagger UI** | Postman 없이 브라우저에서 API를 바로 테스트할 수 있어 개발 속도 향상 |

### Testing

| 기술 | 사용 계기 & 배운 점 |
|------|---------------------|
| **JUnit 5** | Java 테스트의 표준. `@Test`, `@DisplayName`으로 가독성 높은 테스트 작성 |
| **Mockito** | 실제 API를 호출하지 않고 테스트하는 방법 모색 중 도입. Mock 객체의 개념과 중요성 이해 |
| **WireMock** | Lost Ark API 장애로 테스트가 불안정해지는 문제 해결. 외부 의존성 제거의 중요성 체감 |
| **JaCoCo** | "내 코드가 얼마나 테스트되었나?" 궁금해서 도입. 커버리지 수치로 테스트 품질 측정 |
| **AssertJ** | JUnit 기본 assert보다 가독성이 좋아 도입. 메서드 체이닝 기반의 직관적인 검증 |

### External API

| API | 사용 계기 & 배운 점 |
|-----|---------------------|
| **Lost Ark Open API** | 내가 하는 게임의 데이터를 활용하고 싶어 선택. **외부 API는 항상 불완전하다는 전제로 코드를 짜야 한다**는 것을 처음 체감. null 처리, 재시도, Rate Limit 등 고려사항 학습 |

---

## 아키텍처

### 전체 흐름

ListArk는 외부 API의 복잡한 Raw 데이터를  
프론트엔드에서 바로 사용할 수 있는 형태로 가공하는 구조입니다.
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
외부 API의 복잡한 Raw 데이터를  
프론트엔드에서 바로 사용할 수 있는 형태로 가공하는 구조입니다.

자세한 설계 의도와 계층별 책임은 아래 문서를 참고해주세요.
- [Architecture](docs/architecture.md)

---
## 기술적 챌린지 (요약)

ListArk는 단순 기능 구현보다  
**외부 API 불확실성을 어떻게 통제했는지**에 초점을 맞춘 프로젝트입니다.

- HTML이 섞인 Tooltip JSON을 동적으로 파싱해야 하는 문제
- 외부 API의 null 응답으로 인한 NPE 방어
- Reactive 환경에서의 API 재시도 전략 설계
- WireMock을 활용한 외부 API 의존성 제거 테스트

---

## 시작하기

### 필수 요구사항

- Java 17+
- Maven 3.8+
- Lost Ark Open API Key ([발급 방법](https://developer-lostark.game.onstove.com/))

### 설치 및 실행

1. **저장소 클론**
```bash
git clone https://github.com/youndong-jp/ListArk.git
cd ListArk
```

2. **API Key 설정**

`src/main/resources/application.yml` 파일 생성:
```yaml
lostark:
  api:
    key: YOUR_API_KEY_HERE
    base-url: https://developer-lostark.game.onstove.com
```

또는 환경 변수 사용:
```bash
export LOSTARK_API_KEY=your_api_key
```

3. **애플리케이션 실행**
```bash
mvn spring-boot:run
```

4. **API 문서 확인**
```
http://localhost:{port}/swagger-ui.html
```
---

## API 문서
Swagger UI: http://localhost:{port}/swagger-ui.html
(기본 포트는 8080이며, 환경에 따라 달라질 수 있습니다)
- 상세 엔드포인트 설명은 [API Details](docs/api-detail.md) 참고

---

## 테스트 (요약)

테스트는 “정상 흐름 보장”보다  
**실패 가능성이 높은 지점의 신뢰성 확보**에 집중했습니다.

### 테스트 실행
```bash
mvn test
```
- Mapper / Parser 중심 테스트
- WireMock 기반 외부 API Mock
- JaCoCo 커버리지 측정

자세한 테스트 전략은 ***[Testing Strategy](docs/testing.md)*** 에 정리되어 있습니다.

---

## 향후 계획

### 1. 프론트엔드 개발
- React 기반 간단한 UI
- 캐릭터 검색 화면
- 장비/아바타/각인 정보 시각화

### 2. Docker 기반 배포
- Dockerfile 작성
- 환경 변수 기반 설정 분리
- 로컬/서버 동일 실행 환경 구성

### 3. 기능 확장
- Notice API 추가 (공지, 이벤트)
- Auction API 추가 (경매장 정보)
- 기존 Armory 구조 재사용

### 4. 리팩토링
- Mapper/Parser 책임 정리
- 중복 코드 제거
- 테스트하기 어려운 코드 개선

### 5. 재배포 & 운영
- Docker 기반 재배포
- 지인 대상 실제 사용
- 운영 중 발생하는 이슈 개선

---

## 회고

### 잘한 점

1. **이론에서 멈추지 않고 끝까지 만들어봄**
    - 단순 CRUD가 아닌, 실제 서비스 API를 다루는 프로젝트 진행
    - 외부 API 연동 → 데이터 가공 → 응답까지 전체 흐름 경험

2. **복잡한 데이터 가공 문제를 직접 해결**
    - Lost Ark API의 복잡한 Tooltip 구조를 파싱
    - Element 타입 기반 파서 설계 경험
    - 11개 카테고리의 다양한 데이터 구조 처리

3. **테스트를 "나중에라도" 직접 작성해봄**
    - Mapper/Parser 테스트 작성 (98% 커버리지 달성)
    - WireMock을 통해 외부 API 의존성 제거 경험

### 아쉬운 점

1. **TDD 방식으로 시작하지 못함**
    - 설계 없이 "느낌적으로" 코딩해서 구조가 뒤죽박죽
    - 테스트를 나중에 붙이면서 테스트하기 어려운 코드도 발생
    - 다음 프로젝트에서는 설계 → 테스트 → 구현 순서로 진행

2. **Service/Controller 테스트 부족**
    - 핵심 로직 위주로 테스트하다 보니 전체 커버리지는 낮은 편
    - 통합 테스트 범위를 더 넓힐 필요

3. **문서화를 나중에 정리**
    - README, 설계 설명을 뒤늦게 작성
    - 초반부터 기록하면서 개발하는 습관 필요

### 배운 점

1. **외부 API 연동 시 null/장애/실패는 기본 전제**
    - NullSafe 유틸리티로 방어적 프로그래밍
    - Retry 로직으로 복원력 확보
    - WireMock으로 테스트 안정성 확보

2. **"완벽한 설계 후 구현"보다 점진적 개선이 현실적**
    - 일단 만들고 → 고치고 → 테스트 붙이는 방식도 유효
    - Profile → Equipment → Avatar 순으로 점진적 확장

3. **테스트는 커버리지 숫자보다 직접 겪어보는 경험이 중요**
    - Mock, WireMock, JaCoCo를 실제로 사용해보며 체득
    - 테스트 가능한 설계의 중요성 인지

### 다음 프로젝트에서 시도할 것

- **TDD 엄격히 준수**: 설계 → 테스트 → 구현
- **Redis 캐싱**: API 응답 캐싱으로 성능 개선
- **모니터링/로깅**: Prometheus, Grafana, ELK Stack
- **CI/CD**: GitHub Actions로 자동 배포 파이프라인

---

## 개발자

**Youndong JP**
- GitHub: [@youndong-jp](https://github.com/youndong-jp)

---

## 프로젝트 정보

본 프로젝트는 학습 및 포트폴리오 목적으로 제작되었습니다.

---

## 출처 및 감사

- **Smilegate RPG** - Lost Ark Open API 제공
- **로아와** - 레퍼런스 사이트

---
## 참고 문서

- 아키텍쳐 설계 : [Architecture](architecture.md)
- 데이터 흐름 : [Data Flow](data-flow.md)
- API 명세: [API Details](api-detail.md)
- 기술적 설계 의도: [Technical Challenges](tech-challenges.md)
- 테스트 전략 : [Testing Strategy](testing.md)
<div align="center">

Made  by [Youndong JP](https://github.com/youndong-jp)

</div>