package project.controller;

import project.model.*;

public class VehiculeFactory {
    
    public static Car createCar(int length, int id, int numberOfSeats, int numberOfPassengers) {
        return new Car(length, id, numberOfSeats, numberOfPassengers);
    }
    
    public static Wagon createWagon(int length, int id, int maximumLoad, int realLoad) {
        return new Wagon(length, id, maximumLoad, realLoad);
    }
    
    public static Locomotive createLocomotive(int length, int id, int power) {
        return new Locomotive(length, id, power);
    }
}