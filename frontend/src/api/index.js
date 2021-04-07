import axios from "axios";
// import { setInterceptors } from "./common/interceptors";

function createInstance() {
  return axios.create({
    baseURL: "http://localhost:8000"
  });
}

export const instance = createInstance();
