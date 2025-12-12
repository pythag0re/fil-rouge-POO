package project.model;

public class Wagon extends Vehicule {
    private int maximumLoad;
    private int realLoad;

    public Wagon() {}

    public Wagon(int length, int id, int maximumLoad, int realLoad) {
        super(length, id);
        this.maximumLoad = maximumLoad;
        this.realLoad = Math.min(realLoad, maximumLoad);
    }

    public int getMaximumLoad() {
        return maximumLoad;
    }

    public int getRealLoad() {
        return realLoad;
    }

    public void setMaximumLoad(int maximumLoad) {
        this.maximumLoad = maximumLoad;
    }

    public void setRealLoad(int realLoad) {
        this.realLoad = Math.min(realLoad, this.maximumLoad);
    }

    @Override
    public String toString() {
        return "Wagon [id=" + getId() + ", length=" + getLength() + 
               ", maximumLoad=" + maximumLoad + ", realLoad=" + realLoad + "]";
    }
}