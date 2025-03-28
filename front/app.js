import { checkAuth } from "./util/checkAuth.js";
import getCookie from "./util/cookie.js";
import { loadPage } from "./util/load.js";
import { navigateTo } from "./util/navigate.js";

export const routes = {
  "/login": "page/login.html",
  "/signup": "page/signup.html",
  "/user": "page/user.html",
  "/editPassword": "page/editPassword.html",
  "/posts": "page/posts.html",
  "/posts/write": "/page/makePost.html",
};

window.onpopstate = () => {
  route();
};

document.addEventListener("DOMContentLoaded", () => {
  route();
});

async function route() {
  const path = window.location.pathname;

  const isAuthenticated = await checkAuth();

  if (path === "/") {
    if (getCookie("accessToken")) {
      navigateTo("/posts");
      return;
    }
    navigateTo("/login");
    return;
  }

  if (routes[path]) {
    loadPage(path);
    return;
  }

  console.log(path);

  const editMatch = path.match(/^\/posts\/([^/]+)\/edit$/);
  if (editMatch) {
    const postId = editMatch[1];
    loadPage("/posts:postId/edit", postId);
    return;
  }

  const match = path.match(/^\/posts\/([^/]+)$/);
  if (match) {
    const postId = match[1];
    loadPage("/posts:postId", postId);
    return;
  }

  navigateTo("/login");
}
