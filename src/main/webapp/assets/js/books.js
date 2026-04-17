let sortByYearButton=document.getElementById("sortByYearButton");
let sortByTitleButton=document.getElementById("sortByTitleButton");

sortByYearButton.addEventListener("click", function(){
    fetch("http://localhost:8080/miniLibraryManager/api/books/sort/year")
        .then(res => res.json())
        .then(data => {renderTable(data)})
})

sortByTitleButton.addEventListener("click", function(){
    fetch("http://localhost:8080/miniLibraryManager/api/books/sort/title")
        .then(res => res.json())
        .then(data => {renderTable(data)})
})

document.addEventListener("DOMContentLoaded", function() {
     fetch("http://localhost:8080/miniLibraryManager/api/books/all")
         .then(res => res.json())
         .then(data => {renderTable(data)});
 })

function renderTable(data) {
    let table = document.getElementById("booksTable");
    let rows = "";

    data.forEach(book => {
        rows += `
            <tr>
                <td>${book.isbn}</td>
                <td>${book.title}</td>
                <td>${book.year}</td>
                <td>${book.author.name}</td>
                <td>
                    <button onclick="deleteBook(${book.isbn}, this)">Delete</button>
                </td>
            </tr>
        `;
    });

    table.innerHTML = rows;
}

function deleteBook(isbn) {
    fetch("http://localhost:8080/miniLibraryManager/api/books/" + isbn, {
        method: "DELETE"
    }).then(() => window.location.reload());
}