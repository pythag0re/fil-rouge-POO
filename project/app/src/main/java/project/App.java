package project;

import project.view.gui.TrainGUI;
import project.view.console.ConsoleView;
import project.controller.TrainController;
import javax.swing.*;

public class App {
    public static void main(String[] args) {
        // Demander à l'utilisateur quelle interface il souhaite utiliser
        String[] options = {"Interface Graphique", "Console"};
        int choice = JOptionPane.showOptionDialog(null,
            "Choisissez le mode d'interface:",
            "Gestion de Train",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
        
        TrainController controller = new TrainController();
        
        if (choice == 0) {
            // Lancement de l'interface graphique
            SwingUtilities.invokeLater(() -> {
                TrainGUI gui = new TrainGUI();
                gui.setVisible(true);
            });
        } else {
            // Lancement de l'interface console
            ConsoleView consoleView = new ConsoleView(controller);
            consoleView.start();
        }
    }
}