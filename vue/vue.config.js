module.exports = {
  devServer: {
    disableHostCheck: true,
    // https: true,
    // proxy: {
    //   // proxyTable 설정
    //   // "/api": {
    //   //   // api 로 시작하는 소스 는 traget으로 잡아준다. > 사용할때 url 이 api 가 있어야 한다.
    //   //   target: "https://i5d204.p.ssafy.io", // www.xxx.com
    //   //   changeOrigin: true,
    //   // },
    //   "^/api": {
    //     // target: "http://localhost:8080/",
    //     target: "https://i5d204.p.ssafy.io",
    //     // ws: true,
    //     // changeOrigin: true,
    //     secure: false,
    //   },
    //   // "/aw": {
    //   //   // aw로 url 이 시작하면 이렇게 target을 잡아준다.
    //   //   target: "https://i5d204.p.ssafy.io",
    //   //   changeOrign: true,
    //   // },
    // },
  },
  lintOnSave: false,
};
