# Spring Boot Scheduler Develop

본 프로젝트는 Spring Boot와 JPA를 사용하여 구현한 일정 관리 서버이다. 일정 CRUD에 유저 CRUD, 댓글 CRUD 등 기능을 추가하였다.
3 Layer Architecture를 기반으로 설계되었으며, Cookie/Session을 활용해 인증/인가를 적용했다.

## 개발 환경
- **Language**: Java
- **Framework**: Spring Boot
- **Database**: MySQL
- **ORM**: Spring Data JPA
- **IDE**: IntelliJ

<br>

**Base URL**: http://localhost:8080

| 기능    | Method | URL                                          |
| :--- | :--- | :--- |
| 회원가입  | POST   | /users/signup                                |
| 유저 조회 | GET    | /users/{userId}                              |
| 유저 수정 | PATCH  | /users/{userId}                              |
| 유저 삭제 | DELETE | /users/{userId}                              |
| 로그인   | POST   | /users/login                                 |
| 로그아웃  | POST   | /users/logout                                |
| 일정 생성 | POST   | /schedules                                   |
| 전체 조회 | GET    | /schedules                                   |
| 선택 조회 | GET    | /schedules/{scheduleId}                      |
| 일정 수정 | PATCH  | /schedules/{scheduleId}                      |
| 일정 삭제 | DELETE | /schedules/{scheduleId}                      |
| 댓글 생성 | POST   | /schedules/{scheduleId}/comments             |
| 댓글 조회 | GET    | /schedules/{scheduleId}/comments             |
| 댓글 수정 | PATCH  | /schedules/{scheduleId}/comments/{commentId} |
| 댓글 삭제 | DELETE | /schedules/{scheduleId}/comments/{commentId} |

<br>

### 1. 인증, 유저

#### 1-1. 회원가입 (유저 생성)
- Method: `POST`
- URL: `/users/signup`
- Content-Type: `application/json`
- Status Code: `201 Created`

#### Request Body
```json
{
    "name": "이름",
    "email": "이메일",
    "password": "비밀번호"
}
```

#### Response Body
```json
{
    "id": 1,
    "name": "이름",
    "email": "이메일",
    "createdAt": "0000-00-00 00:00:00",
    "modifiedAt": "0000-00-00 00:00:00"
}
```
<br>

#### 1-2. 유저 조회
- Method: `GET`
- URL: `/users/{userId}`
- Status Code: `200 OK`

#### Response Body
```json
{
    "id": 1,
    "name": "이름",
    "email": "이메일",
    "createdAt": "0000-00-00 00:00:00",
    "modifiedAt": "0000-00-00 00:00:00"
}
```
<br>

#### 1-3. 유저 수정
- Method: `PATCH`
- URL: `/users/{userId}`
- Content-Type: `application/json`
- Status Code: `200 OK`

#### Request Body
```json
{
    "name": "수정 이름",
    "email": "수정 이메일"
}
```

#### Response Body
```json
{
    "id": 1,
    "name": "수정 이름",
    "email": "수정 이메일",
    "createdAt": "0000-00-00 00:00:00",
    "modifiedAt": "0000-00-00 00:00:00"
}
```
<br>

#### 1-4. 유저 삭제
- Method: `DELETE`
- URL: `/users/{userId}`
- Status Code: `200 OK`
- 
#### Response Body
```
유저 이름이(가) 삭제되었습니다.
```
<br>

#### 1-5. 로그인
- Method: `POST`
- URL: `/users/login`
- Content-Type: `application/json`
- Status Code: `200 OK`

#### Request Body
```json
{
    "email": "이메일",
    "password": "비밀번호"
}
```
<br>

#### 1-6. 로그아웃
- Method: `POST`
- URL: `/users/logout`
- Status Code: `200 OK`
<br>

### 2. 일정

#### 2-1. 일정 생성
- Method: `POST`
- URL: `/schedules`
- Content-Type: `application/json`
- Status Code: `201 Created`

#### Request Body
```json
{
  "title": "일정 제목",
  "content": "일정 내용"
}
```

#### Response Body
```json
{
    "id": 1,
    "title": "일정 제목",
    "content": "일정 내용",
    "name": "작성자 이름",
    "createdAt": "0000-00-00 00:00:00",
    "modifiedAt": "0000-00-00 00:00:00"
}
```
<br>

#### 2-2. 일정 전체 조회
- Method: `GET`
- URL: `/schedules`
- Query Parameters: `page`(기본값 0), `size`(기본값 10)
- Status Code: `200 OK`

