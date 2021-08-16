<template>
  <el-container>
    <el-main>
      <el-row :gutter="20" justify="space-around">
        <el-col :span="12">
          <video
            ref="videoElement"
            autoplay
            class="myVideo"
            :class="{ myVideoLive: videoStatus == 'LIVE' }"
          ></video>
        </el-col>
        <el-col :span="12">
          <video
            ref="remoteVideo"
            autoplay
            v-show="videoStatus == 'LIVE'"
            class="remoteVideo"
          ></video>
        </el-col>
      </el-row>
    </el-main>
    <el-footer>
      <el-row class="videoOptions">
        <div class="select">
          <el-select
            v-model="audioInputSelect"
            placeholder="마이크 선택"
            @change="start()"
          >
            <el-option
              v-for="(option, index) in mediaOptions.audioinput"
              :key="index"
              :label="`${index + 1}. ${option.label}`"
              :value="option.deviceId"
            >
            </el-option>
          </el-select>
        </div>
        <div class="select">
          <el-select
            v-model="audioOutputSelect"
            placeholder="스피커 선택"
            @change="changeAudioDestination"
          >
            <el-option
              v-for="(option, index) in mediaOptions.audiooutput"
              :key="index"
              :label="`${index + 1}. ${option.label}`"
              :value="option.deviceId"
            >
            </el-option>
          </el-select>
        </div>

        <div class="select">
          <el-select
            v-model="videoSelect"
            placeholder="카메라 선택"
            @change="start()"
          >
            <el-option
              v-for="(option, index) in mediaOptions.videoinput"
              :key="index"
              :label="`${index + 1}. ${option.label}`"
              :value="option.deviceId"
            >
            </el-option>
          </el-select>
        </div>
        <el-button
          icon="el-icon-monitor"
          @click="startScreenStream"
          v-if="screenShare"
          plain
        >
          화면공유
        </el-button>
        <el-button
          icon="el-icon-video-pause"
          type="button"
          @click="start"
          v-else
          plain
        >
          공유중단
        </el-button>
        <el-button v-if="videoStatus == 'CLOSE'" class="enterBtn" plain
          >버튼자리</el-button
        >
        <el-button
          v-if="isUser && socketRead && localVidReady && videoStatus == 'OPEN'"
          @click="connect"
          class="enterBtn"
          plain
          >상담시작</el-button
        >
        <el-button @click="leave" class="closeBtn" plain>상담종료</el-button>
      </el-row>
    </el-footer>
  </el-container>
</template>

