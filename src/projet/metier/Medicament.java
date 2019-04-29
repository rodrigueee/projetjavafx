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
public class Medicament {

    /**
     * id unique du medicament
     */
    private int idmedic;
    /**
     * nom du medicament
     */
    private String nom;
    /**
     * description du medicament
     */
    private String description;
    /**
     * code du medicament
     */
    private String code;

    /**
     * constructeur par defaut
     */
    public Medicament() {
    }

    /**
     * constructeur paramétré
     *
     * @param idmedic
     * @param nom
     * @param description
     * @param code
     */
    public Medicament(int idmedic, String nom, String description, String code) {
        this.idmedic = idmedic;
        this.nom = nom;
        this.description = description;
        this.code = code;
    }

    /**
     * constructeur paramétré
     *
     * @param nom
     * @param description
     * @param code
     */

    public Medicament(String nom, String description, String code) {
        this.nom = nom;
        this.description = description;
        this.code = code;
    }

    /**
     * constructeur paramétré
     *
     * @param code
     */
    public Medicament(String code) {
        this.code = code;
    }

    /**
     * recuperer le nom en mode lecture
     *
     * @return nom du medicament
     */
    public String getNom() {
        return nom;
    }

    /**
     * modifier le nom en mode ecriture
     *
     * @param nom du medicament
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * recuperer la description en mode lecture
     *
     * @return description du medicament
     */
    public String getDescription() {
        return description;
    }

    /**
     * modifier la description en mode ecriture
     *
     * @param description du medicament
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * recuperer le code en mode lecture
     *
     * @return code du medicament
     */
    public String getCode() {
        return code;
    }

    /**
     * modifier le code en mode ecriture
     *
     * @param code du medicement
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * recuperer l'id en mode lecture
     *
     * @return l'id du medicament
     */
    public int getIdmedic() {
        return idmedic;
    }

    public void setIdmedic(int idmedic) {
        this.idmedic = idmedic;
    }

    /**
     * affiche du textes et les valeurs des variables
     *
     * @return le message indiqué
     */
    @Override
    public String toString() {
        return "Medicament{id=" + idmedic + " nom=" + nom + ", description=" + description + ", code=" + code + '}';
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.idmedic;
        hash = 41 * hash + Objects.hashCode(this.nom);
        hash = 41 * hash + Objects.hashCode(this.description);
        hash = 41 * hash + Objects.hashCode(this.code);
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
        final Medicament other = (Medicament) obj;
        if (this.idmedic != other.idmedic) {
            return false;
        }
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        return true;
    }

}
