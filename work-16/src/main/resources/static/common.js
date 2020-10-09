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
            if (contentType && contentType.indexOf("application/json") !== -1) {
                return response.json();
            } else { return Promise.resolve();}
        })
        .then((data) => {
            if (callback && typeof(callback)=="function") callback(data);
        });
}