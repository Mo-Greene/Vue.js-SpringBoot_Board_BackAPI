## ✨ SpringBoot Board BackEndAPI

### 💖 프로젝트 개요
- 사용자의 게시글을 생성, 조회, 수정, 삭제 할 수 있는 게시판 프로젝트

-----------
### 🔧 기술 스택
#### Programming
- Java 11
- Spring Boot 2.6.4

#### DataBase
- MariaDB
- MyBatis

#### API Development and Testing
- Postman

#### IDE
- IntelliJ IDEA Ultimate Edition


-----------
### 💻 프로젝트 설명
- 서버의 역할만을 위한 게시판 BackEnd API
- 클린코드를 지향한 코드작성
- RESTful API 규약에 맞춘 게시판 CRUD 구현
- 사용자 댓글 구현
- 에러핸들링 및 예외처리
- 일관된 응답 형식에 따른 API 응답 구현(ApiResponseDTO)
- 다중 파일 업로드, 파일 다운로드 구현


-----------
### 🏗 ERD
![Untitled](https://user-images.githubusercontent.com/97177357/227842389-eb6e06d1-f6bf-4400-9c26-ecedf5a28ae1.png)


-----------
### 💭 API Collections
- [Postman API](https://documenter.getpostman.com/view/21420226/2s93RNxuip)
- [JavaDoc](/docs/allpackages-index.html)

<details>
<summary>Vue.js 프로젝트 생성 에러 기록</summary>

Vue 설정 오류 해결
- https://araikuma.tistory.com/117

Vue 파일을 생성하면 리눅스상 소유권자가 root로 되어있음
<br/>
backend 파일들은 소유권자 내 이름으로 되어있음
<br/>
이렇게 될 경우 파일들의 소유권이 달라 read-only 파일로써 읽기전용으로만 가능
<br/>
심지어 해제 불가능 (오류 발생)
<br/>
frontend 디렉토리 하위 소유권자를 내이름으로 전부 바꿈
해결

<br/>
sudo chown -R {소유권자}:{그룹식별자} {소유권을 변경하고 싶은 디렉토리명}

<br/>
예시:
sudo chown -R mogreene:staff {소유권을 변경하고 싶은 디렉토리명}

</details>

<br/>

<details>
<summary> 

## Octet-stream 에러 
</summary>

### 에러

```
@GetMapping("/files/download/{fileNo}")
public ResponseEntity<ApiResponseDTO<Resource>> fileDown(@PathVariable("fileNo") Long fileNo) throws IOException {

    FileDTO fileDTO = fileService.downloadFile(fileNo);

    return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream"))
            .header(HttpHeaders.CONTENT_DISPOSITION, fileDTO.getContentDisposition())
            .body(ApiResponseDTO.<Resource>builder()
                    .httpStatus(HttpStatus.OK)
                    .resultCode(HttpStatus.OK.value())
                    .resultData(fileDTO.getResource())
                    .build());
}
```
Vue.js를 통해 파일을 다운로드 받으려고 하니 계속해서 
```
No converter for [class com.mogreene.board.common.api.ApiResponseDTO] with preset Content-Type 'application/octet-stream']
```
위의 WARN 이 표시가 되며 클라이언트 콘솔창엔 cors header 에러가 발생했다.

정확하게 프론트와 백 어느부분의 에러인지 알지 못하여 cors Config를 건드렸지만

역시나 CORS는 문제가 없었다.
```

//CORS 설정 모든 경로,메서드, 헤더를 허용해주고 있다.
@Override
public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
            .allowedOrigins("http://localhost:3000")
            .allowedMethods("*")
            .allowedHeaders("*")
            .maxAge(3600);
}
```
그러다 postman을 통해서 경로를 들어갔을때 다운을 받지 못하는 상황을 발견했고

에러의 원인이 서버에 있다는 것을 알게되었다.

가장 의심이 되는것은 'ApiResponseDTO' 내가 만든 공통 responseDTO다.

에러로그로 잡히기도 하고 그 전에 공통 api를 작성하기 전에는 문제없이 파일이 다운로드가 되었기 때문이다.


### 해결
```
@GetMapping("/files/download/{fileNo}")
public ResponseEntity<Resource> fileDown(@PathVariable("fileNo") Long fileNo) throws IOException {

    FileDTO fileDTO = fileService.downloadFile(fileNo);

    return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType("application/octet-stream"))
            .header(HttpHeaders.CONTENT_DISPOSITION, fileDTO.getContentDisposition())
            .body(fileDTO.getResource());
}
```
공통으로 사용하고 있는 ApiResponseDTO는 HTTP 응답 '본문'에 포함될 수 있는 데이터 객체이다.

하지만 위 클래스를 바이너리 파일 형식인 'application/octet-stream'의 HTTP 응답 본문에 직렬화 할 수 없기 때문에 발생한 오류였다.

이를 해결 하기 위해 ApiResponseDTO 객체를 집어넣지 않고 Resource 그 자체를 반환하도록 하여 에러를 해결하였다.

</details>

<br/>
<details>
<summary>피드백</summary>

리스트릭트를 걸어보자

value 어노테이션 사용

validation 따로 빼서 사용해보자

vue 프론트는 어떤 이벤트에 어떤 라이프사이클을 구현했는지를 공부하는것

blob 파일전송
</details>


자바 api javadoc 뽑아서 정리
포스트맨 document 정리
