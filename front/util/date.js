export const formatDate = (date) => {
  const dateObj = new Date(date);
  const formattedDate = `${dateObj.getFullYear()}-${String(
    dateObj.getMonth() + 1
  ).padStart(2, "0")}-${String(dateObj.getDate()).padStart(2, "0")} ${String(
    dateObj.getHours()
  ).padStart(2, "0")}:${String(dateObj.getMinutes()).padStart(2, "0")}:${String(
    dateObj.getSeconds()
  ).padStart(2, "0")}`;

  return formattedDate;
};
