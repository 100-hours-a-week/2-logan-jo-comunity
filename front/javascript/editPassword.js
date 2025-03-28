import { updatePassword } from "../api/userApi.js";
import { navigateTo } from "../util/navigate.js";
import { validatePassword } from "../util/validate.js";

const passwordInput = document.getElementById("password");
const checkPasswordInput = document.getElementById("check-password");
const errorMessage = document.getElementById("input-password");
const errorCheckMessage = document.getElementById("check-password-message");
const loginButton = document.getElementById("login-submit");
const toast = document.getElementById("toast");

function validateInputs() {
  const passwordValid = validatePassword(passwordInput.value);
  const passwordsMatch = passwordInput.value === checkPasswordInput.value;

  if (passwordInput.value === "") {
    errorMessage.textContent = "*비밀번호를 입력해주세요";
    errorCheckMessage.textContent = "*비밀번호를 한번 더 입력해주세요";
    loginButton.style.backgroundColor = "#aca0eb";
    loginButton.disabled = true;
    loginButton.style.cursor = "not-allowed";
  } else if (!passwordValid) {
    errorMessage.textContent =
      "비밀번호는 8자 이상 20자 이하이며, 대문자, 소문자, 숫자, 특수문자를 각각 최소 1개 포함해야 합니다.";
    loginButton.style.backgroundColor = "#aca0eb";
    loginButton.disabled = true;
    loginButton.style.cursor = "not-allowed";
  } else if (!passwordsMatch) {
    errorMessage.textContent = "*비밀번호 확인과 다릅니다.";
    errorCheckMessage.textContent = "*비밀번호와 다릅니다.";
    loginButton.style.backgroundColor = "#aca0eb";
    loginButton.disabled = true;
    loginButton.style.cursor = "not-allowed";
  } else {
    errorMessage.textContent = "";
    errorCheckMessage.textContent = "";
    loginButton.style.backgroundColor = "#7F6AEE";
    loginButton.disabled = false;
    loginButton.style.cursor = "pointer";
  }
}

passwordInput.addEventListener("input", validateInputs);
checkPasswordInput.addEventListener("input", validateInputs);

loginButton.addEventListener("click", async function (event) {
  try {
    await updatePassword(passwordInput.value);

    toast.style.display = "block";
    toast.style.opacity = 1;
    setTimeout(() => {
      toast.style.opacity = 0;
      setTimeout(() => {
        toast.style.display = "none";
      }, 500);
      navigateTo("/posts");
    }, 2000);
  } catch (error) {
    alert(error.message);
  }
});
validateInputs();
