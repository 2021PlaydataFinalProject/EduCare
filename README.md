# 온라인 학습 케어 서비스(Educare) 

![화면 캡처 2021-04-18 103438](https://user-images.githubusercontent.com/73863771/115131262-bb3b4f00-a031-11eb-924b-6b95ba440eec.png)

# 1. 개요
## 1-1. 주제 선정 배경, 기획 배경
:star: 코로나19와 같은 감영성 질병으로 인해 비대면 수업이 많아지면서 온라인으로 학습하는 학습자가 많아지고 있습니다.

:star: 초등학교, 중학교, 고등학교, 대학교 등의 학생들이 온라인 수업 강의를 수강하게 되면서 비대면으로 온라인 시험을 치르는 학습자가 많아질 것으로 예상되었습니다.

:star: 비대면으로 온라인 시험을 치르게 되면 부정행위가 만연한 시험들이 많아질 것입니다.

:star: 실제 사례로 최근에 발생된 특정 대학교에서의 부정행위로 인해 정직하게 시험을 본 학생들이 피해를 입었으며, 기업들이 특정 학교의 부정행위 보도를 통해 알게 된다면 그 학교의 학점은 인정하지 않을 수 있다는 학생들의 걱정도 있었습니다.

:star: 정직하게 시험 본 학생들이 이의제기를 해서 원칙대로 처리하도록 신고를 했지만 이 사안에 대해서 학교 측에서는 신고 접수를 받았지만 아무런 징계나 진상 조사가 없었습니다.

:star: 이와 달리, 부정 행위를 사전에 예방하기 위해 특정 대학교에서는 비디오 회의, 온라인 미팅, 화면 공유가 가능한 줌(Zoom)과 같은 웹엑스(Webex)를 활용해 수강생들이 노트북과 핸드폰으로 웹엑스에 접속해서 앞, 뒤로 촬영하게 해서 교수가 실시간으로 학생의 모습을 볼 수 있게 하는 메뉴얼을 만들어서 전 학과가 시행하고 있다고 합니다.

:star: 이로 인해 커닝이나 부정행위 사례들이 나오지 않고 있으며, 이러한 프로그램들을 통해서 사전에 부정행위를 방지하게 된다면 학생들은 공정하게 노력한 만큼 성과를 얻을 수 있을 것 입니다.

## 1-2. 과제 목표, 작품 제작의도 및 컨셉
:eyes: 학생들이 온라인 시험을 볼 때 교수님이나 강사님이 실시간으로 학생들의 시험을 감독할 수 있는 서비스를 개발합니다.

:eyes: 학생이 2명 이상 감지가 되면, 교수님이나 강사님들이 실시간으로 감독하지 않아도 온라인 웹(WEB)에서 학생들이 부정행위를 했는지 안했는지 확인이 가능하도록 프로그램을 개발합니다. 

:eyes: 핸드폰이나 기타 전자기기가 감지가 되면, 자동으로 교수님이나 강사님에게 부정행위 시간과 함께 부정행위 여부에 대한 정보가 교수님이나 강사님에게 전달되도록 프로그램을 개발합니다.

:eyes: 시험을 시작하면 자동으로 웹캠에서 시험을 치르는 학생의 모습이 녹화가 되며, 시험 종료시 영상을 다운로드하여 시험 감독관(교수님이나 강사님)께 제출하도록 프로그램을 개발합니다.

## 1-3. 계획(초안, 수정안)
**:page_with_curl:(초안)**

:eyes: 학생이 집중하고 있는지 판단해주는 서비스(Engagement Detection) : OpenCV와 딥러닝을 활용하여 졸음, 머리 움직임, 눈의 움직임, 감정 등의 정보를 통해 학생의 집중도를 예측하는 서비스

:eyes: 시험 감독 서비스(Proctoring-AI) : OpenCV의 YOLOv3(You Only Look Once v3) 딥러닝 기반 실시간 객체 탐지 알고리즘을 활용하여 시험 동영상을 녹화하여 부정 행위 감지 횟수가 증가한 학생의 영상을 시험감독관들이 확인하며, 감지 시간이 로그 기록에 남아 감독관들이 응시자의 부정행위 의심 시점들을 중심으로 영상을 확인할 수 있는 서비스

**:page_with_curl:(수정안)**

:eyes: 학생이 집중하고 있는지 판단해주는 서비스는 학생의 감정을 예측해주는 딥러닝 모델 정확도가 낮아서 실제 시험에서 활용도가 낮다고 판단되어 프로그램 개발을 하지 않았습니다. 

:eyes: 딥러닝 모델 성능 테스트를 통해 정확도가 90% 이상인 딥러닝 모델을 기반으로 하여 모델 성능이 좋다고 판단되는 '시험 감독 서비스'에만 집중하여 프로그램을 개발합니다.

# 2. 추진과정 및 방법
## 2-1. 주요 추진 과정 절차
**:microscope:요구사항 분석**
- 주요 고객 타켓층인 교수님 및 강사님과 학생들의 요구사항을 명세화하기 위해 사용자 요구 기능을 조사 및 분석하였습니다.
- 사용자 요구 기능 
 1) 줌이나 웹엑스을 활용한 온라인 시험에서 교수님 및 강사님은 많은 학생들을 하나의 모니터 화면에서 시험감독을 해야되기 때문에 실시간으로 감독을 하더라도 부정행위를 탐지하기 어려울 수 있습니다.
 2) 학생은 카메라 두개(핸드폰, 노트북)를 사용하지 않고 노트북 웹캠만으로 시험을 볼 수 있게 하고, 학생이 부정 행위를 했다면 이에 대한 증거 자료가 될 수 있는 영상은 시험 감독관에게 전송되어져야 하고, 데이터베이스에 영구보존 되도록 해야합니다.  

