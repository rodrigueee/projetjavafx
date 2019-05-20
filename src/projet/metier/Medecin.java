/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.metier;

import java.util.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import projet.observer.Sujet;

/**
 *
 * @author rodri
 */
public class Medecin extends Sujet {

    /**
     * id unique du medecin
     */
    private int idmed;
    /**
     * nom du medecin
     */
    private String nomM;
    /**
     * prenom du medecin
     */
    private final StringProperty nomProp;
    private String prenomM;
    /**
     * matricule du medecin
     */
    private final StringProperty prenomProp;
    private String matricule;
    /**
     * telephone du medecin
     */
    private String tel;

    /**
     * constructeur par defaut
     */
    public Medecin() {
        this(0, null, null, null, null);
    }

    /**
     * constructeur paramétré
     *
     * @param idmed
     * @param nomM
     * @param prenomM
     * @param tel
     * @param matricule
     */
    public Medecin(int idmed, String nomM, String prenomM, String matricule, String tel) {
        this.idmed = idmed;
        this.nomM = nomM;
        this.prenomM = prenomM;
        this.matricule = matricule;
        this.tel = tel;
        this.prenomProp = new SimpleStringProperty(prenomM);
        this.nomProp = new SimpleStringProperty(nomM);
    }

    /**
     * constructeur paramétré
     *
     * @param nomM
     * @param prenomM
     * @param tel
     * @param matricule
     */
    public Medecin(String nomM, String prenomM, String matricule, String tel) {
        this.nomM = nomM;
        this.prenomM = prenomM;
        this.matricule = matricule;
        this.tel = tel;
        this.prenomProp = new SimpleStringProperty(prenomM);
        this.nomProp = new SimpleStringProperty(nomM);

    }

    /**
     * constructeur paramétré
     *
     * @param matricule
     */
    public Medecin(String matricule) {
        this.matricule = matricule;
        this.prenomProp = new SimpleStringProperty("");
        this.nomProp = new SimpleStringProperty("");
    }

    /**
     * recupérer le nom du medecin en mode lecture
     *
     * @return le nom du medecin
     */
    public String getNomM() {
        return nomM;
    }

    /**
     * modifier le nom du medecin en mode ecriture
     *
     * @param nomM nom du medecin
     */
    public void setNomM(String nomM) {
        this.nomM = nomM;
    }

    /**
     * recupérer le prenom du medecin en mode lecture
     *
     * @return le prenom du medecin
     */
    public String getPrenomM() {
        return prenomM;
    }

    /**
     * modifier le prenom du medecin en mode ecriture
     *
     * @param prenomM le prenom du medecin
     */
    public void setPrenomM(String prenomM) {
        this.prenomM = prenomM;
    }

    /**
     * recupérer le matricule du medecin en mode lecture
     *
     * @return le matricule du medecin
     */
    public String getMatricule() {
        return matricule;
    }

    /**
     * modifier le matricule du medecin en mode ecriture
     *
     * @param matricule le matricule du medecin
     */
    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    /**
     * recupérer le numéro de telephone du medecin en mode lecture
     *
     * @return le numero de telephone du medecin
     */
    public String getTel() {
        return tel;
    }

    /**
     * modifier le numero de telephone du medecin en mode ecriture
     *
     * @param tel le numero de telephone du medecin
     */
    public void setTel(String tel) {
        this.tel = tel;
        notify("Je viens de changer de numéro : " + tel, this);
    }

    /**
     * recupérer l'id du medecin en mode lecture
     *
     * @return l'id du medecin
     */
    public int getIdmed() {
        return idmed;
    }

    public void setIdmed(int idmed) {
        this.idmed = idmed;
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
        return "Matricule : " + matricule + ", Nom : " + nomM + ", Prenom : " + prenomM;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.idmed;
        hash = 97 * hash + Objects.hashCode(this.nomM);
        hash = 97 * hash + Objects.hashCode(this.prenomM);
        hash = 97 * hash + Objects.hashCode(this.matricule);
        hash = 97 * hash + Objects.hashCode(this.tel);
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
        final Medecin other = (Medecin) obj;
        if (this.idmed != other.idmed) {
            return false;
        }
        if (!Objects.equals(this.nomM, other.nomM)) {
            return false;
        }
        if (!Objects.equals(this.prenomM, other.prenomM)) {
            return false;
        }
        if (!Objects.equals(this.matricule, other.matricule)) {
            return false;
        }
        if (!Objects.equals(this.tel, other.tel)) {
            return false;
        }
        return true;
    }

}
