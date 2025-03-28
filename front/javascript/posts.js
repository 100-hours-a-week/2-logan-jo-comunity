import { formatLikes } from "../util/likes.js";
import { formatDate } from "../util/date.js";
import { getPostList } from "../api/postApi.js";

try {
  const response = await getPostList();
  const data = response.data;
  const postsContainer = document.getElementById("posts-list");

  if (data.length > 0) {
    data.map((post) => {
      const postElement = document.createElement("div");
      postElement.className = "post-item";

      postElement.innerHTML = `
            <a href="posts/${post.id}">
              <div class="post-header">
                <h3 class="post-title">${
                  post.title.length > 26
                    ? post.title.slice(0, 26) + "..."
                    : post.title
                }</h3>
              </div>
              <div class="post-header">
                <div class="post-header-left">
                  <div class="post-stats">
                    <span>좋아요 ${formatLikes(post.likes)}</span>
                    <span>댓글 ${formatLikes(post.comments)}</span>
                    <span>조회수 ${formatLikes(post.views)}</span>
                  </div>
                </div>
                <span class="post-date">${formatDate(post.createdAt)}</span>
              </div>
              <div class="post-line"></div>
              <div class="post-header-left">
                <img src="${
                  post.authorImage
                }" alt="profile" class="author-logo">
                <span class="author-name">${post.author}</span>
              </div>
            </a>
            `;

      postsContainer.appendChild(postElement);
    });
  }
} catch (error) {
  console.error(error);
}
