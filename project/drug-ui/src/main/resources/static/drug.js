function companyChange() {
    document.getElementById("newCompanyName").hidden = document.getElementById('company').value!="0";
};

function initData(loadDrugFlag) {
    if (loadDrugFlag) {
        loadDrug();
    } else {
        loadCompanies();
    }
}

function loadDrug() {
    let drugId = document.getElementById("drug-id").value;
    callFetch("/api/drug/"+drugId,"GET", undefined, function(drugInfo){
        document.getElementById("name").value = drugInfo.name;
        document.getElementById("area").value = drugInfo.area;
        document.getElementById("link").value = drugInfo.link;
        document.getElementById("phase").value = drugInfo.phase;
        document.getElementById("description").value = drugInfo.description;
        loadCompanies(drugInfo.company.id);
    });
}

function loadCompanies(companyId) {
    callFetch("/api/company","GET", undefined, function(companyList){
        let companyOptions = document.getElementById("company").innerHTML;
        companyList.forEach(function (company) {
            let selected = companyId==company.id ? "selected=\"true\"" : "";
            companyOptions += `
                    <option value="${company.id}" ${selected}>${company.name}</option>
                `;
        });
        document.getElementById("company").innerHTML = companyOptions;
    });
}

function saveDrug(method) {
    let drugObj = {
        name: document.getElementById("name").value,
        area: document.getElementById("area").value,
        link: document.getElementById("link").value,
        phase: document.getElementById("phase").value,
        description: document.getElementById("description").value
    };
    let company = document.getElementById("company");
    if (company.value=="0") {
        drugObj.newCompanyName = document.getElementById("newCompanyName").value;
    } else {
        let companyName = company.selectedOptions[0].text;
        drugObj.company = {
            id: company.value,
            name: companyName
        }
    }

    let id = method=="PUT" ? "/"+document.getElementById("drug-id").value : "";
    callFetch("/api/drug"+id, method, drugObj, function(res){
        window.location.href = "/";
    });
}