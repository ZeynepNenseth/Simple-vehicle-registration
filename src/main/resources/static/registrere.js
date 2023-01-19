$(function () {
    hentAlleBiler();
})

function hentAlleBiler() {
    $.get("/hentBiler", function (biler) {
        formaterBiler(biler);
    })
        .fail(function(jqXHR) {
            const json = $.parseJSON(jqXHR.responseText);
            $("#feil").html(json.message);
        });
}

function formaterBiler(biler) {
    let ut = "<select id='valgtMerke' onchange='finnModell()'>";

    let forrigeMerke = "";

    ut += "<option>Velg merke</option>";

    for (const bil of biler) {
        if (bil.merke !== forrigeMerke) {
            ut += "<option>" + bil.merke + "</option>";
        }
        forrigeMerke = bil.merke;
    }
    ut += "</select>";
    $("#merke").html(ut);
}

function finnModell() {
    const valgtMerke = $("#valgtMerke").val();
    $.get("/hentBiler", function (biler) {
        formaterModell(biler, valgtMerke);
    });
}

function formaterModell(biler, valgtMerke) {
    let ut = "<select id='valgtModell'>";

    ut += "<option>Velg modell</option>"

    for (const bil of biler) {
        if (bil.merke === valgtMerke) {
            ut += "<option>" + bil.modell + "</option>";
        }
    }
    ut += "</select>";
    $("#modell").html(ut);
}

function validerOgRegMotorvogn() {
    const personnrRiktig = validerPersonnr($("#personnr").val());
    const navnRiktig = validerNavn($("#navn").val(),);
    const adresseRiktig = validerAdresse($("#adresse").val());
    const kjennetegnRiktig = validerKjennetegn($("#kjennetegn").val());

    if(personnrRiktig && navnRiktig && adresseRiktig && kjennetegnRiktig) {
        regMotorvogn();
    }
}

function regMotorvogn() {
    let motorvogn = {
        personnr: $("#personnr").val(),
        navn: $("#navn").val(),
        adresse: $("#adresse").val(),
        kjennetegn: $("#kjennetegn").val(),
        merke: $("#valgtMerke").val(),
        modell: $("#valgtModell").val()
    };

    $.post("/lagreMotorvogn", motorvogn, function () {
        window.location.href = "index.html";
    })
        .fail(function(jqXHR) {
            const json = $.parseJSON(jqXHR.responseText);
            $("#feil").html(json.message);
        });
}