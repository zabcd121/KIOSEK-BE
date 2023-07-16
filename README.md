<!-- PROJECT LOGO -->
<br />
<div align="center">
<a href="https://kiosek.kr">
<img width="394" alt="스크린샷 2022-11-21 오후 3 48 47" src="https://user-images.githubusercontent.com/68465716/237099393-ff26948e-aeaa-4172-9460-77fea0e8169d.png">
</a>
<h3 align="center">KIOSEK</h3>

  <p align="center">
    회의실 예약 및 관리 시스템
    <br />
    <br />
    <a href="https://github.com/brobac/CSE-projectroom-management">FRONTEND PROJECT</a> ·
    <a href="https://github.com/zabcd121/CSE-projectroom-management-Server">BACKEND PROJECT</a>
  </p>
<a href="https://kiosek.kr"><p>https://kiosek.kr</p></a>
</div>
<div align=center><h1>🎊 Service Opened 2023.05.01~</h1>
</div>
<div align=center><h1>📚 BACKEND STACKS</h1>
<img src="https://img.shields.io/badge/java-2E64FE?style=for-the-badge&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
<br>
<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">
<img src="https://img.shields.io/badge/Spring Data JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/Query DSL-5294E2?style=for-the-badge&logo=querydsl&logoColor=white">
<img src="https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=JUnit5&logoColor=white">
<img src="https://img.shields.io/badge/Mockito-25A162?style=for-the-badge&logo=Mockito&logoColor=white">
<img src="https://img.shields.io/badge/jacoco-B40404?style=for-the-badge&logo=jwt&logoColor=white">
<img src="https://img.shields.io/badge/jwt-FFCD00?style=for-the-badge&logo=jwt&logoColor=white">
<br>
<img src="https://img.shields.io/badge/mariadb-003545?style=for-the-badge&logo=mariadb&logoColor=white">
<img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white">
<br>
<img src="https://img.shields.io/badge/ubuntu-E95420?style=for-the-badge&logo=ubuntu&logoColor=white">
<img src="https://img.shields.io/badge/Nginx-009639?style=for-the-badge&logo=Nginx&logoColor=white">
<img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">
<img src="https://img.shields.io/badge/docker compose-2496ED?style=for-the-badge&logo=docker&logoColor=white">
<img src="https://img.shields.io/badge/letsencrypt-003A70?style=for-the-badge&logo=letsencrypt&logoColor=white">
<img src="https://img.shields.io/badge/naver smtp-03C75A?style=for-the-badge&logo=naver&logoColor=white">
<br>
<img src="https://img.shields.io/badge/prometheus-E6522C?style=for-the-badge&logo=prometheus&logoColor=white">
<img src="https://img.shields.io/badge/grafana-F46800?style=for-the-badge&logo=grafana&logoColor=white">

