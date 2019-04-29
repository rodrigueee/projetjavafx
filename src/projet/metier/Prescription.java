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
public class Prescription {

    /**
     * id unique de la prescription
     */
    private int idPresc;
    /**
     * date de la prescription
     */
    private String dateP;
    /**
     * variable d'instance de type medecin
     */
    private Medecin md;
    /**
     * variable d'instance de type patient
     */
    private Patient pt;
    /**
     * liste de type medicament
     */
    private List<Medicament> lMdedic;

    /**
     * constructeur par defaut
     */
    public Prescription() {
    }

    /**
     * constructeur paramétré
     *
     * @param dateP
     * @param md
     * @param pt
     */
    public Prescription(String dateP, Medecin md, Patient pt) {
        this.dateP = dateP;
        this.md = md;
        this.pt = pt;
    }

    /**
     * constructeur paramétré
     *
     * @param dateP
     */
    public Prescription(String dateP) {
        this.dateP = dateP;
    }

    /**
     * recuperer l'id de la prescription en mode lecture
     *
     * @return l'id de la prescription
     */
    public int getIdPresc() {
        return idPresc;
    }

    public void setIdPresc(int idPresc) {
        this.idPresc = idPresc;
    }

    public void setMd(Medecin md) {
        this.md = md;
    }

    public void setPt(Patient pt) {
        this.pt = pt;
    }

    public void setlMdedic(List<Medicament> lMdedic) {
        this.lMdedic = lMdedic;
    }
/**
 * recuperer la date de la prescription
 * @return 
 */
    public String getDateP() {
        return dateP;
    }

    /**
     * modifier la date de la prescription en mode ecriture
     *
     * @param dateP date de la prescription
     */
    public void setDateP(String dateP) {
        this.dateP = dateP;
    }

    /**
     * recuperer une valeur de type medecin en mode lecture
     *
     * @return
     */
    public Medecin getMd() {
        return md;
    }

    /**
     * recuperer une valeur de type patient en mode lecture
     *
     * @return
     */
    public Patient getPt() {
        return pt;
    }

    /**
     * recuperer une liste de type medicament en mode lecture
     *
     * @return
     */
    public List<Medicament> getlMdedic() {
        return lMdedic;
    }

    /**
     * affiche du textes et les valeurs des variables
     *
     * @return le message indiqué
     */
    @Override
    public String toString() {
        return "Prescription{" + "dateP=" + dateP + ", md=" + md + ", pt=" + pt + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.dateP);
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
        final Prescription other = (Prescription) obj;
        if (!Objects.equals(this.dateP, other.dateP)) {
            return false;
        }
        return true;
    }



}
