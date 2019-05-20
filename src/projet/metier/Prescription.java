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

/**
 *
 * @author rodri
 */
public class Prescription {

    /**
     * id unique de la prescription
     */
    private int idPresc;
    private final IntegerProperty idPrescProp;
    /**
     * date de la prescription
     */
    private String dateP;
    private final StringProperty dateProp;
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
    private List<Medicament> lMedic;

    /**
     * constructeur par defaut
     */
    public Prescription() {
        this(null, null, null);
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
        this.idPrescProp = new SimpleIntegerProperty();
        this.dateProp = new SimpleStringProperty(dateP);
    }

    /**
     * constructeur paramétré du builder (design pattern)
     *
     * @param builder instance du builder
     */
    public Prescription(Builder builder) {
        this.idPresc = builder.idPresc;
        this.dateP = builder.dateP;
        this.md = builder.md;
        this.pt = builder.pt;
        this.lMedic = builder.lMedic;
        this.idPrescProp = new SimpleIntegerProperty(builder.idPresc);
        this.dateProp = new SimpleStringProperty(dateP);
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

    public void setlMedic(List<Medicament> lMedic) {
        this.lMedic = lMedic;
    }

    /**
     * recuperer la date de la prescription
     *
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
    public List<Medicament> getlMedic() {
        return lMedic;
    }

    public int getIdPrescProp() {
        return idPrescProp.get();
    }

    public String getDateProp() {
        return dateProp.get();
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
        int hash = 7;
        hash = 37 * hash + this.idPresc;
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
        if (this.idPresc != other.idPresc) {
            return false;
        }
        return true;
    }

    public static class Builder {

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
        private List<Medicament> lMedic;

        /**
         * constucteur par defaut
         */
        public Builder() {
        }

        public Builder setIdPresc(int idPresc) {
            this.idPresc = idPresc;
            return this;
        }

        public Builder setDateP(String dateP) {
            this.dateP = dateP;
            return this;
        }

        public Builder setMedec(Medecin md) {
            this.md = md;
            return this;
        }

        public Builder setPat(Patient pt) {
            this.pt = pt;
            return this;
        }

        public Builder setLMedic(List<Medicament> lMedic) {
            this.lMedic = lMedic;
            return this;
        }

        /**
         * methode qui permet la creation d'une prescription à condition que
         * toutes les donnees obligatoires soient fournies
         *
         * @return retourne une nouvelle prescription
         * @throws Exception 
         */
        public Prescription build() throws Exception {
            if (dateP == null || md == null || pt == null || lMedic == null) {
                throw new Exception("Informations manquantes");
            }
            if (dateP.trim().length() == 0 || lMedic.isEmpty()) {
                throw new Exception("Informations manquantes (Date vide ou liste medic vide)");
            }
            return new Prescription(this);
        }

    }

}
