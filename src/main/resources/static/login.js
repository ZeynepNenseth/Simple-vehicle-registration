function validerOgLogin() {
    const brukernavnOk = validerBrukernavn($("#brukernavn").val());
    const passordOk = validerPassord($("#passord").val());

    if(brukernavnOk && passordOk) {
        login();
    }
}

function login() {
    const bruker = {
        brukernavn : $("#brukernavn").val(),
        passord : $("#passord").val()
    }

    $.get("/login", bruker, function(innlogget) {
        if(innlogget) { //dvs det er true
            window.location.href = 'index.html';
        } else {
            $("#feil").html("Feil brukernavn eller passord");
        }
    })
        .fail(function(jqXHR) {
            const json = $.parseJSON(jqXHR.responseText);
            $("#feil").html(json.message);
        });
}

/* annen mulighet: man sender brukernavn og passord i url'en
function loggInn() {
    if(loggInnValideringOK()){
        const url = "/loggInn?brukernavn="+$("#brukernavn").val()+"&passord="+$("#passord").val();
        $.get( url, function( OK ) {
            if(OK){
                window.location.href="liste.html";
            }
            else{
                $("#feil").html("Feil i brukernavn eller passord");
            }
        })
        .fail(function(jqXHR) {
            const json = $.parseJSON(jqXHR.responseText);
            $("#feil").html(json.message);
        });
    }
}
 */

function krypterPassord() {
        $.get("/krypter", function() {
            window.location.href="login.html";
        })
}