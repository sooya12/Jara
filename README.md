# Sub PJT 3 [2020.08.03 ~ 2020.08.21]  

# :turtle: JARA

![image-20200731120507616](image-20200731120507616.png)

자취 라이프

자취하는 사람들을 위한 SNS



## :computer: Getting started ![badge](https://img.shields.io/badge/SUB2PJT-OK-brightgreen)

![badge](https://img.shields.io/badge/platform-web-lightgrey)

![badge](https://img.shields.io/badge/browser-chrome-red)![badge](https://img.shields.io/badge/flatform-vue-green)![badge](https://img.shields.io/badge/library-spring-pink)![badge](https://img.shields.io/badge/DB-MySQL-skyblue)![badge](https://img.shields.io/badge/language-Java JavaScript-yellow)

![badge](https://img.shields.io/badge/node-12.18.2-brightgreen)![badge](https://img.shields.io/badge/npm-6.14.5-brightgreen)![badge](https://img.shields.io/badge/yarn-1.22.4-blue)![badge](https://img.shields.io/badge/@vue/cli-4.4.6-green)![badge](https://img.shields.io/badge/java-14.0.1-orange)![badge](https://img.shields.io/badge/mvn-3.6.3-blueviolet)![badge](https://img.shields.io/badge/MySQL-8.0.21-blue)



## :floppy_disk: Install

```bash
$ yarn add vuex
```

```bash
$ vue add vue-router vuetify vue-cookies
```



### :cloud: i3a308.p.ssafy.io



#### Frontend

```bash
$ yarn serve --port 3030
```

  또는

```bash
$ npm run serve --port 3030
```



#### Backend

```bash
$ mvn spring-boot : run
```



## :information_desk_person: JARA Service

![image-20200731120614842](image-20200731120614842.png)

#### [Accounts : 회원 기능]

- Post
  - 이메일과 닉네임 중복 체크하여 회원가입
  - 이메일과 비밀번호로 로그인
  - 팔로우 요청 보내기

- Get
  - 회원 정보 전체 조회
  - 회원 정보 조회
  - 비밀번호 변경 전, 이메일 인증 (이메일 유효성 조회)
  - JWT 토큰에 해당하는 회원 정보 조회
- Put
  - 비밀번호 변경
  - 회원 정보 수정
  - 팔로우 요청 승인
- Delete
  - 언팔로우

-----

#### [Articles : 게시글(피드) 공유 기능]

- Post
  - 게시글 등록
  - 게시글 좋아요 등록
  - 게시글 댓글 등록
- Get
  - 사용자가 작성한 게시글 + 팔로우한 사용자가 작성한 게시글의 댓글, 좋아요 사용자 조회
  - 게시글과 게시글의 댓글, 좋아요 사용자 조회
- Put
  - 게시글 제목 / 내용 수정
  - 게시글 공유 수 수정
  - 게시글 댓글 내용 수정
  - 게시글 댓글 숨김/보임 수정
- Delete
  - 게시글과 게시글의 댓글, 좋아요 모두 삭제
  - 게시글 좋아요 삭제
  - 게시글 댓글 삭제

----

#### [Tips : 생활 팁 공유 기능]

- Post
  - 팁 등록
  - 현재 팁을 좋아요 하지 않으면 좋아요 등록 / 팁을 좋아요 하고 있으면 좋아요 삭제
- Get
  - 팁 전체 조회
  - 각 태그(요리/세탁/청소/보관) 에 해당하는 팁 전체 조회
  - 검색어에 해당하는 팁 전체 조회
  - 검색어와 태그에 해당하는 팁 전체 조회
  - 팁 조회
  - 사용자의 팁 좋아요 여부 확인
- Put
  - 팁 제목/내용 수정
  - 팁 조회수 수정
- Delete
  - 팁과 팁의 댓글, 좋아요 삭제

-----

> 서비스 준비중

#### [Barters : 물물교환 기능]

- Post
  - 물물교환 등록
- Get
  - 물물교환 전체 조회
  - 각 태그(구해요/사요/팔아요/나눠요)에 해당하는 물물교환 전체 조회
  - 검색어에 해당하는 물물교환 전체 조회
  - 물물교환 조회
- Put
  - 물물교환 (제목/내용/가격/상태) 수정
  - 물물교환 조회수 증가
- Delete
  - 물물교환 삭제

-----

> 서비스 준비중

#### [Eithers : 투표 기능]

- Post
  - 투표 등록
- Get
  - 투표 전체 조회
  - 투표 조회
- Delete
  - 투표 삭제



## Branch

hotfix : 간단한 오탈자 수정의 경우에 생성하여 머지 후 삭제

develop : 개발동안 default가 될 브랜치

feature : 기능별 브랜치, develop에 머지 후 삭제



## Commit Message

```bash
$ git commit -m "Jira 이슈 번호 | header | 설명"
```

header

> initial : 가장 처음 만든 코드
>
> fix : 비정상 동작 수정 코드
>
> update : 정상적으로 동작하면서 수정/추가/보완된 코드

또는

```b
$ git commit -m "| header | 설명"
```



### :100: 명세 요건 진척률 : %



### :sweat_smile: 어려웠던 점​

:ballot_box_with_check: 




## :computer: 프로젝트 배포

#### Backend 배포 - Spring (STS)

- AWS에 NGINX 설치 (이미 설치된 상태라면 생략)
```
sudo apt-get update				// 설치된 패키지들의 새로운 버전이 있는지 확인
sudo apt-get upgrade			// apt-get udpate를 통해 최신 버전이 확인된 패키지들의 버전을 업그레이드
sudo apt-get install nginx
```

- AWS에서 NGINX 환경설정 (이미 설정한 상태라면 생략)
```
// conf 파일 설정
cd /etc/nginx/sites-available
sudo vi default
```
```
// default 파일

server {
        listen 80 default_server;
        listen [::]:80 default_server;

        # Frontend 설정
        root /var/www/html/dist;        # Front 빌드 파일 위치

        index index.html index.htm;		# index 파일명

        server_name _;					# 서버 도메인

        location / {
                try_files $uri $uri/ /index.html;
        }


        # Backend Proxy 설정
        location /api {
                proxy_pass http://localhost:8399/api/;
                proxy_redirect off;
                charset utf-8;

                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header X-Forwarded-Proto $scheme;
                proxy_set_header X-NginX-Proxy true;
        }
}
```

- AWS에서 환경 설정 후 NGINX 시작 (이미 실행해둔 상태라면 생략)
```
sudo service nginx start
```
또는
```
sudo systemctl start nginx
```

- 로컬에서 `pom.xml`에 플러그인 추가 (이미 추가한 상태라면 생략)
```
<build>
	<plugins>
		<plugin>
			<groupId>org.apache.maven.plugins</groupId>
			<artifactId>maven-assembly-plugin</artifactId>
			<version>2.2.1</version>
			<configuration>
				<descriptorRefs>
					<descriptorRef>jar-with-dependencies</descriptorRef>
				</descriptorRefs>
			</configuration>
		</plugin>
	</plugins>
</build>
```

- 로컬에서 빌드
  - `프로젝트 오른쪽 버튼 클릭` → `Run as` → `Maven Build` → `Goals: assembly:assembly`

- 로컬에서 .jar 파일 확인
  - `s03p12a308\Project\backend\target` 폴더에 `JARA-0.0.1-SNAPSHOT.jar` 파일이 생성됐는지 확인

- 로컬에 있는 .jar 파일을 push
  - 빌드 파일이 .gitignore에 등록돼있기 때문에 `git add`를 할 때,  `git add -f [파일명]` 으로 target 폴더를 강제로 추가한 후에 커밋과 푸시를 해줘야함.

- AWS에서 git clone 또는 git pull
  - 경로 : /jara/s03p12a308
```
// clone
sudo git clone https://lab.ssafy.com/s03-webmobile2-sub2/s03p12a308.git
// pull
sudo git pull origin develop
```

- AWS에서 .jar 파일이 있는 디렉토리로 이동
    - `/JARA/s03p12a308/Project/backend/target`

- AWS에서 .jar 파일 실행
```
// 이렇게 배포하면 터미널을 종료했을 때 프로그램 역시 종료된다.
sudo java -jar JARA-0.0.1-SNAPSHOT.jar
```
```
// 이렇게 nohup 커맨드를 사용하면 ssh 접속이 끊긴 이후에도 백그라운드에서 jar가 계속 돌아간다.
sudo nohup java -jar JARA-0.0.1-SNAPSHOT.jar &
```

- AWS에 jdk (1.8) 설치 (.jar 파일을 실행할 때 java 명령어가 실행되지 않을 경우에 설치)
```
sudo apt-get install openjdk-8-jdk
```

- (참고) Spring Boot 백그라운드 jar 중지
```
ps -ef | grep jar	// PID 확인
sudo kill [pid]		// PID에 해당하는 프로세스 종료
```

- (참고) nginx 재부팅
```
sudo service nginx restart
```

- (참고) AWS 서버 재부팅
```
sudo reboot
```



#### Frontend 배포 - Vue.js (VS Code)

- AWS 서버 접속
```
ssh -i [pem 파일] ubuntu@i3a308.p.ssafy.io
```

- 로컬에서 빌드
```
npm run build
```

- 로컬에서 빌드 후에 생긴 dist 폴더 확인

- 로컬에서 dist 폴더를 AWS로 전송
    - `scp -i "[pem file]" -r "~~~\dist" ubuntu@i3a308.p.ssafy.io:~/dist`
```
#파일 전송시
scp -i [pem file] [upload file] [user id]@[ec2 public IP]:~/[transfer address]
#예시
scp -i Desktop/amazon/juhyung.pem Desktop/pant.py ubuntu@~~~~:~/
#폴더 전송시
scp -i [pem file] -r [upload folder] [user id]@[ec2 public IP]:~/[transfer address]
#예시
scp -i Desktop/amazon/juhyung.pem -r Desktop/example ubuntu@~~~~:~/
```

- AWS에서 dist 폴더를 `/var/www/html/dist`로 옮기기
```
sudo mv ~/dist /var/www/html/dist
```