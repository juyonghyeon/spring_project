window.addEventListener("DOMContentLoaded", function() {
    /* 트렌드 통계 데이터 처리 S */
    let data = document.getElementById("chart-data").innerHTML;
    data = JSON.parse(data);

    const labels = Object.keys(data);
    const values = Object.values(data);
    /* 트렌드 통계 데이터 처리 E */

const ctx1 = document.getElementById('lineChart1');
     new Chart(ctx1, {
       type: 'line',
       data: {
         labels,
         datasets: [{
           label: '일주일간의 누적 트렌드',
           data: values,
           borderWidth: 1
         }]
       },
       options: {
         scales: {
           y: {
             beginAtZero: true
           }
         }
       }
     });

    const ctx2 = document.getElementById('lineChart2');
     new Chart(ctx2, {
       type: 'line',
       data: {
         labels,
         datasets: [{
           label: '한달간의 누적 트렌드',
           data: values,
           borderWidth: 1
         }]
       },
       options: {
         scales: {
           y: {
             beginAtZero: true
           }
         }
       }

       // 커밋용 주석 생성

       // 커밋용 주석 생성

       // 커밋용 주석 생성

       // 커밋용 주석 생성
     });
);