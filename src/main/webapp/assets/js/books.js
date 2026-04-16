document.addEventListener("DOMContentLoaded", function() {
                                         fetch("http://localhost:8080/miniLibraryManager/api/books/all")
                                             .then(res => res.json())
                                             .then(data => {
                                                 let table = document.getElementById("booksTable");
                                                 table.innerHTML = "";

                                                 data.forEach(book => {
                                                     table.innerHTML += `
                                                         <tr>
                                                             <td>${book.isbn}</td>
                                                             <td>${book.title}</td>
                                                             <td>${book.year}</td>
                                                             <td>${book.author.name}</td>
                                                             <td>
                                                                 <button onclick="deleteBook(${book.isbn})">Delete</button>
                                                             </td>
                                                         </tr>
                                                     `;
                                                 });
                                             });
                                     })


function deleteBook(isbn) {
    fetch("http://localhost:8080/miniLibraryManager/api/books/" + isbn, {
        method: "DELETE"
    }).then(() => window.location.reload());
}