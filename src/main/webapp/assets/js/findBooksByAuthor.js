let searchButton = document.getElementById("searchButton");

searchButton.addEventListener("click", function () {
    let author = document.getElementById("author").value;

    if(!author){
        alert("Please enter a author");
        return;
    }

    fetch("http://localhost:8080/miniLibraryManager/api/books/author?authorName="
        + encodeURIComponent(author))
    .then(res => {
        if (!res.ok) {
            throw new Error("Not found");
        }
        return res.text();
    })
    .then(text => {
        if (!text) {
            window.location.href = "no-results.html";
            return;
        }
        return JSON.parse(text);
    })
    .then(data => {
        if (!data) return;
        renderTable(data)
    })
    .catch(err => {
        console.log(err);
        window.location.href = "no-results.html";
    });
});

function renderTable(data) {
    let searchForm = document.getElementById("searchForm");
    let main = document.getElementById("main");

    if(searchForm) searchForm.remove();

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
}