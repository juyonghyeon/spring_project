# 프로젝트 소개
## 개요
- 트렌드 분석 관련 내용 및 목적


## 역할 분담
- 김문수, url 치환코드 작성, 크롤링 관련 코드 작업, 워드클라우드 생성 파이썬 파일 작업
- 이소민, 30일 데이터 크롤링 코드 작성, 화면 출력 작업
- 정호찬, 7일 데이터 크롤링 코드 작성, 
- 주예성, 30일 데이터 크롤링 코드 작성, 입력 url 관리 코드 작업
- 주용현, 7일 데이터 크롤링 코드 작성, 트렌드 데이터 조회 코드 작업

# 기능 설명
- 입력한 url 웹 페이지의 정보를 크롤링하고 저장
- 크롤링한 데이터를 워드 클라우드 이미지와 그래프를 통해 시각화
- 중요한 코드가 있으면 코드와 함께 설명
- 구현화면에 대한 이미지


## 06 / 07
- 각자의 환경 구성
- 특정 범위 데이터 가져오기 기능 작성 (테스트 못함)


## 06 / 08
- 스프링 부트에서 siteUrl 입력해서 해당 웹페이지 크롤링 / 웹 사이트에서 조회하기 이용해서 치환 가능하게
- 특정 범위 데이터 가져오는 기능 작성만 함 / 돌아가는지 확인
- / etc.html 작성, 꺽은선 그래프 작성, 입력받은 url이랑 데이터 가져오는 기능 연결
- 특정 범위 데이터 워드클라우드로 변경 (첨부터 있는 데이터를 워드클라우드로 or 조회하기 누르면 이전 7일 데이터 싹다 생성및 워드클라우드로)
- 기존 접근 내용
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