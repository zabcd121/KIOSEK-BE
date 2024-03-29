<!-- PROJECT LOGO -->
<br />
<div align="center">
<a href="https://kiosek.kr">
<img width="394" alt="스크린샷 2022-11-21 오후 3 48 47" src="https://user-images.githubusercontent.com/68465716/237099393-ff26948e-aeaa-4172-9460-77fea0e8169d.png">
</a>
<h3 align="center">KIOSEK</h3>

  <p align="center">
    회의공간 예약 및 관리 시스템 <br>( 개발 기간 : 2022.10 ~ 운영중 [서비스 오픈: 2023.05] )
    <br />
    <br />
    <a href="https://github.com/brobac/CSE-projectroom-management">FRONTEND PROJECT</a> ·
    <a href="https://github.com/zabcd121/CSE-projectroom-management-Server">BACKEND PROJECT</a>
  </p>
<a href="https://kiosek.kr"><p style="color:#81BEF7 ">https://kiosek.kr</p></a>
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
<br>
<img src="https://img.shields.io/badge/mysql-003545?style=for-the-badge&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white">
<br>
<img src="https://img.shields.io/badge/ubuntu-E95420?style=for-the-badge&logo=ubuntu&logoColor=white">
<img src="https://img.shields.io/badge/Nginx-009639?style=for-the-badge&logo=Nginx&logoColor=white">
<img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">

<img src="https://img.shields.io/badge/prometheus-E6522C?style=for-the-badge&logo=prometheus&logoColor=white">
<img src="https://img.shields.io/badge/grafana-F46800?style=for-the-badge&logo=grafana&logoColor=white">

</div>
<div>
<hr>
    <div style="align-content: center; padding: 0px 200px;">
        <ol>
            <li><a href="#purpose">✅ 시스템 목적</a></li>
            <li><a href="#structure">🏗️ 운영 서버, 개발 서버 구조 및 사양</a></li>
            <li><a href="#diary">📔 프로젝트 회고</a></li>
            <li><a href="#issue">‍❗️기여한 주요 내용</a></li>
            <li><a href="#erd">🛢️ ERD</a></li>
            <li><a href="#operation">‍📷️ 실제 키오스크 운영 사진</a></li>
            <li><a href="#video">‍🎥 주요 기능 시연 영상</a></li>
            <li><a href="#ui">🎨 UI 구현</a></li>
            <li><a href="#flow">📱 시스템 주요 Flow</a></li>
            <li><a href="#mainfc">‍🔧 회의실 관리자 주요 기능</a></li>
        </ol>
<hr>
<h2 id="purpose">✅ 시스템 요약</h2>
        <p>- 회의실 비매너 사용(청결 문제 등)과 자리 점유 등에 불편함을 느끼고 직접 문제를 해결하기 위해 개발한 플랫폼입니다. </p>
<p>- 예약 시스템이 도입되면서 발생하는 노쇼와 같은 문제를 해결하기 위해 스케줄러 기능을 통해 체크인되지 않은 예약건에 대해 자동으로 취소처리를 하고 제재를 가하는 절차를 추가하였습니다.</p>
<p>- 웹 사전예약 또는 키오스크 현장예약을 통해 회의공간을 예약할 수 있습니다.</p>
<p>- 예약 당 발급된 QR 코드를 통해 체크인을 하는 방식을 선택하여 복잡한 인증 절차를 제거하였습니다.</p>
<p>- 좌석을 사용하고 나면 깨끗이 치우고 사진을 찍어서 반납하는 절차를 추가함으로써 청결 문제를 해결하였습니다.</p>
        <hr>

