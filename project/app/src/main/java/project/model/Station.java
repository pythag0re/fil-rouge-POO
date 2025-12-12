package project.model;

public class Station {
    private String nameOfStation;

    public Station(String nameOfStation) {
        this.nameOfStation = nameOfStation;
    }

    public String getNameOfStation() {
        return nameOfStation;
    }

    public void setNameOfStation(String nameOfStation) {
        this.nameOfStation = nameOfStation;
    }

    @Override
    public String toString() {
        return nameOfStation;
    }
}