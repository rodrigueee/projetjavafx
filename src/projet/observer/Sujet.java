package projet.observer;

import java.util.ArrayList;
import java.util.List;
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
     * @param o observeur(patient) à ajouter
     */
    public void add(Observer o) {
        listeObservers.add(o);
    }
    /**
     * methode qui permet de supprimer un observeur
     * @param o observeur(patient) à supprimer
     */
    public void remove(Observer o) {
        listeObservers.remove(o);
    }
    /**
     * methode qui notifie les observeurs
     * @param notif le message à notifier
     * @param md le message qui à changer de numero de tel
     */
    public void notify(String notif, Medecin md) {
        listeObservers.forEach((o) -> {
            o.observe(notif, md);
        });
    }
}
