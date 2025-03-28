import { getUser } from "../api/userApi.js";
import { navigateTo } from "../util/navigate.js";

class HeaderComponent extends HTMLElement {
  static get observedAttributes() {
    return ["back", "menu"];
  }

  constructor() {
    super();
    this.attachShadow({ mode: "open" });

    this.shadowRoot.innerHTML = `
      <style>
        .header {
          width: 100vw;
          height: 103px;
          display: flex;
          align-items: center;
          justify-content: center;
          border-bottom: 1px solid #000;
          font-size: 32px;
          line-height: 28px;
          position: relative;
        }

        .header-container {
          width: 500px;
          display: flex;
          align-items: center;
          justify-content: center;
          position: relative;
        }

        .user-menu {
          display: none;
          list-style-type: none;
          position: absolute;
          top: 40px;
          right: 0;
          z-index: 1000;
          margin: 0;
          background-color: #d9d9d9;
          padding: 4px 24px;
        }

        .user-menu li {
          text-align: center;
        }

        .user-menu li a {
          text-decoration: none;
          font-size: 16px;
          color: black;
          font-weight: 400;
        }

        #logo {
          margin-left: 100px;
          width: 36px;
          height: 36px;
          border-radius: 50%;
          position: absolute;
          right: 0;
          cursor: pointer;
        }
        
        #logo:hover {
          background-color: #e9e9e9;
        }

        #before-arrow {
          display: none;
          width: 24px;
          height: 24px;
          cursor: pointer;
          position: absolute;
          left: 10px;
        }
      </style>

      <header class="header">
        <div class="header-container">
          <img id="before-arrow" src="/public/images/before-arrow.png" alt="before-arrow"/>
          <span>아무 말 대잔치</span>
          <img id="logo" alt="logo" />
          <ul id="user-menu" class="user-menu">
            <li><a href="user">회원정보 수정</a></li>
            <li><a href="editPassword">비밀번호 변경</a></li>
            <li><a href="login">로그아웃</a></li>
          </ul>
        </div>
      </header>
    `;
  }

  connectedCallback() {
    this.logo = this.shadowRoot.getElementById("logo");
    this.userMenu = this.shadowRoot.getElementById("user-menu");
    this.backButton = this.shadowRoot.getElementById("before-arrow");

    if (this.backButton) {
      this.backButton.addEventListener("click", () => window.history.back());
    }

    this.logo.addEventListener("click", this.toggleMenu.bind(this));

    this.updateBackButton();
    this.updateMenuLogo();

    this.loadUserProfile();
  }

  disconnectedCallback() {
    this.logo.removeEventListener("click", this.toggleMenu.bind(this));
    if (this.backButton) {
      this.backButton.removeEventListener("click", () => window.history.back());
    }
  }

  attributeChangedCallback(name) {
    if (name === "back") {
      this.updateBackButton();
    }
    if (name === "menu") {
      this.updateMenuLogo();
    }
  }

  updateBackButton() {
    if (!this.backButton) return;

    if (this.hasAttribute("back")) {
      this.backButton.style.display = "block";
    } else {
      this.backButton.style.display = "none";
    }
  }

  updateMenuLogo() {
    if (!this.logo) return;

    if (
      this.hasAttribute("menu") ||
      window.location.pathname.includes("signup.html")
    ) {
      this.logo.style.display = "none";
    } else {
      this.logo.style.display = "block";
    }
  }

  toggleMenu() {
    this.userMenu.style.display =
      this.userMenu.style.display === "block" ? "none" : "block";
  }

  async loadUserProfile() {
    if (
      !(
        window.location.pathname.includes("login") ||
        window.location.pathname.includes("signup")
      )
    ) {
      try {
        const user = await getUser();

        if (user && user.logoImage) {
          this.logo.src = user.logoImage;
        }
      } catch (err) {
        console.error("로그인이 필요합니다.");
        navigateTo("/login");
      }
    }
  }
}

customElements.define("header-component", HeaderComponent);
