/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controleurs;

import javafx.fxml.FXML;
import main.Main;

/**
 *
 * @author rodri
 */
public class MenuControleur implements ControleursInterface{
    
    private MainControleur mainControleur;

    @FXML
    public void medic() {
        mainControleur.setScreen(Main.medicId);
    }
    

    @Override
    public void setMainControleur(MainControleur mainControleur) {
        this.mainControleur = mainControleur;
    }
    
}
