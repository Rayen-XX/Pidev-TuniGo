package entities;

public class Train {
    private int idTrain ;
    private String numeroTrain;

    public int getIdTrain() {
        return idTrain;
    }

    public void setIdTrain(int idTrain) {
        this.idTrain = idTrain;
    }

    public String getNumeroTrain() {
        return numeroTrain;
    }

    public void setNumeroTrain(String numeroTrain) {
        this.numeroTrain = numeroTrain;
    }

    public Train() {
    }

    public Train(int idTrain, String numeroTrain) {
        this.idTrain = idTrain;
        this.numeroTrain = numeroTrain;
    }

    @Override
    public String toString() {
        return "train{" +
                "idTrain=" + idTrain +
                ", numeroTrain='" + numeroTrain + '\'' +
                '}';
    }
}
