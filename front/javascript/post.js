import { formatLikes } from "../util/likes.js";
import { formatDate } from "../util/date.js";
import { getUser } from "../api/userApi.js";
import { getPost, deletePost } from "../api/postApi.js";
import {
  createComment,
  getComments,
  updateComment,
  deleteComment,
} from "../api/commentApi.js";
import { navigateTo } from "../util/navigate.js";

const path = window.location.pathname.split("/");
const postId = path[path.length - 1];

let user;
let post;
let comments;

try {
  user = await getUser();

  let resPost = await getPost(postId);
  post = await resPost.data;

  let resComments = await getComments(postId);
  comments = await resComments.data;
} catch (error) {
  console.error("Error loading error:", error);
}

if (post) {
  renderPost(post);
  myPostCheck();
}
if (comments.length > 0) renderComments(comments, user);

setupEventListeners(postId, post.likes);
setCommentButtonDefault();

function renderPost(post) {
  document.getElementById("post-title").textContent = post.title;
  document.getElementById("post-author").textContent = post.author;
  document.getElementById("post-date").textContent = formatDate(post.createdAt);
  document.getElementById("post-image").src = post.authorImage;
  document.getElementById("post-main-image").src = post.image;
  document.getElementById("post-content").textContent = post.content;
  document.getElementById("post-likes-count").textContent = formatLikes(
    post.likes
  );
  document.getElementById("post-views").textContent = formatLikes(post.views);
  document.getElementById("post-comments").textContent = formatLikes(
    post.comments
  );
}

function myPostCheck() {
  const buttons = document.getElementById("post-buttons");
  console.log(user.nickname, post.author);

  if (user.nickname !== post.author) {
    buttons.style.display = "none";
  }
}

function renderComments(comments, user) {
  const commentContainer = document.getElementById("comment-container");
  commentContainer.innerHTML = comments
    .map((comment) => createCommentHTML(comment, user))
    .join("");
}

function createCommentHTML(comment, user) {
  return `
    <div class="comment-item" id="${comment.id}">
      <div class="comment-item-header">
        <img class="author-logo" src="${
          comment.userInfo.logoImage
        }" alt="logo" />
        <span class="comment-item-author">${comment.userInfo.nickname}</span>
        <span class="comment-item-date">${formatDate(comment.createdAt)}</span>
      </div>
      <div class="comment-item-content">
        <p id="comment-text-${comment.id}">${comment.content}</p>
      </div>
      ${comment.userId === user.id ? createCommentControls(comment.id) : ""}
    </div>`;
}

function createCommentControls(commentId) {
  return `
    <div class="comment-edit-container">
      <button class="comment-edit-button" data-id="${commentId}">수정</button>
      <button class="comment-delete-button" data-id="${commentId}">삭제</button>
    </div>`;
}

function setupEventListeners(postId, like) {
  document.body.addEventListener("click", handleBodyClick);
  document
    .getElementById("post-edit-button")
    .addEventListener("click", () => navigateTo(`/posts/${postId}/edit`));
  document
    .getElementById("post-delete-button")
    .addEventListener("click", () => toggleModal("delete-modal", true));
  document
    .getElementById("cancel-delete")
    .addEventListener("click", () => toggleModal("delete-modal", false));
  document
    .getElementById("confirm-delete")
    .addEventListener("click", () => deletePostClick());
  setupLikeButton(like);
}

function handleBodyClick(event) {
  const target = event.target;
  if (target.classList.contains("comment-delete-button"))
    deleteCommentClick(target.dataset.id);
  if (target.classList.contains("comment-edit-button"))
    editComment(target.dataset.id);
}

async function deleteCommentClick(commentId) {
  console.log(`댓글 삭제: ${commentId}`);
  try {
    await deleteComment(postId, commentId);
    location.reload();
  } catch (error) {
    alert(error.message);
  }
}

function editComment(commentId) {
  console.log(`댓글 수정: ${commentId}`);
  const commentText = document.getElementById(`comment-text-${commentId}`);
  const commentArea = document.getElementById("comment");
  const commentButton = document.getElementById("comment-button");

  commentArea.value = commentText.textContent;
  commentButton.textContent = "댓글 수정";
  commentButton.style.backgroundColor = "#aca0e8";
  commentButton.style.cursor = "pointer";

  commentButton.onclick = async function () {
    try {
      await updateComment(postId, commentId, commentArea.value);
    } catch (error) {
      alert(error.message);
    } finally {
      commentText.textContent = commentArea.value;
      commentArea.value = "";
      setCommentButtonDefault();
    }
  };
}

function toggleModal(modalId, isVisible) {
  document.getElementById(modalId).style.display = isVisible ? "flex" : "none";
}

async function deletePostClick() {
  try {
    await deletePost(postId);

    toggleModal("delete-modal", false);
    navigateTo("/posts");
  } catch (error) {
    alert(error.message);
  }
}

function setupLikeButton(like) {
  const likesItems = document.getElementById("post-likes");
  const likesCount = document.getElementById("post-likes-count");
  let isLiked = false;
  let likes = like;

  likesItems.addEventListener("click", function () {
    isLiked = !isLiked;
    likes += isLiked ? 1 : -1;
    likesCount.textContent = formatLikes(likes);
    likesItems.style.backgroundColor = isLiked ? "#aca0e8" : "#d9d9d9";
    likesItems.style.color = isLiked ? "#fff" : "#000";
  });
}

function setCommentButtonDefault() {
  const commentArea = document.getElementById("comment");
  const commentButton = document.getElementById("comment-button");

  commentButton.textContent = "댓글 등록";
  commentButton.style.backgroundColor = "#d9d9d9";
  commentButton.style.cursor = "not-allowed";

  commentArea.oninput = function () {
    commentButton.style.backgroundColor = commentArea.value.trim()
      ? "#aca0e8"
      : "#d9d9d9";
    commentButton.style.cursor = commentArea.value.trim()
      ? "pointer"
      : "not-allowed";
  };

  commentButton.onclick = async function () {
    if (!commentArea.value.trim()) return;
    try {
      await createComment(postId, commentArea.value);
      location.reload();
    } catch (error) {
      alert(error.message);
    } finally {
      commentArea.value = "";
      setCommentButtonDefault();
    }
  };
}
