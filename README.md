# 실습을 위한 개발 환경 세팅
* https://github.com/slipp/web-application-server 프로젝트를 자신의 계정으로 Fork한다. Github 우측 상단의 Fork 버튼을 클릭하면 자신의 계정으로 Fork된다.
* Fork한 프로젝트를 eclipse 또는 터미널에서 clone 한다.
* Fork한 프로젝트를 eclipse로 import한 후에 Maven 빌드 도구를 활용해 eclipse 프로젝트로 변환한다.(mvn eclipse:clean eclipse:eclipse)
* 빌드가 성공하면 반드시 refresh(fn + f5)를 실행해야 한다.

# 웹 서버 시작 및 테스트
* webserver.WebServer 는 사용자의 요청을 받아 RequestHandler에 작업을 위임하는 클래스이다.
* 사용자 요청에 대한 모든 처리는 RequestHandler 클래스의 run() 메서드가 담당한다.
* WebServer를 실행한 후 브라우저에서 http://localhost:8080으로 접속해 "Hello World" 메시지가 출력되는지 확인한다.

# 각 요구사항별 학습 내용 정리
* 구현 단계에서는 각 요구사항을 구현하는데 집중한다. 
* 구현을 완료한 후 구현 과정에서 새롭게 알게된 내용, 궁금한 내용을 기록한다.
* 각 요구사항을 구현하는 것이 중요한 것이 아니라 구현 과정을 통해 학습한 내용을 인식하는 것이 배움에 중요하다. 

### 요구사항 1 - http://localhost:8080/index.html로 접속시 응답
* HTTP 

    * HTTP란 하이퍼텍스트 전송 프로토콜로 하이퍼텍스트를 전송해서 웹페이지를 로드한다
    * HTTP 요청
      * url
      * HTTP 메서드 : GET 방식과 POST방식이 있다
        * GET - 클라이언트가 서버에게 정보를 받음 , 서버의 정보를 조회할때 사용함
        * POST - 클라이언트가 서버에 정보를 제출 , 서버의 정보를 변경하거나 추가할때 사용함
        
      * HTTP 본문 : 실제 데이터 컨텐츠/ 메시지 본문 요청 리소스에 따라 HTML,이미지,CSS파일 포함 가능
      * HTTP 요청헤더 : 요청한 URL, 메소드 , 브라우저 및 기타 정보가 포함된다
      
    + HTTP 응답
      + HTTP 상태코드 : HTTP 요청이 성공적으로 완료되었는지 여부를 나타냄
        + 1XX : informational
        + 2XX : 성공
        + 3XX : 리디렉션
        + 4XX : 클라이언트 오류
        + 5XX : 서버 오류
      + HTTP 응답헤더 : 데이터 언어 및 형식 등 정보를 포함
      + HTTP 본문 : 요청된 정보가 포함

  




### 요구사항 2 - get 방식으로 회원가입
* 

### 요구사항 3 - post 방식으로 회원가입
* 

### 요구사항 4 - redirect 방식으로 이동
* 

### 요구사항 5 - cookie
* 

### 요구사항 6 - stylesheet 적용
* 

### heroku 서버에 배포 후
* 