import { deleteUser, getUser, updateUser } from "../api/userApi.js";
import { navigateTo } from "../util/navigate.js";

const nicknameInput = document.getElementById("nickname");
const errorMessage = document.getElementById("error-message");
const editButton = document.querySelector(".button");
const toast = document.getElementById("toast");
const fileInput = document.getElementById("file-input");
const profileImage = document.getElementById("profile-image");
const changeImageButton = document.getElementById("change-image-button");
const submitButton = document.getElementById("submit-button");
const deleteUserButton = document.getElementById("delete-user");
const deleteModal = document.getElementById("delete-modal");
const confirmDeleteButton = document.getElementById("confirm-delete");
const cancelDeleteButton = document.getElementById("cancel-delete");
const emailText = document.getElementById("email-text");

const user = await getUser();
let userInfo;
if (user) {
  try {
    emailText.textContent = user.email;
    nicknameInput.value = user.nickname;
    profileImage.src = user.logoImage;
  } catch (e) {
    console.log(e);
  }
}

const submit = async () => {
  try {
    const response = await updateUser(nicknameInput.value, fileInput.files[0]);

    toast.style.display = "block";
    toast.style.opacity = 1;
    setTimeout(() => {
      toast.style.opacity = 0;
      setTimeout(() => {
        toast.style.display = "none";
      }, 500);
    }, 2000);
  } catch (error) {
    errorMessage.textContent = error.message;
  }
};

editButton.addEventListener("click", function () {
  const nicknameValue = nicknameInput.value.trim();

  if (nicknameValue === "") {
    errorMessage.textContent = "*닉네임을 입력해주세요";
  } else if (nicknameValue.length < 2 || nicknameValue.length > 10) {
    errorMessage.textContent = "*닉네임은 최대 10자 까지 작성 가능합니다.";
  } else {
    errorMessage.textContent = "";

    submit();
  }
});

submitButton.addEventListener("click", () => {
  submit();
  navigateTo("/posts");
});

changeImageButton.addEventListener("click", function () {
  fileInput.click();
});

fileInput.addEventListener("change", function () {
  const file = fileInput.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = function (e) {
      profileImage.src = e.target.result;
    };
    reader.readAsDataURL(file);
  }
});

deleteUserButton.addEventListener("click", function () {
  deleteModal.style.display = "flex";
});

confirmDeleteButton.addEventListener("click", async function () {
  try {
    await deleteUser();

    // accessToken, refreshToken 쿠키 삭제
    document.cookie.split(";").forEach((cookie) => {
      const [name] = cookie.split("=");
      document.cookie = `${name.trim()}=; path=/; max-age=0`;
    });

    navigateTo("/login");
  } catch (error) {
    alert(error.message);
  }
});

cancelDeleteButton.addEventListener("click", function () {
  deleteModal.style.display = "none";
});