</div>
<div>
<hr>
    <div style="align-content: center; padding: 0px 200px;">
        <ol>
            <li><a href="#purpose">✅ 시스템 목적</a></li>
            <li><a href="#flow">📱 시스템 주요 Flow</a></li>
            <li><a href="#mainfc">‍🔧 회의실 관리자 주요 기능</a></li>
            <li><a href="#operation">‍📷️ 실제 키오스크 운영 사진</a></li>
            <li><a href="#diary">📔 프로젝트 회고</a></li>
            <li><a href="#issue">‍❗️이슈 해결 과정 정리 블로그</a></li>
            <li><a href="#structure">🏗️ 서버 내부 구조</a></li>
            <li><a href="#erd">🛢️ ERD</a></li>
            <li><a href="#video">‍🎥 주요 기능 시연 영상</a></li>
            <li><a href="#ui">🎨 UI 구현</a></li>
        </ol>
        <h2 id="purpose">✅ 시스템 목적</h2>
        <p>주 목적은 회의실을 예약하고 사용하는데 있어서 편의성을 제공하고자 한다.</p>
        <p>본 시스템이 부재했던 때에 여러 불편함을 해소하고 사용자 추척이 불가능하여 미흡한 책임감으로 인하여 유지되지 못한 좌석 청결 문제를 해결한다. </p>
        <p>결국 사용자에게 편의성을 제공하지만 책임감을 부여하여 매너있는 태도로 공공시설을 사용하도록 한다.</p>
        <hr>
        <h2 id="flow">📱 시스템 주요 Flow</h2>
        <p align="center"><em>참고) 사용자는 https://kiosek.kr 브라우저를 통해 예약하거나 현장에서 키오스크를 통해 현장예약하여 바로 사용할 수 있다.</em></p>
        <p style="font-size: 17px"><b>1. 웹을 통해 실시간으로 회의실 예약 상황을 확인할 수 있고 원하는 시간에 테이블을 예약하면 예약당 QR코드가 발급된다.</b></p>
        <p>&nbsp; &nbsp; &nbsp; (사용자당 하루에 1번 최대 4시간 2주뒤까지에 대해서 예약 가능하다.)</p>
        <p style="font-size: 17px"><b>2. 발급된 예약확인 QR코드를 키오스크에 인식하여 체크인 한다.</b></p>
        <p>&nbsp; &nbsp; &nbsp;(체크인 가능 시간은 예약 시작시간 20 분전부터 예약 시작시간 20분 후까지이며 해당 시간내로 QR체크인을 진행하지 않을 경우 <b>미사용</b>으로 간주되어 위반내역에 기록되며 즉시 예약이 취소된다.)</p>
        <p style="font-size: 17px"><b>3. 사용후에는 웹을 통해 좌석사진과 함께 반납한다.</b></p>
        <p style="font-size: 17px"><b>4. 사용종료시간 20분 후까지 좌석사진과 함께 반납처리하지 않을 경우 <b>미반납</b>으로 간주되어 위반내역에 기록된다.</b></p>
        <p style="font-size: 17px"><b>5. 위반내역이 <b>3회</b> 발생할 경우 <b>3일간</b> 예약이 불가능하다.</b></p>
        <hr>
        <h2 id="mainfc">👨🏼‍🔧 회의실 관리자 주요 기능</h2>
        <p style="font-size: 17px">1. 전체적인 예약 상황 모니터링</p>
        <p style="font-size: 17px">2. 특식배부와 같은 이유로 테이블을 사용하지 못하도록 해야하는경우 해당 테이블을 특정 기간동안 비활성화</p>
        <p style="font-size: 17px">3. 위반내역 및 제재내역을 확인 및 정지 해제</p>
        <p style="font-size: 17px">4. 예약 정책과 제재 정책을 수정</p>
        <p style="font-size: 17px">5. 사용자의 민원 확인</p>
    </div>
</div>
<br>
<hr>
<div style="align-content: center; padding: 0px 200px;">
<h1 id="operation">📷️ 실제 키오스크 운영 사진</h1>
<h3>예약 웹 서비스 뿐만 아니라 회의실 2곳에서 키오스크 운영 및 관리중</h3>
<div align="center">
<img src="https://user-images.githubusercontent.com/68465716/238135350-13f36c4a-5f1d-4d0b-b732-5c621ece02b4.jpeg" width="900" align="center"></img>
</div>
<br>
<hr>
<h1 id="diary"> 프로젝트 회고 </h1>
  <a href="https://devpoong.tistory.com/105"><p style="font-size: 17px">👉 https://devpoong.tistory.com/105</p></a>
