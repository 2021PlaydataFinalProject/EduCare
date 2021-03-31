//const path = require('path');
// module.exports = {
// 	outputDir: path.resolve(__dirname, '../../../' + 'main/resources/static'),
// };

module.exports = {
	outputDir: '../resources/static',
	indexPath: './public/index.html',
	devServer: {
		proxy: 'http://localhost:80',
	},
	chainWebpack: config => {
		const svgRule = config.module.rule('svg');
		svgRule.uses.clear();
		svgRule.use('vue-svg-loader').loader('vue-svg-loader');
	},
	// configureWebpack: {
	// 	resolve: {
	// 		alias: {
	// 			'@': path.resolve(__dirname, 'src/'),
	// 		},
	// 	},
	// },
};
