<!-- PROJECT LOGO -->
<br />
<div align="center">

<img width="394" alt="스크린샷 2022-11-21 오후 3 48 47" src="https://user-images.githubusercontent.com/68465716/237099393-ff26948e-aeaa-4172-9460-77fea0e8169d.png">



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
<div>
<hr>
<h2 align="center">프로젝트 목표</h2>
<hr>
<h2 align="center">사용자 주요 플로우</h2>
<p align="center"><사용자는 https://kiosek.kr 브라우저를 통해 예약하거나 현장에서 키오스크를 통해 현장예약하여 바로 사용할 수 있다.></p>
<p align="center"><b>1. 웹을 통해 실시간으로 프로젝트실 예약 상황을 확인할 수 있고 원하는 시간에 테이블을 예약하면 예약당 QR코드가 발급된다.</b></p>
<p align="center">(사용자당 하루에 1번 최대 4시간 2주뒤까지에 대해서 예약 가능하다.)</p>
<p align="center"><b>2. 발급된 예약확인 QR코드를 키오스크에 인식하여 Check In 한다.</b></p>
<p align="center">(체크인 가능 시간은 예약 시작시간 20분전부터 예약 시작시간 20분후까지이며 해당 시간내로 QR체크인을 진행하지 않을 경우 <b>미사용</b>으로 간주되어 위반내역에 기록된다.)</p>
<p align="center"><b>3. 사용후에는 웹을 통해 좌석사진과 함께 반납한다.</b></p>
<p align="center"><b>4. 사용후 좌석사진과 함께 반납처리하지 않을 경우 <b>미반납</b>으로 간주되어 위반내역에 기록된다.</b></p>
<p align="center"><b>5. 위반내역이 <b>3회</b> 발생할 경우 <b>3일간</b> 예약이 불가능하다.</b></p>
<br></br>

<h2 align="center">프로젝트실 관리자 주요 기능</h3>
<p align="center"><b>1. 전체적인 예약 상황 모니터링 </b></p>
<p align="center"><b>2. 특식배부와 같은 이유로 테이블을 사용하지 못하도록 해야하는경우 해당 테이블을 특정 기간동안 비활성화</b></p>
<p align="center"><b>3. 위반내역 및 제재내역을 확인 및 정지 해제</b></p>
<p align="center"><b>4. 예약 정책과 제재 정책을 수정</b></p>
</div>
<br>
<hr>
<h1 align="center">서버 내부 구조</h1>
<br>
<img src="https://github.com/zabcd121/CSE-projectroom-management-Server/assets/68465716/d3258a2f-2ec4-49a5-9a5a-27a65d23cf92" width="900" align="center"></img>
<br>
<hr>

<h1 align="center">🎨 UI 구현</h1>
<h2 align="center"> 1️⃣ 예약 사용자 UI </h2>
<h3>1. 회원가입</h3>
<img src="https://user-images.githubusercontent.com/68465716/237120059-495aacfe-5d8b-4278-a629-4faea41ad812.png" height="500">
<h3>2. 예약페이지</h3>
<img src="https://user-images.githubusercontent.com/68465716/237120048-f416e183-f514-45b1-ae4c-30e3f093d46f.png" height="800">

<h3>3. 마이페이지</h3>
    <h3>(1) 예약 내역 확인</h3>
    <h3>(2) 반납</h3>
    <h3>(3) 현장예약용 QR코드 확인</h3>

<h2 align="center"> 2️⃣ 키오스크 UI</h2>
    <h3>1. 메인페이지</h3>
    <h3>2. 예약확인 페이지</h3>
    <h3>3. 현장예약 페이지</h3>

<h2 align="center">3️⃣ 관리자 UI</h2>

   