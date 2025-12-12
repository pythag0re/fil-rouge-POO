package project.model;

public class Locomotive extends Vehicule {
    private int power;

    public Locomotive() {}

    public Locomotive(int length, int id, int power) {
        super(length, id);
        this.power = power;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    @Override
    public String toString() {
        return "Locomotive [id=" + getId() + ", length=" + getLength() + ", power=" + power + "]";
    }
}