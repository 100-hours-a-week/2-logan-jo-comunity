import { apiRequest, apiRequestFile } from "./apiRequest.js";

export async function getPostList() {
  try {
    const response = await apiRequest("/posts");

    return response;
  } catch (error) {
    throw error;
  }
}
export async function getPost(id) {
  try {
    const response = await apiRequest(`/posts/${id}`);

    return response;
  } catch (error) {
    throw error;
  }
}
export async function createPost(title, content, image) {
  try {
    const formData = new FormData();
    formData.append("title", title);
    formData.append("content", content);
    formData.append("image", image);

    const response = await apiRequestFile("/posts", "POST", formData);

    return response;
  } catch (error) {
    throw error;
  }
}
export async function updatePost(id, title, content, image) {
  try {
    const formData = new FormData();
    formData.append("title", title);
    formData.append("content", content);
    if (image) {
      formData.append("image", image);
    }

    const response = await apiRequestFile(`/posts/${id}`, "PATCH", formData);
    return response;
  } catch (error) {
    throw error;
  }
}
export async function deletePost(id) {
  try {
    const response = await apiRequest(`/posts/${id}`, "DELETE");

    return response;
  } catch (error) {
    throw error;
  }
}
