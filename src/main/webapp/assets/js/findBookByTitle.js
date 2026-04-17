let searchButton = document.getElementById("searchButton");

searchButton.addEventListener("click", function () {
    let title = document.getElementById("title").value;

    if (!title) {
        alert("Please enter a title");
        return;
    }

    fetch("http://localhost:8080/miniLibraryManager/api/books/search?title=" + title)
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
            main.innerHTML =
            `<table border="1">
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
            </table>`;
}