<br>
<hr>
<h1 id="issue">❗️이슈 해결 과정 정리</h1>
    <h3>1. Custom Exception, ExceptionHandler 설계에서 나쁜 코드에 대한 고민과 리팩토링 과정</h3>
    <p>예외상황마다 각각 하나의 커스텀 예외를 생성하다보니, 커스텀 예외의 수가 너무 많아지는 문제가 있었습니다.<br>
    이를 해결하기 위해 표준예외를 사용하는 대신 현재 프로젝트에서 주로 발생하는 예외를 대상으로 한번 더 추상화하고 ErrorCode를 추가적으로 인자로 넣어주어 가독성을 높이는 방식으로 구현하였습니다.</p>
    <a href="https://devpoong.tistory.com/90"><p style="font-size: 17px">👉 https://devpoong.tistory.com/90</p></a>
    <h3>2. 중복 예약 동시성 문제 해결과정 정리</h3>
    <p>여러 개의 세션에서 동시에 중복된 예약을 시도할 때 Named Lock을 이용하여 문제를 해결하였습니다.<br>
        @Synchronized는 AOP 방식으로 동작하기 때문에 여러 세션에서 동시에 중복된 예약을 시도할 때 문제를 해결할 수 없었습니다.<br>
        테이블이나 row 단위로 Lock을 거는 Pessimistic Lock과는 다르게, Named Lock을 이용하여 회의실 정보와 예약 시간 메타데이터를 이용하여 Locking 하였습니다.</p>
    <a href="https://devpoong.tistory.com/82"><p style="font-size: 17px">👉 https://devpoong.tistory.com/82</p></a>
    <h3>3. 단위 테스트, 통합 테스트에 대한 방향 잡기</h3>
    <p>단위 테스트는 서비스 계층을 대상으로 DB나 파일 시스템, 외부 시스템등의 공유 의존성을 Mocking 하여 끊어내어 진행하였습니다. <br>
    반면에 통합테스트는 컨트롤러를 대상으로 테스트 할 수 없는 외부 시스템을 제외하고 공유 의존성을 연결하여 진행하였습니다.</p>
    <a href="https://devpoong.tistory.com/91"><p style="font-size: 17px">👉 https://devpoong.tistory.com/91</p></a>
    <h3>4. Scheduler 작업을 이용한 자동 예약 취소•반납 및 제재를 어떻게 구현</h3>
    <p>사용자가 특정시간내로 체크인을 하지 않은 경우에는 예약을 취소하고 위반 내역에 추가하고, 좌석 사진과 함께 반납을 하지 않은 경우에도 자동으로 위반 내역에 추가합니다.<br>
    위반내역이 특정횟수에 도달하면 회원 계정을 정지시키는 이러한 프로세스를 구현하기 위해서는 정해진 시간에 특정 작업을 수행하는 Scheduler가 필요했습니다.<br>
    Spring에서는 @Scheduled 어노테이션과 cron 명령을 사용하여 원하는 시간에 작업을 세팅할 수 있었습니다.</p>
    <a href="https://devpoong.tistory.com/74"><p style="font-size: 17px">👉 https://devpoong.tistory.com/74</p></a>
    <h3>5. Docker 여러 컨테이너의 볼륨, 네트워크를 간편하게 관리하고 실행하는 방법 (with Docker Compose)</h3>
    <p>MariaDB, Redis, Spring Boot, Prometheus, Grafana 등의 도커 컨테이너를 네트워크로 연결하고 볼륨을 설정하고 의존관계 및 환경설정을 할 수 있는 방법을 찾아보다가 Docker Compose를 사용하게 되었습니다.<br>
    기존에 각각 직접 docker run 명령에 볼륨 옵션이나 환경설정 등을 부여하다보니 오타 등의 실수가 발생하여 비효율적인 부분이 많았지만,<br>
    docker compose를 이용하여 docker-compose.yml 파일 하나와 docker compose 명령어를 통해 컨테이너 여러개를 한곳에서 관리할 수 있다는 장점으로 비효율적인 작업을 줄일 수 있었습니다.</p>
    <a href="https://devpoong.tistory.com/85"><p style="font-size: 17px">👉 https://devpoong.tistory.com/85</p></a>
    <h3>6. 개인정보 암호화 (with AES256)</h3>
    <p>실제 서비스를 운영하기 위해서는 개인정보를 암호화해야 할 필요가 있었습니다. <br>
    해당 프로젝트에서는 AES256 대칭키 암호 알고리즘을 사용하였습니다.<br> 1단계 앞의 암호문 블록에 평문 블록을 XOR 하여 암호화를 수행하는 CBC Mode를 사용하여 기밀성을 높였습니다.</p>
    <a href="https://devpoong.tistory.com/88"><p style="font-size: 17px">👉 https://devpoong.tistory.com/88</p></a>
    <h3>7. Jwt Token 인증방식을 사용하면서 memberId를 path variable로 받고 2차검증하는 비용 절감</h3>
    <p>JWT Token 인증방식을 이용하기 때문에 해당 토큰에서 사용자의 식별값을 얻을 수 있으므로 Path variable로 회원의 id를 받아와서 2차검증을 할 필요가 없습니다.<br>
    따라서 로그인 된 회원의 요청시에는 회원 id는 받지 않도록 구현하여 2차검증의 비용을 줄였습니다.</p>
    <a href="https://devpoong.tistory.com/87"><p style="font-size: 17px">👉 https://devpoong.tistory.com/87</p></a>
    <h3>8. HTTPS를 적용하기 - Client, CA, Web Server의 키를 주고 받는 과정</h3>
    <p>Presentation Layer에서 TLS 계층을 추가적으로 거치는 HTTPS를 적용하여 중간자 공격으로 부터 데이터를 보호하고자 하였습니다.<br>
    공개키를 이용해 대칭키를 암호화하여 전달한 후에 이 대칭키를 개인키로 복호화하여 복호화된 대칭키를 얻어냅니다.<br>
