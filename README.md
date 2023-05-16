<!-- PROJECT LOGO -->
<br />
<div align="center">
<a href="https://kiosek.kr">
<img width="394" alt="스크린샷 2022-11-21 오후 3 48 47" src="https://user-images.githubusercontent.com/68465716/237099393-ff26948e-aeaa-4172-9460-77fea0e8169d.png">
</a>
<h3 align="center">KIOSEK</h3>

  <p align="center">
    금오공과대학교 컴퓨터소프트웨어공학과 프로젝트실 예약 시스템
    <br />
    <br />
    <a href="https://east-character-a95.notion.site/KIOSEK-763c315e47394a37aed2b4192207c78bhttps://east-character-a95.notion.site/KIOSEK-763c315e47394a37aed2b4192207c78b">NOTION</a> ·
    <a href="https://github.com/brobac/CSE-projectroom-management">FRONTEND PROJECT</a> ·
    <a href="https://github.com/zabcd121/CSE-projectroom-management-Server">BACKEND PROJECT</a>
  </p>
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
            <li><a href="#mainfc">‍🔧 프로젝트실 관리자 주요 기능</a></li>
            <li><a href="#structure">🏗️ 서버 내부 구조</a></li>
            <li><a href="#video">‍🎥 주요 기능 시연 영상</a></li>
            <li><a href="#ui">🎨 UI 구현</a></li>
        </ol>
        <h2 id="purpose">✅ 시스템 목적</h2>
        <p>주 목적은 프로젝트실을 예약하고 사용하는데 있어서 편의성을 제공하고자 한다.</p>
        <p>본 시스템이 부재했던 때에 여러 불편함을 해소하고 사용자 추척이 불가능하여 미흡한 책임감으로 인하여 유지되지 못한 좌석 청결 문제를 해결한다. </p>
        <p>결국 사용자에게 편의성을 제공하지만 책임감을 부여하여 매너있는 태도로 공공시설을 사용하도록 한다.</p>
        <hr>
        <h2 id="flow">📱 시스템 주요 Flow</h2>
        <p align="center"><em>참고) 사용자는 https://kiosek.kr 브라우저를 통해 예약하거나 현장에서 키오스크를 통해 현장예약하여 바로 사용할 수 있다.</em></p>
        <p style="font-size: 17px"><b>1. 웹을 통해 실시간으로 프로젝트실 예약 상황을 확인할 수 있고 원하는 시간에 테이블을 예약하면 예약당 QR코드가 발급된다.</b></p>
        <p>&nbsp; &nbsp; &nbsp; (사용자당 하루에 1번 최대 4시간 2주뒤까지에 대해서 예약 가능하다.)</p>
        <p style="font-size: 17px"><b>2. 발급된 예약확인 QR코드를 키오스크에 인식하여 체크인 한다.</b></p>
        <p>&nbsp; &nbsp; &nbsp;(체크인 가능 시간은 예약 시작시간 20 분전부터 예약 시작시간 20분 후까지이며 해당 시간내로 QR체크인을 진행하지 않을 경우 <b>미사용</b>으로 간주되어 위반내역에 기록되며 즉시 예약이 취소된다.)</p>
        <p style="font-size: 17px"><b>3. 사용후에는 웹을 통해 좌석사진과 함께 반납한다.</b></p>
        <p style="font-size: 17px"><b>4. 사용종료시간 20분 후까지 좌석사진과 함께 반납처리하지 않을 경우 <b>미반납</b>으로 간주되어 위반내역에 기록된다.</b></p>
        <p style="font-size: 17px"><b>5. 위반내역이 <b>3회</b> 발생할 경우 <b>3일간</b> 예약이 불가능하다.</b></p>
        <hr>
        <h2 id="mainfc">👨🏼‍🔧 프로젝트실 관리자 주요 기능</h2>
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
<h1 id="structure">🏗️ 실제 운영 사진</h1>
<h3>프로젝트 실습실 2곳에서 키오스크 운영 및 관리중</h3>
<div align="center">
<img src="https://user-images.githubusercontent.com/68465716/238135350-13f36c4a-5f1d-4d0b-b732-5c621ece02b4.jpeg" width="900" align="center"></img>
</div>
<br>
<hr>
<h1 id="structure">🏗️ 서버 내부 구조</h1>
<div align="center">
<img src="https://user-images.githubusercontent.com/68465716/237288889-b163ff56-208a-4ab4-a064-77ce5c9e9564.png" width="900" align="center"></img>
</div>
<br>
<hr>
<h1 id="structure">🛢️ ERD</h1>
<div align="center">
<img src="https://user-images.githubusercontent.com/68465716/238134761-a61ff74a-f7d7-44ea-95ff-331ffbf3d785.png">
</div>
<br>
<hr>
<h1 id="issue">설계 및 기술적인 고민 해결</h1>
<hr>
    <h1 id="video">🎥 주요 기능 시연 영상</h1>
    <h2> 1️⃣ 예약 </h2>
    <img src="https://user-images.githubusercontent.com/68465716/237363611-50fa5d46-1ef7-48b9-8c25-d083c4dab047.gif">
    <br><br>
    <h2> 2️⃣ 체크인 </h2>
    <img src="https://user-images.githubusercontent.com/68465716/237354711-477b7b4c-4f2c-4cba-ace2-a6efcd7530f4.gif">
    <br><br>
    <h2> 3️⃣ 반납 시연 </h2>
    <img src="https://user-images.githubusercontent.com/68465716/237363643-b9caf7a8-a994-471f-8f19-1b0c2ba420ab.gif">
    <br><br>
    <h2> 4️⃣ 현장예약 시연 </h2>
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