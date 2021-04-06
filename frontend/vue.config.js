// module.exports = {
//   publicPath:
//     process.env.DEPLOY_ENV === "GH_PAGES"
//       ? "/admin-null-vue-bulma-dashboard/"
//       : "/",

//   // Remove moment.js from chart.js
//   // https://www.chartjs.org/docs/latest/getting-started/integration.html#bundlers-webpack-rollup-etc
//   configureWebpack: config => {
//     return {
//       externals: {
//         moment: "moment"
//       }
//     };
//   }
// };
module.exports = {
  outputDir: "../../resources/static",
  indexPath: "./public/index.html",
  devServer: {
    proxy: "http://localhost:8000"
  },
  chainWebpack: config => {
    const svgRule = config.module.rule("svg");
    svgRule.uses.clear();
    svgRule.use("vue-svg-loader").loader("vue-svg-loader");
  }
  // configureWebpack: {
  // 	resolve: {
  // 		alias: {
  // 			'@': path.resolve(__dirname, 'src/'),
  // 		},
  // 	},
  // },
};
