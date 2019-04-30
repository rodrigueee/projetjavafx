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
public class PatientControleur implements ControleursInterface {

    MainControleur mainControleur;

    @Override
    public void setMainControleur(MainControleur mainControleur) {
        this.mainControleur = mainControleur;
    }

    @FXML
    public void accueil() {
        mainControleur.setScreen(Main.mainId);
    }

    @FXML
    public void medic() {
        mainControleur.setScreen(Main.medicId);
    }

    @FXML
    public void medec() {
        mainControleur.setScreen(Main.medecId);
    }

    @FXML
    public void presc() {
        mainControleur.setScreen(Main.prescId);
    }

    @Override
    public void setMod(ModeleDAO mod) {
    }

}
