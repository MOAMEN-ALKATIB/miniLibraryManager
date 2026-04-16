let createButton = document.getElementById("createButton");

createButton.addEventListener("click", function () {

    let book = {
        isbn: document.getElementById("isbn").value,
        title: document.getElementById("title").value,
        year: document.getElementById("year").value,
        author: {
            name: document.getElementById("author").value
        }
    };

    fetch('http://localhost:8080/miniLibraryManager/api/books/insert', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(book)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Error while adding book");
        }
        window.location.href = "books.html";
    })
    .catch(error => {
        console.error(error);
        alert("Failed to add book");
    });

});