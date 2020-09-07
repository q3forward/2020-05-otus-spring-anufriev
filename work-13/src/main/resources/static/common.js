function callFetch(resource, method, formdata, callback) {
    fetch(resource, {
        headers: {
            'Content-Type': 'application/json'
        },
        method,
        body: JSON.stringify(formdata)
    })
        .then((response) => {
            if (response.status==403) {
                alert("Недостаточно прав");
            } else {
                const contentType = response.headers.get("content-type");
                if (contentType && contentType.indexOf("application/json") !== -1) {
                    return response.json();
                } else {
                    return Promise.resolve();
                }
            }
        })
        .then((data) => {
            if (callback && typeof(callback)=="function") callback(data);
        });
}