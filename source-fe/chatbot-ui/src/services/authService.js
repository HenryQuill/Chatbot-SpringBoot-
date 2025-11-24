import axios from "axios";

const API_URL = "http://localhost:8080/api/auth/";

// Register
export const register = (username, email, password) => {
  return axios.post(API_URL + "signup", { username, email, password });
};

// Login
export const login = (username, password) => {
  return axios.post(API_URL + "signin", { username, password })
    .then((response) => {
      if (response.data.accessToken) {
        localStorage.setItem("user", JSON.stringify(response.data));
      }
      return response.data;
    });
};

// Logout
export const logout = () => {
  localStorage.removeItem("user");
};

// Get stored user
export const getCurrentUser = () => {
  return JSON.parse(localStorage.getItem("user"));
};

// Axios instance with JWT
export const authAxios = axios.create();

// Interceptor → attach token if available
authAxios.interceptors.request.use(
  (config) => {
    const user = getCurrentUser();
    if (user?.accessToken) {
      config.headers.Authorization = `Bearer ${user.accessToken}`;
    }
    return config;
  },
  (error) => Promise.reject(new Error(error))
);