<script>
import { ref, onMounted, reactive, computed } from 'vue';
import { useStore } from 'vuex';
export default {
  setup() {
    const store = useStore();
    const videoElement = ref('');
    const audioInputSelect = ref('');
    const audioOutputSelect = ref('');
    const videoSelect = ref('');
    const remoteVideo = ref('');
    const videoStatus = computed(() => store.state.video_status);
    const sessionId = computed(() => store.getters['get_selected_idx']);
    const screenShare = ref(true);
    const isUser = computed(() => store.getters.is_user); // 유저일때만 상담시작 버튼 보이게
    const localVidReady = ref(false);

    const mediaOptions = reactive({
      audioinput: [],
      audiooutput: [],
      videoinput: [],
    });

    var localStream = null;
    var peerConnection = null;
    var peerStarted = false;
    const closeVideoWindow = () => {
      store.commit("CLOSE_VIDEO");
    };
    // 카메라 / 마이크 목록 가져오기
    onMounted(async () => {
      try {
        const deviceInfos = await navigator.mediaDevices.enumerateDevices();
        gotDevicesList(deviceInfos);
        videoElement.value.volume = 0; // 하울링 방지
        start(); // 기본 장치로 stream 바인딩하기
        socketInit(sessionId.value); // 세션아이디로 소켓열기
      } catch (err) {
        handleError(err);
        // enumerateDevices()에 .then으로 해당 함수의 실행을 마치고 난 뒤에 로딩을 해야
        // 모든 장치를 가져와서 표시가 가능합니다.
      }
    });



    // 리스트에 있는데 미디어 디바이스에 접근이 불가능한 경우임.
    function handleError(error) {
      // 여기서 에러문구 띄워주면 좋을 것 같습니다.
      alert('디바이스를 다른 곳에서 사용 중인지 확인해주시기 바랍니다 ');
      console.log('navigator.MediaDevices.getUserMedia error: ', error.message, error.name);
    }

    // 카메라 / 마이크 목록 가져오기
    // device들의 정보를 받아 해석하여 분류하는 코드
    const gotDevicesList = (deviceInfos) => {
      //장치 가져올 때 초기화 하는 부분
      mediaOptions.audioinput = [];
      mediaOptions.audiooutput = [];
      mediaOptions.videoinput = [];
      for (let i = 0; i < deviceInfos.length; i++) {
        const deviceInfo = deviceInfos[i];
        if (deviceInfo.kind === 'audioinput') {
          mediaOptions.audioinput.push(deviceInfo);
        } else if (deviceInfo.kind === 'audiooutput') {
          mediaOptions.audiooutput.push(deviceInfo);
        } else if (deviceInfo.kind === 'videoinput') {
          mediaOptions.videoinput.push(deviceInfo);
        }
      }
    };

    const start = async () => {
      console.log('IN START');
      //이미 localStream이 있으면 종료하는 부분
      if (localStream) {
        localStream.getTracks().forEach((track) => {
          track.stop();
        });
      }
      const audioSource = audioInputSelect.value; // 선택한 device의 ID
      const videoSource = videoSelect.value; // video device id

      const constraints = {
        audio: {
          deviceId: audioSource ? { exact: audioSource } : undefined,
        },
        video: {
          deviceId: videoSource ? { exact: videoSource } : undefined,
        },
      };
      try {
        const userMedia = await navigator.mediaDevices.getUserMedia(constraints);
        const devices = await gotStream(userMedia);
        gotDevicesList(devices);
        setTimeout(() => {
          localVidReady.value = true;
        }, 1000);
      } catch (err) {
        console.log(err);
        alert('디바이스가 없습니다. 다시 확인해주세요.');
      }
      if (socketRead.value) {
        sendReconnectRequest();
      }
    };

    // 로컬 엘리먼트랑 스트림을 연결하여 보여주기
    const gotStream = (stream) => {
      localStream = stream; // 스트림 접근용 변수에 저장
      videoElement.value.srcObject = stream;
      screenShare.value = true;
      // 비디오 엘리멘트에 스트림을 연결하여 화면에 표시하는 기능
      return navigator.mediaDevices.enumerateDevices();
    };

    // 재접속을 하는 이유는, Stream에 바인딩된 Device가 바뀌어도
    // peer에 반영이 되지 않습니다.
    // renegotiation이 필요한 부분
    function sendReconnectRequest() {
      let req = { type: 'reconnectRequest' };
      socket.send(JSON.stringify(req));
    }
    // ===================The following is socket======================
    // var user = "1a2a3a4a-1a2a3a4a"
    const socketUrl = 'wss://i5d204.p.ssafy.io/api/msgServer/'; // 메세지 시그널링 서버 주소
    // var socketUrl = "wss://59.151.220.195:8088/api/msgServer/"; // 메세지 시그널링 서버 주소
    let socket = null;
    const socketRead = ref(false); // socket이 열려있는지 flag. 소켓 통신이 이벤트 기반 처리기 때문에 flag값 없이는 코딩이 불가능함
    const socketInit = (roomId) => {
      // console.log(socketUrl, roomId);
      console.log(socketUrl);
      console.log(roomId);
      socket = new WebSocket(socketUrl + roomId); // roomId 붙여서 접속
      socket.onclose = function () {
        socket = null;
        peerStarted = false;
        localStream = null;
      };
      socket.onopen = function () {
        console.log('Successfully connected to the server...');
        socketRead.value = true;
        // 소켓 연결 성공
      };
      socket.onclose = function (e) {
        console.log(e);
        console.log('The connection to the server is closed:' + e.code);
        alert('상담이 종료되었습니다.');
        socketRead.value = false;
        peerStarted = false;
        // 스트림 모든 트랙 불러와서 사용 중지
        const stream = videoElement.value.srcObject;
        const tracks = stream.getTracks();
        tracks.forEach(function (track) {
          track.stop();
        });
        videoElement.value.srcObject = null;
        store.commit("CLOSE_VIDEO");

        if (peerConnection != null) {
          peerConnection.close();
          peerConnection = null;
        }
        localStream = null;
        videoElement.value.srcObject = null;
        // 소켓 연결이 끊어지거나, 연결 시도 중 실패할 경우
      };

      socket.onmessage = function (res) {
        var evt = JSON.parse(res.data);
        console.log(evt);
        // 시그널링 서버에서 이루어지는 Handshake는 여기를 참조
        // https://dzone.com/articles/scaling-webrtc-based-applications
        if (evt.type === 'offer') {
          console.log('Receive the offer, set the offer, and send the answer...');
          onOffer(evt);
        } else if (evt.type === 'answer' && peerStarted) {
          console.log('Receive answer, set answer SDP');
          onAnswer(evt);
        } else if (evt.type === 'candidate' && peerStarted) {
          console.log('ICE candidate received...');
          onCandidate(evt);
        } else if (evt.type === 'bye' && peerStarted) {
          console.log('WebRTC communication disconnected');
          stop();
        } else if (evt.type === 'reconnectRequest' && peerStarted) {
          // alert("reconnect req ");
          let res = { type: 'reconnectResponse' };
          peerStarted = false;
          // localStream = null;
          remoteVideo.value.src = '';
          async () => {
            await peerConnection.close();
          };
          peerConnection = null;
          socket.send(JSON.stringify(res));

          // connect();
        } else if (evt.type === 'reconnectResponse' && socketRead.value) {
          peerStarted = false;
          // localStream = null;
          remoteVideo.value.src = '';
          async () => {
            await peerConnection.close();
          };

          peerConnection = null;
          connect();
          // await peerConnection.close();
          // alert("reconnect start")
        }
      };
    };

    const connect = () => {
      if (!peerStarted && localStream && socketRead.value) {
        sendOffer(); // offer 시작
        peerStarted = true;
      } else {
        if (!localStream) {
          alert('Please capture local video data first.');
        }
        if (!socketRead.value) {
          alert('Please open socket before connect.');
        }
        if (peerStarted) {
          alert('Already peer have started.');
        }
      }
      store.commit('LIVE_VIDEO');
      console.log(videoStatus.value);
    };

    const sendOffer = () => {
      peerConnection = prepareNewConnection();
      peerConnection.createOffer(
        function (sessionDescription) {
          //Called on success
          peerConnection.setLocalDescription(sessionDescription);
          console.log('Send: SDP');
          console.log(sessionDescription);
          sendSDP(sessionDescription);
        },
        function (err) {
          //Called on failure
          console.log('Failed to create offer' + err);
        },
        mediaConstraints
      );
    };

    const prepareNewConnection = () => {
      var pc_config = {
        iceServers: [
          {
            url: 'stun:13.124.49.8:3478',
            username: 'test',
            credential: 'test',
          },
          // 방화벽, 네트워크 설정 등의 장벽 때문에 상대방의 네트워크 위치를 파악하기 힘들기 때문에
          // STUN 서버를 통해 정보를 주고 받는다.
        ],
      };
      var peer = null;
      try {
        peer = new webkitRTCPeerConnection(pc_config);
      } catch (e) {
        console.log('Failed to establish connection, error:' + e.message);
      }

      // Send all ICE candidates to the other party
      peer.onicecandidate = function (evt) {
        if (evt.candidate) {
          console.log(evt.candidate);
          console.log(evt.candidate);
          sendCandidate({
            type: 'candidate',
            sdpMLineIndex: evt.candidate.sdpMLineIndex,
            sdpMid: evt.candidate.sdpMid,
            candidate: evt.candidate.candidate,
          });
        }
        // 이론상으론 다대다도 가능하지만, 각 클라이언트에 가해지는 컴퓨팅 부담이 심해져서 1:1로만 사용하기.
      };
      console.log('Add local video stream...');
      peer.addStream(localStream);

      //
      peer.addEventListener('addstream', onRemoteStreamAdded, false);
      // 상대방이 스트림을 추가했을 때, 발생하는 이벤트.
      peer.addEventListener('removestream', onRemoteStreamRemoved, false);

      peer.onconnectionstatechange = function (evt) {
        console.log(evt);
        switch (peer.connectionState) {
          case 'connected':
            // alert("peer connected");
            store.commit("LIVE_VIDEO");
            break;
          case 'disconnected':
            alert('peer disconnected');
            break;
          case 'failed':
            // One or more transports has terminated unexpectedly or in an error
            alert('peer failed');
            break;
          case 'closed':
            alert('peer closed');
            // The connection has been closed
            break;
        }
      };

      // When the remote video stream is received, use the local video element for display
      function onRemoteStreamAdded(event) {
        console.log('Add remote video stream');
        console.log(event.stream);
        // remoteVideo.src = window.URL.createObjectURL(event.stream);
        remoteVideo.value.srcObject = event.stream;
        // #remote-video에 상대방 스트림 바인딩하기
      }

      // When the remote communication ends, cancel the display in the local video element
      function onRemoteStreamRemoved(event) {
        console.log('Remove remote video stream');
        remoteVideo.value.src = '';
      }

      return peer;
    };
    // stop connection
    const leave = () => {
      console.log('hang up.');
      stop();
    };
    const stop = () => {
      // localstream 초기값으로 바꾸
      // track stop 위치 조정해서 넣어보기
      socket.close();
      peerConnection.removeStream(localStream);
      peerConnection.close();
      peerConnection = null;
      peerStarted = false;
      // 소켓 연결 종료 및 피어커넥션 다 종료
      store.commit("CLOSE_VIDEO");
    };
    // 오디오 출력을 변경하는 코드입니다. 로컬에서 이뤄지는 작업이므로
    // 재접속이 필요하지 않습니다.
    const changeAudioDestination = () => {
      const audioDestination = audioOutputSelect.value;
      attachSinkId(remoteVideo.value, audioDestination);
    };

    //해당 element의 오디오 출력을 담당할 장치를 지정합니다.
    const attachSinkId = (element, sinkId) => {
      // if (typeof element.sinkId === "undefined") {
      element
        .setSinkId(sinkId)
        .then(() => {
          console.log(`Success, audio output device attached: ${sinkId}`);
        })
        .catch((error) => {
          let errorMessage = error;
          if (error.name === 'SecurityError') {
            errorMessage = `You need to use HTTPS for selecting audio output device: ${error}`;
            // HTTPS 환경에서 개발이 잘 진행되었다면 특별히 볼 일은 없는 문구이며,
            // 사용자에게 띄울 필요는 없는 문구입니다.
          }
          console.error(errorMessage);
          // Jump back to first output device in the list as it's the default.
          audioOutputSelect.selectedIndex = 0;
        });
      // }
    };

    // ===================The above is socket======================

    // 서로의 화상이 보일 element를 가져오면 되며 아마 vue에선 $refs가 있을텐데 이게 좋은 방법인진 잘 모르겠음
    var mediaConstraints = {
      mandatory: {
        OfferToReceiveAudio: true,
        OfferToReceiveVideo: true,
      },
    };

    //----------------------Exchange information -----------------------
    // 이 부분은 그대로 복붙해서 사용할 것
    function onOffer(evt) {
      console.log('Received offer...');
      console.log(evt);
      setOffer(evt);
      sendAnswer(evt);
      peerStarted = true;
    }

    function onAnswer(evt) {
      console.log('Answer received...');
      console.log(evt);
      setAnswer(evt);
    }

    function onCandidate(evt) {
      var candidate = new RTCIceCandidate({
        sdpMLineIndex: evt.sdpMLineIndex,
        sdpMid: evt.sdpMid,
        candidate: evt.candidate,
      });
      console.log('Candidate received...');
      console.log(candidate);
      peerConnection.addIceCandidate(candidate);
    }

    function sendSDP(sdp) {
      var text = JSON.stringify(sdp);
      console.log('Send sdp.....');
      console.log(text); // "type":"offer"....
      // textForSendSDP.value = text;
      // Send sdp through socket
      socket.send(text);
    }

    //---------------------- Process connection -----------------------
    function sendCandidate(candidate) {
      var text = JSON.stringify(candidate);
      console.log(text); // "type":"candidate","sdpMLineIndex":0,"sdpMid":"0","candidate":"....
      socket.send(text); // socket send
    }

    function setOffer(evt) {
      if (peerConnection) {
        console.error('peerConnection already exists!');
        return;
      }
      peerConnection = prepareNewConnection();
      peerConnection.setRemoteDescription(new RTCSessionDescription(evt));
    }

    function sendAnswer(evt) {
      console.log('Send Answer, create remote session description...');
      if (!peerConnection) {
        console.error('peerConnection does not exist!');
        return;
      }

      peerConnection.createAnswer(
        function (sessionDescription) {
          //When successful
          peerConnection.setLocalDescription(sessionDescription);
          console.log('Send: SDP');
          console.log(sessionDescription);
          sendSDP(sessionDescription);
        },
        function () {
          //When it fails
          console.log('Failed to create Answer');
        },
        mediaConstraints
      );
    }

    function setAnswer(evt) {
      if (!peerConnection) {
        console.error('peerConnection does not exist!');
        return;
      }
      peerConnection.setRemoteDescription(new RTCSessionDescription(evt));
    }

    const startScreenStream = () => {
      navigator.mediaDevices
        .getDisplayMedia()
        .then((stream) => {
          // screenStream = stream;

          // Stream에서 비디오 트랙 제거
          for (let i = 0; i < localStream.getTracks().length; i++) {
            let track = localStream.getTracks()[i];
            if (track.kind === 'video') {
              localStream.removeTrack(track);
            }
          }
          localStream.addTrack(stream.getVideoTracks()[0]);

          if (socketRead.value) {
            sendReconnectRequest();
          }
          // return navigator.mediaDevices.enumerateDevices();
          screenShare.value = false;
        })
        .catch((err) => {
          console.log(err);
          alert('화면 선택이 안되었습니다. 다시 확인해주세요');
        });
      // start();
    };
    return {
      store,
      audioInputSelect,
      audioOutputSelect,
      videoSelect,
      mediaOptions,
      videoElement,
      remoteVideo,
      sessionId,
      screenShare,
      videoStatus,
      isUser,
      socketRead,
      localVidReady,
      gotDevicesList,
      handleError,
      start,
      gotStream,
      socketInit,
      connect,
      sendOffer,
      prepareNewConnection,
      leave,
      stop,
      changeAudioDestination,
      attachSinkId,
      startScreenStream,
      closeVideoWindow,
    };
  },
};
</script>
<style scoped>
.myVideo {
  border: 1px solid black;
  height: 30rem;
}
.myVideoLive {
  border: 1px solid black;
  width: 25rem;
  height: 25rem;
  /* 사이즈 작게 왼쪽 아래 라든가? */
}
.remoteVideo {
  width: 25rem;
  height: 25rem;
  border: 1px solid black;
  /* 꽉찬화면? */
}
.select {
  margin: 0.2rem;
}
.videoOptions {
  justify-content: center;
}
.videoElement {
  width: 500px;
}
.enterBtn {
  background-color: #006f3e;
  color: white;
}
#closeBtn {
  background-color: red;
  color: white;
}
</style>
