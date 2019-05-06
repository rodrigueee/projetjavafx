package main;

import java.sql.Connection;
import java.sql.SQLException;
import projet.controleurs.MainControleur;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import myconnections.DBConnection;
import projet.modele.ModeleDAO;

/**
 * classe principale de l'app(main)
 *
 * @author rodri
 */
//Gaëtan m'a fait un cours sur JAVAFX (bases)
public class Main extends Application {

    /**
     * l'id de l'ecran d'accueil
     */
    public static String mainId = "main";
    /**
     * fichier d'ecran d'accueil
     */
    private static String mainFile = "/projet/vues/Main.fxml";

    /**
     * id de l'ecran du medicament
     */
    public static String medicId = "medicament";
    /**
     * fichier de l'ecran du medicament
     */
    private static String medicFile = "/projet/vues/Medicament.fxml";

    public static String medecId = "medecin";
    private static String medecFile = "/projet/vues/Medecin.fxml";

    public static String patId = "patient";
    private static String patFile = "/projet/vues/Patient.fxml";

    public static String prescId = "prescription";
    private static String prescFile = "/projet/vues/Prescription.fxml";
    /**
     * titre de l'app
     */
    public static String title = "Gestion Pharmacie - Projet JavaFX";

    /**
     * methode qui demarre l'application
     * etablie la connexion a la BDD 
     * charge les ecrans via le controleur principale
     * affiche l'ecran d'accueil et configure la fenetre de l'application
     * a la fermeture de l'application, ferme la BDD
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {

        Connection cnx = DBConnection.getConnection();
        if (cnx == null) {
            try {
                stop();
            } catch (Exception e) {
                System.out.println("Erreur de fermeture de l'application " + e.getMessage());
            }
        }
        MainControleur mainControleur = new MainControleur();
        ModeleDAO mod = ModeleDAO.getInstance(cnx);
        mainControleur.setMod(mod);
        mainControleur.loadScreen(mainId, mainFile);
        mainControleur.loadScreen(medicId, medicFile);
        mainControleur.loadScreen(medecId, medecFile);
        mainControleur.loadScreen(patId, patFile);
        mainControleur.loadScreen(prescId, prescFile);

        mainControleur.setScreen(mainId);

        Group root = new Group();
        root.getChildren().addAll(mainControleur);
        Scene scene = new Scene(root);
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        // Evenement de fermeture de fenetre, ferme la connexion a la bdd et l'application
        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            try {
                cnx.close();
                stop();
            } catch (SQLException e) {
                System.out.println("Erreur de fermeture de la bdd " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Erreur de fermeture de l'application " + e.getMessage());
            }
        });
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
