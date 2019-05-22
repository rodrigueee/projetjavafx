package projet.observer;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import main.Main;
import projet.metier.Medecin;

/**
 *
 * @author rodri
 */
public abstract class Sujet {

    /**
     * liste des observeurs(patients)
     */
    protected List<Observer> listeObservers = new ArrayList<>();

    /**
     * methode qui permet d'ajouter un observeur
     *
     * @param o observeur(patient) à ajouter
     */
    public void add(Observer o) {
        listeObservers.add(o);
    }

    /**
     * methode qui permet de supprimer un observeur
     *
     * @param o observeur(patient) à supprimer
     */
    public void remove(Observer o) {
        listeObservers.remove(o);
    }

    /**
     * methode qui notifie les observeurs
     *
     * @param notif le message à notifier
     * @param md le message qui à changer de numero de tel
     */
    public void notify(String notif, Medecin md) {
        String message = "";
        for (Observer o : listeObservers) {
            message += o.observe(notif, md) + "\n\n";
        }
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle(Main.title);
        info.setHeaderText("Notification(s)");
        info.setContentText(message);
        info.show();
    }
}
