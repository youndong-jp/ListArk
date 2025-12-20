# API Details

0. 문서 목적

이 문서는 ListArk가 제공하는 **외부 API 엔드포인트의 상세 명세** 를 설명합니다.

- 요청 URL / Method
- Request Parameter
- Response 구조
- 에러 응답 규칙

ListArk API는
Lost Ark Open API의 Raw 데이터를 
**프론트엔드 친화적인 Tidy 구조** 로 변환하여 제공합니다.

---
## 1. 공통 응답 구조

모든 API 응답은 `ApiResponse<T>` 형태로 반환됩니다.

### 성공 응답
```json
{
  "success": true,
  "data": {}
}
```
`data` 필드에는 각 api별 응답 데이터가 포함됩니다 
### 실패 응답
```json
{
"success": false,
"error": {
"code": "CHARACTER_NOT_FOUND",
"message": "존재하지 않는 캐릭터입니다.",
"status": 404
}
}
```
### 응답 필드 설명 
| 필드            | 설명          |
| ------------- | ----------- |
| success       | 요청 성공 여부    |
| data          | 성공 시 응답 데이터 |
| error         | 실패 시 에러 정보  |
| error.code    | 도메인 에러 코드   |
| error.message | 에러 메시지      |
| error.status  | HTTP 상태 코드  |
 ---
## 2. 캐릭터 전체 정보 조회 (Tidy)

캐릭터의 Armory 정보를 **프론트엔드 친화적인 구조(Tidy)** 로 반환합니다.

### Endpoint
GET /api/characters/{characterName}/armory

### Path Parameters

| 이름 | 타입 | 설명 |
|-----|------|------|
| characterName | String | 캐릭터 이름 |

### 성공 응답 예시
```json
{
  "success": true,
  "data": {
    "profile": { },
    "equipment": [ ],
    "avatar": [ ],
    "engraving": { },
    "gem": { },
    "card": { },
    "skill": [ ],
    "arkGrid": { },
    "arkPassive": { },
    "colosseum": { },
    "collectible": { }
  }
}
```
### 포함 데이터 요약
| 항목          | 설명        |
| ----------- | --------- |
| profile     | 기본 캐릭터 정보 |
| equipment   | 장비 정보     |
| avatar      | 아바타 정보    |
| engraving   | 각인 정보     |
| gem         | 보석 정보     |
| card        | 카드 정보     |
| skill       | 전투 스킬     |
| arkGrid     | 아크 그리드    |
| arkPassive  | 아크 패시브    |
| colosseum   | PvP 정보    |
| collectible | 수집형 콘텐츠   |

---
## 3. 캐릭터 전체 정보 조회 (Raw)

Lost Ark Open API의 Raw JSON 구조를 그대로 반환합니다.  
디버깅 및 데이터 구조 확인 용도로 제공됩니다.

### Endpoint
GET /api/raw/characters/{characterName}/armory

### 특징
- Lost Ark Open API 응답과 거의 동일
- Tidy 변환 없음
- 프론트엔드 직접 사용은 권장하지 않음
  캐릭터 기본 정보 조회
  md
  코드 복사
## 4. 원정대 정보 조회

캐릭터의 기본 정보 및 동일 서버 캐릭터 목록을 조회합니다.

### Endpoint
GET /api/characters/{characterName}

### 성공 응답 예시
```json
{
  "success": true,
  "data": {
    "characterName": "홀리나이트",
    "serverName": "카단",
    "className": "홀리나이트",
    "siblings": [
      {
        "characterName": "서브캐릭",
        "className": "버서커"
      }
    ]
  }
}
```
---

## 5. 공지사항 조회

Lost Ark 공식 공지사항을 조회합니다.

### Endpoint
GET /api/notices

### 성공 응답 예시
```json
{
  "success": true,
  "data": [
    {
      "title": "12월 업데이트 안내",
      "type": "공지",
      "date": "2024-12-01",
      "link": "https://..."
    }
  ]
}
```
---

## 6. 헬스 체크

서버 상태 확인용 API입니다.

### Endpoint
GET /api/ping

### 응답 예시
```json
{
  "success": true,
  "data": {
    "status": "UP"
  }
}
```
---

## 7. 에러 응답 규칙

외부 API 에러 및 비즈니스 예외는 모두 도메인 예외로 변환됩니다.

### 주요 에러 코드

| HTTP | Code | 설명 |
|-----|------|------|
| 401 | API_KEY_EXPIRED | API Key 인증 실패 |
| 404 | CHARACTER_NOT_FOUND | 캐릭터 없음 |
| 429 | TOO_MANY_REQUESTS | 요청 제한 |
| 500 | INTERNAL_SERVER_ERROR | 서버 내부 오류 |
| 503 | EXTERNAL_API_ERROR | 외부 API 장애 |

모든 에러는 `GlobalExceptionHandler`에서 처리됩니다.
---
## 8. 참고 문서

- Swagger UI: `http://localhost:{port}/swagger-ui.html`
    - 기본 포트는 `8080`이며, 환경에 따라 달라질 수 있습니다.
- 아키텍쳐 설계 : [Architecture](architecture.md)
- 데이터 흐름 : [Data Flow](data-flow.md)
- 기술적 설계 의도: [Technical Challenges](tech-challenges.md)
- 테스트 전략 : [Testing Strategy](testing.md)