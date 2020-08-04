## :pig2: 8월 3일(월) 오늘 한 일 :full_moon:

* 인증 이메일 보내기 (대체로 삽질.. 하..)

  > * 참고했던 링크 - spring boot rest 
  >
  >   [https://medium.com/@js230023/spring-boot-%EB%A9%94%EC%9D%BC-%EB%B3%B4%EB%82%B4%EA%B8%B0-f01751da4c02](https://medium.com/@js230023/spring-boot-메일-보내기-f01751da4c02)
  >
  >   https://gofnrk.tistory.com/82
  >
  >   https://a1010100z.tistory.com/6
  >
  >   https://drsggg.tistory.com/403 | 발신인의 이메일을 프로퍼티에 넣는 거였음 | 꼼꼼하게 읽기
  >
  >   ======================
  >
  >   https://newehblog.tistory.com/12
  >
  >   https://infondgndg91.blogspot.com/2018/01/spring-mybatismysql.html
  >
  >   -> 이사람 git 
  >
  >   https://github.com/ndgndg91/BalPoom
  >
  >   https://www.baeldung.com/spring-email
  >
  >   [https://medium.com/@js230023/spring-boot-%EB%A9%94%EC%9D%BC-%EB%B3%B4%EB%82%B4%EA%B8%B0-f01751da4c02](https://medium.com/@js230023/spring-boot-메일-보내기-f01751da4c02)
  >
  > * 계속 회원가입 시 인증메일을 보내려는 노력을 했고 (실패)
  >
  >   혹시나 싶어서 그냥 /accounts/email 로 메일을 보냈더니 그냥 메일을 보내는 것은 성공함
  >
  >   * MailHandler, AccountContoller, application.properties, pom.xml 수정
  >
  > * 구글 이메일 : lcy00707@gmail | 비밀번호 : ssafy1234 | property에 사용중인 비밀번호 : yguphiqgtiwcblae : 구글 2차 인증 코드 
  >
  > * ![image-20200803204011691](C:\Users\multicampus\AppData\Roaming\Typora\typora-user-images\image-20200803204011691.png)

* 오늘의 깃오류 : PULL PUSH 충돌 오류

  > Pull is not possible because you have unmerged files 에러 : git pull 시에 발생한 오류
  >
  > * 해결
  >
  > git status
  >
  > git commit -am "원래 커밋메세지"
  >
  > 



## :pig_nose: 8월 4일 (화) 오늘 한 일 :fire:

* 회원가입 시 메일 발송

  > 1. db update 
  >    * account table에 status : boolean, code : int 추가
  > 2. 회원 가입 
  >    * account 에 정보 추가
  >    * status = false(0) 
  >    * code = 랜덤 값이 들어감
  >    * 이메일 발송 (code 값 전송)
  > 3. 인증 이메일
  >    * /accounts/certification 이동
  >    * code 발급 확인
  > 4. 회원가입 확인
  >    * code 값 입력
  >    * status = true(1) 
  >    * 로그인 가능 상태

  > * 추가 사항
  >   * 코드값 변경 : 현재는 6자리 숫자 (다른 것으로 변경이 가능한지)
  >   * 로그인이 status = true(1)인 상태에 되도록 했는지 확인하기

* 비밀번호 찾기 시 메일 발송

  > 1. 비밀번호 변경을 위한 코드 변경
  >    * /accounts/changepw
  >    * code 값 변경
  >    * 이메일 발송 (code 값 전송)
  > 2. 인증 이메일
  >    * /accounts/setnewpw 이동
  >    * 변경된 code 발급 확인
  > 3. 비밀번호 변경하기
  >    * 변경된 code 값 입력
  >    * 변경할 비밀번호 입력

  > * 추가 사항
  >   * api 수정할 가능성이 있음





## :pig2: 8월 5일 (수) 오늘 할 일 :droplet:

* 인증 코드 값 변경
* 로그인 기능 확인
* 실시간 채팅
* 알람 기능