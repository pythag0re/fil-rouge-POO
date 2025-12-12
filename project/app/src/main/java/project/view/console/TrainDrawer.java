package project.view.console;

import project.model.*;
import java.util.List;

public class TrainDrawer {
    
    public static String drawTrain(Train train) {
        StringBuilder sb = new StringBuilder();
        List<Vehicule> vehicules = train.getVehicules();
        Location locLocation = train.getLocomotiveLocation();
        
        for (int i = 0; i < vehicules.size(); i++) {
            Vehicule v = vehicules.get(i);
            
            if (v instanceof Locomotive) {
                sb.append(drawLocomotive((Locomotive) v, locLocation));
            } else if (v instanceof Car) {
                sb.append(drawCar((Car) v));
            } else if (v instanceof Wagon) {
                sb.append(drawWagon((Wagon) v));
            }
            
            if (i < vehicules.size() - 1) {
                sb.append("-");
            }
        }
        
        return sb.toString();
    }
    
    private static String drawLocomotive(Locomotive loc, Location location) {
        StringBuilder sb = new StringBuilder();
        if (location == Location.FRONT) {
            sb.append("<").append(loc.getPower());
            for (int i = 0; i < loc.getLength(); i++) {
                sb.append("#");
            }
        } else {
            for (int i = 0; i < loc.getLength(); i++) {
                sb.append("#");
            }
            sb.append(loc.getPower()).append(">");
        }
        return sb.toString();
    }
    
    private static String drawCar(Car car) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(car.getNumberOfPassengers());
        for (int i = 0; i < car.getLength(); i++) {
            sb.append("=");
        }
        sb.append(car.getNumberOfSeats()).append("]");
        return sb.toString();
    }
    
    private static String drawWagon(Wagon wagon) {
        StringBuilder sb = new StringBuilder();
        sb.append("|").append(wagon.getRealLoad());
        for (int i = 0; i < wagon.getLength(); i++) {
            sb.append("_");
        }
        sb.append(wagon.getMaximumLoad()).append("|");
        return sb.toString();
    }
}