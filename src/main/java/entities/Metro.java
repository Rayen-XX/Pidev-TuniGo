package entities;

public class Metro {
    private int idMetro ;
    private String numeroMetro;

    public int getIdMetro() {
        return idMetro;
    }

    public void setIdMetro(int idMetro) {
        this.idMetro = idMetro;
    }

    public String getNumeroMetro() {
        return numeroMetro;
    }

    public void setNumeroMetro(String numeroMetro) {
        this.numeroMetro = numeroMetro;
    }

    public Metro() {
    }

    public Metro(int idMetro, String numeroMetro) {
        this.idMetro = idMetro;
        this.numeroMetro = numeroMetro;
    }

    @Override
    public String toString() {
        return "Metro{" +
                "idMetro=" + idMetro +
                ", numeroMetro='" + numeroMetro + '\'' +
                '}';
    }
}
