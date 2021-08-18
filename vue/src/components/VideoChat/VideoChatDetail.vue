<template>
  <div style="height: 100%">
    <el-row class="videoStyle centerOptions">
      <video
        ref="videoElement"
        autoplay
        class="myVideo radiusRectangular commonVideo"
        :class="{ myVideoLive: videoStatus == 'LIVE' }"
      ></video>
    </el-row>
    <el-row class="videoStyle centerOptions">
      <video
        ref="remoteVideo"
        autoplay
        v-show="videoStatus == 'LIVE'"
        class="remoteVideo radiusRectangular commonVideo"
      ></video>
      <div v-show="videoStatus != 'LIVE'">
        <div>
          <img class="clear-img" src="@/assets/HiphopLogo.png" alt="logo" />
          <p id="connectingMsg">
            상대방과의 연결 중입니다. <br />
            잠시만 기다려주세요.
          </p>
        </div>
      </div>
    </el-row>
    <el-row class="centerOptions">
      <div class="select">
        <el-select v-model="audioInputSelect" placeholder="마이크 선택" @change="start()">
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
        <el-select v-model="videoSelect" placeholder="카메라 선택" @change="start()">
          <el-option
            v-for="(option, index) in mediaOptions.videoinput"
            :key="index"
            :label="`${index + 1}. ${option.label}`"
            :value="option.deviceId"
          >
          </el-option>
        </el-select>
      </div>
    </el-row>
    <el-row class="centerOptions">
      <el-button
        icon="el-icon-monitor"
        @click="startScreenStream"
        class="enterBtn"
        v-if="screenShare"
      >
        화면공유
      </el-button>
      <el-button icon="el-icon-video-pause" type="button" @click="start" class="stopBtn" v-else>
        공유중단
      </el-button>
      <el-button
        v-if="isUser && socketRead && localVidReady && videoStatus == 'OPEN'"
        @click="connect"
        class="enterBtn"
        >상담시작</el-button
      >
      <el-button icon="el-icon-phone" @click="leave" class="closeBtn">상담종료</el-button>
    </el-row>
  </div>
</template>

