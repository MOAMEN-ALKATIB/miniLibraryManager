function loadBooks() {
    fetch("/api/books")
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
}

function deleteBook(isbn) {
    fetch("/api/books/" + isbn, {
        method: "DELETE"
    }).then(() => loadBooks());
}