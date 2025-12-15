package project.model;

public class Car extends Vehicule {
    private int numberOfSeats;
    private int numberOfPassengers;

    public Car() {}

    public Car(int length, int id, int numberOfSeats, int numberOfPassengers) {
        super(length, id);
        this.numberOfSeats = numberOfSeats;
        this.numberOfPassengers = Math.min(numberOfPassengers, numberOfSeats);
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = Math.min(numberOfPassengers, this.numberOfSeats);
    }

    @Override
    public String toString() {
        return "Car [id=" + getId() + ", length=" + getLength() + 
            ", numberOfSeats=" + numberOfSeats + 
            ", numberOfPassengers=" + numberOfPassengers + "]";
    }
}