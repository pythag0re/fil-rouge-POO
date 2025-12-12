package project.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;

public class Train {
    private Station departureStation;
    private Station destinationStation;
    private List<Vehicule> vehicules;
    private Location locomotiveLocation;

    public Train() {
        this.departureStation = new Station("Unknown");
        this.destinationStation = new Station("Unknown");
        this.vehicules = new LinkedList<>();
        this.locomotiveLocation = Location.FRONT;
    }

    public Train(Station departureStation, Station destinationStation) {
        this.departureStation = departureStation;
        this.destinationStation = destinationStation;
        this.vehicules = new LinkedList<>();
        this.locomotiveLocation = Location.FRONT;
    }

    // Getters et Setters
    public Station getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(Station departureStation) {
        this.departureStation = departureStation;
    }

    public Station getDestinationStation() {
        return destinationStation;
    }

    public void setDestinationStation(Station destinationStation) {
        this.destinationStation = destinationStation;
    }

    public Location getLocomotiveLocation() {
        return locomotiveLocation;
    }

    public void setLocomotiveLocation(Location locomotiveLocation) {
        this.locomotiveLocation = locomotiveLocation;
    }

    public List<Vehicule> getVehicules() {
        return new LinkedList<>(vehicules);
    }

    // Méthodes métier
    public void addVehicule(Vehicule vehicule) {
        if (vehicule instanceof Locomotive) {
            addLocomotive((Locomotive) vehicule);
        } else {
            vehicules.add(vehicule);
        }
    }

    public void addLocomotive(Locomotive locomotive) {
        if (locomotiveLocation == Location.FRONT) {
            vehicules.add(0, locomotive);
        } else {
            vehicules.add(locomotive);
        }
    }

    public boolean removeVehiculeById(int id) {
        Iterator<Vehicule> iterator = vehicules.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId() == id) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public boolean removeVehiculeByIndex(int index) {
        if (index >= 0 && index < vehicules.size()) {
            vehicules.remove(index);
            return true;
        }
        return false;
    }

    public Vehicule findVehiculeById(int id) {
        for (Vehicule v : vehicules) {
            if (v.getId() == id) {
                return v;
            }
        }
        return null;
    }

    public Vehicule getVehiculeByIndex(int index) {
        if (index >= 0 && index < vehicules.size()) {
            return vehicules.get(index);
        }
        return null;
    }

    public boolean hasLocomotive() {
        for (Vehicule v : vehicules) {
            if (v instanceof Locomotive) {
                return true;
            }
        }
        return false;
    }

    public int getTotalVehicules() {
        return vehicules.size();
    }

    @Override
    public String toString() {
        return "Train:\nDeparture Station: " + departureStation + 
               "\nDestination Station: " + destinationStation + 
               "\nVehicules: " + vehicules;
    }
}