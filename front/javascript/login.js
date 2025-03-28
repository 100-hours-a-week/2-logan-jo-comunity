import { login } from "../api/userApi.js";
import { navigateTo } from "../util/navigate.js";
import { validateEmail, validatePassword } from "../util/validate.js";

const emailInput = document.getElementById("email");
const passwordInput = document.getElementById("password");
const errorMessage = document.getElementById("error-message");
const loginButton = document.getElementById("login-submit");

const validateInputs = () => {
  const emailValid = validateEmail(emailInput.value);
  const passwordValid = validatePassword(passwordInput.value);

  if (!emailValid) {
    setError("올바른 이메일 형식을 입력하세요.");
  } else if (!passwordValid) {
    setError(
      "비밀번호는 8~20자이며, 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다."
    );
  } else {
    clearError();
  }
};

const setError = (message) => {
  errorMessage.textContent = message;
  loginButton.style.backgroundColor = "#aca0eb";
  loginButton.disabled = true;
  loginButton.style.cursor = "not-allowed";
};

const clearError = () => {
  errorMessage.textContent = "";
  loginButton.style.backgroundColor = "#7F6AEE";
  loginButton.disabled = false;
  loginButton.style.cursor = "pointer";
};

const handleSubmit = async () => {
  try {
    const email = emailInput.value;
    const password = passwordInput.value;

    const result = await login(email, password);

    document.cookie = `accessToken=${result.accessToken}`;

    navigateTo("/posts");
  } catch (error) {
    errorMessage.textContent = error.message;
  }
};

const handleKeyPress = (event) => {
  if (event.key === "Enter") {
    event.preventDefault();
    if (emailInput.value && !passwordInput.value) {
      passwordInput.focus();
    } else if (emailInput.value && passwordInput.value) {
      handleSubmit();
    }
  }
};

emailInput.addEventListener("input", validateInputs);
emailInput.addEventListener("keypress", handleKeyPress);
passwordInput.addEventListener("input", validateInputs);
passwordInput.addEventListener("keypress", handleKeyPress);
loginButton.addEventListener("click", (event) => {
  event.preventDefault();
  handleSubmit();
});

validateInputs();