<script>
import { ref, onMounted, reactive, computed } from "vue";
import { useStore } from "vuex";
export default {
  setup() {
    const store = useStore();
    const videoElement = ref("");
    const audioInputSelect = ref("");
    const audioOutputSelect = ref("");
    const videoSelect = ref("");
    const remoteVideo = ref("");
    const videoStatus = computed(() => store.state.video_status);
    const sessionId = computed(() => store.getters["get_selected_idx"]);
    const screenShare = ref(true);
    const isUser = computed(() => store.getters.is_user);
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
    onMounted(async () => {
      try {
        const deviceInfos = await navigator.mediaDevices.enumerateDevices();
        gotDevicesList(deviceInfos);
        videoElement.value.volume = 0;
        start();
        socketInit(sessionId.value);
      } catch (err) {
        handleError(err);
      }
    });

    function handleError(error) {
      alert("디바이스를 다른 곳에서 사용 중인지 확인해주시기 바랍니다 ");
      console.log("error: ", error.message, error.name);
    }

    const gotDevicesList = (deviceInfos) => {
      mediaOptions.audioinput = [];
      mediaOptions.audiooutput = [];
      mediaOptions.videoinput = [];
      for (let i = 0; i < deviceInfos.length; i++) {
        const deviceInfo = deviceInfos[i];
        if (deviceInfo.kind === "audioinput") {
          mediaOptions.audioinput.push(deviceInfo);
        } else if (deviceInfo.kind === "audiooutput") {
          mediaOptions.audiooutput.push(deviceInfo);
        } else if (deviceInfo.kind === "videoinput") {
          mediaOptions.videoinput.push(deviceInfo);
        }
      }
    };

    const start = async () => {
      if (localStream) {
        localStream.getTracks().forEach((track) => {
          track.stop();
        });
      }
      const audioSource = audioInputSelect.value;
      const videoSource = videoSelect.value;

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
        alert("디바이스가 없습니다. 다시 확인해주세요.");
      }
      if (socketRead.value) {
        sendReconnectRequest();
      }
    };

    const gotStream = (stream) => {
      localStream = stream;
      videoElement.value.srcObject = stream;
      screenShare.value = true;
      return navigator.mediaDevices.enumerateDevices();
    };

    function sendReconnectRequest() {
      let req = { type: "reconnectRequest" };
      socket.send(JSON.stringify(req));
    }

    const socketUrl = "wss://i5d204.p.ssafy.io/api/msgServer/";
    let socket = null;
    const socketRead = ref(false);
    const socketInit = (roomId) => {
      socket = new WebSocket(socketUrl + roomId);
      socket.onclose = function () {
        socket = null;
        peerStarted = false;
        localStream = null;
      };
      socket.onopen = function () {
        socketRead.value = true;
      };
      socket.onclose = function (e) {
        alert("상담이 종료되었습니다.");
        socketRead.value = false;
        peerStarted = false;
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
      };

      socket.onmessage = function (res) {
        var evt = JSON.parse(res.data);
        if (evt.type === "offer") {
          onOffer(evt);
        } else if (evt.type === "answer" && peerStarted) {
          onAnswer(evt);
        } else if (evt.type === "candidate" && peerStarted) {
          onCandidate(evt);
        } else if (evt.type === "bye" && peerStarted) {
          stop();
        } else if (evt.type === "reconnectRequest" && peerStarted) {
          let res = { type: "reconnectResponse" };
          peerStarted = false;
          remoteVideo.value.src = "";
          async () => {
            await peerConnection.close();
          };
          peerConnection = null;
          socket.send(JSON.stringify(res));
        } else if (evt.type === "reconnectResponse" && socketRead.value) {
          peerStarted = false;
          remoteVideo.value.src = "";
          async () => {
            await peerConnection.close();
          };

          peerConnection = null;
          connect();
        }
      };
    };

    const connect = () => {
      if (!peerStarted && localStream && socketRead.value) {
        sendOffer();
        peerStarted = true;
      } else {
        if (!localStream) {
          alert("Please capture local video data first.");
        }
        if (!socketRead.value) {
          alert("Please open socket before connect.");
        }
        if (peerStarted) {
          alert("Already peer have started.");
        }
      }
      store.commit("LIVE_VIDEO");
    };

    const sendOffer = () => {
      peerConnection = prepareNewConnection();
      peerConnection.createOffer(
        function (sessionDescription) {
          peerConnection.setLocalDescription(sessionDescription);
          sendSDP(sessionDescription);
        },
        function (err) {
          console.log("error:" + err);
        },
        mediaConstraints
      );
    };

    const prepareNewConnection = () => {
      var pc_config = {
        iceServers: [
          {
            url: "stun:13.124.49.8:3478",
            username: "test",
            credential: "test",
          },
        ],
      };
      var peer = null;
      try {
        peer = new webkitRTCPeerConnection(pc_config);
      } catch (e) {
        console.log("error:" + e.message);
      }
      peer.onicecandidate = function (evt) {
        if (evt.candidate) {
          sendCandidate({
            type: "candidate",
            sdpMLineIndex: evt.candidate.sdpMLineIndex,
            sdpMid: evt.candidate.sdpMid,
            candidate: evt.candidate.candidate,
          });
        }
      };
      peer.addStream(localStream);
      peer.addEventListener("addstream", onRemoteStreamAdded, false);
      peer.addEventListener("removestream", onRemoteStreamRemoved, false);

      peer.onconnectionstatechange = function (evt) {
        switch (peer.connectionState) {
          case "connected":
            store.commit("LIVE_VIDEO");
            break;
          case "disconnected":
            alert("peer disconnected");
            break;
          case "failed":
            alert("peer failed");
            break;
          case "closed":
            alert("peer closed");
            break;
        }
      };

      function onRemoteStreamAdded(event) {
        remoteVideo.value.srcObject = event.stream;
      }

      function onRemoteStreamRemoved(event) {
        remoteVideo.value.src = "";
      }

      return peer;
    };

    const leave = () => {
      stop();
    };
    const stop = () => {
      socket.close();
      peerConnection.removeStream(localStream);
      peerConnection.close();
      peerConnection = null;
      peerStarted = false;
      store.commit("CLOSE_VIDEO");
    };

    const changeAudioDestination = () => {
      const audioDestination = audioOutputSelect.value;
      attachSinkId(remoteVideo.value, audioDestination);
    };

    const attachSinkId = (element, sinkId) => {
      element
        .setSinkId(sinkId)
        .then(() => {})
        .catch((error) => {
          let errorMessage = error;
          if (error.name === "SecurityError") {
            errorMessage = `You need to use HTTPS for selecting audio output device: ${error}`;
          }
          console.error(errorMessage);
          audioOutputSelect.selectedIndex = 0;
        });
    };

    var mediaConstraints = {
      mandatory: {
        OfferToReceiveAudio: true,
        OfferToReceiveVideo: true,
      },
    };

    function onOffer(evt) {
      setOffer(evt);
      sendAnswer(evt);
      peerStarted = true;
    }

    function onAnswer(evt) {
      setAnswer(evt);
    }

    function onCandidate(evt) {
      var candidate = new RTCIceCandidate({
        sdpMLineIndex: evt.sdpMLineIndex,
        sdpMid: evt.sdpMid,
        candidate: evt.candidate,
      });
      peerConnection.addIceCandidate(candidate);
    }

    function sendSDP(sdp) {
      var text = JSON.stringify(sdp);
      socket.send(text);
    }

    function sendCandidate(candidate) {
      var text = JSON.stringify(candidate);
      socket.send(text);
    }

    function setOffer(evt) {
      if (peerConnection) {
        console.error("peerConnection already exists!");
        return;
      }
      peerConnection = prepareNewConnection();
      peerConnection.setRemoteDescription(new RTCSessionDescription(evt));
    }

    function sendAnswer(evt) {
      if (!peerConnection) {
        console.error("peerConnection does not exist!");
        return;
      }

      peerConnection.createAnswer(
        function (sessionDescription) {
          peerConnection.setLocalDescription(sessionDescription);
          sendSDP(sessionDescription);
        },
        function () {
          console.log("error");
        },
        mediaConstraints
      );
    }

    function setAnswer(evt) {
      if (!peerConnection) {
        console.error("error");
        return;
      }
      peerConnection.setRemoteDescription(new RTCSessionDescription(evt));
    }

    const startScreenStream = () => {
      navigator.mediaDevices
        .getDisplayMedia()
        .then((stream) => {
          for (let i = 0; i < localStream.getTracks().length; i++) {
            let track = localStream.getTracks()[i];
            if (track.kind === "video") {
              localStream.removeTrack(track);
            }
          }
          localStream.addTrack(stream.getVideoTracks()[0]);

          if (socketRead.value) {
            sendReconnectRequest();
          }
          screenShare.value = false;
        })
        .catch((err) => {
          console.log(err);
          alert("화면이 선택되지 않았습니다. 다시 확인해주세요");
        });
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
.videoStyle {
  height: 35%;
  padding-bottom: 30px;
  overflow: hidden;
}
.commonVideo {
  width: auto;
  height: 100%;
  overflow: hidden;
}

.clear-img {
  width: auto;
  height: auto;
  max-width: 80%;
  opacity: 0.3;
}

.centerOptions {
  justify-content: center;
  padding-bottom: 10px;
}

#connectingMsg {
  position: absolute;
  text-align: center;
  top: 40%;
  left: 20%;
  right: 20%;
  font-size: 18pt;
  margin-bottom: 0;
}

.select {
  margin: 0.2rem;
}

.enterBtn {
  background-color: #006f3e;
  color: white;
  font-size: 12px;
  border: 0;
}
.stopBtn {
  background-color: #ffbd44;
  color: #fff;
  font-size: 12px;
  border: 0;
}
.closeBtn {
  background-color: #ff605c;
  color: #fff;
  font-size: 12px;
  border: 0;
}

.radiusRectangular {
  border-radius: 30px;
  border: none;
}
</style>
