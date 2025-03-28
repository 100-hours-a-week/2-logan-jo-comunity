import { getPost, updatePost } from "../api/postApi.js";

const path = window.location.pathname;
const match = path.match(/^\/posts\/([^/]+)\/edit\/?$/);
const postId = match[1];
let post;

try {
  const response = await getPost(postId);
  post = response.data;

  if (post) {
    document.getElementById("title").value = post.title;
    document.getElementById("content").value = post.content;
    document.getElementById("file-name").textContent = post.authorLogo
      ? post.authorLogo.split("/").pop()
      : "파일을 선택해주세요.";
  }
} catch (error) {
  console.error("Error loading post:", error);
}

const titleInput = document.getElementById("title");
const contentInput = document.getElementById("content");
const submitButton = document.querySelector(".submit-button");
const errorMessage = document.getElementById("error-message");
const fileInput = document.getElementById("file-input");

const fileNameDisplay = document.getElementById("file-name");
fileInput.addEventListener("change", function () {
  const fileName = fileInput.files[0]
    ? fileInput.files[0].name
    : "파일을 선택해주세요.";
  document.getElementById("file-name").textContent = fileName;
});

function validateInputs() {
  const titleValid = titleInput.value.trim() !== "";
  const contentValid = contentInput.value.trim() !== "";

  if (titleValid && contentValid) {
    submitButton.style.backgroundColor = "#7F6AEE";
    errorMessage.textContent = "";
  } else {
    submitButton.style.backgroundColor = "#aca0eb";
    errorMessage.textContent = "*제목, 내용을 모두 입력해주세요";
  }
}

validateInputs();
titleInput.addEventListener("input", validateInputs);
contentInput.addEventListener("input", validateInputs);

fileInput.addEventListener("change", function () {
  const fileName = fileInput.files[0]
    ? fileInput.files[0].name
    : "선택된 파일 없음";
  fileNameDisplay.textContent = fileName;
});

post.title = titleInput.value;
post.content = contentInput.value;
post.authorLogo = fileInput.files[0]
  ? fileInput.files[0].name
  : post.authorLogo;

submitButton.addEventListener("click", async function (event) {
  event.preventDefault();

  if (titleInput.value.trim() === "" || contentInput.value.trim() === "") {
    errorMessage.textContent = "*제목, 내용을 모두 입력해주세요";
    return;
  }
  try {
    await updatePost(
      postId,
      titleInput.value,
      contentInput.value,
      fileInput.files[0]
    );
    window.history.back();
  } catch (error) {
    alert(error.message);
  }
});
