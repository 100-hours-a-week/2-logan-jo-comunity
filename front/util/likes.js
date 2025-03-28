export const formatLikes = (likes) => {
  if (likes >= 1000 && likes < 1000000) {
    return (likes / 1000).toFixed(0) + "k";
  } else if (likes >= 1000000) {
    return (likes / 1000000).toFixed(0) + "M";
  }
  return likes;
};
