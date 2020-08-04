# Sub PJT III - 모바일 웹 디자인 및 기본 구성

## :bar_chart: 진척률(%)
- 이슈 진행률 : 19%

## :confused: 어려웠던 점 & 느낀점
- (2020-08-03)
    - 파일 업로드 구현을 하고 있는데, 이미지를 어떤식으로 서버에 저장할지 고민해봐야겠다.

## :computer: Getting Started (개발환경 및 빌드 실행)

### Front-End
- 개발환경 설치
    - yarn 설치 : https://classic.yarnpkg.com/en/docs/install#windows-stable
    - Vue-cli 설치 : `yarn global add @vue/cli`
    - Sass 설정 : `yarn add node-sass sass-loader`
    - Vue-router, Vuex 설치 : `yarn add vue-router vuex`
- 서버 실행 : `yarn serve --port 3030`
- URL : localhost:3030

### Back-End
- 개발환경 설치
    - Spring Boot 설치 (jdk 다운로드 & 환경변수 설정)
    - Maven 설치 (Maven 다운로드 & 환경변수 설정)
- 서버 실행 : WebCurationApplication.java --> Run
- URL : localhost:8081
- Swagger URL : localhost:8081/swagger-ui.html

### DB (MariaDB)
- AWS 서버에서 Docker에 MariaDB를 올려 사용.
- mariadb 인코딩 (맨 처음에 한 번만 설정)
    - 참고 : https://app-developer.tistory.com/121
- mariadb time zone을 ASIA/SEOUL로 변경  (맨 처음에 한 번만 설정) (데이터베이스들의 시간을 한국시간으로 맞추기 위해서)
    - 검색

### AWS & Docker
- AWS 서버 접속
    - `ssh -i [pem key] ubuntu@[URL]`
- Docker에서 구동 중인 Container 확인
    - `docker container ps`
- Dcoker의 모든 Container 확인
    - `docker container ps -a`
- mariadb 컨테이터 안으로 들어가기
    - `docker exec -it maria-db bash`
- mariadb 접속
    - `mariadb -u root -p`
- Container 안으로 들어가지 않고 바로 mariadb 접속
    - `docker exec -it maria-db mysql -u root -p`
- Container가 꺼졌을 때 키는법
    - `docker start [Container 이름]`
    - ex) `docker start maria-db`