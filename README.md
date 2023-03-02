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