<h1 id="structure">🏗️ 운영 서버, 개발 서버 아키텍처 및 사양</h1>
<h3>(1) 운영서버 : On-premise <br><br>(OS: ubuntu20.04, CPU: i5-6500, RAM: 4GB)</h3>
<div align="center">
<img src="https://user-images.githubusercontent.com/68465716/257221542-b1e52fb0-8ec8-4991-ae31-1bdb13c70213.png" align="center"></img>
</div>
<h3>(2) 개발서버 : AWS Cloud <br><br>(OS: ubuntu20.04, EC2 instance: t2.medium, vCPU: 2, RAM: 4GB)</h3>
<div align="center">
<img src="https://user-images.githubusercontent.com/68465716/257219243-9e1c2498-d3a7-48ef-a71d-8efc81ba0f32.png" align="center"></img>
</div>
<br>
</div>
<br>
<div style="align-content: center; padding: 0px 200px;">
<hr>
<h1 id="diary"> 프로젝트 회고 </h1>
<details>
    <summary>"테스트 코드는 선택이 아닌 필수다"</summary>
    <p>이전 다른 프로젝트에서는 테스트 코드가 없어서 포스트맨으로 API 테스트할 때는 잘 되다가 예기치 못한 오류가 나곤 했었다. 코드를 리팩터링하거나 로직을 수정할 경우에 발생하는 휴먼 에러 또는 예기치 못한 에러를 여러 케이스로 작성해 놓은 테스트 코드 덕분에 막을 수 있었다.

생각보다 리팩터링을 할 때 실수가 자주 생겼는데, 코드 수정 후에 테스트 코드를 실행시켜 보는 습관을 들여놓음으로써 어느 부분에서 장애가 났는지를 빠르게 확인할 수 있었다. 기존에 잘 동작하던 테스트 코드가 갑자기 실패한다는 것은 내가 방금 수정한 부분이 잘못됐다는 것이기 때문이다.

이번 프로젝트를 진행하면서 단위 테스트에 대한 모호했던 생각들이 정리되었고, 통합 테스트 코드를 통해 직접적으로 API를 테스트 코드를 작성해 놓음으로써 반복적인 작업을 줄일 수 있었다.
</p>
</details>
<details>
    <summary>"실수를 받아들일 수 있어야 한다"</summary>
    <p>누구든지 실수를 할 수 있기에 상대방의 실수에 대해서 받아들일 수 있어야 하고, 내가 한 실수에 대해서 솔직해야 한다. 
나는 처음에 그러지 못했던 것 같다.
나는 백엔드를 전체적으로 맡았기 때문에 프론트엔드쪽에서 문제가 발생하였을 때 같이 원인을 찾아주곤 했다.
점점 일정이 바빠지고 팀원의 실수가 잦아질 때는 속으로 화도 났던 것 같다.
하지만, 팀원측의 실수라고 생각하고 몇 시간 동안이나 원인을 파악하다가 역으로 내 잘못이었음을 알게 된 순간도 있었다.
상대방의 실수로 인해서 내 시간을 낭비한다고 생각하고 있었는데, 내 잘못임을 알게된 순간에는 정말 부끄럽기도 했고 미안한 마음이 컸다.
실수는 정말 나 너 할 거 없이 모두에게 찾아오는 시련인 것 같다.

이러한 일이 있은 후에는 마인드를 다르게 가지기로 했다. "실수는 누구든지 할 수 있고 문제를 해결했을 때는 박수를 쳐주자!"라고.
서로 발생한 실수에 대해서 사과와 원망을 하는게 아니라 문제를 해결하고 서로 기뻐해주는 것이다.
이러한 마인드를 바탕으로 하니 실수를 숨기지 않고 함께 해결해 나가는 힘이 생겼다고 생각한다.
</p>
</details>
<details>
    <summary>"시각적 자료를 이용하여 상대방을 설득하자"</summary>
    <p>KIOSEK 프로젝트 초창기 UI를 설계할 때 어떻게 메인 예약 페이지를 구성해야 할지 많이 의견이 분분했고 충돌이 있었다.

더 많은 의견이 있었지만 최종적으로 고민했던 세가지 의견을 나열해 보았다.

첫 번째 의견은 네이버 예약 형식대로 박스 형태를 나열해 보자.
두 번째 의견은 원하는 시간대를 먼저 선택하면 남아있는 좌석을 리스트업 해주자.
세 번째 의견은 쏘카의 UI를 참고하여 상태바의 형태로 시간대를 나타내어서 나타내보자.

