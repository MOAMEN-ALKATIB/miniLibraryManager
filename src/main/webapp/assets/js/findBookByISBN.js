let searchButton = document.getElementById("searchButton");


searchButton.addEventListener("click", function () {
    let isbn = document.getElementById("isbn").value;

    fetch("http://localhost:8080/miniLibraryManager/api/books/" + isbn)
        .then(res => res.json())
        .then(data => {
        let searchForm = document.getElementById("searchForm");
        let main = document.getElementById("main");
        searchForm.remove();
        main.innerHTML = "";
main.innerHTML +=`<table border="1">
  <thead>
  <tr>
    <th>ISBN</th>
    <th>Title</th>
    <th>Year</th>
    <th>Author</th>
  </tr>
  </thead>
  <tbody>
    <tr>
        <td>${data.isbn}</td>
        <td>${data.title}</td>
        <td>${data.year}</td>
        <td>${data.author.name}</td>
    </tr>
  </tbody>
</table>`
        });
});

