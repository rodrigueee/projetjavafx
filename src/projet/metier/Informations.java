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
public class Informations {

    /**
     * quantité du medicament
     */
    private int quantite;
    /**
     * unité du medicament
     */
    private int unite;
    /**
     * variable d'instance de type prescription
     */
    private Prescription pr;
    /**
     * variable d'instance de type medicament
     */
    private Medicament medic;

    /**
     * constructeur par défaut
     */
    public Informations() {
    }

    /**
     * constructeur paramétré
     *
     * @param quantite
     * @param unite
     * @param pr
     * @param medic
     */
    public Informations(int quantite, int unite, Prescription pr, Medicament medic) {
        this.quantite = quantite;
        this.unite = unite;
        this.pr = pr;
        this.medic = medic;
    }

    /**
     * constructeur paramétré
     *
     * @param quantite
     * @param unite
     */
    public Informations(int quantite, int unite) {
        this.quantite = quantite;
        this.unite = unite;
    }

    /**
     * recuperer une valeur de type prescription en mode lecture
     *
     * @return
     */
    public Prescription getPr() {
        return pr;
    }

    /**
     * recuperer une valeur de type medicament en mode lecture
     *
     * @return
     */
    public Medicament getMedic() {
        return medic;
    }

    /**
     * recuperer la quantité du medicament en mode lecture
     *
     * @return la quantité du medicament
     */
    public int getQuantite() {
        return quantite;
    }

    /**
     * modifier la quantité du medicament en mode ecriture
     *
     * @param quantite du medicament
     */
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    /**
     * recuperer l'unite du medicament en mode lecture
     *
     * @return l'unité du medicament
     */
    public int getUnite() {
        return unite;
    }

    /**
     * modifier l'unité du medicament en mode ecriture
     *
     * @param unite du medicament
     */
    public void setUnite(int unite) {
        this.unite = unite;
    }

    /**
     * affiche du textes et les valeurs des variables
     *
     * @return le message indiqué
     */
    @Override
    public String toString() {
        return "Informations{" + "quantite=" + quantite + ", unite=" + unite + ", pr=" + pr + ", medic=" + medic + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.quantite;
        hash = 97 * hash + this.unite;
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
        final Informations other = (Informations) obj;
        if (this.quantite != other.quantite) {
            return false;
        }
        if (this.unite != other.unite) {
            return false;
        }
        return true;
    }

}
