let searchButton = document.getElementById("searchButton");

searchButton.addEventListener("click", function () {
    let author = document.getElementById("author").value;

    fetch("http://localhost:8080/miniLibraryManager/api/books/author?authorName="
        + encodeURIComponent(author))
        .then(res => res.json())
        .then(data => {

            let searchForm = document.getElementById("searchForm");
            let main = document.getElementById("main");

            searchForm.remove();
            main.innerHTML = "";

            let table = `
            <table>
              <thead>
                <tr>
                  <th>ISBN</th>
                  <th>Title</th>
                  <th>Year</th>
                  <th>Author</th>
                </tr>
              </thead>
              <tbody>
            `;

            data.forEach(book => {
                table += `
                    <tr>
                        <td>${book.isbn}</td>
                        <td>${book.title}</td>
                        <td>${book.year}</td>
                        <td>${book.author.name}</td>
                    </tr>
                `;
            });

            table += `
              </tbody>
            </table>
            `;

            main.innerHTML = table;
        });
});