function callFetch(resource, method, formdata, callback) {
    fetch(resource, {
        headers: {
            'Content-Type': 'application/json'
        },
        method,
        body: JSON.stringify(formdata)
    })
        .then((response) => {
            const contentType = response.headers.get("content-type");
            const status = response.status;
            if (status==200) {
                if (contentType && contentType.indexOf("application/json") !== -1) {
                    return response.json();
                } else Promise.resolve();
            } else { return Promise.reject(new Error("Сервис недоступен"));}
        })
        .then(data => {
            if (callback && typeof(callback)=="function") callback(data);
        }, error => {
            alert(error.message);
        });
}