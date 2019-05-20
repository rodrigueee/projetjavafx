/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controleurs;

import javafx.fxml.FXML;
import main.Main;
import projet.modele.ModeleDAO;

/**
 *
 * @author rodri
 */
public class MenuControleur implements ControleursInterface {

    /**
     * controleur principal de l'app
     */
    private MainControleur mainControleur;

    /**
     * methode qui affiche l'ecran medicament
     */
    @FXML
    public void medic() {
        mainControleur.setScreen(Main.medicId);
    }

    /**
     * methode qui affiche l'ecran medecin
     */
    @FXML
    public void medec() {
        mainControleur.setScreen(Main.medecId);
    }

    /**
     * methode qui affiche l'ecran patient
     */
    @FXML
    public void pat() {
        mainControleur.setScreen(Main.patId);
    }

    /**
     * methode qui affiche l'ecran presciption
     */
    @FXML
    public void presc() {
        mainControleur.setScreen(Main.prescId);
    }

    /**
     *setteur du controleur de l'app
     * @param mainControleur
     */
    @Override
    public void setMainControleur(MainControleur mainControleur) {
        this.mainControleur = mainControleur;
    }

    /**
     * setteur du modeleDAO de l'app
     *
     * @param mod modeleDAO de l'app
     */
    @Override
    public void setMod(ModeleDAO mod) {
    }

}
