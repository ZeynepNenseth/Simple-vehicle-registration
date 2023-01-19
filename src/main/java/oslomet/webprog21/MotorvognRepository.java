package oslomet.webprog21;

import org.mindrot.jbcrypt.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.BeanDefinitionParserDelegate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MotorvognRepository {
    @Autowired
    private JdbcTemplate db;

    private Logger logger = LoggerFactory.getLogger(MotorvognRepository.class);

    /* private String krypterPassord(String passord) {
    BCryptPassordEncoder bCrypt = new BCryptPassordEncoder(strength: 10);
    return bCrypt.encode(passord)
    }
    public boolean krypterAllePassord() {
    String sql = "SELECT * FROM Bruker";
    String kryptertPassord;
    try {
    List <Bruker>List = db. query(sql, new BeanpropertyRowMapper(Bruker.class));
    for (Bruker bruker : list) {
    kryptertPassord = krypterPassord(bruker.getPassord());
    sql = "UPDATE Bruker SET passord=? WHERE id=?";
    db.update(sql, kryptertPassord, bruker.getId();
    }
    }
    }*/
    private String krypterPassordDb(String passord) {
        String kryptertPassord = BCrypt.hashpw(passord,BCrypt.gensalt(10));
        return kryptertPassord;
    }

    private boolean sjekkPassord(String passord, String hashPassord) {
        boolean matchingPassord = BCrypt.checkpw(passord, hashPassord);
        return matchingPassord;
    }

    public List<Bil> hentAlleBilerDb() {
        String sql = "SELECT * FROM Bil";
        try {
            List<Bil> alleBiler = db.query(sql, new BeanPropertyRowMapper(Bil.class));
            return alleBiler;
        } catch (Exception e){
            logger.error("Feil i hentAlleBiler: " + e);
            return null;
        }

    }

    public boolean lagreMotorvognDb(Motorvogn innMotorvogn) {
        String sql = "INSERT INTO Motorvogn(personnr, navn, adresse, kjennetegn, merke, modell) VALUES(?,?,?,?,?,?)";
        try {
            db.update(sql,innMotorvogn.getPersonnr(),innMotorvogn.getNavn(),innMotorvogn.getAdresse(),
                    innMotorvogn.getKjennetegn(), innMotorvogn.getMerke(),innMotorvogn.getModell());
            return true;
        } catch (Exception e) {
            logger.error("Feil i lagreMotorvogn: " + e);
            return false;
        }
    }

    public List<Motorvogn> hentAlleMotorvognDb() {
        String sql = "SELECT * FROM Motorvogn";
        try {
            List<Motorvogn> alleMotorvogn = db.query(sql, new BeanPropertyRowMapper(Motorvogn.class));
            return alleMotorvogn;
        } catch (Exception e) {
            logger.error("Feil i hentAlleMotorvogn: " + e);
            return null;
        }
    }

    public Motorvogn henteEnMotorvognDb(int id) {
        String sql = "SELECT * FROM Motorvogn WHERE id=?";
        try {
            Motorvogn enMotorvogn = db.queryForObject(sql, BeanPropertyRowMapper.newInstance(Motorvogn.class), id);
            return enMotorvogn;
        } catch (Exception e) {
            logger.error("Feil i henteEnMotorvogn" + e);
            return null;
        }
    }

    public boolean endreEnMotorvognDb(Motorvogn enMotorvogn) {
        String sql = "UPDATE Motorvogn SET personnr=?, navn=?, adresse=?, kjennetegn=?, merke=?, modell=? WHERE id=?";
        try {
            db.update(sql, enMotorvogn.getPersonnr(), enMotorvogn.getNavn(), enMotorvogn.getAdresse(), enMotorvogn.getKjennetegn(), enMotorvogn.getMerke(),
                    enMotorvogn.getModell(), enMotorvogn.getId());
            return true;
        } catch (Exception e) {
            logger.error("Feil i endreEnMotorvogn: " + e);
            return false;
        }
    }

    public boolean slettEnMotorvognDb(int id) {
        String sql = "DELETE FROM Motorvogn WHERE id=?";
        try {
            db.update(sql, id);
            return true;
        } catch (Exception e) {
            logger.error("Feil i slettEnMotorvogn: " + e);
            return false;
        }
    }

    public boolean slettAlleMotorvognDb() {
        String sql = "DELETE FROM Motorvogn";
        try {
            db.update(sql);
            return true;
        } catch (Exception e) {
            logger.error("Feil i slettAlleMotorvogn: " + e);
            return false;
        }
    }

    public boolean loggInnDb(Bruker innBruker) {
        boolean riktigPassord = false;
        String sql = "SELECT * FROM Bruker WHERE brukernavn=?";

        try {
           Bruker funnet = db.queryForObject(sql, new BeanPropertyRowMapper<>(Bruker.class),innBruker.getBrukernavn());
           //det samme som sql, BeanPropertyRowMapper.newInstance(Bruker.class), innBruker.getBrukernavn()
            if(funnet !=null) {
                if(sjekkPassord(innBruker.getPassord(), funnet.getPassord())) {
                    riktigPassord = true;
                }
            } return riktigPassord;

        } catch (Exception e) {
            logger.error("Feil i loggInnDb: " + e);
            return false;
        }

    }

    /*public boolean loggInnDb(String brukernavn, String passord) {
        String sql = "SELECT COUNT(*) FROM Bruker WHERE brukernavn=? AND passord=?";
        try {
            int antall = db.queryForObject(sql, Integer.class, brukernavn, passord);
            if(antall > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error("Feil i loggInnDb: " + e);
            return false;
        }

    }*/


    public List<Bruker> hentAlleBruker() {
        String sql = "SELECT * FROM Bruker";
        try {
            List<Bruker> brukerList = db.query(sql, new BeanPropertyRowMapper(Bruker.class));
            for(Bruker b: brukerList) {
                String hash = krypterPassordDb(b.getPassord());
                String sql2 = "UPDATE Bruker SET passord=? WHERE brukernavn=?";
                db.update(sql2, hash, b.getBrukernavn());
            }
            return db.query(sql, new BeanPropertyRowMapper(Bruker.class));
        } catch (Exception e) {
            logger.error("Feil i hentAlleBruker: " + e);
            return null;
        }
    }

    /*public boolean lagreBrukerDb(Bruker innBruker) {
        String hash = krypterPassordDb(innBruker.getPassord());
        String sql = "INSERT INTO Bruker (brukernavn, passord) VALUES (?,?)";
        try {
            db.update(sql, innBruker.getBrukernavn(), hash);
            return true;
        } catch (Exception e) {
            logger.error("Feil i lagreBrukerDb: " + e);
            return false;
        }
    }*/
}
