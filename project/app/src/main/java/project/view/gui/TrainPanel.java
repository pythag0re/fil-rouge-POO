package project.view.gui;

import project.model.*;
import javax.swing.*;
import java.awt.*;

public class TrainPanel extends JPanel {
    private Train train;
    private static final int VEHICLE_HEIGHT = 60;
    private static final int VEHICLE_SPACING = 5;
    
    public TrainPanel(Train train) {
        this.train = train;
        setBackground(new Color(245, 250, 255));
        setPreferredSize(new Dimension(800, 150));
    }
    
    public void setTrain(Train train) {
        this.train = train;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (train == null || train.getVehicules().isEmpty()) {
            drawEmptyTrainMessage(g2d);
            return;
        }
        
        int startX = 50;
        int centerY = getHeight() / 2;
        
        // Dessiner les rails
        drawRails(g2d, startX, centerY);
        
        // Dessiner chaque véhicule
        for (int i = 0; i < train.getVehicules().size(); i++) {
            Vehicule v = train.getVehicules().get(i);
            int vehicleWidth = Math.max(40, v.getLength() * 4);
            
            drawVehicle(g2d, v, startX, centerY - VEHICLE_HEIGHT/2, vehicleWidth, VEHICLE_HEIGHT);
            
            // Dessiner le lien entre les véhicules
            if (i < train.getVehicules().size() - 1) {
                drawLink(g2d, startX + vehicleWidth, centerY);
            }
            
            startX += vehicleWidth + VEHICLE_SPACING + 10;
        }
        
        // Ajouter une légende si l'espace le permet
        if (startX < getWidth() - 200) {
            drawLegend(g2d, getWidth() - 180, 20);
        }
    }
    
    private void drawEmptyTrainMessage(Graphics2D g2d) {
        g2d.setColor(Color.GRAY);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        String message = "🚂 Aucun train à afficher - Ajoutez des véhicules !";
        int messageWidth = g2d.getFontMetrics().stringWidth(message);
        g2d.drawString(message, getWidth()/2 - messageWidth/2, getHeight()/2);
    }
    
    private void drawRails(Graphics2D g2d, int startX, int centerY) {
        g2d.setColor(Color.DARK_GRAY);
        g2d.setStroke(new BasicStroke(2));
        
        // Rails supérieurs et inférieurs
        int railOffset = 30;
        g2d.drawLine(startX - 20, centerY - railOffset, getWidth() - 20, centerY - railOffset);
        g2d.drawLine(startX - 20, centerY + railOffset, getWidth() - 20, centerY + railOffset);
        
        // Traverses
        g2d.setColor(new Color(139, 69, 19)); // Marron pour les traverses
        g2d.setStroke(new BasicStroke(3));
        for (int x = startX; x < getWidth(); x += 30) {
            g2d.drawLine(x, centerY - railOffset, x, centerY + railOffset);
        }
    }
    
    private void drawVehicle(Graphics2D g2d, Vehicule v, int x, int y, int width, int height) {
        if (v instanceof Locomotive) {
            drawLocomotive(g2d, (Locomotive) v, x, y, width, height);
        } else if (v instanceof Car) {
            drawCar(g2d, (Car) v, x, y, width, height);
        } else if (v instanceof Wagon) {
            drawWagon(g2d, (Wagon) v, x, y, width, height);
        }
    }
    
