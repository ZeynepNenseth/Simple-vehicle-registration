$(function() {
    hentAlleMotorvogn();

})

function hentAlleMotorvogn() {
    $.get("hentAlleMotorvogn", function(motorvogn) {
        formaterMotorvogn(motorvogn);
    })
        .fail(function(jqXHR) {
            const json = $.parseJSON(jqXHR.responseText);
            $("#feil").html(json.message);
        });
}

function slettEnMotorvogn(id) {
    const url = "/slettEnMotorvogn?id=" + id; //er dette egentlig "http://localhost:8080/slettEnMotorvogn...
    $.get(url, function() {
        window.location.href="/index.html"; //hvorfor ikke å skrive det ut fra vehicleList??
    })
        .fail(function(jqXHR) {
            const json = $.parseJSON(jqXHR.responseText);
            $("#feil").html(json.message);
        });
}

function formaterMotorvogn(motorvogn) {
    let ut = "<table class='table table-striped'><tr>" +
        "<th>Personnr</th><th>Navn</th><th>Adresse</th><th>Kjennetegn</th><th>Bilmerke</th><th>Bilmodell</th>" +
        "<th></th><th></th></tr>";

    for(let info of motorvogn) {
        ut += "<tr>";
        ut += "<td>" + info.personnr + "</td>" +
            "<td>" + info.navn + "</td>" +
            "<td>" + info.adresse + "</td>" +
            "<td>" + info.kjennetegn + "</td>" +
            "<td>" + info.merke + "</td>" +
            "<td>" + info.modell + "</td>" +
            "<td><a class='btn btn-primary' href='endre.html?id="+ info.id +"'>Endre</a></td>" +
            "<td><button class='btn btn-danger' onclick='slettEnMotorvogn("+ info.id +")'>Slett</button></td>" +
            "</tr>";
    }
        ut += "</table>";
        $("#vehicleList").html(ut);
}

function slettAlleMotorvogn() {
    $.get("/slettAlle", function() {
        window.location.href="/index.html";//hvorfor ikke å skrive det ut fra vehicleList??
    })
        .fail(function(jqXHR) {
            const json = $.parseJSON(jqXHR.responseText);
            $("#feil").html(json.message);
        });
}

function logout() {
    $.get("/logout", function() {
        window.location.href="login.html";
    })
}