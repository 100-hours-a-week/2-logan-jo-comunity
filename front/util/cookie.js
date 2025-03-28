function getCookie(name) {
  const matches = document.cookie.match(new RegExp(`(^|; )${name}=([^;]*)`));
  return matches ? matches[2] : undefined;
}

export default getCookie;
