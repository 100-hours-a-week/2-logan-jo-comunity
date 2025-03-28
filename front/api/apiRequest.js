import getCookie from "../util/cookie.js";

const baseUrl = "http://localhost:8080";
const token = getCookie("accessToken");

export async function apiRequest(endpoint, method = "GET", data = null) {
  const url = `${baseUrl}${endpoint}`;

  const options = {
    method,
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    credentials: "include",
  };

  if (data) {
    options.body = JSON.stringify(data);
  }

  try {
    const response = await fetch(url, options);

    if (!response.ok) {
      const errorData = await response.json().catch(() => null);
      throw new Error(errorData.message || "API 요청 실패");
    }

    return await response.json().catch(() => null);
  } catch (error) {
    console.error("API 요청 오류:", error.message);
    throw error;
  }
}
// file 처리를 위한 application/json 제거
export async function apiRequestFile(endpoint, method = "POST", data = null) {
  const url = `${baseUrl}${endpoint}`;

  const options = {
    method,
    headers: {
      Authorization: `Bearer ${token}`,
    },
    credentials: "include",
    body: data,
  };
  try {
    const response = await fetch(url, options);

    if (!response.ok) {
      const errorData = await response.json().catch(() => null);
      throw new Error(errorData.message || "API 요청 실패");
    }

    return await response.json().catch(() => null);
  } catch (error) {
    console.error("API 요청 오류:", error.message);
    throw error;
  }
}
