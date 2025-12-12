package project.model;

public abstract class Vehicule {
    private int length;
    private int id;

    public Vehicule(int length, int id) {
        this.length = length;
        this.id = id;
    }

    public Vehicule() {}

    public int getLength() {
        return length;
    }

    public int getId() {
        return id;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setId(int id) {
        this.id = id;
    }
}