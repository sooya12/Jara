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



## :pig_nose: 8월 4일 (화) 오늘 할 일 :fire:

* 회원가입 시 메일 발송

  > 1. 회원가입 버튼을 누른다고 해서 db에 바로 올라가지 않음 (대기)
  >    * db에 T/F 컬럼을 추가하고 회원가입 버튼을 누름과 동시에 F상태 + 메일 발송
  > 2. 인증 메일 확인 버튼을 누르면 DB에서 T/F 컬럼 상태를 T로 바꾸고 로그인 가능

* 비밀번호 찾기 시 메일 발송

  > 생각하기~~