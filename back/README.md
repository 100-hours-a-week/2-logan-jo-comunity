## 📌 프로젝트 개요

"아무 말 대잔치" 커뮤니티 백엔드 구현 입니다.

### 🔗 프론트엔드 레포지토리 링크

[FrontEnd](https://github.com/100-hours-a-week/2-logan-jo-week-coumunity)

### 🛠️ 기술 스택

<img src="https://img.shields.io/badge/Spring Boot-green?style=for-the-badge&logo=기술스택아이콘&logoColor=white">

## 📌 폴더 구조

```
📦back
 ┣ 📂config
 ┃ ┣ 📂jwt
 ┃ ┃ ┣ 📜JwtAuthenticationFilter.java
 ┃ ┃ ┗ 📜JwtUtil.java
 ┃ ┣ 📜SecurityConfig.java
 ┃ ┣ 📜SwaggerConfig.java
 ┃ ┗ 📜WebConfig.java
 ┣ 📂controller
 ┃ ┣ 📜CommentController.java
 ┃ ┣ 📜PostController.java
 ┃ ┣ 📜RootController.java
 ┃ ┣ 📜TokenController.java
 ┃ ┗ 📜UserController.java
 ┣ 📂dto
 ┃ ┣ 📂Comment
 ┃ ┃ ┣ 📜CommentCreateRequest.java
 ┃ ┃ ┣ 📜CommentDto.java
 ┃ ┃ ┗ 📜CommentUserInfo.java
 ┃ ┣ 📂Post
 ┃ ┃ ┣ 📜PostCreateRequest.java
 ┃ ┃ ┗ 📜PostDto.java
 ┃ ┣ 📂Token
 ┃ ┃ ┣ 📜TokenDto.java
 ┃ ┃ ┗ 📜TokenResponse.java
 ┃ ┣ 📂User
 ┃ ┃ ┣ 📜UserCreateRequest.java
 ┃ ┃ ┣ 📜UserDto.java
 ┃ ┃ ┣ 📜UserLoginRequest.java
 ┃ ┃ ┣ 📜UserPasswordUpdateRequest.java
 ┃ ┃ ┗ 📜UserUpdateRequest.java
 ┃ ┗ 📜Response.java
 ┣ 📂entity
 ┃ ┣ 📜Comment.java
 ┃ ┣ 📜Post.java
 ┃ ┣ 📜RefreshToken.java
 ┃ ┗ 📜User.java
 ┣ 📂exception
 ┃ ┣ 📂code
 ┃ ┃ ┣ 📜ConflictException.java
 ┃ ┃ ┣ 📜ForbiddenException.java
 ┃ ┃ ┣ 📜NotFoundException.java
 ┃ ┃ ┗ 📜UnauthorizedException.java
 ┃ ┣ 📜ErrorResponse.java
 ┃ ┗ 📜GlobalExceptionHandler.java
 ┣ 📂repository
 ┃ ┣ 📜CommentRepository.java
 ┃ ┣ 📜PostRepository.java
 ┃ ┣ 📜RefreshTokenRepository.java
 ┃ ┗ 📜UserRepository.java
 ┣ 📂service
 ┃ ┣ 📜CommentService.java
 ┃ ┣ 📜ImageUploadService.java
 ┃ ┣ 📜PostService.java
 ┃ ┣ 📜TokenService.java
 ┃ ┗ 📜UserService.java
 ┣ 📂util
 ┃ ┣ 📂message
 ┃ ┃ ┣ 📜ErrorMessage.java
 ┃ ┃ ┗ 📜SuccessMessage.java
 ┃ ┗ 📜ApiResponse.java
 ┗ 📜BackApplication.java
```

## 📌 구현 기능

| 기능 및 동작     | 파일명                         | 설명                                               |
|-------------|-----------------------------|--------------------------------------------------|
| 웹 기본 설정     | WebConfig.java              | CORS설정, 쿠키 설정 등 웹 설정                             |
| API 문서화     | SwaggerConfig.java          | Swagger를 사용해 API 문서화                             |
| 보안 검사       | SecurityConfig.java         | 웹 보안 검사                                          | 
| 유저 인증인가 처리  | jwtAuthentication.java      | `JwtUtil` 을 사용해 유저가 Authorization 토큰을 가지고 있는지 검사 |
| 전역 에러처리 로직  | GlobalExceptionHandler.java | 컨트롤러에서 발생하는 전역에러 처리                              |
| 최상위 컨트롤러    | RootController.java         | 최상위 컨트롤러로, 서버가 정상적으로 작동하는지 확인하는 엔드포인트            |
| 유저 컨트롤러     | UserController.java         | 유저 관련 API 엔드포인트를 관리하는 컨트롤러                       |
| 토큰 컨트롤러     | TokenController.java        | 토큰 관련 API 엔드포인트를 관리하는 컨트롤러                       |
| 게시글 컨트롤러    | PostController.java         | 게시글 관련 API 엔드포인트를 관리하는 컨트롤러                      |
| 댓글 컨트롤러     | CommentController.java      | 댓글 관련 API 엔드포인트를 관리하는 컨트롤러                       |
| 유저 서비스      | UserService.java            | 유저 관련 비즈니스 로직을 처리하는 서비스                          |
| 토큰 서비스      | TokenService.java           | 토큰 관련 비즈니스 로직을 처리하는 서비스                          |
| 게시글 서비스     | PostService.java            | 게시글 관련 비즈니스 로직을 처리하는 서비스                         |
| 이미지 업로드 서비스 | ImageUploadService.java     | `ImageBB`를 사용한 이미지 업로드 관련 비즈니스 로직을 처리하는 서비스      |
| 댓글 서비스      | CommentService.java         | 댓글 관련 비즈니스 로직을 처리하는 서비스                          |
| DTO         | fileName + DTO.java         | 엔티티와 컨트롤러 사이의 데이터 전달을 위한 DTO 객체                  |
| 엔티티         | fileName.java               | 데이터베이스와 연동되는 엔티티 객체                              |
| 레포지토리       | fileNameRepository.java     | 데이터베이스와 연동되는 레포지토리 객체                            |

## 📌 회고

### 1. refreshToken의 재발급 로직 문제

백엔드에서 body로 accessToken을 발급받고 재발급을 테스트했을때는 정상적으로 동작했지만, 클라이언트로 httpOnly속성의 쿠리를 전달하고, 전달받아서
재발급 하도록 로직을 변경 한 이후, 재발급되지 않는 문제가 존재했습니다.
추가적인 방법을 조사하던 중, httpOnly속성의 쿠키를 사용하는것 이외에 Redis를 사용해서 토큰을 저장하고, 일부 key값만 전달해서 토큰을 재발급
하는 방법도 있다는 것을 알게 되었다. 추후에는 Redis를 사용해서 토큰을 저장하고, 재발급하는 방법을 사용해야 할 것 같다.

### 2. 이미지 업로드 로직

현재는 imageBB를 이용해서 처리하지만, 추후 AWS로 배포가 될 경우, S3를 사용해서 배포되도록 개선이 필요하다. 현재는 iamgeBB를 사용해서 저장,
url 경로 반환도 가능하지만, 매우 느리게 동작하고, 용량이 큰 상태로 저장하면서 성능저하의 문제가 존재한다. S3를 사용해서 저장하는 방식으로 개선할 예정이다.

### 3. 테스트 코드 작성

구체적인 테스트코드가 존재하지 않습니다. JUnit을 통한 테스트 추가가 필요합니다. 추가적으로 Swagger의 문서에 작성된 오류와 예외에 대해
명확히 작성할 필요가 있습니다.
