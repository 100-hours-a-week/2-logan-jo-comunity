import { apiRequest } from "../api/apiRequest.js";
import getCookie from "./cookie.js";

export async function checkAuth() {
  const accessToken = getCookie("accessToken");

  if (accessToken) {
    return true;
  }

  try {
    const response = await apiRequest("/auth/token", "POST");

    if (!response.ok) {
      throw new Error(response.message);
    }
    document.cookie = `accessToken=${response.accessToken}`;

    return true;
  } catch (error) {
    return false;
  }
}
