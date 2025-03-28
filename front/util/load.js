import { routes } from "../app.js";

export function loadPage(url, id) {
  const main = document.getElementById("app");
  let route;

  if (url === "/posts:postId") {
    route = "/page/post.html";
  } else if (url === "/posts:postId/edit") {
    route = "/page/editPost.html";
  } else {
    route = routes[url] || routes["/"];
  }
  fetch(route)
    .then((response) => response.text())
    .then((html) => {
      main.innerHTML = html;

      let jsPath = "/javascript/" + route.split("/").pop();
      jsPath = jsPath.replace(".html", ".js");
      loadScript(jsPath);
    })
    .catch((error) => {
      console.error("페이지 로드 오류:", error.message);
    });
}

export function loadScript(url) {
  const script = document.createElement("script");
  script.src = url;
  script.type = "module";
  document.body.appendChild(script);
}

export function loadCSS(url) {
  const link = document.createElement("link");
  link.rel = "stylesheet";
  link.href = url;
  document.head.appendChild(link);
}
