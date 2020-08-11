# 2020.08.03 Day :one:

### :computer:

:link: 소스코드 이관

:card_file_box: App bar drawer 위치 문제 해결

:policewoman: 신고 페이지 작성

:white_check_mark: Either 페이지 작성



### :blonde_woman:

:woman_facepalming: ㅎr... OR... 왜 오와열이 맞지 않는걸까? 어디서 틀린걸까...



# 2020.08.04 Day :two:

### :computer:

:curly_loop: 무한스크롤 구현!

:card_file_box: App bar drawer setter 오류 해결

:bar_chart: Eithers 작성, 댓글 페이지 작성



### :blonde_woman:

:100: 무한스크롤을 드디어 구현했다. 해보니까 쉽다... :sob: 지난주에 구현할 수 있었을 거 같은 아쉬움이 든다.

> vue-check-view 라이브러리를 통해 보이지 않는 div를 만들어 놓고, div가 window에 바닥에 닿을 때마다 피드를 5개씩 불러오도록 했다.
>
> > axios요청 말고 한번만 불러온 상태로 front에서 for문을 돌려야 하는 지 고민이 된다.

:keyboard: 구글링을 통해 setter 오류를 해결했다. :thumbsup:

> v-model 대신 value로 주면 된다.
>
> vuex를 쓰는 게 참 쉽지 않다.

:x: store에서 userInfo를 null로 줬더니, script 로딩 속도가 store 로딩 속도보다 빨라서 에러가 난다.

​      그래서 빈 array로 줬는데 이 경우에는 새로고침시에 문제가 생긴다... 뭐지...?

:heavy_check_mark: 내일 아침에 진님이랑 이야기해서 either 백을 조금 수정하면 오전중에 완성될 것 같다 :laughing:



# 2020.08.05 Day :three:

### :computer:

:bar_chart: Eithers 완료!

:floppy_disk: 피드 파일 업로드 구현

> 미리보기까지만!

:email: 회원가입과 비밀번호 변경 시 이메일 인증 구현



### :blonde_woman:

:fist_right: 계획대로 착착 진행되고 있다. 잘 되고 있다.

:framed_picture: vue로 미리보기하는 방법을 드디어 깨달았다.



# 2020.08.06 Day :four:

### :computer:

:bell: firebase realtime database를 이용한 알림센터 구현



### :blonde_woman:

:woman_facepalming: 하루 종일 firebase로 씨름했다. 이번 프로젝트를 하면서 Vue.js를 이용한 웹 프로젝트에 대해 블로그를 해보면 어떨까? 하는 생각이 들었다. 알림센터를 거의 다 구현하긴 했는데, 문제는 팔로우 요청 승인 / 거절 후 firebase에서 삭제하는 방법인데 어떻게 해야할 지 계속 고민해봐야겠다. 흠.

> key-value를 이용한 noSQL은 처음이어서 많이 버벅였다.



# 2020.08.07 Day :five:

### :computer:

:clipboard: 중간평가 준비

:bell: 알림센터 구현 완료



### :blonde_woman:

:100: firebase realtime db를 사용하는 방법을 완전히 깨우쳤다.

