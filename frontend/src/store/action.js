import { fetchUserImage } from "../api/auth.js";

export default {
  FETCH_IMAGE({ commit }) {
    fetchUserImage()
      .then(({ data }) => commit("FETCH_IMAGE", data))
      .catch(error => console.log(error));
  }
};
