package oslomet.webprog21;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RestController
public class MotorvognController {
    @Autowired
    private MotorvognRepository rep;

    @Autowired
    private HttpSession session;

    private Logger logger = LoggerFactory.getLogger(MotorvognController.class);

    private boolean validerMotorvogn(Motorvogn innMotorvogn) {
        String regexpPersonnr = "[0-9]{11}"; // hva med int ??
        String regexpNavn = "[a-zA-ZøæåØÆÅ. \\-]{2,30}";
        String regexpAdresse = "[0-9a-zA-ZøæåØÆÅ. \\-]{2,50}";
        String regexpKjennetegn = "([A-ZØÆÅ]{2})?\\d{5}";

        boolean pIdOk = innMotorvogn.getPersonnr().matches(regexpPersonnr);
        boolean navnOk = innMotorvogn.getNavn().matches(regexpNavn);
        boolean adresseOk = innMotorvogn.getAdresse().matches(regexpAdresse);
        boolean kjennetegnOk = innMotorvogn.getKjennetegn().matches(regexpKjennetegn);

        if (pIdOk && navnOk && adresseOk && kjennetegnOk) {
            return true;
        }
        logger.error("Valideringsfeil!");
        return false;
    }

    @GetMapping("/hentBiler")
    public List<Bil> hentAlleBiler(HttpServletResponse response) throws IOException {
        List<Bil> bilListe = rep.hentAlleBilerDb();
        if(bilListe == null) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i DB - prøv igjen senere");
        }
        return bilListe;
    }

    @PostMapping("/lagreMotorvogn")
    public void lagreMotorvogn(Motorvogn innMotorvogn, HttpServletResponse response) throws IOException {
        if (!validerMotorvogn(innMotorvogn)) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i validering - prøv igjen senere");
        } else {
            if (!rep.lagreMotorvognDb(innMotorvogn)) {
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i DB - prøv igjen senere");
            }
        }
    }

    @GetMapping("hentEnMotorvogn")
    public Motorvogn hentEnMotorvogn(int id, HttpServletResponse response) throws IOException {
        Motorvogn enMotorvogn = rep.henteEnMotorvognDb(id);
        if(enMotorvogn == null) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i DB - prøv igjen senere");
        }
        return enMotorvogn;
    }

    @GetMapping("/hentAlleMotorvogn")
    public List<Motorvogn> hentAlleMotorvogn(HttpServletResponse response) throws IOException {
        List<Motorvogn> alleMotorvogn = rep.hentAlleMotorvognDb();
        if(alleMotorvogn == null) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i DB - prøv igjen senere");
        }
        return alleMotorvogn;
    }

    @PostMapping("/endreEnMotorvogn")
    public void endreEnMotorvogn(Motorvogn enMotorvogn, HttpServletResponse response) throws IOException {
        if (!validerMotorvogn(enMotorvogn)) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i validering - prøv igjen senere");
        } else {
            if(!rep.endreEnMotorvognDb(enMotorvogn)) {
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i DB - prøv igjen senere");
            }
        }

    }

    @GetMapping("/slettEnMotorvogn")
    public void slettEnMotorvogn(int id, HttpServletResponse response) throws IOException {
        if(!rep.slettEnMotorvognDb(id)) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i DB - prøv igjen senere");
        }
    }

    @GetMapping("/slettAlle")
    public void slettAlleMotorvogn(HttpServletResponse response) throws IOException {
        if(!rep.slettAlleMotorvognDb()) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i DB - prøv igjen senere");
        }
    }

    @GetMapping("/login")
    public boolean loggInn(Bruker innBruker) {
        if (rep.loggInnDb(innBruker)) {
            session.setAttribute("innlogget", true);
            return true;
        } else {
            return false;
        }
    }

    /*@GetMapping("/login")
    public boolean loggInn(String brukernavn, String passord) {
        if (rep.loggInnDb(brukernavn, passord)) {
            session.setAttribute("innlogget", true);
            return true;
        } else {
            return false;
        }
    }*/

/* annen måte å sette session
    @GetMapping("/login")
    public boolean login(Kunde kunde) {

        if(rep.sjekkNavnOgPassord(kunde)){
            session.setAttribute("Innlogget",kunde);
            return true;
        }
        return false;
    }*/

    @GetMapping("/logout")
    public void loggUt() {
        session.setAttribute("innlogget", false);
    }

/* annen måte å logge seg ut
    @GetMapping("/logout")
    public void logout() {
        session.removeAttribute("Innlogget");
    }*/

    @GetMapping("/krypter")
    public void krypterDbPassord() {
        rep.hentAlleBruker();
    }
}

