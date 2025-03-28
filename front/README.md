## 📌 프로젝트 개요

"아무 말 대잔치" 커뮤니티 프론트엔드 구현 입니다.

### 🔗 백엔드 레포지토리 링크

[BackEnd](https://github.com/100-hours-a-week/2-logan-jo-comunity/tree/main/back)

### 🛠️ 기술 스택

<img src="https://img.shields.io/badge/HTML5-red?style=for-the-badge&logo=기술스택아이콘&logoColor=white">
<img src="https://img.shields.io/badge/CSS-blue?style=for-the-badge&logo=기술스택아이콘&logoColor=white">
<img src="https://img.shields.io/badge/Vanila JS-yellow?style=for-the-badge&logo=기술스택아이콘&logoColor=white">

## 📌 폴더 구조

```
📦 ADVANCE
├── 📁 api
│   ├── apiRequest.js
│   ├── commentApi.js
│   ├── postApi.js
│   └── userApi.js
├── 📁 components
│   └── Header.js
├── 📁 javascript
│   ├── editPassword.js
│   ├── editPost.js
│   ├── login.js
│   ├── makePost.js
│   ├── post.js
│   ├── posts.js
│   ├── signup.js
│   └── user.js
├── 📁 page
│   ├── editPassword.html
│   ├── editPost.html
│   ├── login.html
│   ├── makePost.html
│   ├── post.html
│   ├── posts.html
│   ├── signup.html
│   └── user.html
├── 📁 public
├── 📁 styles
├── 📁 util
│   ├── checkAuth.js
│   ├── cookie.js
│   ├── date.js
│   ├── likes.js
│   ├── load.js
│   ├── navigate.js
│   └── validate.js
├── app.js
├── index.html
└── README.md
```

## 📌 구현 기능

| 화면 및 컴포넌트      | 파일명            | 설명                                                                        |
| --------------------- | ----------------- | --------------------------------------------------------------------------- |
| 헤더 컴포넌트         | Header.js         | 모든 화면에 들어갈 헤더 컴포넌트, userApi의 `getUser`를 통해 유저 로고 적용 |
| 로그인 화면           | login.html        |                                                                             |
| 회원가입 화면         | signup.html       |                                                                             |
| 회원정보 수정         | user.html         |                                                                             |
| 비밀번호 수정         | editPassword.html |                                                                             |
| 게시글 목록 조회 화면 | posts.html        |                                                                             |
| 단일 게시글 화면      | post.html         |                                                                             |
| 게시글 생성 화면      | makePost.html     |                                                                             |
| 게시글 수정 화면      | editPost.html     |                                                                             |
| 로그인 함수           | login.js          | userApi의 `login`를 가져와서 로그인 적용                                    |
| 회원가입 함수         | signup.js         | userApi의 `signup`을 가져와 회원가입 적용                                   |
| 회원정보 수정 함수    | user.js           | userApi의 `updateUser`를 가져와 유저 수정 적용                              |
| 비밀번호 수정 함수    | editPassword.js   | userApi의 `updatePassword`를 가져와 비밀번호 수정 적용                      |
| 게시글 목록 조회 함수 | posts.js          | postApi의 `getPostList` 를 가져와 전체 post 렌더링 진행                     |
| 단일 게시글 조회 함수 | post.js           | postApi의 `getPost`와 commentApi의 서비스를 가져와 댓글 기능 추가           |
| 게시글 생성 함수      | makePost.js       | postApi의 `createPost`를 가져와 게시글 생성                                 |
| 게시글 수정           | editPost.js       | postApi의 `updatePost`를 가져와 게시글 수정 진행                            |

## 📌 회고

### 1. 코드 분리 문제

post와 comment가 분리되어 있지 않아, 가독성이 떨어졌습니다. 서로의 의존도가 높아서 분리가 되지 않았고, 그로인해 댓글 추가, 삭제 시
특정 부분의 재로딩이 아닌 전체 재로딩이 진행되는게 아쉽습니다.

### 2. 토큰 연장 문제

기존 의도는 accessToken의 유효기간이 만료되고, refreshToken이 존재하면 토크 연장 및 재발급을 시도하고, 실패하면 `/login`으로 리다이랙션 되는 것을 목표로 했지만, 토큰의 유효성 검사만 성공하고, 재발급되지 않고 있는 문제가 있습니다. 백엔드를 함께 확인하며 수정할 필요가
있습니다.

### 3. 라이프사이클 적용

react의 `useEffect`의 라이프사이클을 적용해서 `unmount`발생시 정리함수가 실행되도록 개선해 메모리 효율성을 높일 필요성이 있습니다.
