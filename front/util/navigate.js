import { loadPage } from "./load.js";

export function navigateTo(url) {
  window.history.pushState({}, url, window.location.origin + url);
  loadPage(url);
  location.reload();
}

document.addEventListener("click", (event) => {
  const link = event.target.closest("a");
  if (link && link.getAttribute("href")?.startsWith("/")) {
    event.preventDefault();
    navigateTo(link.getAttribute("href"));
  }
});
