# 톡톡톡(Tok! Tok! Tok!)
> **채팅 / 화면공유 / 화상통화 기능을 제공하는 화상 상담 플랫폼**
<div align="center" style="text-align: center">
<img src="https://img.shields.io/badge/java 1.8-007396?style=for-the-badge&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/SpringBoot 2.5.2-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white">
<img src="https://img.shields.io/badge/maven 4.0.0-C71A36?style=for-the-badge&logo=Apache Maven&logoColor=white">
<img src="https://img.shields.io/badge/Docker & DockerCompose 20.10.7 + 3 13.3-2496ED?style=for-the-badge&logo=Docker&logoColor=white">
<img src="https://img.shields.io/badge/Jenkins LTS_JDK11-D24939?style=for-the-badge&logo=Jenkins&logoColor=white">
<img src="https://img.shields.io/badge/Nginx 14.17.3-009639?style=for-the-badge&logo=nginx&logoColor=white">
<img src="https://img.shields.io/badge/COTURN 4.5.1.1-000000?style=for-the-badge&logoColor=white">
<img src="https://img.shields.io/badge/Vue.js 3-4FC08D?style=for-the-badge&logo=Vue.js&logoColor=white">
<img src="https://img.shields.io/badge/IntelliJ IDEA 2021.01-000000?style=for-the-badge&logo=IntelliJ IDEA&logoColor=white">
<img src="https://img.shields.io/badge/Spring Tool Suite 3.9.14-6DB33F?style=for-the-badge&logo=Spring&logoColor=white">
<img src="https://img.shields.io/badge/Visual Studio Code 1.59.0-007ACC?style=for-the-badge&logo=Visual Studio Code&logoColor=white">
<img src="https://img.shields.io/badge/Django-092E20?style=for-the-badge&logo=Django&logoColor=white">
</div>

<div align="center" style="text-align: center">
<img align="center" src="main_image.jpg" width="70%" style="text-align: center"/>
</div>

## 개요
> 기존의 채팅봇 서비스에 WebRTC를 접목하여 고객의 문제를 실시간 화상채팅으로 좀 더 심도있게 들여다보고 해결해 줄 수 있는 솔루션을 구현하였습니다.
- 유튜브 데모 동영상 - https://youtu.be/856b8lwlSAg

## Preview
<div align="center" style="text-align: center">
<img align="center" src="main_image2.gif" width="70%" style="text-align: center"/>
</div>

## Folder
```
.\
|-- spring\
|-- django\
|-- porting_manual\
|  |-- dmp_pdata.sql
|  |-- 포팅_메뉴얼.pdf
|-- jenkins\
|-- postgres\
|-- vue\
```

## 소개 자료
프로젝트를 한눈에 보기 쉽게 정리해놓은 PDF 파일
```
.\
|-- 소개자료.pdf
```
 

## Porting Manual(with dump data)
포팅을 위한 메뉴얼과 initialize를 위한 데이터
```
.\
|-- porting_manual\
|  |-- dmp_pdata.sql
|  |-- 포팅_메뉴얼.pdf
```

## How to install
1. root 디렉토리에서 `docker-compose up`을 합니다.
2. vue 디렉토리에 들어가 `yarn run serve`을 합니다.
3. 이후 아래의 로컬 환경에서 테스트가 가능합니다.

For user :  http://localhost:8080/userchat
For admin : http://localhost:8080/

4. 포팅 메뉴얼을 참고하시면 도움이 됩니다.

## Team
| 이름 | github | 역할 | 비고 |
|--|--|--|--|
| 한주연 | [taxol1203](https://github.com/taxol1203) | Backend | Leader|
| 문일민 | [smileostrich](https://github.com/smileostrich) | Backend | - |
| 김대연 | [daebalprime](https://github.com/daebalprime) | Backend | - |
| 김도형 | [teemollt](https://github.com/teemollt) | Frontend | - |
| 김소빈 | [starempty](https://github.com/starempty) | Frontend | - |
| 최민수 | [neverte](https://github.com/neverte) | Frontend | - |

## Licences
``` Licences   
BSD 3
Copyright (c) 2014, The WebRTC project authors. 

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:
- Redistributions of source code must retain the above copyright notice, 
this list of conditions and the following disclaimer.
- Redistributions in binary form must reproduce the above copyright notice, 
this list of conditions and the following disclaimer in the documentation 
and/or other materials provided with the distribution.
- Neither the name of Google nor the names of its contributors may be 
used to endorse or promote products derived from this software 
without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) 
HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, 
STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
```