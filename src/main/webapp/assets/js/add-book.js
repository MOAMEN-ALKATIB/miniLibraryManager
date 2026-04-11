let createButton=document.getElementById("createButton");
createButton.addEventListener("click",function (){
    let book = {
        isbn: document.getElementById("isbn").value,
        title: document.getElementById("title").value,
        year: document.getElementById("year").value,
        author: {
            name: document.getElementById("author").value
        }
        $.ajax({
        url: 'http://localhost:8080/miniLibraryManager/connection/create',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(book),
        dataType: 'json',
        success: function (book) {
            // POST modification
            window.location.href = `status.html?name=${data.name}&gender=${data.gender}&age=${data.age}&hunger=${data.hunger}&mood=${data.mood}&energie=${data.energie}&status=${data.status}&maxAge=${data.maxAge}`;
            // ->
        },
    })
}});