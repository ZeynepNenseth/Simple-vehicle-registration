function validerPersonnr(personnr) {
    const regexp = /^[0-9]{11}$/;
    const riktig = regexp.test(personnr);

    if(!riktig) {
        $("#wrongPId").html("Personnummer må bestå av 11 tall");
        return false;
    } else {
        $("#wrongPId").html("");
        return true;
    }
}

function validerNavn(navn) {
    const regexp = /^[a-zA-ZøæåØÆÅ. \-]{2,30}$/;
    const riktig = regexp.test(navn);

    if(!riktig) {
        $("#wrongName").html("Navnet må bestå av 2 til 30 bokstaver");
        return false;
    } else {
        $("#wrongName").html("");
        return true;
    }
}

function validerAdresse(adresse) {
    const regexp = /^[0-9a-zA-ZøæåØÆÅ. \-]{2,50}$/;
    const riktig = regexp.test(adresse);

    if(!riktig) {
        $("#wrongAddress").html("Adressen må bestå av 2 til 50 bokstaver og tall");
        return false;
    } else {
        $("#wrongAddress").html("");
        return true;
    }
}

function validerKjennetegn(kjennetegn) {
    const regexp = /^([A-ZØÆÅ]{2})?\d{5}$/;
    const riktig = regexp.test(kjennetegn);

    if(!riktig) {
        $("#wrongLicence").html("Kjennetegn må bestå av 2 store bokstaver og 5 siffer");
        return false;
    } else {
        $("#wrongLicence").html("");
        return true;
    }
}

function validerBrukernavn(brukernavn) {
    const regexp = /^[a-zA-ZøæåØÆÅ. \-]{2,30}$/;
    const riktig = regexp.test(brukernavn);

    if(!riktig) {
        $("#wrongUsername").html("Brukernavnet må bestå av 2 til 30 bokstaver");
        return false;
    } else {
        $("#wrongUsername").html("");
        return true;
    }
}

function validerPassord(passord) {
    const regexp = /^(?=.*[A-ZÆØÅa-zøæå])(?=.*\d)[A-ZØÆÅa-zøæå\d]{8,}$/;
    const riktig = regexp.test(passord);

    if(!riktig) {
        $("#wrongPassword").html("Passordet må være minimum 8 tegn, minst en bokstav og et tall");
        return false;
    } else {
        $("#wrongPassword").html("");
        return true;
    }
}

/* annen mulighet
function loggInnValideringOK() {
    return (validerBrukernavn && validerPassord());
}*/