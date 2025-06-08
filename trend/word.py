import os
import sys
import json
from wordcloud import WordCloud
from time import strftime

# sys.argv에 값이 넘어오지 않은 경우
if len(sys.argv) < 2:
    sys.exit(1)

# 워드 클라우드 이미지가 저장될 경로 체크 및 생성
path = sys.argv[1] if sys.argv[1] else "c:/tmp"

if not os.path.isdir(path):
    os.mkdir(path)

text = sys.argv[2]

# 워드 클라우드 이미지 생성
image_file = strftime("%Y%m%d%H%M") + "_total.jpg"
wc = WordCloud(font_path='C:/trend/NanumGothic-ExtraBold.ttf', 
               background_color='white', 
               max_font_size=100, 
               width=500, height=300)
cloud = wc.generate_from_text(text)
cloud.to_file(f"{path}/{image_file}")