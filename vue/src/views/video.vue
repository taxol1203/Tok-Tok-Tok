<template>
  <div class="select">
    <label for="audioSource">Audio input source: </label
    ><select ref="audioInputSelect">
      <option v-for="(option, index) in mediaOptions.audioinput" :key="index">
        {{ option }}
      </option>
    </select>
  </div>

  <div class="select">
    <label for="audioOutput">Audio output destination: </label>
    <select ref="audioOutputSelect">
      <option v-for="(option, index) in mediaOptions.audiooutput" :key="index">
        {{ option }}
      </option>
    </select>
  </div>

  <div class="select">
    <label for="videoSource">Video source: </label
    ><select ref="videoSelect">
      <option v-for="(option, index) in mediaOptions.videoinput" :key="index">
        {{ option }}
      </option>
    </select>
  </div>
  <!-- <button type="button" onclick="socketInit('a');">Socket Init ROOM 1</button>
  <button type="button" onclick="socketInit('b');">Socket Init ROOM 2</button>
  <button type="button" onclick="startVideo();">
    Start capturing video information
  </button>
  <button type="button" onclick="stopVideo();">
    Stop capturing video information
  </button> -->
  <!-- &nbsp;&nbsp;&nbsp;&nbsp;
  <button type="button" onclick="connect();">establish connection</button>
  <button type="button" onclick="startScreenStream();">화면공유</button>
  <button type="button" onclick="stopScreenStream();">화면공유</button>
  <button type="button" onclick="leave();">leave</button>
  <br />
  <div>
    <video
      ref="videoElement"
      autoplay
      style="width: 240px; height: 180px; border: 1px solid black"
    ></video>
    <video
      id="remote-video"
      autoplay
      style="width: 240px; height: 180px; border: 1px solid black"
    ></video> -->
  <!-- </div> -->
</template>

