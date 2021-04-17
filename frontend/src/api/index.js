import axios from "axios";
import { setInterceptors } from "./common/interceptors";

function createInstance() {
  return axios.create({
    baseURL: "http://localhost:8000"
  });
}

//엑시오스 초기화 함수
function createInstanceWithAuth(url) {
  const instance = axios.create({
    baseURL: `${process.env.VUE_APP_API_URL}${url}`
  });
  return setInterceptors(instance);
}

export const instance = createInstance();
export const tests = createInstanceWithAuth("tests");