이렇게 서로 전달된 대칭키를 이용하여 데이터를 암호화 및 복호화합니다.</p>
    <a href="https://devpoong.tistory.com/86"><p style="font-size: 17px">👉 https://devpoong.tistory.com/86</p></a>
    <br>
    <h2>기타 프로젝트를 진행하면서 작성한 게시글</h2>
    <h3>하나의 물리 서버에 React 빌드파일과 Spring Boot Jar Tomcat WAS 배포를 위한 NGINX 설정</h3>
    <a href="https://devpoong.tistory.com/84"><p style="font-size: 17px">👉 https://devpoong.tistory.com/84</p></a>
    <h3>운영환경 구축하기 with Spring Actuator, Micrometer, Prometheus, Grafana</h3>
    <a href="https://devpoong.tistory.com/89"><p style="font-size: 17px">👉 https://devpoong.tistory.com/89</p></a>
    <h3>application.yml 변경사항이 jar파일에 제대로 적용되지 않는 문제</h3>
    <a href="https://devpoong.tistory.com/80"><p style="font-size: 17px">👉 https://devpoong.tistory.com/80</p></a>

<hr>
<h1 id="structure">🏗️ 서버 내부 구조</h1>
<div align="center">
<img src="https://user-images.githubusercontent.com/68465716/237288889-b163ff56-208a-4ab4-a064-77ce5c9e9564.png" width="900" align="center"></img>
</div>
<br>
<hr>
<h1 id="erd">🛢️ ERD</h1>
<div align="center">
<img src="https://user-images.githubusercontent.com/68465716/238134761-a61ff74a-f7d7-44ea-95ff-331ffbf3d785.png">
</div>
<br>
<hr>
    <h1 id="video">🎥 주요 기능 시연 영상</h1>
    <h2> 1️⃣ 예약 - 웹 </h2>
    <img src="https://user-images.githubusercontent.com/68465716/237363611-50fa5d46-1ef7-48b9-8c25-d083c4dab047.gif">
    <br><br>
    <h2> 2️⃣ 체크인 - 키오스크 </h2>
    <img src="https://user-images.githubusercontent.com/68465716/237354711-477b7b4c-4f2c-4cba-ace2-a6efcd7530f4.gif">
    <br><br>
    <h2> 3️⃣ 반납 시연 - 웹 </h2>
    <img src="https://user-images.githubusercontent.com/68465716/237363643-b9caf7a8-a994-471f-8f19-1b0c2ba420ab.gif">
    <br><br>
    <h2> 4️⃣ 현장예약 시연 - 키오스크</h2>
    <p>실제로는 터치 디스플레이 사용</p>
    <img src="https://user-images.githubusercontent.com/68465716/237353578-eb4f7cd4-34d4-4b54-a643-81401d435519.gif">
<hr>
<h1 id="ui">🎨 UI 구현</h1>
<div>
<h2 align="center"> 1️⃣ 예약 사용자 UI </h2>
<h2>1. 회원가입</h2>
<img src="https://user-images.githubusercontent.com/68465716/237120059-495aacfe-5d8b-4278-a629-4faea41ad812.png" height="500">
<h2>2. 예약페이지</h2>
<img src="https://user-images.githubusercontent.com/68465716/237120048-f416e183-f514-45b1-ae4c-30e3f093d46f.png" height="700">

