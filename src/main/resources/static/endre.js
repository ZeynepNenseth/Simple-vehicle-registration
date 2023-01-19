$(function() {
    const id = window.location.search.substring(1); //forklar dette igjen leter den etter id kolonnen? hvorfra
    const url = "/hentEnMotorvogn?" + id; // id = 'id=...',
    $.get(url, function(enMotorvogn) {
        $("#id").val(enMotorvogn.id);
        $("#personnr").val(enMotorvogn.personnr);
        $("#navn").val(enMotorvogn.navn);
        $("#adresse").val(enMotorvogn.adresse);
        $("#kjennetegn").val(enMotorvogn.kjennetegn);
        $("#merke").val(enMotorvogn.merke);
        $("#modell").val(enMotorvogn.modell);
    })
        .fail(function(jqXHR) {
            const json = $.parseJSON(jqXHR.responseText);
            $("#feil").html(json.message);
        });
})
function validerOgEndreMotorvogn() {
    const personnrRiktig = validerPersonnr($("#personnr").val());
    const navnRiktig = validerNavn($("#navn").val(),);
    const adresseRiktig = validerAdresse($("#adresse").val());
    const kjennetegnRiktig = validerKjennetegn($("#kjennetegn").val());

    if(personnrRiktig && navnRiktig && adresseRiktig && kjennetegnRiktig) {
        endreMotorvogn();
    }
}


function endreMotorvogn() {
    let motorvogn = {
        id : $("#id").val(),
        personnr: $("#personnr").val(),
        navn: $("#navn").val(),
        adresse: $("#adresse").val(),
        kjennetegn: $("#kjennetegn").val(),
        merke: $("#merke").val(),
        modell: $("#modell").val()
    };

    $.post("/endreEnMotorvogn", motorvogn, function () {
        window.location.href = "index.html";
    })
        .fail(function(jqXHR) {
                const json = $.parseJSON(jqXHR.responseText);
                $("#feil").html(json.message);
            });
}