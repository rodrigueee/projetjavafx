package projet.controleurs;

import java.io.IOException;
import java.util.HashMap;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import projet.modele.ModeleDAO;

/**
 * controleur principal de l'app
 *
 * @author rodri
 */
public class MainControleur extends StackPane {

    /**
     * Stock les écrans à afficher
     */
    private HashMap<String, Node> screens = new HashMap<>();
    /**
     * modeleDAO de l'app
     */
    private ModeleDAO mod;

    /**
     * constructeur par defaut qui appelle le constructeur parent
     */
    public MainControleur() {
        super();
    }

    /**
     * Ajoute l'ecran à la collection
     *
     * @param name id de l'ecran
     * @param screen l'ecran à ajouter
     */
    private void addScreen(String name, Node screen) {
        screens.put(name, screen);
    }

    /**
     * Charge le fichier fxml, ajoute l'écran à la collection d'écrans et     
     * injecte finalement le screenPane au contrôleur.
     *
     * @param name id de l'ecran
     * @param resource fichier fxml de l'ecran
     */
    public void loadScreen(String name, String resource) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = (Parent) myLoader.load();
            ControleursInterface myScreenControler = ((ControleursInterface) myLoader.getController());
            myScreenControler.setMainControleur(this);
            myScreenControler.setMod(mod);
            addScreen(name, loadScreen);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Cette méthode essaie d'afficher l'écran avec un nom prédéfini. Tout
     * d'abord, il s'assure que l'écran a déjà été chargé. Alors s'il y a plus
     * d'un écran, le nouvel écran est ajouté en second, puis l'écran actuel est
     * supprimé. Si aucun écran n'est affiché, le nouvel écran est simplement
     * ajouté à la racine.
     *
     * @param name id l'ecran
     */
    public void setScreen(final String name) {
        if (screens.get(name) != null) {   //ecran charge
            final DoubleProperty opacity = opacityProperty();

            if (!getChildren().isEmpty()) {    //s'il y a plus d'un ecran
                Timeline fade = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
                        new KeyFrame(new Duration(1000), (ActionEvent t) -> {
                            getChildren().remove(0);                    //retire l'ecran affiche
                            getChildren().add(0, screens.get(name));     //ajoute l'ecran
                            Timeline fadeIn = new Timeline(
                                    new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                                    new KeyFrame(new Duration(800), new KeyValue(opacity, 1.0)));
                            fadeIn.play();
                        }, new KeyValue(opacity, 0.0)));
                fade.play();

            } else {
                setOpacity(0.0);
                getChildren().add(screens.get(name));       //aucun autre ecran n'a été affiché, alors il suffit d'afficher l'ecran actuel
                Timeline fadeIn = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                        new KeyFrame(new Duration(2500), new KeyValue(opacity, 1.0)));
                fadeIn.play();
            }
        } else {
            System.out.println("screen hasn't been loaded!!! \n");
        }
    }

    /**
     * setter du modeleDAO de l'app
     * @param mod le modeleDAO de l'app
     */
    public void setMod(ModeleDAO mod) {
        this.mod = mod;
    }
}
