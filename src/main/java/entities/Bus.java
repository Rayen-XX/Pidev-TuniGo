package entities;

public class Bus {
    private int idBus ;
    private String numeroBus;

    public int getIdBus() {
        return idBus;
    }

    public void setIdBus(int idBus) {
        this.idBus = idBus;
    }

    public String getNumeroBus() {
        return numeroBus;
    }

    public void setNumeroBus(String numeroBus) {
        this.numeroBus = numeroBus;
    }

    public Bus() {
    }

    public Bus(int idBus, String numeroBus) {
        this.idBus = idBus;
        this.numeroBus = numeroBus;
    }

    @Override
    public String toString() {
        return "Metro{" +
                "idBus=" + idBus +
                ", numeroBus='" + numeroBus + '\'' +
                '}';
    }
}
