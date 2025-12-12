package project.controller;

import project.model.*;

public class TrainController {
    private Train train;
    
    public TrainController() {
        this.train = new Train();
    }
    
    public TrainController(Train train) {
        this.train = train;
    }
    
    public void addLocomotive(int length, int id, int power, Location location) {
        train.setLocomotiveLocation(location);
        Locomotive locomotive = VehiculeFactory.createLocomotive(length, id, power);
        train.addLocomotive(locomotive);
    }
    
    public void addCar(int length, int id, int seats, int passengers) {
        Car car = VehiculeFactory.createCar(length, id, seats, passengers);
        train.addVehicule(car);
    }
    
    public void addWagon(int length, int id, int maxLoad, int realLoad) {
        Wagon wagon = VehiculeFactory.createWagon(length, id, maxLoad, realLoad);
        train.addVehicule(wagon);
    }
    
    public Train getTrain() {
        return train;
    }
    
    public void setDepartureStation(String name) {
        train.setDepartureStation(new Station(name));
    }
    
    public void setDestinationStation(String name) {
        train.setDestinationStation(new Station(name));
    }
}