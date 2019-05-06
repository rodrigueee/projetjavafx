package projet.controleurs;

import projet.modele.ModeleDAO;
/**
 * l'interface des controleurs
 * definie les methodes à implementer dans chaque controleurs 
 * @author rodri
 */
public interface ControleursInterface {
    
    /**
     * Cette méthode permettra l’injection du controleur principal
     * @param mainControleur le controleur principal de l'app
    */
    void setMainControleur(MainControleur mainControleur);
    /**
     * cette methode permettra l'injection du modeleDAO
     * @param mod le modeleDAO de l'app
     */
    void setMod(ModeleDAO mod);
}
