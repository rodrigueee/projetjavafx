package projet.metier;

import java.util.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import projet.observer.Observer;

public class Patient extends Observer {

    /**
     * id unique du patient
     */
    private int idpat;
    /**
     * nom du patient
     */
    private String nomP;
    private final StringProperty nomProp;
    /**
     * prenom du patient
     */
    private String prenomP;
    private final StringProperty prenomProp;
    /**
     * telephone du patient
     */
    private String tel;

    /**
     * Constructeur par défaut
     */
    public Patient() {
        this(0, null, null, null);
    }

    /**
     * constructeur paramétré
     *
     * @param idpat
     * @param nomP
     * @param prenomP
     * @param tel
     */
    public Patient(int idpat, String nomP, String prenomP, String tel) {
        this.idpat = idpat;
        this.nomP = nomP;
        this.prenomP = prenomP;
        this.tel = tel;
        this.prenomProp = new SimpleStringProperty(prenomP);
        this.nomProp = new SimpleStringProperty(nomP);
    }

    /**
     * Constructeur complet
     */
    public Patient(String nomP, String prenomP, String tel) {
        this.nomP = nomP;
        this.prenomP = prenomP;
        this.tel = tel;
        this.prenomProp = new SimpleStringProperty(prenomP);
        this.nomProp = new SimpleStringProperty(nomP);

    }

    /**
     * Constructeur paramétré
     */
    public Patient(String nomC, String prenomP) {
        this.nomP = nomP;
        this.prenomP = prenomP;
        this.prenomProp = new SimpleStringProperty(prenomP);
        this.nomProp = new SimpleStringProperty(nomP);
    }

    /**
     * recuperer le nom du patient en mode lecture
     *
     * @return le nom du patient
     */
    public String getNomP() {
        // TODO implement here
        return nomP;
    }

    /**
     * modifier le nom du patient en mode ecriture
     *
     * @param nomP nom du patient
     */
    public void setNomP(String nomP) {

        this.nomP = nomP;
    }

    /**
     * recuperer le prenom du patient en mode lecture
     *
     * @return le prenom du patient
     */
    public String getPrenomP() {
        // TODO implement here
        return prenomP;
    }

    /**
     * modifier le prenom du patient en mode ecriture
     *
     * @param prenomP prenom du patient
     */
    public void setPrenomP(String prenomP) {
        // TODO implement here
        this.prenomP = prenomP;
    }

    /**
     * recuperer le numero de telephone du patient en mode lecture
     *
     * @return le numero de téléphone
     */
    public String getTel() {
        // TODO implement here
        return tel;
    }

    /**
     * modifier le numero de telephone du patient en mode ecriture
     *
     * @param tel telephone du patient
     */
    public void setTel(String tel) {
        // TODO implement here
        this.tel = tel;
    }

    /**
     * recuperer l'id du patient en mode lecture
     *
     * @return l'id du patient
     */
    public int getIdpat() {
        return idpat;
    }

    public void setIdpat(int idpat) {
        this.idpat = idpat;
    }

    public String getPrenomProp() {
        return prenomProp.get();
    }

    public String getNomProp() {
        return nomProp.get();
    }

    /**
     * affiche du textes et les valeurs des variables
     *
     * @return le message indiqué
     */
    @Override
    public String toString() {
        return "Nom : " + nomP + ", Prenom : " + prenomP;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + this.idpat;
        hash = 23 * hash + Objects.hashCode(this.nomP);
        hash = 23 * hash + Objects.hashCode(this.prenomP);
        hash = 23 * hash + Objects.hashCode(this.tel);
        return hash;
    }

    /**
     * permet de comparer des objets
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Patient other = (Patient) obj;
        if (this.idpat != other.idpat) {
            return false;
        }
        if (!Objects.equals(this.nomP, other.nomP)) {
            return false;
        }
        if (!Objects.equals(this.prenomP, other.prenomP)) {
            return false;
        }
        if (!Objects.equals(this.tel, other.tel)) {
            return false;
        }
        return true;
    }
    /**
     * methode qui notifie le changement du numero de tel du medecin de ce patient
     * @param notif message à notifié
     * @param md le medecin qui à changé de modifier
     * @return le message de notification
     */
    @Override
    public String observe(String notif, Medecin md) {
        return prenomP + " " + nomP + " à été notifié par le médecin : " + md.getNomM() + " " + md.getPrenomM() + " de : " + notif;
    }

}
