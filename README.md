# T-CATCH

## 🔅 핵심 기능

- 티켓 예매
- 공연 리뷰 작성
- 공지사항과 Q&A 게시판
- 관리자 페이지(티켓관리, 고객관리)


## 🔅 기본 정보
 - 진행 기간 : 2023.02.15~2023.03.07 (3주)
 - 참여 인원 : 6명

<hr>

## 🔅 개발 환경
- Back-end
  - Java 17
  - SpringBoot 3.0
  - Oracle 21c
  - JavaScript & Jquery
  - MyBatis & Spring JPA
  - Maven
  - Lombok
  - Spring Security
  - Tomcat 9.0
 
- Front-end
  - HTML5
  - Thymeleaf
  - JavaScript
  - Jquery


<hr>

## 🔅 서비스 흐름도

<img width="950" alt="image" src="https://user-images.githubusercontent.com/97737386/229759345-d6083d7b-17ca-4515-bfa6-0e26533c5371.png">

<hr>

## 🔅 ER Diagram

![9](https://user-images.githubusercontent.com/97737386/230890793-5c7aaa4e-cca8-4330-b722-37e9d71755a0.png)
<hr>


## 🔅 담당 기능

('▶' 표시된 각 항목 클릭 시 내용을 펼칠 수 있습니다.) 

<details>
<summary><h3> 예매 완료 이메일/문자 전송</h3></summary>

<img width="300" src="https://user-images.githubusercontent.com/97737386/231175621-bb1235ea-b582-444f-90ec-88abbbd33b5c.png">

- 예약 완료 시 자동 전송되는 이메일과 문자메시지입니다.
- HTML 형식의 이메일을 발송하여 예매 내역을 확인할 수 있는 링크를 포함시켰습니다.
- 예매 테이블과 티켓 테이블을 조인해 공연 일시, 장소 등의 정보를 함께 출력했습니다.

</details>

<details>
<summary><h3>Q&A 게시판</h3></summary>

![image](https://user-images.githubusercontent.com/97737386/231177037-7e2b7a87-2e1a-41f5-8990-78a7e695f194.png)

- 답변 여부와 비공개 여부를 아이콘으로 처리해 가독성을 높였습니다.
- 비공개/공개 여부를 설정할 수 있게 했고, 답글 등록과 수정, 삭제는 관리자만 가능하도록 접근을 제어했습니다.
- 답글이 작성될 경우 문의 작성자에게 알림이 전송됩니다.
- 관리자로 접속할 경우, 답글 내용이 있을 경우에만 수정과 삭제가 가능하도록 만들었습니다.

</details>

<details>
<summary><h3>공지사항 게시판</h3></summary>

![image](https://user-images.githubusercontent.com/97737386/231177147-34422a14-90e4-4e77-8431-fe1956175f71.png)
- Spring Security를 적용해 관리자만 공지사항 작성, 수정, 삭제할 수 있도록 제어했습니다.
- 공지사항 수정 페이지 요청 시 DB에 저장된 분류, 제목, 내용 값을 자동으로 출력했습니다.
</details>

<details>
<summary><h3>Q&A & 공지사항 게시판 공통</h3></summary>

<img width="300" src="https://user-images.githubusercontent.com/97737386/231179935-8f0e9638-35df-42a1-a705-8c3a14698a21.png">
<br>
<img width="300" src="https://user-images.githubusercontent.com/97737386/231179959-1515521d-f8f6-44c9-8992-7dd9cf1a865a.png">
<br>
<img width="150" src="https://user-images.githubusercontent.com/97737386/231181145-95047ccd-5011-4c68-a508-ebabcdcae6f2.png">

- 페이징 · 검색 · 카테고리별로 보기 기능 등 공통되는 코드를 모듈화하여 불필요한 반복을 줄였습니다.
- 검색 키워드, 검색 기준 칼럼을 세션에 저장하여 페이지가 넘어가도 그대로 유지되도록 만들었습니다.
- 검색+카테고리별로 보기를 함께 적용 가능하도록 만들었습니다.

</details>

<details>
<summary><h3>Q&A 답변 알림</summary></h3>

<img width="500" src="https://user-images.githubusercontent.com/97737386/231181805-2ead1d70-159c-47f2-a132-2435477bfe35.png">

- Ajax를 이용해 아래의 기능을 구현했습니다.
  - 알림 생성
  - 읽음 처리
  - 안 읽은 알림 갯수 출력
  - 알림 삭제
</details>

<details>
<summary><h3>공연 리뷰</summary></h3>

<img width="600" src="https://user-images.githubusercontent.com/97737386/231184429-d01248a5-7a8a-400c-96e2-dc64a5833d66.png">

- 기본적인 CRUD 및 페이징 처리
- DB TICKET 테이블과 REVIEW 테이블을 Join해 티켓정보를 리뷰와 함께 출력했습니다.
</details>

<details>
<summary><h3>Kakao 주소 API 적용</summary></h3>

<img width="600" src="https://user-images.githubusercontent.com/97737386/231184335-b03c15cc-1bd5-4a09-ac41-2304dd00c5d1.png">

- 회원가입·회원정보 수정 시 사용자에게 주소를 입력받기 위해 Kakao 주소 API를 적용했습니다.
- 주소 API를 담당하는 코드는 모듈화해 재사용했습니다.
</details>

## 🔅 핵심 트러블슈팅

### [1] 알림 기능을 위한 테이블 추가
 
 <img width="700" src="https://user-images.githubusercontent.com/97737386/231192261-b7968bc3-9bc6-49e0-a71d-f8d7381cc526.png">

1. 상황: 제가 구현한 Q&A 알림 기능은 문의글에 답변이 작성될 시 작성자에게 알림을 띄우는 기능입니다. 프로젝트 설계 시 알림 생성, 읽음 처리, 안 읽은 알림 갯수 출력, 삭제 등의 기능을 요구사항으로 작성했습니다. 

2. 문제: 막상 알림 기능을 만들려고 보니, Q&A 테이블에 알림 칼럼을 넣어 구현하기에는 비효율적이라는 것을 깨았습니다. 읽음/안 읽음 여부를 저장하는 칼럼과 정렬을 위한 알람일시 칼럼이 필요하기 때문입니다. 이 칼럼들을 Q&A 테이블에 추가할 수도 있지만, 알림이 아직 생성되지 않았거나 삭제된 문의글도 있을 수 있기 때문에 메모리 활용 측면에서 비효율적일 수 있습니다. 또한 이행종속이 발생해 정규화가 되지 않은 모델이 됩니다.
 
3. 해결: notification 테이블을 위의 이미지와 같이 추가로 설계했습니다. notification 테이블은 customer 테이블과 qna 테이블을 참조하여, join을 통해 각 사용자에 맞는 알림을 출력하고 해당 알림에 연관된 문의 글을 링크할 수 있게 했습니다.

<br>

 ### [2] 코드 중복 제거를 위한 모듈화
 
 1. 상황: Q&A와 공지사항 게시판, 리뷰 목록 페이지에서 페이징, 검색, 카테고리별 보기 기능이 공통으로 사용됩니다.
 
 2. 문제: 처음에는 코드를 복사해서 사용했는데, 코드 수정 시 각 페이지마다 수정해야 해서 효율성이 떨어졌습니다.
 
 3. 해결: 중복되는 코드를 모듈화해 코드 중복을 줄이고 유지보수성을 높였습니다. 
 
 ##### 페이징 기능 모듈화
 
 <details>
  <summary>기존 코드</summary>
  
  ```
        // 페이징
        if (pageNum==null){
            pageNum=1;
        }
        int totalRecord=DBManager.getTotalQnaRecord(hashMap);
        int pageSize=10;
        int totalPage=(int)Math.ceil((double)totalRecord/pageSize);
        if(totalPage==0){
            totalPage=1;
        }
        mav.addObject("totalPage",totalPage);

        // 해당 페이지의 시작 글번호, 끝 글번호
        int startNo=(pageNum-1)*pageSize+1;
        int endNo=pageNum*pageSize;
        hashMap.put("startNo",startNo);
        hashMap.put("endNo",endNo);

        // 페이지를 페이징
        int pageGroupSize=5;   // 한 페이지 당 페이지 번호 몇 개씩 출력할지

        int firstPage=((pageNum-1)/pageGroupSize)*pageGroupSize+1;
        int lastPage=firstPage+pageGroupSize-1;
        if(lastPage>totalPage){
            lastPage=totalPage;
        }
 ```
        
 </details>
 
 <details>
  <summary>개선된 코드</summary>
  
  ```
        // 페이징
        Page page=new Page(DBManager.getTotalQnaRecord(hashMap),10,5,pageNum);

        int totalPage=page.getTotalPage();

        mav.addObject("totalPage",totalPage);

        // 해당 페이지의 시작 글번호, 끝 글번호
        hashMap.put("startNo",page.getStartNo());
        hashMap.put("endNo",page.getEndNo());

        // 해당 페이지에서 보여줄 페이지 번호 첫 번째와 마지막
        int firstPage=page.getFirstPage();
        int lastPage=page.getLastPage();
  ```
  
 </details>
 
 ##### 검색 & 카테고리별 보기 기능 모듈화
 
 <details>
  <summary>기존 코드</summary>
  
  ```
        HashMap<String, Object> hashMap=new HashMap<String,Object>();

        // 카테고리별로 보기
        if(category!=null) {
            switch (category) {
                case "book":
                    category = "예매/드로우";
                    break;
                case "transaction":
                    category = "결제/환불";
                    break;
                case "etc":
                    category = "기타";
                    break;
            }

            // 카테고리가 all이면 세션에 저장된 카테고리 지우기
            if(category.equals("all")) {
                if(session.getAttribute("category")!=null) {
                    session.removeAttribute("category");
                }
                category = null;
            }else{  // all이 아니라 특정 카테고리를 선택했을 경우
                //카테고리를 세션에 저장한다.
                session.setAttribute("category",category);
                //세션에 원래 있던 카테는 필요없음.
            }
            // 카테고리를 클릭 안했을 경우
        }else{
            // 그 전에 클릭했던 게 있다면 (=세션에 저장되어 있다면)
            if(session.getAttribute("category")!=null) {
                //세션에 저장되어 있는 카테를 가져온다.
                category = (String) session.getAttribute("category");

                //둘다 없다면 => 카테고리별로 보기 x => null => 처리 필요 없음.
            }
        }

        hashMap.put("category", category);

        //검색
        if(keyword!=null) {
            session.setAttribute("keyword",keyword);
            session.setAttribute("searchColumn", searchColumn);
            hashMap.put("searchColumn", searchColumn);
            hashMap.put("keyword", keyword);
        }else{
            if(session.getAttribute("keyword")!=null){
                keyword= (String) session.getAttribute("keyword");
                searchColumn= (String) session.getAttribute("searchColumn");
                hashMap.put("searchColumn", searchColumn);
                hashMap.put("keyword", keyword);
            }
        }
  ```
  
 </details>
 
  
  [개선된 코드](https://github.com/HannahKim33/T-Catch/blob/a4604e25d78cc37286b5574953243d91b64a57d2/src/main/java/com/example/finalpro/controller/QnaController.java#L64)
 
 <br>
  
 ### [3] 답글 등록 처리 속도 느린 문제
  
  
  1. 상황: 관리자가 답글 등록 버튼을 누르면 1) DB에 답글을 insert하고, 2) 알림을 발생시키고, 3) 이메일을 자동 발송합니다. 초기 코드에서는 세 가지 기능이 모두 실행된 후 화면에 "등록되었습니다"라는 alert를 출력했습니다.
  
  2. 문제: 1), 2), 3)을 모두 수행하는 데 몇 초 정도의 시간이 걸리기 때문에 사용자 입장에서는 화면에 변화가 없어 일처리가 되고 있는 건지 아닌지 알 수 없습니다.
  
  3. 해결: 기존 코드에서는 하나의 Ajax에서 1), 2), 3)이 순차적으로 실행되었습니다. 하지만 사용자 입장에서는 등록 버튼 클릭 후 2)와 3)을 기다릴 이유가 없습니다. 개선 코드에서는 2)와 3)을 분리해 따로 Ajax 실행되게 해서, 1) 실행 후 alert을 띄우면서 동시에 2)와 3)을 비동기식으로 실행했습니다.
  
  [해당 코드](https://github.com/HannahKim33/T-Catch/blob/173404de41ffae2a491c3bab72a311c62a5ee50d/src/main/resources/static/js/qna/detail.js#L16)

 


## 🔅 느낀 점 

- 모듈화 : 중복되는 코드를 모듈화하면 재사용성이 높아지고 유지보수가 훨씬 쉬워짐을 느꼈습니다.

  상단 네비게이션 바에 로그인을 했을 시 로그아웃, 마이페이지 버튼을 출력하고 로그인을 하지 않았을 시 로그인과 회원가입 버튼을 출력하도록 만들었는데, 이를 위해 로그인 아이디를 세션에 저장하고 불러오는 자바스크립트 코드가 모든 페이지마다 필요했습니다. 처음에는 이 코드를 모든 html 페이지에 직접 입력했습니다. 그 후 한 조원이 일부 페이지에서 이 코드를 수정하면서 코드가 어떤 페이지에서는 동작하고 어떤 페이지에서는 동작하지 않는 문제가 발생했습니다. 또한 네비게이션 바에는 검색 창도 있었는데, 이 또한 이 코드를 만든 조원이 일부 수정하면서 일부 페이지에서만 작동하고 다른 페이지에서는 작동하지 않았습니다.

  저의 제안으로 네비게이션 바와 로그인 코드를 따로 js 파일로 만든 후 각 html 마다 js 파일을 import 하는 방식으로 변경했습니다. 이렇게 하자 로그인 방식과 네비게이션 바 작동 방식에 통일성을 주고 수정 시 모든 html 파일을 수정하지 않고 한 js 파일만 수정하면 되어 유지보수가 훨씬 용이해졌습니다.

- 코드 분리 : DBManager를 테이블 별로 나누지 않고 하나의 클래스에 모든 테이블에 대한 메소드를 작성했습니다. 그 결과 한 파일이 너무 길어져 원하는 코드를 찾기 어려워지고 팀원들의 코드를 merge 할 시 충돌을 해결하기 매우 번거로웠습니다. 다음부터는 코드를 보기 쉽고 합치기 쉽게 하기 위해 기능별로 최대한 분할해 파일을 작성해야겠다고 생각했습니다.

- 설계의 중요성 : 협업으로 이루어지는 프로젝트인 만큼 처음에 최대한 세세하게 설계를 잘 해야 나중에 어려움 없이 수월하게 코딩을 할 수 있다는 것을 알게 되었습니다.

  설계 시 변수명의 규칙은 카멜표기법으로 하고 줄임말을 쓰지 않고 명확한 변수명을 사용하기로 했습니다. 하지만 dao나 vo 처럼 자주 쓰일 변수들의 구체적인 명칭을 정하지는 않았습니다. 그 결과 CustomerDAO 객체를 생성 시 어떤 조원은 customerDao, 다른 조원은 dao라고 쓰는 문제가 발생했습니다. 다음부터는 자주 쓰는 변수명의 경우 명칭을 미리 정하는 것이 좋겠다고 생각했습니다.
 
  DB 모델링 또한 최대한 세세하게 생각해서 해야 함을 느꼈습니다. 코딩을 하면서 DB 구조를 수정해야 하는 일이 자주 발생했습니다. 예를 들어 카카오 주소 API를 적용하기 위해서는 주소를 저장하는 칼럼이 네 개가 필요했는데, 처음 설계 시 하나로만 구성했기 때문에 네 개로 바꿔주어야 했습니다. 그러기 위해서 기존 Customer instance를 삭제해야 했는데, Customer 테이블의 자식 테이블이 많고 자식 테이블로 상속된 customer의 PK의 제약조건이 Not null로 설정되어 있어 각 테이블의 자식 레코드까지 모두 삭제해줘야 했습니다. Not null 제약조건의 경우 개발이 어느 정도 완료된 후 추가하는 것도 좋은 방법이 될 것 같다고 생각했습니다.

- 기반 지식의 중요성 : 기능을 다 완성하기는 했지만 내부 동작 원리를 모르는 채로 코딩한 경우도 있어 앞으로 더 많은 공부가 필요하다는 것을 느꼈습니다. 

  예를 들어 Spring Security를 적용 시, DB를 변경하는 작업을 하는 form에는 crsf 토큰을 포함시켜야 하는데, 프로젝트를 제 시간에 끝내는 것에 신경쓰다 보니 왜 그래야 하는지에 대한 학습을 할 시간은 부족해서 아쉬웠습니다. 앞으로 이런 부분들을 더 공부해나가고 싶습니다.


<details>
<summary><h2>🔅 프로젝트 일지</h2></summary>

- 2/19-2/20
  - Notice와 QNA의 list/detail 조회 기능 완성
- 2/21
  - Spring Security 오류 해결
- 2/22
  - QNA insert, Notice insert 기능 완성
- 2/23
  - qna/detail 페이지에 관리자가 답글 작성/수정/삭제하는 기능 추가
  - QNA 글 공개 선택 여부/해당 작성자인지 여부에 따라 접근 제어
- 2/24
  - qna/insert 페이지에 사용자가 예매한 티켓 정보 출력
- 2/25
  - qna update & delete, notice update & delete 기능 완성
  - 조회수 증가 기능 추가
  - JS 파일 모듈화
- 2/26
  - notice 페이징, 제목/내용 검색 기능, 카테고리별 보기 기능 추가
- 2/27
  - 답변 알림 기능 추가 (새 테이블 notification 추가)
    -클릭하면 숫자 없어지기
    -알림 클릭하면 qna 상세페이지로 연결
    -x 누르면 삭제 (->ajax로) (화면에서 없어지기)
- 2/28
  - 전체 팀원 코드 merge 및 오류 수정
- 3/1
  - 회원가입, 회원정보 수정 페이지에서 카카오 주소 API를 적용해 사용자 주소를 입력받는 기능 추가
- 3/2
  - 예매 완료시 확인 문자메시지 전송 기능 추가
- 3/3
  - 마이페이지 내 후기(Review) 보기, 작성, 수정, 삭제 기능 추가
- 3/4
  - notification 수정(0개일 때 처리(숫자 표시 x, "알림이 없습니다." 출력))
  - myPageBook에서 insertReview로 넘어갈 때 이미 리뷰를 등록한 티켓이면 alert 띄우고 insert 화면으로 안 넘어가게 처리
  - QNA 답글 등록 시 링크가 포함된 email 발송 기능
- 3/5
  - 전반적인 오류 수정 및 전체 브랜치 merge
- 3/6
  - 네비게이션, 검색, 로그인 기능 임포트
  - 마이페이지 리뷰 페이징
  - 레코드가 0개일 경우의 페이징 처리
  - QNA 공개여부, 답변여부 아이콘 처리
  - QNA 공개여부/사용자에 따라 상세페이지 링크 유무 조정
  - 리뷰 등록 시 이미 사용자가 리뷰를 등록한 작품이면 등록페이지로 넘어가지 않게 처리
  - 등록일자 포맷 처리 (분까지만 나오게)
- 3/7
  - 포스터 이미지, 버튼, a태그 CSS 적용
  - 답변 알림, 리뷰 불러오기 오류 수정
</details>
