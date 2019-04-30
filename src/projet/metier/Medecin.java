/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.metier;

import java.util.*;

/**
 *
 * @author rodri
 */
public class Medecin {

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
    private String prenomM;
    /**
     * matricule du medecin
     */
    private String matricule;
    /**
     * telephone du medecin
     */
    private String tel;

    /**
     * constructeur par defaut
     */
    public Medecin() {
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
    }

    /**
     * constructeur paramétré
     *
     * @param nomM
     * @param prenomM
     * @param tel
     * @param matricule
     */
    public Medecin(String nomM, String prenomM, String matricule,String tel) {
        this.nomM = nomM;
        this.prenomM = prenomM;
        this.matricule = matricule;
        this.tel = tel;
        
    }

    /**
     * constructeur paramétré
     *
     * @param matricule
     */
    public Medecin(String matricule) {
        this.matricule = matricule;
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

    /**
     * affiche du textes et les valeurs des variables
     *
     * @return le message indiqué
     */
    @Override
    public String toString() {
        return "Medecin{" + "idmed=" + idmed + ", nomM=" + nomM + ", prenomM=" + prenomM + ", matricule=" + matricule + ", tel=" + tel + '}';
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
