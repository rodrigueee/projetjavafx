package projet.observer;

import projet.metier.Medecin;

/**
 *
 * @author rodri
 */
public abstract class Observer {

    /**
     * methode qui notifie le changement du numero de tel du medecin de ce
     * patient (est override dans patient)
     *
     * @param notif message à notifié
     * @param md le medecin qui à changé de modifier
     * @return le message de notification
     */
    public abstract String observe(String notif, Medecin md);

}
