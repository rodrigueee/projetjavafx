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

    private MainControleur mainControleur;

    @FXML
    public void medic() {
        mainControleur.setScreen(Main.medicId);
    }
        @FXML
    public void medec() {
        mainControleur.setScreen(Main.medecId);
    }
        @FXML
    public void pat() {
        mainControleur.setScreen(Main.patId);
    }
        @FXML
    public void presc() {
        mainControleur.setScreen(Main.prescId);
    }


    @Override
    public void setMainControleur(MainControleur mainControleur) {
        this.mainControleur = mainControleur;
    }

    @Override
    public void setMod(ModeleDAO mod) {
    }

}
