## 개요
DNF의 테이베르스 아이템 등급을 <https://developers.neople.co.kr/>에서 호출 후 
네이버 던전앤 파이터 공식 카페에 게시(해당 카페 조회시 가입이 필요) <https://cafe.naver.com/dfither/20183510>

## 개발 환경
Language : open JDK 1.8
IDE : eclipse neon
VCS : Github or Git
VCS Tool : SourceTree
Server : NCloud [Compact] 1vCPU, 2GB Mem
Server OS : Centos7.3

## 개발 기간
20190809 ~ 20190813 (4일, 실작업시간을 얼마안됨..)

## 시스템 서버 환경

shell script 생성
<code>
  java -jar /app/DNF_RatingScheduler.jar >> /app/javaLogs.log
</code>

crontab set 매일 00시마다 실행
<code>
  0 0 * * * sh /app/dnf_shellscript.sh
</code>

## 개선사항
**1. resources path :** 현재 APIKEY.properties 와 DNF_Equipment.json 을 파일로 받고있는데 개발환경(Window10)와 서비스 서버(Centos7.3)이 달라 경로가 달라 개선을 해야한다. 

**2. naverCafeWriter class :** 최대한 모듈화를 비중을 놓고 작업하려 했고, 네이버 카페 글쓰기 api로 호출시 html 코드가 가장 큰 문제가 되었다. cafe와 html code를 분리하려했으나 비중이 없다 생각하여 그대로 병합하여 작업. 결과는 처참히 스파게티가 되어버렸다.
생각이외로 html를 손댈구간이 많았다. 사실상 리펙토링이 필요한 부분이다.
(해결) htmlBuilder 라이브러리를 별도로 구현하여 사용하도록 해 보다 더 가독성이 좋아짐.

**3. log :** 간단한 시스템으로 끝낼려 하여 따로 log파일을 구성하지 않았다. 현재 서버에만 javalog.log에 출력결과물만 기록되도록 개발 하였다.
(해결) println를 사용하여 간단 로그를 출력하도록하였고, 매일 업데이트되는 데이터들은 DB에 들어가도록 수정

**4. UI :** 웹가 앱에서 조회시 서로 style 적용 방법이 달랐다. 예)간단한 글자 색입힐시 앱에서는 <code>\<font style='color:red;'></code> 로 해야지만 색이 입힌다. 글자만 봐도 직업 하나하나 글써보고 코드분석하여 해야한다; 사실상 포기. 시간이 너무 많이걸린다. 최대한 간략 간소하게 다루었다.
  

## 기타
현재 서비스 서버가 월 26,000원인데 현재 10만 크레딧(10/31까지)으로 간신히 버티고있다.
더이상의 **추가 작업**을 없을 것이며, 간간히 오류정도만 잡아줄 것이다.
만약 반응이 좋다면 다른 무료 서버로 이관하여 이 서비스만 연장할것 이다.

## 스크린샷
https://blogfiles.pstatic.net/MjAxOTA4MTNfNjcg/MDAxNTY1NjY0MDk2NTM4.OcHwtEwh0SE7hXkYQ7AZzdvv_Tbp2v9gjiITyzOpzIUg.Mym9XrqqJfI_4WLpWEaN03eH_JYfphousi8Y-sS4wFog.PNG.tony950620/image.png

https://blogfiles.pstatic.net/MjAxOTA4MTNfMjg3/MDAxNTY1NjY0MDY4ODk2.jLsi3CkJuAUN_qMrdjFop2G_gecZZ3EB7nDxf7FCRlEg.A7Jk-FAFbZVASnVRtHt6duiG9--OU5_9rxpikttNYv8g.PNG.tony950620/image.png


## feature/*
20190823 - MariaDB를 구축하여 DB연동. 매일 정시마다 업데이트된 데이터를 INSERT, 카페 글에선 극옵션일 경우 초록색으로 변경하였다.



## hotfix
20190826 - DNF Develop 상점 등급 rest 데이터 00시에 정확히 모든 데이터가 업데이트가 안되는 것으로 확인;
그러므로 00시에 돌던 내 시스템은 어제자의 데이터(업데이트 전)와 오늘날의 데이터(업데이트 된)가 짬뽕됬다..
일단은 긴급히 어제의 모든 장비등급과 오늘 업데이트 될 장비등급이 완전히 같다면 업데이트가 안된것으로 판단하여
2초마다 최대 180초 동안 재호출하도록 변경하였다.

무료로 받는 데이터인지라 따질수도 없고 왜 이러한 하드코딩을 해야하는지 난감한 상황이다;