<script>
import { ref, onMounted, reactive } from "vue";
export default {
  setup() {
    const videoElement = ref("");
    const audioInputSelect = ref("");
    const audioOutputSelect = ref("");
    const videoSelect = ref("");
    const mediaOptions = reactive({
      audioinput: [],
      audiooutput: [],
      videoinput: [],
    });
    const value = "";
    const selectors = [audioInputSelect, audioOutputSelect, videoSelect];
    onMounted(async () => {
      try {
        const deviceInfos = await navigator.mediaDevices.enumerateDevices();
        gotDevicesList(deviceInfos);
      } catch {
        handleError;
        // enumerateDevices()에 .then으로 해당 함수의 실행을 마치고 난 뒤에 로딩을 해야
        // 모든 장치를 가져와서 표시가 가능합니다.
        // start(); // 기본 장치로 stream 바인딩하기
        console.log(audioInputSelect);
      }
    });

    // function connect() {
    //   if (!peerStarted && localStream && socketRead) {
    //     sendOffer(); // offer 시작
    //     peerStarted = true;
    //   } else {
    //     if (!localStream) {
    //       alert("Please capture local video data first.");
    //     }
    //     if (!socketRead) {
    //       alert("Please open socket before connect.");
    //     }
    //     if (peerStarted) {
    //       alert("Already peer have started.");
    //     }
    //   }
    // }

    // // stop connection
    // function leave() {
    //   console.log("hang up.");
    //   stop();
    // }

    // function stop() {
    //   socket.close();
    //   peerConnection.removeStream(localStream);
    //   peerConnection.close();
    //   peerConnection = null;
    //   peerStarted = false;
    //   // 소켓 연결 종료 및 피어커넥션 다 종료
    // }

    // 카메라 / 마이크 목록 가져오기
    // device들의 정보를 받아 해석하여 분류하는 코드
    const gotDevicesList = (deviceInfos) => {
      for (let i = 0; i < deviceInfos.length; i++) {
        const deviceInfo = deviceInfos[i];
        if (deviceInfo.kind === "audioinput") {
          mediaOptions.audioinput.push(deviceInfo.label);
        } else if (deviceInfo.kind === "audiooutput") {
          mediaOptions.audiooutput.push(deviceInfo.label);
        } else if (deviceInfo.kind === "videoinput") {
          mediaOptions.videoinput.push(deviceInfo.label);
        }
      }
    };
    // 해당 element의 오디오 출력을 담당할 장치를 지정합니다.
    // function attachSinkId(element, sinkId) {
    //   // if (typeof element.sinkId === "undefined") {
    //   element
    //     .setSinkId(sinkId)
    //     .then(() => {
    //       console.log(`Success, audio output device attached: ${sinkId}`);
    //     })
    //     .catch((error) => {
    //       let errorMessage = error;
    //       if (error.name === "SecurityError") {
    //         errorMessage = `You need to use HTTPS for selecting audio output device: ${error}`;
    //         // HTTPS 환경에서 개발이 잘 진행되었다면 특별히 볼 일은 없는 문구이며,
    //         // 사용자에게 띄울 필요는 없는 문구입니다.
    //       }
    //       console.error(errorMessage);
    //       // Jump back to first output device in the list as it's the default.
    //       audioOutputSelect.selectedIndex = 0;
    //     });
    //   // } 
    // }

    // // 오디오 출력을 변경하는 코드입니다. 로컬에서 이뤄지는 작업이므로
    // // 재접속이 필요하지 않습니다.
    // function changeAudioDestination() {
    //   const audioDestination = audioOutputSelect.value;
    //   attachSinkId(remoteVideo, audioDestination);
    // }

    // // 로컬 엘리먼트랑 스트림을 연결하여 보여주기
    // function gotStream(stream) {
    //   console.log(stream);
    //   localStream = stream; // 스트림 접근용 변수에 저장
    //   videoElement.srcObject = stream;
    //   // 비디오 엘리멘트에 스트림을 연결하여 화면에 표시하는 기능
    //   return navigator.mediaDevices.enumerateDevices();
    // }

    function handleError(error) {
      console.log(
        // 리스트에 있는데 미디어 디바이스에 접근이 불가능한 경우임.
        // 여기서 에러문구 띄워주면 좋을 것 같습니다.
        // 다른 디바이스에서 사용중인지는 않은지 확인해주시기 바랍니다 같은....
        "navigator.MediaDevices.getUserMedia error: ",
        error.message,
        error.name
      );
    }
    return {
      audioInputSelect,
      audioOutputSelect,
      videoSelect,
      mediaOptions,
      value,
      selectors,
      videoElement,
      gotDevicesList,
      handleError,

    };
    // // var screenStream = undefined;

    // function startScreenStream() {
    //   navigator.mediaDevices.getDisplayMedia()
    //     .then((stream) => {
    //       // screenStream = stream;

    //       // Stream에서 비디오 트랙 제거
    //       for (i = 0; i < localStream.getTracks().length; i++) {
    //         let track = localStream.getTracks()[i];
    //         if (track.kind === "video") {
    //           localStream.removeTrack(track);
    //         }
    //       }
    //       localStream.addTrack(stream.getVideoTracks()[0]);

    //       if (socketRead) {
    //         sendReconnectRequest();
    //       }
    //       // return navigator.mediaDevices.enumerateDevices();
    //     })
    //     .catch((err) => {
    //       console.log(err);
    //       alert("화면 선택이 안되었습니다. 다시 확인해주세요");
    //     });
    //   // start();
    // }
    // function stopScreenStream() {
    //   // 화면 공유의 경우에는, 스트림을 생성한 뒤에 카메라로 연결된
    //   // 비디오 트랙을 제거 후 화면공유 트랙을 집어넣는 방식입니다.
    //   // 그렇기에 그냥 start() 하면 알ㅇ아서 지금 선택된 카메라로
    //   // 스트림을 다시 생성합니다.
    //   start();
    // }
    // // 선택한 오디오 비디오 디바이스를 element에 바인딩하고
    // // 소켓에 연결까지 함
    // function start() {
    //   if (localStream) {
    //     localStream.getTracks().forEach((track) => {
    //       track.stop();
    //     });
    //   }
    //   const audioSource = audioInputSelect.value; // 선택한 device의 ID
    //   const videoSource = videoSelect.value;

    //   const constraints = {
    //     audio: {
    //       deviceId: audioSource ? { exact: audioSource } : undefined,
    //       // 현재 option에서 선택된 id를 보유한 오디오 소스를 붙인다
    //     },
    //     video: {
    //       deviceId: videoSource ?
    //         { exact: videoSource }
    //         : undefined,
    //     },
    //   };
    //   navigator.mediaDevices
    //     .getUserMedia(constraints)
    //     .then(gotStream) // html element와 video/audio를 부 착한다
    //     .then(gotDevicesList) // 디바이스 목록을 최신화한다.
    //     .then(() => {
    //       if (socketRead) {
    //         sendReconnectRequest(); // 만약 소켓이 연결되어있다면 재접속 
    //       }
    //     });
    //   // getUserMedia -> 반드시 .then으로 다룰 것. 
    //   // .catch(handleError);
    //   // console.log("start");
    //   // console.log(socketRead);
    // }

    // // 재접속을 하는 이유는, Stream에 바인딩된 Device가 바뀌어도
    // // peer에 반영이 되지 않습니다.
    // // renegotiation이 필요한 부분
    // function sendReconnectRequest() {
    //   let req = { type: "reconnectRequest" };
    //   socket.send(JSON.stringify(req));
    // }

    // // function changeTracks(newStream) {
    // //   let newTracks = newStream.getTracks().forEach((newTrack) => {
    // //     console.log(newTrack);

    // //     localStream.addTrack(newTrack);
    // //   });
    // // }

    // audioInputSelect.onchange = start;
    // audioOutputSelect.onchange = changeAudioDestination;
    // videoSelect.onchange = start;
    // // ===================The following is socket======================
    // // var user = "1a2a3a4a-1a2a3a4a"
    // var socketUrl = "wss://i5d204.p.ssafy.io/api/msgServer/"; // 메세지 시그널링 서버 주소
    // // var socketUrl = "wss://59.151.220.195:8088/api/msgServer/"; // 메세지 시그널링 서버 주소
    // var socket = null;
    // var socketRead = false; // socket이 열려있는지 flag. 소켓 통신이 이벤트 기반 처리기 때문에 flag값 없이는 코딩이 불가능함
    // function socketInit(roomId) {
    //   console.log(socketUrl);
    //   socket = new WebSocket(socketUrl + roomId); // roomId 붙여서 접속
    //   console.log("!");
    //   socket.onclose = function () {
    //     socket = null;
    //     peerStarted = false;
    //     localStream = null;
    //   };
    //   socket.onopen = function () {
    //     console.log("Successfully connected to the server...");
    //     socketRead = true;
    //     // 소켓 연결 성공
    //   };
    //   socket.onclose = function (e) {
    //     console.log(e);
    //     console.log("The connection to the server is closed:" + e.code);
    //     alert("상담이 종료되었습니다.");
    //     socketRead = false;
    //     peerStarted = false;
    //     if (peerConnection != null) {
    //       peerConnection.close();
    //       peerConnection = null;
    //     }
    //     localStream = null;
    //     localVideo.srcObject = null;
    //     // 소켓 연결이 끊어지거나, 연결 시도 중 실패할 경우
    //   };

    //   socket.onmessage = function (res) {
    //     var evt = JSON.parse(res.data);
    //     console.log(evt);
    //     // 시그널링 서버에서 이루어지는 Handshake는 여기를 참조
    //     // https://dzone.com/articles/scaling-webrtc-based-applications
    //     if (evt.type === "offer") {
    //       console.log(
    //         "Receive the offer, set the offer, and send the answer..."
    //       );
    //       onOffer(evt);
    //     } else if (evt.type === "answer" && peerStarted) {
    //       console.log("Receive answer, set answer SDP");
    //       onAnswer(evt);
    //     } else if (evt.type === "candidate" && peerStarted) {
    //       console.log("ICE candidate received...");
    //       onCandidate(evt);
    //     } else if (evt.type === "bye" && peerStarted) {
    //       console.log("WebRTC communication disconnected");
    //       stop();
    //     } else if (evt.type === "reconnectRequest" && peerStarted) {
    //       // alert("reconnect req ");
    //       let res = { type: "reconnectResponse" };
    //       peerStarted = false;
    //       // localStream = null;
    //       remoteVideo.src = "";
    //       async () => { await peerConnection.close(); };
    //       peerConnection = null;
    //       socket.send(JSON.stringify(res));

    //       // connect();
    //     } else if (evt.type === "reconnectResponse" && socketRead) {
    //       peerStarted = false;
    //       // localStream = null;
    //       remoteVideo.src = "";
    //       async () => { await peerConnection.close(); };

    //       peerConnection = null;
    //       connect();
    //       // await peerConnection.close();
    //       // alert("reconnect start")
    //     }
    //   };
    // }

    // // ===================The above is socket======================

    // var localVideo = document.getElementById("local-video");
    // var remoteVideo = document.getElementById("remote-video");
    // localVideo.volume = 0; // 하울링 방지
    // // localVideo.mute = true;
    // // 서로의 화상이 보일 element를 가져오면 되며 아마 vue에선 $refs가 있을텐데 이게 좋은 방법인진 잘 모르겠음

    var localStream = null;
    var peerConnection = null;
    var peerStarted = false;
    // var mediaConstraints = {
    //   mandatory: {
    //     OfferToReceiveAudio: true,
    //     OfferToReceiveVideo: true,
    //   },
    // };

    // //----------------------Exchange information -----------------------
    // // 이 부분은 그대로 복붙해서 사용할 것
    // function onOffer(evt) {
    //   console.log("Received offer...");
    //   console.log(evt);
    //   setOffer(evt);
    //   sendAnswer(evt);
    //   peerStarted = true;
    // }

    // function onAnswer(evt) {
    //   console.log("Answer received...");
    //   console.log(evt);
    //   setAnswer(evt);
    // }

    // function onCandidate(evt) {
    //   var candidate = new RTCIceCandidate({
    //     sdpMLineIndex: evt.sdpMLineIndex,
    //     sdpMid: evt.sdpMid,
    //     candidate: evt.candidate,
    //   });
    //   console.log("Candidate received...");
    //   console.log(candidate);
    //   peerConnection.addIceCandidate(candidate);
    // }

    // function sendSDP(sdp) {
    //   var text = JSON.stringify(sdp);
    //   console.log("Send sdp.....");
    //   console.log(text); // "type":"offer"....
    //   // textForSendSDP.value = text;
    //   // Send sdp through socket
    //   socket.send(text);
    // }

    // //---------------------- Process connection -----------------------
    // function sendCandidate(candidate) {
    //   var text = JSON.stringify(candidate);
    //   console.log(text); // "type":"candidate","sdpMLineIndex":0,"sdpMid":"0","candidate":"....
    //   socket.send(text); // socket send
    // }
    // function prepareNewConnection() {
    //   var pc_config = {
    //     iceServers: [
    //       {
    //         url: "stun:13.124.49.8:3478",
    //         username: "test",
    //         credential: "test",
    //       },
    //       // 방화벽, 네트워크 설정 등의 장벽 때문에 상대방의 네트워크 위치를 파악하기 힘들기 때문에
    //       // STUN 서버를 통해 정보를 주고 받는다.
    //     ],
    //   };
    //   var peer = null;
    //   try {
    //     peer = new webkitRTCPeerConnection(pc_config);
    //   } catch (e) {
    //     console.log("Failed to establish connection, error:" + e.message);
    //   }

    //   // Send all ICE candidates to the other party
    //   peer.onicecandidate = function (evt) {
    //     if (evt.candidate) {
    //       console.log(evt.candidate);
    //       console.log(evt.candidate);
    //       sendCandidate({
    //         type: "candidate",
    //         sdpMLineIndex: evt.candidate.sdpMLineIndex,
    //         sdpMid: evt.candidate.sdpMid,
    //         candidate: evt.candidate.candidate,
    //       });
    //     }
    //     // 이론상으론 다대다도 가능하지만, 각 클라이언트에 가해지는 컴퓨팅 부담이 심해져서 1:1로만 사용하기.
    //   };
    //   console.log("Add local video stream...");
    //   peer.addStream(localStream);

    //   // 
    //   peer.addEventListener("addstream", onRemoteStreamAdded, false);
    //   // 상대방이 스트림을 추가했을 때, 발생하는 이벤트.
    //   peer.addEventListener("removestream", onRemoteStreamRemoved, false);

    //   peer.onconnectionstatechange = function (evt) {
    //     console.log(evt);
    //     switch (peer.connectionState) {
    //       case "connected":
    //         // alert("peer connected");
    //         break;
    //       case "disconnected":
    //         alert("peer disconnected");
    //         break;
    //       case "failed":
    //         // One or more transports has terminated unexpectedly or in an error
    //         alert("peer failed");
    //         break;
    //       case "closed":
    //         alert("peer closed");
    //         // The connection has been closed
    //         break;
    //     }
    //   };

    //   // When the remote video stream is received, use the local video element for display
    //   function onRemoteStreamAdded(event) {
    //     console.log("Add remote video stream");
    //     console.log(event.stream);
    //     // remoteVideo.src = window.URL.createObjectURL(event.stream);
    //     remoteVideo.srcObject = event.stream;
    //     // #remote-video에 상대방 스트림 바인딩하기
    //   }

    //   // When the remote communication ends, cancel the display in the local video element
    //   function onRemoteStreamRemoved(event) {
    //     console.log("Remove remote video stream");
    //     remoteVideo.src = "";
    //   }

    //   return peer;
    // }

    // function sendOffer() {
    //   peerConnection = prepareNewConnection();
    //   peerConnection.createOffer(
    //     function (sessionDescription) {
    //       //Called on success
    //       peerConnection.setLocalDescription(sessionDescription);
    //       console.log("Send: SDP");
    //       console.log(sessionDescription);
    //       sendSDP(sessionDescription);
    //     },
    //     function (err) {
    //       //Called on failure
    //       console.log("Failed to create offer");
    //     },
    //     mediaConstraints
    //   );
    // }

    // function setOffer(evt) {
    //   if (peerConnection) {
    //     console.error("peerConnection already exists!");
    //     return;
    //   }
    //   peerConnection = prepareNewConnection();
    //   peerConnection.setRemoteDescription(new RTCSessionDescription(evt));
    // }

    // function sendAnswer(evt) {
    //   console.log("Send Answer, create remote session description...");
    //   if (!peerConnection) {
    //     console.error("peerConnection does not exist!");
    //     return;
    //   }

    //   peerConnection.createAnswer(
    //     function (sessionDescription) {
    //       //When successful
    //       peerConnection.setLocalDescription(sessionDescription);
    //       console.log("Send: SDP");
    //       console.log(sessionDescription);
    //       sendSDP(sessionDescription);
    //     },
    //     function () {
    //       //When it fails
    //       console.log("Failed to create Answer");
    //     },
    //     mediaConstraints
    //   );
    // }

    // function setAnswer(evt) {
    //   if (!peerConnection) {
    //     console.error("peerConnection does not exist!");
    //     return;
    //   }
    //   peerConnection.setRemoteDescription(new RTCSessionDescription(evt));
    // }
  }
}
</script>
