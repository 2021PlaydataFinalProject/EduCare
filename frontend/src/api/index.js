import axios from 'axios';
import { setInterceptors } from './common/interceptors';

function createInstance() {
	return axios.create({
		baseURL: process.env.VUE_APP_API_URL,
	});
}

// 엑시오스 초기화 함수
function createInstanceWithAuth() {
	const instance = axios.create({
		//	baseURL: `${process.env.VUE_APP_API_URL}${url}`,
		baseURL: '',
	});
	return setInterceptors(instance);
}

export const instance = createInstance();
export const meetingrooms = createInstanceWithAuth();