나는 세번째 의견을 냈었다.
어떻게 팀원들에게 내가 생각하고 있는 것들을 정확히 전달할 수 있을지 고민하였다.

그에 대한 결론은 내가 제대로 설명할 수 없는데 남을 설득할 생각부터 하지 말고, 쓰던지 그리던지 해서 먼저 나부터 정리를 하자. 또한 의견에 대해서만 충돌이 있어야지 사람끼리의 충돌이 있어서는 안 된다. 정리되지 않은 의견으로 설득할 때는 오히려 반감을 사기 쉽다.

팀원을 설득하기 전 시각적으로 보여줌으로써 이해를 도울 수 있는 UI를 간단하게 스케치하고 다른 방식에 비해 가질 수 있는 장점에 대해 생각해 보았다.

이에 나는 첫 번째 의견이 구현이 더 편한 장점이 있지만 30분 단위의 24시간 동안 예약에 대해서 48개의 블록이 생기는 건 사용자 입장에서 불편함을 줄 가능성이 있을 것 같다. 두번째 의견은 예약 현황을 한눈에 볼 수 없고 계속 남는 시간대가 있는지 눌러봐야 한다는 단점이 있을 것 같다. 세번째 의견으로 진행했을 경우에는 이러이러한 UI를 바탕으로 관리자가 예약을 막아놓은 시간, 이미 예약된 시간, 예약 가능한 시간을 상태바로 한눈에 보고 예약할 수 있어 더 좋은 UX를 제공해 줄 수 있을것 같다.라는 식으로 발언했던 것으로 기억한다.

해당 의견에 대해 팀원은 내가 간단하게 스케치해놓은 UI를 통해 조금 더 관심을 갖고 긍정적인 질문들을 하였고, 이제는 내가 제시한 의견에 대해서 더 구체화할 수 있는 방안에 대해서 얘기하는 방향으로 흘러가게 되었다.

내 머릿속에서도 복잡한 생각을 누군가에게 전달할 때는 데이터를 기반으로 하거나 시각적으로 보여줄 수 있는 자료가 있을 때 더 설득력이 있다고 생각한다. 상대방을 설득한다는 것은 아직도 쉽지 않은 일이다.


</p>
</details>
<details>
    <summary>"머릿속으로 생각중일 때는 상대방에게 표현해야 한다."</summary>
    <p>회의도중에 팀원의 의견에 대해서 생각이나 고민을 하고 있을 때는 그걸 표현해줘야 한다. 
말없이 고민을 할 경우에는 상대방은 "내 의견이 별로인가?"라고 생각할 수도 있다는 것을 알았다.

나는 그래서 상대방이 의견을 내었고 내가 잠시 생각할 시간이 필요하다면 "좋은 의견이라고 생각하는데 잠시만 머릿속으로 정리해볼게"라고 표현한다. 이러면 상대방은 자신의 의견이 존중받음을 느낄 수 있고, 그 의견에 부정적인 견해를 전달하더라도 "충분히 생각해 보고 한 말이구나"라는 느낌을 받을 수 있다고 생각한다.
</p>
</details>
    <a href="https://devpoong.tistory.com/105"><p style="font-size: 17px">👉 회고 글 보기</p></a>
<br>
<hr>
<h1 id="issue">❗️기여한 주요 내용</h1>
    <h3>1. 분산 락을 이용한 중복 예약 동시성 문제 해결</h3>
    <p>여러 개의 세션에서 동시에 중복된 예약을 시도하는 문제에 대해 MySQL의 <b>Named Lock</b>을 이용하여 문제를 해결했습니다.<br>

