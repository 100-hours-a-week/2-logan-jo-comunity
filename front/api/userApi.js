import { apiRequest, apiRequestFile } from "./apiRequest.js";

export async function signup(email, password, nickname, logoImage) {
  try {
    const formData = new FormData();
    formData.append("email", email);
    formData.append("password", password);
    formData.append("nickname", nickname);
    formData.append("logoImage", logoImage);

    await apiRequestFile("/users/signup", "POST", formData);
  } catch (error) {
    throw error;
  }
}
export async function login(email, password) {
  try {
    const response = await apiRequest("/users/login", "POST", {
      email,
      password,
    });
    return response;
  } catch (error) {
    throw error;
  }
}
export async function getUser() {
  try {
    const response = await apiRequest("/users");

    return response;
  } catch (error) {
    throw error;
  }
}
export async function updateUser(nickname, logoImageFile) {
  try {
    const formData = new FormData();
    formData.append("nickname", nickname);
    formData.append("logoImage", logoImageFile);

    const response = await apiRequestFile("/users", "PATCH", formData);
    return response;
  } catch (error) {
    throw error;
  }
}
export async function updatePassword(password) {
  try {
    const response = await apiRequest("/users/password", "PATCH", {
      newPassword: password,
    });
    return response;
  } catch (error) {
    throw error;
  }
}
export async function deleteUser() {
  try {
    await apiRequest("/users", "DELETE");
  } catch (error) {
    throw error;
  }
}