**:computer:데이터베이스 및 프로그램 설계**

- 데이터베이스(DB) 설계

![ERD Diagram](https://user-images.githubusercontent.com/37354978/115163259-2133df00-a0e3-11eb-9a28-5060ee9e2d28.PNG)

**:bar_chart:test 테이블**<br>
- 시험번호, 시험시작시간, 시험종료시간, 시험가이드설명, 시험과목명, 교수님 및 강사님 아이디 정보가 있습니다.

**:bar_chart:user 테이블**<br>
- 교수님 및 강사님 또는 학생을 두가지 역할(role)로 나누어서 구분되도록 하고, 사용자 아이디, 비밀번호, 핸드폰 번호, 프로필 이미지, 사용자 이름 정보가 있습니다.

**:bar_chart:testproblem 테이블**<br>
- 시험문제 번호, 문제 설명, 문제 보기(객관식 1,2,3,4번 선지), 문제 관련 이미지, 학생이 선택한 문제 답안, 시험과목 정보가 있습니다.

**:bar_chart:studenttest 테이블**<br>
- 부정행위 시간, 부정행위 여부, 교수님 및 강사님이 작성하신 시험문제 답안, 채점 결과, 시험완료 여부, 녹화된 영상 이름, 학생 아이디, 시험과목 정보가 있습니다.

**:raising_hand:학생**

![학생회원페이지](https://user-images.githubusercontent.com/37354978/115165730-3c561d00-a0ea-11eb-8b68-685359b86976.PNG)

- 학생은 회원가입을 통해 학습자 아이디와 비밀번호, 이름, 핸드폰 번호, 프로필 이미지를 DB에 저장하고, 학습자의 아이디와 비밀번호를 통해 로그인을 합니다.
- 학생은 웹 페이지에서 시험 시작을 버튼을 클릭하면 시험문제가 있는 페이지로 이동하고, 교수님 및 강사님이 출제하신 시험 문제를 볼 수 있으며, 시험 보는 모습이 영상으로 시험 시작과 동시에 자동 녹화되도록 합니다.
- 시험 종료시 시험 완료 버튼을 클릭하면, 녹화된 영상을 다운로드 할 수 있으며, 다운로드된 영상을 웹 페이지에 업로드하여 시험 종료 버튼 클릭시 녹화된 영상과 시험 완료 여부에 관한 정보가 DB에 저장되도록 합니다. 이때 웹 캠에 핸드폰이 탐지되거나 학생이 2명이상 탐지되었을 경우 부정행위로 간주되어 부정 행위 시간과 부정 행위 여부 등에 관한 정보를 시험 감독관에게 전송되도록 합니다.

**:information_desk_person:교수님 및 강사님(시험 감독관)**

![교수강사회원페이지](https://user-images.githubusercontent.com/37354978/115165734-44ae5800-a0ea-11eb-8f53-e0a48f694120.PNG)

- 교수님 및 강사님은 시험을 생성하고, 시험과목명, 시험시작시간과 시험종료시간을 설정하고, 시험가이드에 대한 설명을 웹 페이지에서 작성해서 DB에 저장합니다.
- 시험 관리 페이지에서 학생이 부정행위를 했다면 학생이 영상을 업로드하고 제출했었던 해당 영상을 볼 수 있으며, DB에 저장된 부정행위 시간 및 부정행위 여부에 관한 정보를 웹 페이지에서 확인이 가능하도록 합니다.


**:computer:구현/코딩** <br>
**:computer:BACKEND**
- Spring Security, JWT 이용한 인증&인가&보안, CRUD 등
- 로그인시 발급된 JWT 토큰을 Header에 담아 Vue에 전송, JWT 토큰이 없거나 위조 되었을 경우 서비스 이용 불가
- DB 테이블 설계, 로깅

**:computer:FRONTEND**
- Vue.js 기반 프론트 작업 (Vue cli, vue router, vuex)
- ESLint + prettier 사용
- Buefy + Bulma 를 사용한 UI 작업
- Axios 로 백엔드와의 로직및 데이터 연동 작업 (로그인/회원가입및 필요 CRUD) 
- 유저 input 받기및 로직이 실행되었을때 유저에게 명확한 정보 전달 (UI/UX 개선) 
- Video.js, JWT decode및 추가 모듈을 활용한 데이터 렌더링 
- 전 페이지 비동기 처리 
- 세션데이터활용해 유저의 정보 기반으로 페이지단 출력

**:computer:OPENCV & FLASK & DEEPLEARNING**
- Flask 기반으로 openCV 작업과 yolo v3 사용
- mysql과 flask 연동 및 springboot + vue + flask 서버 이동
- 비디오 녹화 기능, 부정행위 탐지 시간 설정 및 출력 기능 등 


**:alarm_clock:이슈 점검**
- 이슈 1  : 
내용 : 
MYSQL DB driver , dataSource 문제

해결 👍 
pom.xml 에서 my-sql-connector 버전을 
<version>8.0.18</version> 라인을 추가하여
8.0.18로 수정하였습니다.

- 이슈 2 :
내용 : 
When allowCredentials is true, allowedOrigins cannot contain the special value "*"since that cannot be set on the "Access-Control-Allow-Origin" response header. 
To allow credentials to a set of origins, list them explicitly or consider using "allowedOriginPatterns" instead.

Access to XMLHttpRequest at 'http://localhost:8000/user/signup' from origin 'http://localhost:8081' has been blocked by CORS policy:
 Response to preflight request doesn't pass access control check: No 'Access-Control-Allow-Origin' header is present on the requested resource.

해결 👍 
educare 폴더 > CorsConfig.java 파일 >corsFilter 메서드에서
config.addAllowedOrigin("*");를 
config.addAllowedOrigin("http://localhost:8081");

위와 같이 수정해서 해결하였습니다.

## 2-2. 방법 및 활용 프로그램
![Used Program2](https://user-images.githubusercontent.com/37354978/115165279-1af43180-a0e8-11eb-9575-058df0dab7c2.PNG)


## 2-3. 단계별 수행 내용(기획, 제작, 테스트)

**:page_with_curl:기획**
- 온라인 시험에서 부정행위 사례를 조사하여 실제 부정행위가 어떻게 이루어지고 있는지 조사하였습니다.
- 실제 특정 대학교에서 적발된 부정행위는 시험시간 중에 2명이나 9명씩 짝을 지어 모여서 시험을 보는 경우가 있었고, 시험 문제가 나오면 카카오톡이나 보안이 철저한 텔레그렘으로 협의를 하면서 시험을 치르는 사례들이 있었습니다.


**제작** <br>
**:computer:BACKEND 팀**
- 권한을 강사 또는 학생 할지 선택 -> 현재 구현 되어있음
- 처음에는 학생 권한으로 가입하고 -> 나중에는 어드민이 강사로 권한을 바꿔주는 방법으로 하려고 했었음 -> 그러나, 시간상 불가능할 것으로 판단됨.
- signin, signup 백엔드 & 프론트 연결 성공
- TestController , StudentTest 프론트가 받기 쉬운 방식으로 구현
- TestProblem 구현 완료 -> 테스트 완료
- USER CRUD 구현 및 테스트 완료
- 세션 스토리지에 JWT를 HEADER : Authorization에 넣어주기


**:computer:FRONTEND 팀**
- SignIn, SignUp Form 가져오기 / 추가구현하기
- http://localhost:8082/#/signin에서 # 제거
- TestProblem 쪽 페이지 구현 완료 
- 로그인 , 로그아웃 구현 진행 중 ( USER 객체 가져와서 페이지에 보이기 )
- addTest 문제 / 답안 4지선다 구현
- Educare 를 클릭할때 서비스페이지로 이동
- 회원가입 버튼 추가 및 각 페이지단 레이아웃 수정
- TestSuperVision.vue 만들고, video.js 설치 (감독영상 스트리밍할 때 필요함)
- 라우터 가드 설치 (로그인한 유저만 페이지 접근 가능) 
- 카카오 오븐 기준으로 변경된 테이블 수정 
- AddTest.vue, ModifyTest.vue 레이아웃 완료 (버튼 클릭시 Instructor.vue로 이동하게 해놓음)
- 전체적으로 테이블 띄우는 페이지들 데이터 연동
- TestSupervision.vue 비디오 렌더링 완료
- 프론트 & 백앤드 연결 계속 시도하고 있는중
- jwt 디코딩으로 유저 이메일 navbar에 뜨도록 구현 
- 백앤드쪽에서 업뎃해준 로직들 테스트하면서 프론트로 연결
- TestSupervision.vue 에서 비디오/학생 답안이랑 다른 데이터 띄우기 
- TestSupervision -> studentTest data 렌더링이 힘듬 object 데이터를 가져오는거 시도 
- ManageStudent.vue 해결 마무리 하기 
- TestGuide.vue 데이터 연동 
- npm i jwt-decode (설치)--> node_modules 업데이트 하기 (공존 구글 드라이브에 업데이트 완료 -> 즉 모두들 새로운 node_modules로 갈아끼우세요)
- 로그인 및 로그아웃 버튼 클릭시 화면 자동 새로고침  
- 강사 및 학생에 따른 탭 구분 
- 로그인 전에 이미지 옆에 텍스트로 '로그인' 넣기 
- 프론트 & 백엔드 & ML 전체 코드 백업 
- ModifyTest.vue 구현 
- AddTestProblems.vue 의 delete problem button 더하기 

**:computer:ML 팀**
- mysql 과 flask 연동 성공
- 사물, 사람 탐지된 String 데이터  mysql DB 에 전송 예정 
- 동영상 전송 관련 사이트 조사 (php 없는걸 위주로 검색)
- mysql mobile detected 가능
- 비디오 영상을 보내는건 조사 중 
-  Flask 인강? -> 비디오 영상 제목 데이터 전송 -> mysql DB  해결 ? -> mysql에 비디오 제목 데이터를 넣을 예정  
- 동영상 기록 -> 녹화 가능 -> 녹화본에 있는 제목이 들어가게끔 구현 
- Flask 서버 구동 안 할 예정  ( MVC 패턴 구현X)
- Flask 로 화면단 구현 마무리 진행
- 비디오 저장 -> 유저가 업로드 -> Spring Boot로 Post 보내는 방법 -> Spring Boot 구현 하면 테스트 예정
- mobile detected 와 같은 string 데이터들을 리스트에 담는 것 구현 완료 -> 백엔드 테스트 예정 
- JSON으로 스프링 부트에 보내는 것을 LiST로 보내는 것 구현
- user_id에 따른 시험문제를 가져올 수 있게함
- timelist 해결 -> string으로 이어붙여서 DB 에 저장 -> 스프링부트로 UPDATE
- FLASK 컨트롤러 & VUE 연동 
- video 영상 길이 문제 해결 
- 시험 종료 후 server.py에서 timelist 출력 
- 비디오 영상 : avi -> mp4로 변경
- flask 서버 실행 시 recording 자동 시작 
- for문 이용해서  usertestnum이용해서 문제 화면단에 불러오기




**:page_with_curl:테스트**
- Spring Boot 백엔드와 Vue 프론트엔드 연동
- Flask 백엔드와 Vue 프론트엔드 연동
- YOLOv3 딥러닝 모델 정확도 테스트 (결과: 정확도 90% 이상)

# 3. 결과

## 시험 전

![시험전](https://user-images.githubusercontent.com/37354978/115171239-98766c80-a0fd-11eb-83fa-1d754b5ccb9b.PNG)

## 시험 후

![시험후](https://user-images.githubusercontent.com/37354978/115171266-a7f5b580-a0fd-11eb-88a9-ddbe0d68155f.PNG)

## 3-1. 시연

**:computer:SPRINGBOOT 환경 설정 및 실행**

1. 경로 지정 및 환경 설치 및 실행 

- IDE를 실행 할 시에 backend 파일 경로까지 설정하여 실행합니다.
```backend
C:\ITSTUDY\FinalProject\EduCare\backend
```

- 프로젝트 파일을 우클릭하여 Run as -> Spring boot app 실행


**:computer:vue.js 환경 설정 및 실행**

1. 경로 지정 및 환경 설치 및 실행 

- 개인마다 경로에 차이가 있을 수 있습니다. 해당 프로젝트의 경로를 확인한 후 frontend까지 지정해야합니다.

```bash
cd C:\ITSTUDY\FinalProject\EduCare\frontend
```

- 경로 하단에 npm i 를 통해 node-modules 를 설치합니다.

```bash
C:\ITSTUDY\FinalProject\EduCare\frontend> npm i
```

- 설치 완료시 서버를 실행합니다.

```bash
C:\ITSTUDY\FinalProject\EduCare\frontend> npm run serve
```

**:computer:ML 환경 설정 및 실행**

1. 환경 설치

```bash
python -m pip install opencv-python
pip install scikit-learn==0.22.2
pip install --user tensorflow==2.2.0
python -m pip install cmake
pip install mtcnn
pip install Flask
pip install pymysql
pip install Flask-MySQLdb
pip install flask-cors
```

2. 온라인 상에서 다음과 같은 yolov3.weights 를 다운받기

```bash
yolov3.weights
```

- 다운받은 폴더의 경로를 flask 경로 하단에 넣기

3. 서버 실행 방법

```bash
python server.py
```



## 3-2. 느낀 점, 결론, 기대효과
- 교수님과 학생들은 신뢰관계이고, 교수님은 학생들을 위해 정직하게 최선을 다해서 강의하고, 학생들은 정직하게 최선을 다해 공부해서 꿈을 키워가는 것이 맞는 것 같다고 생각되시는 교수님들께도움이 될 것 같습니다. 특히, 부정행위 의심 학생 너무 많아서 생기시는 우울감을 해결해드릴 수 있을 것 같습니다.
- 공정하게 노력한 만큼 성과를 얻고자 하는 정직한 학생들에게 도움이 될 것 이라고 생각되며, 향후 생명을 다루는 미래의 의사들이 될 의대 학생들에게도 부정행위와 관련된 생각과 행동을 못하게 되어 도움이 될 것 같습니다.

- 희성 : 평소에 다뤄보고 싶었던 Spring Security, Jwt를 공부하며 이를 활용하는 서비스를 구현해보아서 좋았고, 제가 더 배워야 할 것이 많다는 것을 다시금 느꼈습니다.
다들 수료를 앞두고 바쁜 와중에도 열심히 프로젝트 개발에 참여해 주어 정말 고맙고, 어려운 문제가 발생했을 때 서로 의견을 공유하며 해결했던 경험이 보람차고 뿌듯했던 프로젝트였습니다!! 

- AJ : 기존에 사용했었던 Spring Boot + Vue.js 에 Open CV와 Flask를 더해 더 유의미한 프로젝트였습니다. 과정은 도전적이였고 팀의 분업과 협업을 통해 많은 것들을 배웠습니다. 

## 3-3. 개선 사항 및 활용 계획
- 학생이 시험 중 일때, 구글과 같은 다른 사이트에 접속하지 못하도록 구현해서 구글링을 통해 검색한 자료들을 그대로 답안에 갖다 붙여 제출하는 사례가 없도록 하겠습니다.
- 학생이 온라인으로 수업을 수강할 때, 집중도 감지 기능을 추가하여 구현하고자 합니다. 
- 향후 클라우드 서비스를 활용하여 실제 상용화할 계획 입니다.

# 4. 참고 자료
1. https://www.youtube.com/watch?v=twCvhisJpZg
