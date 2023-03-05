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
sudo chown -R mogreene:staff {소유권을 변경하고 싶은 디렉토리명}

<br/>
<br/>
<br/>
<details>
<summary><h3>피드백</h3></summary>
SpringBoot를 완성시키는걸 위주로!!

Vue는 차차 공부하자

메서드 이름 수정!! 생각하자 클린코드

dto 주석까지 확인하기

메소드 자체에 시그니처를 넣을때 주의해서 넣자

간단한(ex: 파일존재유무, 간단한 포맷팅?) 처리는 서브쿼리로 처리해서

포맷팅 정도는 화면에서 구분해도됨 이건 비즈니스로직도아님

ex) 관리자일 경우, 일본일 경우, 중국일 경우~

@ModelAttribute file도 받을수있나보다

수정할때 파일 삭제도 확인해서 만들어보자

댓글있을때 파일삭제 안되게

vue.js promise?? await

cascade 아예 사용안함

리스트릭트를 걸어보자

value 어노테이션 사용

validation 따로 빼서 사용해보자

vue 프론트는 어떤 이벤트에 어떤 라이프사이클을 구현했는지를 공부하는것

blob 파일전송
</details>
