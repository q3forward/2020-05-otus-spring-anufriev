function authorChange() {
    document.getElementById("newAuthorName").hidden = document.getElementById('author').value!="0";
};
function genreChange() {
    document.getElementById("newGenreName").hidden = document.getElementById('genre').value!="0";
};

function initData(loadBookFlag) {
    if (loadBookFlag) {
        loadBook();
    } else {
        loadAuthors();
        loadGenres();
    }
}

function loadBook() {
    let bookId = document.getElementById("book-id").value;
    callFetch("/api/book/"+bookId,"GET", undefined, function(bookInfo){
        document.getElementById("title").value = bookInfo.title;
        loadAuthors(bookInfo.author.id);
        loadGenres(bookInfo.genre.id);
    });
}

function loadAuthors(authorId) {
    callFetch("/api/authors","GET", undefined, function(authorList){
        let authorOptions = document.getElementById("author").innerHTML;
        authorList.forEach(function (author) {
            let selected = authorId==author.id ? "selected=\"true\"" : "";
            authorOptions += `
                    <option value="${author.id}" ${selected}>${author.name}</option>
                `;
        });
        document.getElementById("author").innerHTML = authorOptions;
    });
}

function loadGenres(genreId) {
    callFetch("/api/genres","GET", undefined, function(genreList){
        let genreOptions = document.getElementById("genre").innerHTML;
        genreList.forEach(function (genre) {
            let selected = genreId==genre.id ? "selected=\"true\"" : "";
            genreOptions += `
                    <option value="${genre.id}" ${selected}">${genre.name}</option>
                `;
        });
        document.getElementById("genre").innerHTML = genreOptions;
    });
}

function saveBook(method) {
    let bookObj = {
        title: document.getElementById("title").value
    };
    let author = document.getElementById("author");
    if (author.value=="0") {
        bookObj.newAuthorName = document.getElementById("newAuthorName").value;
    } else {
        let authorName = author.selectedOptions[0].text;
        bookObj.author = {
            id: author.value,
            name: authorName
        }
    }

    let genre = document.getElementById("genre");
    if (genre.value=="0") {
        bookObj.newGenreName = document.getElementById("newGenreName").value;
    } else {
        let genreName = genre.selectedOptions[0].text;
        bookObj.genre = {
            id: genre.value,
            name: genreName
        }
    }

    let id = method=="PUT" ? "/"+document.getElementById("book-id").value : "";
    callFetch("/api/book"+id, method, bookObj, function(res){
        window.location.href = "/";
    });
}