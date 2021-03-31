import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import VueSession from 'vue-session';

const sessionOptions = {
	persist: true,
};
Vue.use(VueSession, sessionOptions);

Vue.config.productionTip = false;

new Vue({
	router,
	store,
	render: h => h(App),
}).$mount('#app');
