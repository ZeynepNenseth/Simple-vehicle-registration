package oslomet.webprog21;

public class Bil {
    private int id;
    private String merke;
    private String modell;

    public Bil(int id, String merke, String modell) {
        this.id = id;
        this.merke = merke;
        this.modell = modell;
    }

    public Bil() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMerke() {
        return merke;
    }

    public void setMerke(String merke) {
        this.merke = merke;
    }

    public String getModell() {
        return modell;
    }

    public void setModell(String modell) {
        this.modell = modell;
    }
}