> 처음엔 realtime db에 값을 로그인할 때 처음 `once` 로 받고, 이후 `on`으로 받아야 하는지 고민했다.
>
> > `on` | `once` & `on` 둘 다 시도해 보고 이런저런 이벤트를 시도해 본 결과 `on`으로 설정했다. 
>
> `set()`을 통해 쓰니 참조된 주소의 값이 계속 set()을 통해 쓰여지는 마지막 데이터로 바뀌었고, 'value' 값으로 받으니 경로 전체의 내용을 읽고 변경사항을 수신대기하는 이벤트여서 바뀌는 대로 하나씩만 저장이 됐다.
>
> > (시도를 통해 얻은) 나쁜 예
> >
> > ```JS
> > notification: [
> >   {id},
> >   {key: 'adklfjsdfeiMasf-w3'}
> > ]
> > ```
>
> 이후 `push()`와 'child_added'로 했더니 db에 저장 후 받아온 key 값을 다시 푸쉬하니 key는 key대로 따로 생성되고 저장이 되어버렸다. :sob:
>
> > (잘못된 시도와) 나쁜 결과
> >
> > ```JS
> > <-- 요청 -->
> > const key = firebase.database().ref(`liked/${writer}`).push({like: 1, by: this.$store.state.userInfo.id}).key
> > firebase.database().ref(`liked/${writer}`).push({key: key})
> > 
> > <-- 결과 -->
> > notification: [
> >   {
> >     like: 1,
> >     by: 5
> >   },
> >   {
> >     key: 'adfmsdfSADFaeee-14VD'
> >   }
> > ]
> > ```
>
> 최종적으로 참조하는 주소로 푸쉬를 통해 키 값을 만들고, 이를 받아온 뒤 `update`를 통해 값을 저장해 주었다. 그리고 'child_updated' 이벤트를 사용하니 업데이트가 됨에 따라 참조하는 주소의 키값과 데이터가 저장된 온전한 객체를 받아 올 수 있었다.
>
> > (구글링을 열심히 해서 나온) 좋은 코드와 결과
> >
> > ```JS
> > <-- 요청 -->
> > const key = firebase.database().ref(`liked/${writer}`).push().key
> > const update = {
> >   like: 1,
> >   by: 5,
> >   key: key
> > }
> > firebase.database().ref(`liked/${writer}`).update(update)
> > 
> > <-- 결과 -->
> > notification: [
> >   {
> >     like: 1,
> >     by: 5,
> >     key: 'adfmsdfSADFaeee-14VD'
> >   },
> > ]
> > ```



# 2020.08.10 Day :six:

### :computer:

:file_folder: firebase storage를 통해 이미지 업로드 구현



### :blonde_woman:

:100: firebase storage를 사용하는 방법을 완전히 깨우쳤다.

> realtime DB를 사용하는 것과 비슷하다. documentation을 참고하였는데 자꾸 URL을 받아 올 수 없는 문제가 있었다. console.log에는 url이 찍히는데 나의 data 어디에도 저장되지 않는 그런... 문제... :cry: 1시간을 가볍게 :hammer: 삽질 후 url을 data에 저장할 수 있었다. 
>
> > 삽질
> >
> > ```js
> > firebase.storage().ref(`images/${article.id}`).put(file)
> > firebase.storage().ref().child(`images/${article.id}`).getDownloadURL().then(function(url) => )
> > ```
>
> > 잘 된예(인줄 만 알고 있었던 예)
> >
> > ```js
> > firebase.storage().ref(`images/${article.id}`).put(file)
> > firebase.storage().ref().child(`images/${article.id}`).getDownloadURL().then(url => this.img_src = url)
> > ```
>
> 하지만, URL을 불러오는게 너무 느리다... 내일 다시 해봐야겠다.



# 2020.08.11 Day :seven:

### :computer:

:couple: 유저페이지에 유저가 작성한 피드와 스크랩한 팁을 보여주는 기능 추가

:bookmark: tip 스크랩 기능 구현

> SSAFY :dog2: 공통프로젝트 6주(?)차면 백도 건들 수 있게 된다.

:cloud: 회원가입 시 유저가 입력한 지역의 날씨 정보 표시

:file_folder: firebase storage를 통한 이미지 업로드 수정



### :blonde_woman:

:black_large_square: 인스타그램처럼 hover시에 이미지가 어두워지는 걸 구현하고 싶어서 또 열심히 구글링 :mag:을 했다.

> ```html
> <style scoped> 
>   .back {
>     background-color= rgba(0, 0, 0, 0.3);
>     position: absolute;
>     width:
>     height:
>   }
> </style>
> ```
>
> position이 `absolute`인 것과 이미지 크기만큼 똑같은 크기를 주는 것 :star:

:woman_facepalming: firebase storage를 사용하는 방법을 완전히 깨우친 줄로만 알았다. 왜일까.. storage에서 URL을 불러오는 게 너무 느리다... 이러면 나가리다...

> 아무래도 파일 용량의 크기 때문일까 추측만 해본다. :grey_question:
>
> 또 이런 저런 구글링 :mag: 끝에 setTimeout 함수 대신에 이렇게 적용시켜 봤는데 잘 모르겠다. 기존의 방식에서 child()를 쓰지 않는 방법인데 뭐가 다른건지도 잘은 모르겠다. 하지만 setTimeout으로 시간을 버는 것보다 훨씬 빠른 것 같다.
>
> > ```js
> > firebase.storage().ref(`images/${article.id}`).put(file).then(() => {
> >   firebase.storage().ref(`images/${article.id}`).getDownloadURL().then(url => this.img_src = url)
> > })
> > ```