    private void drawLocomotive(Graphics2D g2d, Locomotive loc, int x, int y, int width, int height) {
        // Corps principal
        g2d.setColor(Color.RED);
        g2d.fillRoundRect(x, y, width, height, 15, 15);
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawRoundRect(x, y, width, height, 15, 15);
        
        // Cabine
        g2d.setColor(new Color(200, 200, 220));
        g2d.fillRect(x + width - 20, y, 20, height);
        
        // Roues
        g2d.setColor(Color.BLACK);
        int wheelY = y + height - 8;
        for (int i = 0; i < 4; i++) {
            int wheelX = x + 10 + i * (width - 20) / 3;
            g2d.fillOval(wheelX, wheelY, 16, 16);
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fillOval(wheelX + 4, wheelY + 4, 8, 8);
            g2d.setColor(Color.BLACK);
        }
        
        // Détails
        g2d.setColor(Color.YELLOW);
        g2d.fillOval(x + 5, y + 5, 10, 10); // Phare
        
        // Texte
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 10));
        String powerText = "P: " + loc.getPower();
        int textWidth = g2d.getFontMetrics().stringWidth(powerText);
        g2d.drawString(powerText, x + width/2 - textWidth/2, y + height/2 + 5);
    }
    
    private void drawCar(Graphics2D g2d, Car car, int x, int y, int width, int height) {
        // Corps principal
        g2d.setColor(new Color(70, 130, 180)); // Bleu acier
        g2d.fillRect(x, y, width, height);
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawRect(x, y, width, height);
        
        // Fenêtres
        g2d.setColor(new Color(135, 206, 250)); // Bleu ciel
        int windowHeight = height / 3;
        for (int i = 0; i < 3; i++) {
            g2d.fillRect(x + 5, y + 5 + i * (windowHeight + 5), width - 10, windowHeight);
        }
        
        // Roues
        g2d.setColor(Color.BLACK);
        int wheelY = y + height - 6;
        for (int i = 0; i < 3; i++) {
            int wheelX = x + 15 + i * (width - 30) / 2;
            g2d.fillOval(wheelX, wheelY, 12, 12);
        }
        
        // Texte - passagers
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 10));
        String passengersText = car.getNumberOfPassengers() + "/" + car.getNumberOfSeats();
        int textWidth = g2d.getFontMetrics().stringWidth(passengersText);
        g2d.drawString(passengersText, x + width/2 - textWidth/2, y + height - 20);
    }
    
    private void drawWagon(Graphics2D g2d, Wagon wagon, int x, int y, int width, int height) {
        // Corps principal
        g2d.setColor(new Color(34, 139, 34)); // Vert forêt
        g2d.fillRect(x, y, width, height);
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawRect(x, y, width, height);
        
        // Portes
        g2d.setColor(new Color(160, 82, 45)); // Marron
        g2d.fillRect(x + width/2 - 15, y + 10, 30, height - 20);
        
        // Roues
        g2d.setColor(Color.BLACK);
        int wheelY = y + height - 6;
        for (int i = 0; i < 4; i++) {
            int wheelX = x + 10 + i * (width - 20) / 3;
            g2d.fillOval(wheelX, wheelY, 10, 10);
        }
        
        // Texte - charge
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 10));
        String loadText = wagon.getRealLoad() + "/" + wagon.getMaximumLoad();
        int textWidth = g2d.getFontMetrics().stringWidth(loadText);
        g2d.drawString(loadText, x + width/2 - textWidth/2, y + 20);
    }
    
    private void drawLink(Graphics2D g2d, int x, int y) {
        g2d.setColor(Color.DARK_GRAY);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(x, y - VEHICLE_HEIGHT/4, x + 10, y - VEHICLE_HEIGHT/4);
        g2d.drawLine(x, y + VEHICLE_HEIGHT/4, x + 10, y + VEHICLE_HEIGHT/4);
        
        // Connecteur
        g2d.setColor(Color.ORANGE);
        g2d.fillOval(x + 3, y - 3, 6, 6);
    }
    
    private void drawLegend(Graphics2D g2d, int x, int y) {
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.drawString("Légende:", x, y);
        
        y += 20;
        
        // Locomotive
        g2d.setColor(Color.RED);
        g2d.fillRect(x, y, 15, 10);
        g2d.setColor(Color.BLACK);
        g2d.drawString(": Locomotive", x + 20, y + 10);
        
        y += 20;
        
        // Voiture
        g2d.setColor(new Color(70, 130, 180));
        g2d.fillRect(x, y, 15, 10);
        g2d.setColor(Color.BLACK);
        g2d.drawString(": Voiture", x + 20, y + 10);
        
        y += 20;
        
        // Wagon
        g2d.setColor(new Color(34, 139, 34));
        g2d.fillRect(x, y, 15, 10);
        g2d.setColor(Color.BLACK);
        g2d.drawString(": Wagon", x + 20, y + 10);
    }
}