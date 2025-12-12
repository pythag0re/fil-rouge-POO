package project.view.console;

import project.controller.TrainController;
import project.model.*;
import java.util.Scanner;

public class ConsoleView {
    private TrainController controller;
    private Scanner scanner;
    
    public ConsoleView(TrainController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }
    
    public void start() {
        System.out.println("=== GESTION DE TRAIN (Console) ===");
        
        int choice;
        do {
            displayMenu();
            choice = scanner.nextInt();
            scanner.nextLine();
            
            handleChoice(choice);
            
        } while (choice != 8);
        
        scanner.close();
    }
    
    private void displayMenu() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Voir le train");
        System.out.println("2. Ajouter une locomotive");
        System.out.println("3. Ajouter une voiture");
        System.out.println("4. Ajouter un wagon");
        System.out.println("5. Définir station de départ");
        System.out.println("6. Définir station d'arrivée");
        System.out.println("7. Voir représentation graphique");
        System.out.println("8. Quitter");
        System.out.print("Votre choix: ");
    }
    
    private void handleChoice(int choice) {
        switch (choice) {
            case 1:
                displayTrainInfo();
                break;
            case 2:
                addLocomotive();
                break;
            case 3:
                addCar();
                break;
            case 4:
                addWagon();
                break;
            case 5:
                setDepartureStation();
                break;
            case 6:
                setDestinationStation();
                break;
            case 7:
                displayGraphicalTrain();
                break;
            case 8:
                System.out.println("Au revoir !");
                break;
            default:
                System.out.println("Choix invalide !");
        }
    }
    
    private void displayTrainInfo() {
        Train train = controller.getTrain();
        System.out.println("\n=== INFORMATION DU TRAIN ===");
        System.out.println("Station de départ: " + train.getDepartureStation());
        System.out.println("Station d'arrivée: " + train.getDestinationStation());
        System.out.println("Nombre de véhicules: " + train.getTotalVehicules());
        
        if (train.getTotalVehicules() > 0) {
            System.out.println("\nListe des véhicules:");
            for (Vehicule v : train.getVehicules()) {
                System.out.println("  - " + v);
            }
        }
    }
    
    private void addLocomotive() {
        System.out.print("Longueur: ");
        int length = scanner.nextInt();
        System.out.print("ID: ");
        int id = scanner.nextInt();
        System.out.print("Puissance: ");
        int power = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Position (FRONT/BACK): ");
        String position = scanner.nextLine();
        Location location = position.equalsIgnoreCase("BACK") ? Location.BACK : Location.FRONT;
        
        controller.addLocomotive(length, id, power, location);
        System.out.println("Locomotive ajoutée !");
    }
    
    private void addCar() {
        System.out.print("ID: ");
        int id = scanner.nextInt();
        System.out.print("Longueur: ");
        int length = scanner.nextInt();
        System.out.print("Nombre de sièges: ");
        int seats = scanner.nextInt();
        System.out.print("Nombre de voyageurs: ");
        int passengers = scanner.nextInt();
        scanner.nextLine();
        
        controller.addCar(length, id, seats, passengers);
        System.out.println("Voiture ajoutée !");
    }
    
    private void addWagon() {
        System.out.print("ID: ");
        int id = scanner.nextInt();
        System.out.print("Longueur: ");
        int length = scanner.nextInt();
        System.out.print("Charge utile maximale: ");
        int maxLoad = scanner.nextInt();
        System.out.print("Charge utile réelle: ");
        int realLoad = scanner.nextInt();
        scanner.nextLine();
        
        controller.addWagon(length, id, maxLoad, realLoad);
        System.out.println("Wagon ajouté !");
    }
    
    private void setDepartureStation() {
        System.out.print("Nom de la station de départ: ");
        String name = scanner.nextLine();
        controller.setDepartureStation(name);
        System.out.println("Station de départ définie !");
    }
    
    private void setDestinationStation() {
        System.out.print("Nom de la station d'arrivée: ");
        String name = scanner.nextLine();
        controller.setDestinationStation(name);
        System.out.println("Station d'arrivée définie !");
    }
    
    private void displayGraphicalTrain() {
        String trainDrawing = TrainDrawer.drawTrain(controller.getTrain());
        System.out.println("\nReprésentation graphique du train:");
        System.out.println(trainDrawing);
    }
}