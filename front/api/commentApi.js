import { apiRequest } from "./apiRequest.js";

export async function getComments(postId) {
  try {
    const response = await apiRequest(`/posts/${postId}/comments`);
    return response;
  } catch (error) {
    throw error;
  }
}
export async function createComment(postId, content) {
  try {
    const response = await apiRequest(`/posts/${postId}/comments`, "POST", {
      content,
    });
    return response;
  } catch (error) {
    throw error;
  }
}
export async function getComment(commentId) {
  try {
    const response = await apiRequest(`/posts/${postId}/comments/${commentId}`);
    return response;
  } catch (error) {
    throw error;
  }
}
export async function updateComment(postId, commentId, content) {
  try {
    const response = await apiRequest(
      `/posts/${postId}/comments/${commentId}`,
      "PATCH",
      { content }
    );
    return response;
  } catch (error) {
    throw error;
  }
}
export async function deleteComment(postId, commentId) {
  try {
    const response = await apiRequest(
      `/posts/${postId}/comments/${commentId}`,
      "DELETE"
    );
    return response;
  } catch (error) {
    throw error;
  }
}
