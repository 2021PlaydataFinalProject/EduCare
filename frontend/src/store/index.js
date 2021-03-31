import Vue from 'vue';
import Vuex from 'vuex';
import {
	getAuthFromCookie,
	getUserFromCookie,
	saveAuthToCookie,
	saveUserToCookie,
} from '@/utils/cookies';
import { signinUser } from '../api/auth';
import { instance } from '../api';

Vue.use(Vuex);

export default new Vuex.Store({
	state: {
		id: getUserFromCookie() || '',
		token: getAuthFromCookie() || '',
		clientinfo: [
			{
				id: '',
				pw: '',
				name: '',
				nickname: '',
				phonenumber: '',
				birth: '',
				gender: '',
			},
		],
	},
	getters: {
		isSignin(state) {
			return state.id !== '';
		},
	},
	mutations: {
		setId(state, id) {
			state.id = id;
		},
		clearId(state) {
			state.id = '';
		},
		setToken(state, token) {
			state.token = token;
		},
		clearToken(state) {
			state.token = '';
		},
		getclient: function(state) {
			return state.clientinfo;
		},
	},
	actions: {
		async signin({ commit }, userData) {
			const { data } = await signinUser(userData);
			console.log(data.token);
			commit('setToken', data.token);
			commit('setId', data.user.id);
			saveAuthToCookie(data.token);
			saveUserToCookie(data.user.id);
			return data;
		},
		async getclient({ commit }) {
			return await instance
				.get('getMemberList')
				.then(resData => {
					this.clientinfo = resData.data;
					console.log('receive from server!!!!!!!!!!!!:' + resData.data);
					commit('getclient');
				})
				.catch(error => {
					console.log(error);
				});
		},
	},
	modules: {},
});
