package project.view.gui;

import project.controller.TrainController;
import project.model.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class TrainGUI extends JFrame {
    private TrainController controller;
    private TrainPanel trainPanel;
    private JTextArea infoArea;
    private JTable vehiculeTable;
    private DefaultTableModel tableModel;
    private JLabel stationDepartLabel;
    private JLabel stationArriveeLabel;
    private JLabel totalVehiculesLabel;
    
    public TrainGUI() {
        this.controller = new TrainController();
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("🚂 Gestion de Train - Interface Graphique");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Panel supérieur avec les informations principales
        JPanel headerPanel = createHeaderPanel();
        
        // Panel pour les boutons de contrôle
        JPanel controlPanel = createControlPanel();
        
        // Panel pour le dessin du train
        trainPanel = new TrainPanel(controller.getTrain());
        trainPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLUE, 2),
            "Représentation Visuelle du Train",
            TitledBorder.CENTER,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14),
            Color.BLUE
        ));
        
        // Panel pour le tableau des véhicules
        JPanel tablePanel = createTablePanel();
        
        // Panel pour les informations détaillées
        JPanel infoPanel = createInfoPanel();
        
        // Organisation dans des onglets
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Vue d'ensemble", createOverviewPanel());
        tabbedPane.addTab("Liste détaillée", tablePanel);
        tabbedPane.addTab("Informations", infoPanel);
        
        // Ajout des composants à la fenêtre
        add(headerPanel, BorderLayout.NORTH);
        add(controlPanel, BorderLayout.WEST);
        add(tabbedPane, BorderLayout.CENTER);
        
        setSize(1200, 800);
        setLocationRelativeTo(null);
        updateDisplay();
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 10, 0));
        panel.setBackground(new Color(240, 240, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        Font headerFont = new Font("Arial", Font.BOLD, 14);
        Color headerColor = new Color(0, 0, 100);
        
        stationDepartLabel = createInfoLabel("Départ: Unknown", headerFont, headerColor);
        stationArriveeLabel = createInfoLabel("Arrivée: Unknown", headerFont, headerColor);
        totalVehiculesLabel = createInfoLabel("Véhicules: 0", headerFont, headerColor);
        
        JButton refreshBtn = new JButton("🔄 Actualiser");
        refreshBtn.setFont(new Font("Arial", Font.BOLD, 12));
        refreshBtn.setBackground(new Color(100, 150, 255));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.addActionListener(e -> updateDisplay());
        
        panel.add(stationDepartLabel);
        panel.add(stationArriveeLabel);
        panel.add(totalVehiculesLabel);
        panel.add(refreshBtn);
        
        return panel;
    }
    
    private JLabel createInfoLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return label;
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Actions"));
        panel.setPreferredSize(new Dimension(200, 0));
        
        String[] buttonInfos = {
            "🚂 Ajouter Locomotive", 
            "🚌 Ajouter Voiture", 
            "📦 Ajouter Wagon",
            "📍 Station Départ", 
            "🎯 Station Arrivée",
            "✏️ Modifier Véhicule",
            "🗑️ Supprimer Véhicule",
            "💾 Exporter Données",
            "📊 Statistiques"
        };
        
        for (String btnText : buttonInfos) {
            JButton button = new JButton(btnText);
            button.setFont(new Font("Arial", Font.PLAIN, 12));
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setBackground(new Color(245, 245, 245));
            
            if (btnText.contains("Ajouter")) {
                button.setBackground(new Color(220, 240, 220));
            } else if (btnText.contains("Supprimer")) {
                button.setBackground(new Color(255, 220, 220));
            }
            
            // Associer les actions
            if (btnText.contains("Locomotive")) {
                button.addActionListener(this::addLocomotiveAction);
            } else if (btnText.contains("Voiture")) {
                button.addActionListener(this::addCarAction);
            } else if (btnText.contains("Wagon")) {
                button.addActionListener(this::addWagonAction);
            } else if (btnText.contains("Départ")) {
                button.addActionListener(this::setDepartureAction);
            } else if (btnText.contains("Arrivée")) {
                button.addActionListener(this::setDestinationAction);
            } else if (btnText.contains("Modifier")) {
                button.addActionListener(this::modifyVehiculeAction);
            } else if (btnText.contains("Supprimer")) {
                button.addActionListener(this::removeVehiculeAction);
            } else if (btnText.contains("Exporter")) {
                button.addActionListener(this::exportDataAction);
            } else if (btnText.contains("Statistiques")) {
                button.addActionListener(this::showStatisticsAction);
            }
            
            panel.add(button);
        }
        
        return panel;
    }
    
    private JPanel createOverviewPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        
        // Ajout du panel de dessin
        panel.add(trainPanel, BorderLayout.CENTER);
        
        // Panel pour les statistiques rapides
        JPanel statsPanel = createQuickStatsPanel();
        panel.add(statsPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createQuickStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 10, 0));
        panel.setBorder(BorderFactory.createTitledBorder("Statistiques Rapides"));
        panel.setBackground(new Color(240, 250, 240));
        
        // Ces labels seront mis à jour dans updateDisplay()
        JLabel locLabel = new JLabel("Locomotives: 0");
        JLabel carLabel = new JLabel("Voitures: 0");
        JLabel wagonLabel = new JLabel("Wagons: 0");
        JLabel totalLabel = new JLabel("Total: 0");
        
        for (JLabel label : new JLabel[]{locLabel, carLabel, wagonLabel, totalLabel}) {
            label.setFont(new Font("Arial", Font.BOLD, 12));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            panel.add(label);
        }
        
        // Stocker les références pour les mettre à jour
        panel.putClientProperty("locLabel", locLabel);
        panel.putClientProperty("carLabel", carLabel);
        panel.putClientProperty("wagonLabel", wagonLabel);
        panel.putClientProperty("totalLabel", totalLabel);
        
        return panel;
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Table model
        String[] columns = {"ID", "Type", "Longueur", "Détails", "Actions"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Seule la colonne Actions est éditable
            }
        };
        
        vehiculeTable = new JTable(tableModel);
        vehiculeTable.setRowHeight(30);
        vehiculeTable.setFont(new Font("Arial", Font.PLAIN, 12));
        vehiculeTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        
        // Personnalisation de la colonne Actions
        TableColumn actionColumn = vehiculeTable.getColumnModel().getColumn(4);
        actionColumn.setCellRenderer(new ButtonRenderer());
        actionColumn.setCellEditor(new ButtonEditor(new JCheckBox()));
        
        JScrollPane scrollPane = new JScrollPane(vehiculeTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        infoArea = new JTextArea();
        infoArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        infoArea.setEditable(false);
        infoArea.setBackground(new Color(250, 250, 250));
        
        JScrollPane scrollPane = new JScrollPane(infoArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Informations Complètes"));
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    // Classes pour les boutons dans le tableau
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }
        
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText("Modifier");
            setBackground(new Color(220, 230, 255));
            return this;
        }
    }
    
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private boolean isPushed;
        
        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }
        
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            button.setText("Modifier");
            isPushed = true;
            return button;
        }
        
        public Object getCellEditorValue() {
            if (isPushed) {
                int row = vehiculeTable.getSelectedRow();
                if (row >= 0) {
                    int id = (int) tableModel.getValueAt(row, 0);
                    modifySelectedVehicule(id);
                }
            }
            isPushed = false;
            return "Modifier";
        }
    }
    
    // Méthodes d'action améliorées
    private void addLocomotiveAction(ActionEvent e) {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        
        JTextField lengthField = new JTextField("10");
        JTextField idField = new JTextField();
        JTextField powerField = new JTextField("1000");
        JComboBox<String> positionCombo = new JComboBox<>(new String[]{"En tête", "En queue"});
        
        panel.add(new JLabel("Longueur:"));
        panel.add(lengthField);
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Puissance (en kW):"));
        panel.add(powerField);
        panel.add(new JLabel("Position:"));
        panel.add(positionCombo);
        
        int result = JOptionPane.showConfirmDialog(this, panel, 
                "Ajouter une Locomotive", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                int length = Integer.parseInt(lengthField.getText());
                int id = Integer.parseInt(idField.getText());
                int power = Integer.parseInt(powerField.getText());
                Location location = positionCombo.getSelectedIndex() == 1 ? Location.BACK : Location.FRONT;
                
                controller.addLocomotive(length, id, power, location);
                updateDisplay();
                JOptionPane.showMessageDialog(this, "✅ Locomotive ajoutée avec succès !");
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, 
                        "❌ Erreur: Veuillez entrer des nombres valides!", 
                        "Erreur", 
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void addCarAction(ActionEvent e) {
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        
        JTextField idField = new JTextField();
        JTextField lengthField = new JTextField("8");
        JTextField seatsField = new JTextField("50");
        JTextField passengersField = new JTextField("30");
        
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Longueur:"));
        panel.add(lengthField);
        panel.add(new JLabel("Nombre de sièges:"));
        panel.add(seatsField);
        panel.add(new JLabel("Nombre de voyageurs:"));
        panel.add(passengersField);
        
        int result = JOptionPane.showConfirmDialog(this, panel, 
                "Ajouter une Voiture", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                int length = Integer.parseInt(lengthField.getText());
                int seats = Integer.parseInt(seatsField.getText());
                int passengers = Integer.parseInt(passengersField.getText());
                
                controller.addCar(length, id, seats, passengers);
                updateDisplay();
                JOptionPane.showMessageDialog(this, "✅ Voiture ajoutée avec succès !");
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, 
                        "❌ Erreur: Veuillez entrer des nombres valides!", 
                        "Erreur", 
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void addWagonAction(ActionEvent e) {
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        
        JTextField idField = new JTextField();
        JTextField lengthField = new JTextField("12");
        JTextField maxLoadField = new JTextField("10000");
        JTextField realLoadField = new JTextField("5000");
        
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Longueur:"));
        panel.add(lengthField);
        panel.add(new JLabel("Charge maximale (kg):"));
        panel.add(maxLoadField);
        panel.add(new JLabel("Charge actuelle (kg):"));
        panel.add(realLoadField);
        
        int result = JOptionPane.showConfirmDialog(this, panel, 
                "Ajouter un Wagon", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                int length = Integer.parseInt(lengthField.getText());
                int maxLoad = Integer.parseInt(maxLoadField.getText());
                int realLoad = Integer.parseInt(realLoadField.getText());
                
                controller.addWagon(length, id, maxLoad, realLoad);
                updateDisplay();
                JOptionPane.showMessageDialog(this, "✅ Wagon ajouté avec succès !");
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, 
                        "❌ Erreur: Veuillez entrer des nombres valides!", 
                        "Erreur", 
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void modifyVehiculeAction(ActionEvent e) {
        Train train = controller.getTrain();
        if (train.getVehicules().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                    "❌ Aucun véhicule à modifier!", 
                    "Information", 
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String[] options = {"Par ID", "Par Index", "Annuler"};
        int choice = JOptionPane.showOptionDialog(this,
                "Comment souhaitez-vous sélectionner le véhicule ?",
                "Modifier un Véhicule",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        
        if (choice == 0) { // Par ID
            String idStr = JOptionPane.showInputDialog(this, "Entrez l'ID du véhicule:");
            if (idStr != null) {
                try {
                    int id = Integer.parseInt(idStr);
                    modifySelectedVehicule(id);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "ID invalide!");
                }
            }
        } else if (choice == 1) { // Par Index
            String indexStr = JOptionPane.showInputDialog(this, 
                    "Entrez l'index du véhicule (0-" + (train.getVehicules().size()-1) + "):");
            if (indexStr != null) {
                try {
                    int index = Integer.parseInt(indexStr);
                    if (index >= 0 && index < train.getVehicules().size()) {
                        Vehicule v = train.getVehicules().get(index);
                        modifySelectedVehicule(v.getId());
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Index invalide!");
                }
            }
        }
    }
    
    private void modifySelectedVehicule(int id) {
        Train train = controller.getTrain();
        Vehicule v = null;
        for (Vehicule vehicule : train.getVehicules()) {
            if (vehicule.getId() == id) {
                v = vehicule;
                break;
            }
        }
        
        if (v == null) {
            JOptionPane.showMessageDialog(this, "Véhicule non trouvé!");
            return;
        }
        
        if (v instanceof Locomotive) {
            Locomotive loc = (Locomotive) v;
            String newPower = JOptionPane.showInputDialog(this, 
                    "Nouvelle puissance (actuelle: " + loc.getPower() + "):", 
                    String.valueOf(loc.getPower()));
            if (newPower != null) {
                try {
                    loc.setPower(Integer.parseInt(newPower));
                    updateDisplay();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Valeur invalide!");
                }
            }
        } else if (v instanceof Car) {
            Car car = (Car) v;
            String newPassengers = JOptionPane.showInputDialog(this,
                    "Nouveau nombre de voyageurs (max " + car.getNumberOfSeats() + 
                    ", actuel: " + car.getNumberOfPassengers() + "):",
                    String.valueOf(car.getNumberOfPassengers()));
            if (newPassengers != null) {
                try {
                    int passengers = Integer.parseInt(newPassengers);
                    if (passengers <= car.getNumberOfSeats()) {
                        car.setNumberOfPassengers(passengers);
                        updateDisplay();
                    } else {
                        JOptionPane.showMessageDialog(this, 
                                "Ne peut dépasser " + car.getNumberOfSeats() + "!");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Valeur invalide!");
                }
            }
        } else if (v instanceof Wagon) {
            Wagon wagon = (Wagon) v;
            String newLoad = JOptionPane.showInputDialog(this,
                    "Nouvelle charge (max " + wagon.getMaximumLoad() + 
                    ", actuelle: " + wagon.getRealLoad() + "):",
                    String.valueOf(wagon.getRealLoad()));
            if (newLoad != null) {
                try {
                    int load = Integer.parseInt(newLoad);
                    if (load <= wagon.getMaximumLoad()) {
                        wagon.setRealLoad(load);
                        updateDisplay();
                    } else {
                        JOptionPane.showMessageDialog(this, 
                                "Ne peut dépasser " + wagon.getMaximumLoad() + "!");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Valeur invalide!");
                }
            }
        }
    }
    
    private void removeVehiculeAction(ActionEvent e) {
        Train train = controller.getTrain();
        if (train.getVehicules().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Aucun véhicule à supprimer!");
            return;
        }
        
        String[] options = {"Par ID", "Par Index", "Annuler"};
        int choice = JOptionPane.showOptionDialog(this,
                "Comment souhaitez-vous sélectionner le véhicule à supprimer ?",
                "Supprimer un Véhicule",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        
        if (choice == 0) { // Par ID
            String idStr = JOptionPane.showInputDialog(this, "Entrez l'ID du véhicule:");
            if (idStr != null) {
                try {
                    int id = Integer.parseInt(idStr);
                    if (train.removeVehiculeById(id)) {
                        updateDisplay();
                        JOptionPane.showMessageDialog(this, "✅ Véhicule supprimé !");
                    } else {
                        JOptionPane.showMessageDialog(this, "❌ ID non trouvé !");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "ID invalide!");
                }
            }
        } else if (choice == 1) { // Par Index
            String indexStr = JOptionPane.showInputDialog(this, 
                    "Entrez l'index du véhicule (0-" + (train.getVehicules().size()-1) + "):");
            if (indexStr != null) {
                try {
                    int index = Integer.parseInt(indexStr);
                    if (train.removeVehiculeByIndex(index)) {
                        updateDisplay();
                        JOptionPane.showMessageDialog(this, "✅ Véhicule supprimé !");
                    } else {
                        JOptionPane.showMessageDialog(this, "❌ Index invalide !");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Index invalide!");
                }
            }
        }
    }
    
    private void setDepartureAction(ActionEvent e) {
        String name = JOptionPane.showInputDialog(this, 
                "Nom de la station de départ:", 
                controller.getTrain().getDepartureStation().toString());
        if (name != null && !name.trim().isEmpty()) {
            controller.setDepartureStation(name);
            updateDisplay();
        }
    }
    
    private void setDestinationAction(ActionEvent e) {
        String name = JOptionPane.showInputDialog(this, 
                "Nom de la station d'arrivée:", 
                controller.getTrain().getDestinationStation().toString());
        if (name != null && !name.trim().isEmpty()) {
            controller.setDestinationStation(name);
            updateDisplay();
        }
    }
    
    private void exportDataAction(ActionEvent e) {
        StringBuilder data = new StringBuilder();
        Train train = controller.getTrain();
        
        data.append("=== EXPORT DES DONNÉES DU TRAIN ===\n\n");
        data.append("Station de départ: ").append(train.getDepartureStation()).append("\n");
        data.append("Station d'arrivée: ").append(train.getDestinationStation()).append("\n");
        data.append("Position locomotive: ").append(train.getLocomotiveLocation()).append("\n");
        data.append("Nombre total de véhicules: ").append(train.getVehicules().size()).append("\n\n");
        
        data.append("=== LISTE DES VÉHICULES ===\n");
        for (Vehicule v : train.getVehicules()) {
            data.append(v.toString()).append("\n");
        }
        
        JTextArea textArea = new JTextArea(data.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        
        JOptionPane.showMessageDialog(this, scrollPane, "Export des Données", 
                JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showStatisticsAction(ActionEvent e) {
        Train train = controller.getTrain();
        int locomotives = 0;
        int cars = 0;
        int wagons = 0;
        int totalLength = 0;
        int totalPassengers = 0;
        int totalLoad = 0;
        
        for (Vehicule v : train.getVehicules()) {
            totalLength += v.getLength();
            
            if (v instanceof Locomotive) {
                locomotives++;
            } else if (v instanceof Car) {
                cars++;
                totalPassengers += ((Car) v).getNumberOfPassengers();
            } else if (v instanceof Wagon) {
                wagons++;
                totalLoad += ((Wagon) v).getRealLoad();
            }
        }
        
        String stats = "📊 STATISTIQUES DU TRAIN\n\n" +
                      "🚂 Composition:\n" +
                      "  • Locomotives: " + locomotives + "\n" +
                      "  • Voitures: " + cars + "\n" +
                      "  • Wagons: " + wagons + "\n" +
                      "  • Total: " + train.getVehicules().size() + " véhicules\n\n" +
                      "📏 Dimensions:\n" +
                      "  • Longueur totale: " + totalLength + " unités\n" +
                      "  • Voyageurs transportés: " + totalPassengers + "\n" +
                      "  • Charge transportée: " + totalLoad + " kg\n\n" +
                      "📍 Informations:\n" +
                      "  • Départ: " + train.getDepartureStation() + "\n" +
                      "  • Arrivée: " + train.getDestinationStation() + "\n" +
                      "  • Position locomotive: " + train.getLocomotiveLocation();
        
        JTextArea textArea = new JTextArea(stats);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        
        JOptionPane.showMessageDialog(this, scrollPane, "Statistiques", 
                JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void updateDisplay() {
        Train train = controller.getTrain();
        
        // Mise à jour des labels d'en-tête
        stationDepartLabel.setText("🚉 Départ: " + train.getDepartureStation());
        stationArriveeLabel.setText("🎯 Arrivée: " + train.getDestinationStation());
        totalVehiculesLabel.setText("🚂 Véhicules: " + train.getVehicules().size());
        
        // Mise à jour du tableau
        tableModel.setRowCount(0);
        int index = 0;
        for (Vehicule v : train.getVehicules()) {
            String type = "Inconnu";
            String details = "";
            
            if (v instanceof Locomotive) {
                type = "Locomotive";
                details = "Puissance: " + ((Locomotive) v).getPower() + " kW";
            } else if (v instanceof Car) {
                type = "Voiture";
                Car car = (Car) v;
                details = String.format("Sièges: %d/%d", 
                        car.getNumberOfPassengers(), car.getNumberOfSeats());
            } else if (v instanceof Wagon) {
                type = "Wagon";
                Wagon wagon = (Wagon) v;
                details = String.format("Charge: %d/%d kg", 
                        wagon.getRealLoad(), wagon.getMaximumLoad());
            }
            
            tableModel.addRow(new Object[]{
                v.getId(), type, v.getLength(), details, "Modifier"
            });
            index++;
        }
        
        // Mise à jour des informations détaillées
        StringBuilder info = new StringBuilder();
        info.append("=== INFORMATION DÉTAILLÉE DU TRAIN ===\n\n");
        info.append("📋 CONFIGURATION GÉNÉRALE\n");
        info.append(String.format("%-25s: %s\n", "Station de départ", train.getDepartureStation()));
        info.append(String.format("%-25s: %s\n", "Station d'arrivée", train.getDestinationStation()));
        info.append(String.format("%-25s: %s\n", "Position locomotive", train.getLocomotiveLocation()));
        info.append(String.format("%-25s: %d\n\n", "Nombre de véhicules", train.getVehicules().size()));
        
        if (!train.getVehicules().isEmpty()) {
            info.append("📦 INVENTAIRE DES VÉHICULES\n");
            for (int i = 0; i < train.getVehicules().size(); i++) {
                Vehicule v = train.getVehicules().get(i);
                info.append(String.format("\n[%d] %s", i, v));
            }
        }
        
        infoArea.setText(info.toString());
        
        // Mise à jour du dessin
        trainPanel.setTrain(train);
        trainPanel.repaint();
        
        // Mise à jour des statistiques rapides
        updateQuickStats();
    }
    
    private void updateQuickStats() {
        Train train = controller.getTrain();
        int locomotives = 0;
        int cars = 0;
        int wagons = 0;
        
        for (Vehicule v : train.getVehicules()) {
            if (v instanceof Locomotive) {
                locomotives++;
            } else if (v instanceof Car) {
                cars++;
            } else if (v instanceof Wagon) {
                wagons++;
            }
        }
        
        // Mettre à jour les statistiques rapides si le panel existe
        Component[] components = getContentPane().getComponents();
        for (Component comp : components) {
            if (comp instanceof JTabbedPane) {
                Component tab = ((JTabbedPane) comp).getComponentAt(0);
                if (tab instanceof JPanel) {
                    JPanel quickStatsPanel = findQuickStatsPanel((JPanel) tab);
                    if (quickStatsPanel != null) {
                        JLabel locLabel = (JLabel) quickStatsPanel.getClientProperty("locLabel");
                        JLabel carLabel = (JLabel) quickStatsPanel.getClientProperty("carLabel");
                        JLabel wagonLabel = (JLabel) quickStatsPanel.getClientProperty("wagonLabel");
                        JLabel totalLabel = (JLabel) quickStatsPanel.getClientProperty("totalLabel");
                        
                        if (locLabel != null) locLabel.setText("🚂 Locomotives: " + locomotives);
                        if (carLabel != null) carLabel.setText("🚌 Voitures: " + cars);
                        if (wagonLabel != null) wagonLabel.setText("📦 Wagons: " + wagons);
                        if (totalLabel != null) totalLabel.setText("📊 Total: " + train.getVehicules().size());
                    }
                }
            }
        }
    }
    
    private JPanel findQuickStatsPanel(JPanel panel) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel child = (JPanel) comp;
                if (child.getClientProperty("locLabel") != null) {
                    return child;
                }
            }
        }
        return null;
    }
    
    public static void startGUI() {
        SwingUtilities.invokeLater(() -> {
            TrainGUI gui = new TrainGUI();
            gui.setVisible(true);
        });
    }
}