#### Response Body
```json
{
  "content": [
    {
      "id": 1,
      "title": "일정 제목",
      "content": "일정 내용",
      "commentCount": 0,
      "name": "작성자 이름",
      "createdAt": "0000-00-00 00:00:00",
      "modifiedAt": "0000-00-00 00:00:00"
    },
    {
      "id": 2,
      "title": "일정 제목",
      "content": "일정 내용",
      "commentCount": 0,
      "name": "작성자 이름",
      "createdAt": "0000-00-00 00:00:00",
      "modifiedAt": "0000-00-00 00:00:00"
    }
  ],
  "pageNumber": 0,
  "pageSize": 10,
  "totalElements": 2,
  "totalPages": 1,
  "last": true
}
```
<br>

#### 2-3. 일정 선택 조회

- Method**: `GET`
- URL**: `/schedules/{scheduleId}`
- Path Parameters: `scheduleId`
- Status Code: `200 OK`

#### Response Body
```json
{
    "id": 1,
    "title": "일정 제목",
    "content": "일정 내용",
    "name": "작성자 이름",
    "createdAt": "0000-00-00 00:00:00",
    "modifiedAt": "0000-00-00 00:00:00",
    "comments": [
        {
            "id": 1,
            "content": "댓글 내용",
            "name": "댓글 작성자",
            "createdAt": "0000-00-00 00:00:00",
            "modifiedAt": "0000-00-00 00:00:00",
            "scheduleId": 1
        }
    ]
}
```
<br>

#### 2-4. 일정 수정
- Method: `PATCH`
- URL: `/schedules/{scheduleId}`
- Path Parameters: `scheduleId`
- Content-Type: `application/json`
- Status Code: `200 OK`

#### Request Body
```json
{
  "title": "수정할 제목"
}
```

#### Response Body
```json
{
    "id": 1,
    "title": "수정 제목",
    "content": "기존 내용",
    "name": "작성자 이름",
    "createdAt": "0000-00-00 00:00:00",
    "modifiedAt": "0000-00-00 00:00:00"
}
```
<br>

#### 2-5. 일정 삭제
- Method: `DELETE`
- URL: `/schedules/{scheduleId}`
- Path Parameter: `scheduleId`
- Status Code: `200 OK`

#### Response Body
```
할 일 제목이(가) 삭제되었습니다.
```
<br>

### 3. 댓글

#### 3-1. 댓글 생성
- Method: `POST`
- URL: `/schedules/{scheduleId}/comments`
- Content-Type: `application/json`
- Status Code: `201 Created`

#### Request Body
```json
{
  "content": "댓글 내용"
}
```

#### Response Body
```json
{
    "id": 1,
    "name": "작성자 이름",
    "content": "댓글 내용",
    "scheduleId": 1,
    "createdAt": "0000-00-00 00:00:00",
    "modifiedAt": "0000-00-00 00:00:00"
}
```
<br>

#### 3-2. 댓글 조회
- Method: `GET`
- URL: `/schedules/{scheduleId}/comments`
- Path Parameter: `scheduleId`
- Status Code: `200 OK`

#### Response Body
```json
[
    {
      "id": 1,
      "name": "작성자 이름",
      "content": "댓글 내용",
      "scheduleId": 1,
      "createdAt": "0000-00-00 00:00:00",
      "modifiedAt": "0000-00-00 00:00:00"
    },
    {
      "id": 2,
      "name": "작성자 이름",
      "content": "댓글 내용",
      "scheduleId": 1,
      "createdAt": "0000-00-00 00:00:00",
      "modifiedAt": "0000-00-00 00:00:00"
    }
  ]
```
<br>

#### 3-3. 댓글 수정
- Method: `PATCH`
- URL: `/schedules/{scheduleId}/comments/{commentId}`
- Path Parameters: `scheduleId`, `commentId`
- Content-Type: `application/json`
- Status Code: `200 OK`

#### Request Body
```json
{
  "content": "수정 내용"
}
```

#### Response Body
```json
{
    "id": 1,
    "content": "수정 내용",
    "name": "작성자 이름",
    "scheduleId": 1,
    "createdAt": "0000-00-00 00:00:00",
    "modifiedAt": "0000-00-00 00:00:00"
}
```
<br>

#### 3-4. 댓글 삭제
- Method: `DELETE`
- URL: `/schedules/{scheduleId}/comments/{commentId}`
- Path Parameter: `scheduleId`, `commentId`
- Status Code: `200 OK`

#### Response Body
```
댓글이(가) 삭제되었습니다.
```
<br><br>

## Scheduler ERD
<img width="745" height="484" alt="Image" src="https://github.com/user-attachments/assets/2e11d00b-b14f-4880-8cb9-53c44c17c1e4" />