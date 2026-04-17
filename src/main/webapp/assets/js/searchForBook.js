let searchList = document.getElementById("search");
let selectButton = document.getElementById("selectButton");

selectButton.addEventListener("click", function () {
  let selectedOption = searchList.options[searchList.selectedIndex].value;
  let form = "";
  switch (selectedOption) {
    case "ISBN":
      window.location.href="find-book-by-isbn.html";
      break;
    case "TITLE":
      window.location.href="find-book-by-title.html";
      break;
    default:
      window.location.href="find-books-by-author.html";
      break;

  }
})