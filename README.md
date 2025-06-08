# 프로젝트 소개
## 개요
- 트렌드 분석 관련 내용 및 목적


## 역할 분담
- 김문수, url 치환코드 작성
- 이소민, 30일 데이터 크롤링 코드 작성
- 정호찬, 7일 데이터 크롤링 코드 작성
- 주예성, 30일 데이터 크롤링 코드 작성
- 주용현, 7일 데이터 크롤링 코드 작성

# 기능 설명
- 설명
- 중요한 코드가 있으면 코드와 함께 설명
- 구현화면에 대한 이미지

## 일정기간 데이터 끌어오기
- 스프링 부트에서 조절
- 확인할 데이터는 테이블에서 직접 수정

## 06 / 07
- 특정 범위 데이터 가져오기 기능 작성 (테스트 못함)


## 오늘 한 것 / 내일 해야할 것
- 스프링 부트에서 siteUrl 입력해서 해당 웹페이지 크롤링 / 웹 사이트에서 조회하기 이용해서 치환 가능하게
- 특정 범위 데이터 가져오는 기능 작성만 함 / 돌아가는지 확인
- / etc.html 작성, 꺽은선 그래프 작성, 입력받은 url이랑 데이터 가져오는 기능 연결
- 특정 범위 데이터 워드클라우드로 변경 (첨부터 있는 데이터를 워드클라우드로 or 조회하기 누르면 이전 7일 데이터 싹다 생성및 워드클라우드로)

- 기존에 접근 할려했던 내용
LocalDateTime start = search.getSDate().atStartOfDay();
LocalDateTime end = search.getEDate().atTime(23, 59, 59);
List<Trend> data = repository.getPeriodTrend(category, start, end);

## 워드 클라우드 이미지 생성
image_file = strftime("%Y%m%d%H%M") + "_total.jpg"
wc = WordCloud(font_path='C:/trend/NanumGothic-ExtraBold.ttf',
background_color='white',
max_font_size=100,
width=500, height=300)
cloud = wc.generate_from_text(text)
cloud.to_file(f"{path}/{image_file}")