Lock을 얻기 위한 용도의 DataSource를 분리함으로써, Lock이 필요하지 않은 요청들에 대해서 커넥션 풀을 보장해줄 수 있도록 하였습니다.<br>
또한 GET_LOCK과 RELEASE_LOCK 요청에 대해 같은 커넥션을 사용할 수 있도록 JDBC로 직접 구현하였습니다.
Named Lock의 lock name은 회의실 정보와 예약 시간을 메타데이터로 이용했습니다. <br>최종적으로 100개의 멀티 쓰레드로 동시성 테스트를 진행했을 때 1개의 예약만 DB에 삽입되었음을 확인했습니다.</p>
    <a href="https://devpoong.tistory.com/82"><p style="font-size: 17px">👉 블로그 글 보기</p></a>
    <h3>2. Index를 이용하여 기간별 예약 내역 조회 쿼리 1374%(174ms → 11.8ms) 성능 개선</h3>
    <p>KIOSEK에서는 예약과 관련하여 **시간**을 많이 다루고 있습니다. 따라서, DATETIME 타입의 컬럼을 범위 비교 또는 동등 비교하여 조회하는 쿼리가 많이 존재합니다.

카디널리티, 선택도, 활용도, **수정빈도**를 고려하여 DATETIME 타입의 예약 시간 컬럼을 **단일 컬럼 인덱스**로 적용하여 기간별 예약 내역 조회 쿼리에 대해 약 34만 개의 데이터를 대상으로 500개의 데이터를 추출하는데 **174ms → 11.8ms**로 **1374%** 향상시켰습니다.</p>
    <a href="https://devpoong.tistory.com/121"><p style="font-size: 17px">👉 블로그 글 보기</p></a>
    <h3>3. 총 예약 건수를 캐싱하여 관리자 예약 내역 Paging 조회 API 56% (1.74s -> 1.17s) 성능 개선</h3>
    <p>페이지를 넘길때마다 비효율적으로 호출되던 Count Query로 인한 성능저하를 해결하기 위해, **Look-Aside** 전략을 이용해 **Redis**에 **총 예약건수를 캐싱**하는 방법을 적용했습니다.<br>
예약이 들어오면 캐시 데이터를 지워주고 Cache Miss가 발생하면 Count Query를 새로 실행하고 캐싱하는 방식으로 약 34만 개의 데이터를 대상으로 1.7s → 1.1s 로 성능을 약 56% 향상 시켰습니다.  </p>
    <a href="https://devpoong.tistory.com/124"><p style="font-size: 17px">👉 블로그 글 보기</p></a>
    <h3>4. Custom Exception 더 나은 설계에 대한 고민과 리팩토링 과정</h3>
    <p>예외상황마다 각각 하나의 커스텀 예외를 생성하다보니, 커스텀 예외의 수가 너무 많아지는 문제가 있었습니다.<br>
    이를 해결하기 위해 표준예외를 사용하는 대신 현재 프로젝트에서 주로 발생하는 예외를 대상으로 한번 더 추상화하고<br> ErrorCode를 추가적으로 인자로 넣어주어 가독성을 높이는 방식으로 구현하였습니다.</p>
    <a href="https://devpoong.tistory.com/90"><p style="font-size: 17px">👉 블로그 글 보기</p></a>

<hr>
<h1 id="erd">🛢️ ERD</h1>
<div align="center">
<img src="https://user-images.githubusercontent.com/68465716/238134761-a61ff74a-f7d7-44ea-95ff-331ffbf3d785.png">
</div>
<br>
<hr>
<h1 id="operation">📷️ 실제 키오스크 운영 사진</h1>
<h3>예약 웹 서비스 뿐만 아니라 회의실 2곳에서 키오스크 운영 및 관리중</h3>
<div align="center">
<img src="https://user-images.githubusercontent.com/68465716/238135350-13f36c4a-5f1d-4d0b-b732-5c621ece02b4.jpeg" width="900" align="center"></img>
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
        <p style="font-size: 18px">실제 운영을 위한 관리자 기능을 아래와 같이 제공합니다.</p>
        <p style="font-size: 17px">1. 전체적인 예약 상황 모니터링</p>
        <p style="font-size: 17px">2. 회의공간 비활성화 (공간을 사용하지 못하게 해야하는 경우 특정기간 동안 비활성화)</p>
        <p style="font-size: 17px">3. 위반내역 및 제재내역을 확인 및 정지 해제</p>
        <p style="font-size: 17px">4. 예약 정책과 제재 정책을 수정</p>
        <p style="font-size: 17px">5. 사용자의 민원 확인</p>
    </div>
</div>
