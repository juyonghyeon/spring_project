import os
import re
import sys
import json
from wordcloud import WordCloud
from time import strftime

# sys.argv에 값이 넘어오지 않은 경우
if len(sys.argv) < 2:
    sys.exit(1)


# # 워드 클라우드 이미지가 저장될 경로 체크 및 생성
filePath = sys.argv[1]



try:
    items = json.loads(sys.argv[2])
except:
    p = re.compile(r'[ㄱ-ㅎ가-힣]+')
    items = p.sub(lambda m: '"' + m.group() + '"', sys.argv[2]) 
    items = json.loads(items)    



# 워드 클라우드 이미지 생성
wc = WordCloud(font_path='C:/trend/NanumGothic-ExtraBold.ttf', 
               background_color='white', 
               max_font_size=100, 
               width=500, height=300)
cloud = wc.generate_from_frequencies(items)
cloud.to_file(f"{filePath}")