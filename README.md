# T-CATCH

## 🔅 핵심 기능

- 티켓 예매 웹사이트
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

#### 예매 완료 이메일/문자 전송

#### Q&A 게시판

#### 공지사항 게시판

#### Q&A 답변 알림

#### 공연 리뷰

#### Kakao 주소 API 적용

## 🔅 핵심 트러블슈팅

<details>
<summary><h4>알림 기능을 위한 테이블 추가</h4></summary>

주요 기능 중 하나인 Q&A 답변 알림 기능을 Q&A 테이블만을 이용해서는 완성할 수 없다는 것을 깨달았습니다.

그래서 notification 테이블을 추가계설계해 알림 생성, 읽음 표시, 삭제, 알림 갯수 출력 등이 가능하게 만들었습니다.

notification 테이블은 qna 테이블과 customer 테이블의 기본키를 참조합니다.
 
</details>

<details>

 <summary><h4>중복 코드 제거를 위한 모듈화</h4></summary>
 
 페이징 기능 모듈화
 
 제가 맡은 리뷰, 공지사항, Q&A는 모두 페이징 기능이 들어가 있습니다. 페이징을 위해 중복되는 코드가 세 번 반복되는 문제가 있었습니다.
 
 그래서 Page라는 Java Class를 만들어 이 클래스를 세 곳에서 사용해 유지보수성과 효율성을 높였습니다. 
 
 <details>
  <summary>기존 코드</summary>
 </details>
 
 <details>
  <summary>개선된 코드</summary>
 </details>
 
 
</details>




<details>
 <summary><h2>🔅 그 외 트러블슈팅</h2></summary>
 <details>
 <summary><h3>Ajax 호출 오류</h3>(cannot find template)</summary>

 ```java
 @GetMapping("/qna/updateAnswer")
   public String insertAnswer(int qna_no, String qna_answer){
       QnaVO q=new QnaVO();
       q.setQna_no(qna_no);
       q.setQna_answer(qna_answer);
       return ""+DBManager.updateAnswer(q);
   }
 ```

 @ResponseBody 를 붙이지 않아서 template[1]을 찾을 수 없다는 에러 뜸. 리턴 값이 1이라서 1이라는 이름의 템플릿을 찾은 듯함
 </details>

<details>
<summary><h3>Security 403 forbidden error</h3></summary>
Security 사용할 경우 form에 토큰 추가해야 함 (DB modify할 때)

```html
<input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}">
```
</details>

<details>
<summary><h3>sqlSessionFactory가 null일 경우</h3></summary>
```xml
Cannot invoke "org.apache.ibatis.session.SqlSessionFactory.openSession()" because "com.example.finalpro.db.DBManager.sqlSessionFactory" is null] with root cause
```

-> mapper 파일 문제

Possible causes:

- xml태그 위치가 잘못되었다.
- xxxMapper.xml 파일을 Configuration 파일에 연결 안 했다.
- Configuration 파일에 mapper resource가 중복되었다.
- alias를 잘못 적었다.
- mapper 파일 내 중복되는 id가 있다.
- mapper 파일 내에 

```xml
<<<<<<<<< Temporary merge branch 1
```

이런 깃 충돌 메시지가 남아 있다.
</details>

<details>
<summary><h3>Controller Mapping 오류</h3></summary>

```xml
Error creating bean with name 'org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration': Unsatisfied dependency expressed through method 'setFilterChains' parameter 0: Error creating bean with name 'filterChain' defined in class path resource [com/example/finalpro/SecurityConfig.class]: Failed to instantiate [org.springframework.security.web.SecurityFilterChain]: Factory method 'filterChain' threw exception with message: Error creating bean with name 'mvcHandlerMappingIntrospector' defined in class path resource [org/springframework/boot/autoconfigure/web/servlet/WebMvcAutoConfiguration$EnableWebMvcConfiguration.class]: Error creating bean with name 'requestMappingHandlerMapping' defined in class path resource [org/springframework/boot/autoconfigure/web/servlet/WebMvcAutoConfiguration$EnableWebMvcConfiguration.class]: Ambiguous mapping. Cannot map 'ticketController' method 
com.example.finalpro.controller.TicketController#main()
to {GET [/main]}: There is already 'customerController' bean method
com.example.finalpro.controller.CustomerController#main(HttpSession, Model) mapped.
```

팀원들 파일을 merge한 후 @Getmapping(”/main”) 이 두 controller에 존재해서 문제가 발생했다. 한 uri당 하나만 매핑되어야 한다.

</details>

<details>
<summary><h3>form 안에 있는 button을 누르면 자동으로 form이 submit 되는 문제</h3></summary>
button을 &lt;button&gt; 말고 &lt;input type=”button”&gt;으로 만들면 해결됨
</details>

<details>
<summary><h3>Mybatis Mapper SQL에서 칼럼 이름을 변수로 지정할 경우 표기법</h3></summary>
```
${변수}
<!--예: order by ${칼럼이름}-->
```

값을 받아올 경우와는 달리 #{}가 아님을 주의!! 
</details>
<details>
<summary><h3>dependency 추가 시 미인식 문제</h3></summary>
pom.xml에 dependency 추가했을 때 프로젝트 reload를 해줘야 dependency를 인식한다.

(IntelliJ) reload 방법: 오른쪽 Maven 탭→프로젝트이름→reload project
</details>

<details>
<summary><h3>service 객체가 null인 경우</h3></summary>
```
Cannot invoke "com.example.finalpro.service.TicketService.findByTicketid(int)" because "this.ts" is null
```
→ @Autowired 를 빠트려서 발생한 문제
</details>

<details>
<summary><h3>Ajax 실행 시 무한루프</h3></summary>
Controller에서 Ajax 작성할 때 메소드 리턴타입을 void로 하면 무한루프가 생길 수 있다.
</details>
</details>

## 🔅 인사이트

- 모듈화 : 중복되는 코드를 모듈화하면 재사용성이 높아지고 유지보수가 훨씬 쉬워짐을 느꼈습니다.

- 설계의 중요성 : 협업으로 이루어지는 프로젝트인 만큼 처음에 최대한 세세하게 설계를 잘 해야 나중에 어려움 없이 수월하게 코딩을 할 수 있다는 것을 알게 되었습니다.

- 협업의 중요성 : 팀원끼리 서로 돕고 알게 된 내용들을 공유함으로써 시너지 효과를 얻을 수 있고, 적극적으로 의사소통해야 효율적으로 일을 함께해 나갈 수 있다는 사실을 알게 되었습니다.

- 각주의 중요성 : 코드마다 설명하는 각주를 닮으로써 협업과 유지보수가 쉬워짐을 알게 되었습니다.

- 기반 지식의 중요성 : 기능을 다 완성하기는 했지만 내부 동작 원리를 모르는 채로 코딩한 경우도 있어 앞으로 더 많은 공부가 필요하다는 것을 느꼈습니다.


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
