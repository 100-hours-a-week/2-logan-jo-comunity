import { createPost } from "../api/postApi.js";
import { navigateTo } from "../util/navigate.js";

const titleInput = document.getElementById("title");
const contentInput = document.getElementById("content");
const submitButton = document.querySelector(".submit-button");
const errorMessage = document.getElementById("error-message");
const fileInput = document.getElementById("file-input");
const fileNameDisplay = document.getElementById("file-name");

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

fileInput.addEventListener("change", async function () {
  const fileName = fileInput.files[0]
    ? fileInput.files[0].name
    : "선택된 파일 없음";
  fileNameDisplay.textContent = fileName;
});

submitButton.addEventListener("click", async function (event) {
  event.preventDefault();

  if (titleInput.value.trim() === "" || contentInput.value.trim() === "") {
    errorMessage.textContent = "*제목, 내용을 모두 입력해주세요";
    return;
  } else {
    try {
      await createPost(
        titleInput.value,
        contentInput.value,
        fileInput.files[0]
      );
      navigateTo("/posts");
    } catch (error) {
      errorMessage.textContent = error.message;
    }
  }
});
