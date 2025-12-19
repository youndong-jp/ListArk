# ListArk Data Flow (Raw → Tidy)

이 문서는 ListArk API 요청이 처리되는 **실제 실행 흐름**을 설명합니다.

## 1. 정상 데이터 흐름

```
HTTP Request
GET /api/characters/{name}/armory
↓
ArmoryTidyController
- 요청 수신
- Service 호출
↓
ArmoryTidyService
- Client를 통해 Raw DTO 조회
↓
ArmoryClient
- WebClient로 Lost Ark API 호출
- Retry 적용
↓
Lost Ark API
- Raw JSON 응답
↓
ArmoryTidyService
- 11개 Mapper 순차 호출
↓
Mapper
- Raw DTO → Tidy DTO 변환
- NullSafe 적용
- 필요 시 Parser 호출
↓
Parser
- Tooltip JSON 파싱
- HTML 태그 제거
- 문자열 정제
↓
ArmoryTidyService
- 모든 결과 조합
- ArmoryTidyDto 생성
↓
Controller
- ApiResponse로 래핑
↓
HTTP Response
200 OK
```
## 2. 에러 흐름 – API 일시 장애 (Retry)
```
HTTP Request
GET /api/characters/{name}/armory
↓
ArmoryTidyController
- 요청 수신
- Service 호출
↓
ArmoryTidyService
- Client를 통해 Raw DTO 조회
↓
ArmoryClient
- WebClient로 Lost Ark API 호출
- Retry 적용
↓
[5] Lost Ark API
- 503 Service Unavailable

[6] RetryUtils
- 1차 재시도 (Backoff)

[7] Lost Ark API
- 503 Service Unavailable

[8] RetryUtils
- 2차 재시도

[9] Lost Ark API
- 200 OK
```
## 3. 핵심 포인트 정리

- Raw → Tidy 변환은 **Service 중심**
- Mapper / Parser는 **조합 대상**
- 외부 API 장애는 **전제 조건**
- 모든 응답은 **일관된 구조**
