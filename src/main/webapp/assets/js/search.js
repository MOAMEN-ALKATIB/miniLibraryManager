function searchByISBN() {
    let isbn = document.getElementById("isbn").value;

    fetch("/api/books/" + isbn)
        .then(res => res.json())
        .then(data => {
            document.getElementById("result").innerText =
                JSON.stringify(data, null, 2);
        });
}