<h2>3. 마이페이지</h2>
<h3>(1) 예약 내역 조회</h3>
<img src="https://user-images.githubusercontent.com/68465716/237259301-a39c3739-de01-4c99-825a-c3af3b892e06.png" width="800" height="500">
<h3>(2) 과거 예약 내역 조회</h3>
<img src="https://user-images.githubusercontent.com/68465716/237260124-f66b6f43-ecaf-4d4e-9f4c-17f3379f7bba.png" width="800" height="500">
<h3>(3) 위반 내역 조회</h3>
<img src="https://user-images.githubusercontent.com/68465716/237260549-8dae6e02-52b6-487b-8dbf-e501dafac912.png" width="800" height="500">
<h3>(4) 반납</h3>
<p>체크인 후 반납하기 버튼 활성화됨</p>
<img src="https://user-images.githubusercontent.com/68465716/237260541-af0b9074-0a8f-4f29-99d9-05960395e2c5.png" width="800" height="500">
<img src="https://user-images.githubusercontent.com/68465716/237259313-97915f15-673e-4c3f-8901-2f5db7ffd7e9.jpeg" width="400" height="650">
<h3>(5) 현장예약용 QR코드 확인</h3>
<img src="https://user-images.githubusercontent.com/68465716/237259305-9263d255-b49c-404c-8202-0b941fb2b400.png" width="800" height="500">

<hr>

<h2 align="center"> 2️⃣ 키오스크 UI</h2>
    <h2>1. 메인페이지</h2>
    <img src="https://user-images.githubusercontent.com/68465716/237259321-8c4f14c4-0d4a-4d87-bf27-e1a09b60e5e5.png">
    <br>
    <h2>2. 현장예약 페이지</h2>
    <h3>(1) 사용할 테이블을 선택 - 각 테이블별로 최대 사용가능한 시간 표시</h3>
    <img src="https://user-images.githubusercontent.com/68465716/237259328-6902dd58-c793-4814-9ec3-7f73559bdc85.png">
    <br>
    <br>
    <h3>(2) 선택한 테이블의 사용 종료 시간 선택</h3>
    <img src="https://user-images.githubusercontent.com/68465716/237259332-f03fb3e7-b02b-4991-9478-4cc85562f560.png">
    <br>
    <br>
    <h3>(3) 마이페이지의 현장예약용 QR코드 인식</h3>
    <br>
    <h2>3. 예약확인 페이지</h2>
    <img src="https://user-images.githubusercontent.com/68465716/237354711-477b7b4c-4f2c-4cba-ace2-a6efcd7530f4.gif">

<hr>

<h2 align="center"> 3️⃣ 관리자 UI</h2>
    <h2>1. 예약 관리페이지</h2>
    <img src="https://user-images.githubusercontent.com/68465716/237266147-1a8aef45-b9a2-4ac5-8908-1c6d294bce56.png">
    <img src="https://user-images.githubusercontent.com/68465716/237266141-639cffbf-97fd-4d0a-a4c4-2ad313c35c99.png">
    <h2>2. 민원 관리페이지</h2>
    <img src="https://user-images.githubusercontent.com/68465716/237265815-d1c6bc82-e63d-41ff-8194-77b142be514d.png">
    <img src="https://user-images.githubusercontent.com/68465716/237267461-866a4696-d70d-4184-8bf8-ebee00230ba1.png">
    <h2>3. 사용자 제재 관리페이지</h2>
    <img src="https://user-images.githubusercontent.com/68465716/237265818-353b0e3d-ee13-4cce-ab8c-ecab4f4ad5f9.png" width="1000" height="650">
    <img src="https://user-images.githubusercontent.com/68465716/237265823-fcd4e39b-f061-473e-8139-30abe9010106.png">
    <h2>4. 예약 비활성화 관리페이지</h2>
    <img src="https://user-images.githubusercontent.com/68465716/237265805-c26ccdb1-595b-4908-9017-0942e3291ffa.png" width="1000" height="650">
    <img src="https://user-images.githubusercontent.com/68465716/237265811-d1f74613-6134-4cb4-8fb4-aa51523e5182.png">
    <h2>5. 정책 관리페이지</h2>
    <img src="https://user-images.githubusercontent.com/68465716/237265824-47bb161e-025c-4bff-9bf6-aec4ec2f920c.png" width="1000" height="650">
</div>
</div>
