module.exports = {
  lintOnSave: false,
  devServer: {
    disableHostCheck: true,
  },
};

// const createProxyMiddleware = require("http-proxy-middleware");
// module.exports = function (app) {
//   app.use(
//     "/api",
//     createProxyMiddleware({
//       target: "http://i5d204.p.ssafy.io:8088",
//       changeOrigin: true,
//     })
//   );
// };
// module.exports = {
//   // port: 5000,
//   // disableHostCheck: true,
//   lintOnSave: false,
//   devServer: {

//   },
// };
