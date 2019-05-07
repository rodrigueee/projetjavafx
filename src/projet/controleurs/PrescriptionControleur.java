/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controleurs;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Main;
import projet.metier.Medecin;
import projet.metier.Medicament;
import projet.metier.Prescription;
import projet.modele.ModeleDAO;

/**
 *
 * @author rodri
 */
public class PrescriptionControleur implements Initializable, ControleursInterface {

    private MainControleur mainControleur;
    private ModeleDAO mod;

    /**
     * liste observable des medecins(pour la tableview)
     */
    private ObservableList<Prescription> prescListe = FXCollections.observableArrayList();
    /**
     * tableview des medecins
     */
    @FXML
    public TableView<Prescription> prescTable;
    /**
     * colonne du nom de la tableview medecin
     */
    @FXML
    public TableColumn<Prescription, Integer> idColonne;
    /**
     * colonne du prenom de la tableview medecin
     */
    @FXML
    public TableColumn<Prescription, String> dateColonne;
    /**
     * variables des textfields
     */
    @FXML
    public TextField date;
    @FXML
    public Label idPresc, fkMedec, fkPat;
    @FXML
    public ListView<Medicament> listeMedic;
    /**
     * variables des boutons
     */
    @FXML
    public Button modifBtn, suppBtn, retourBtn;

    /**
     * Appele pour initialiser un contrôleur après le traitement complet de son
     * élément racine. source JAVA DOC
     * (https://docs.oracle.com/javase/8/javafx/api/javafx/fxml/Initializable.html)
     *
     * @param location Emplacement utilisé pour résoudre les chemins relatifs de
     * l'objet racine ou null si l'emplacement n'est pas connu.
     * @param resources Les ressources utilisées pour localiser l'objet racine,
     * ou null si l'objet racine n'a pas été localisé.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the person table with the two columns.
        idColonne.setCellValueFactory(new PropertyValueFactory<>("idPrescProp"));
        dateColonne.setCellValueFactory(new PropertyValueFactory<>("dateProp"));
    }

    @Override
    public void setMainControleur(MainControleur mainControleur) {
        this.mainControleur = mainControleur;
    }

    /**
     * verifie si un element est selectionne dans la tableview si oui, active
     * les differents boutons et differents textfields avec leur texte sinon
     * desactive les differents boutons et differents textfields
     */
    @FXML
    public void selectPresc() {
        Prescription p = prescTable.getSelectionModel().getSelectedItem();
        if (p != null) {
            modifBtn.setDisable(false);
            suppBtn.setDisable(false);
            date.setDisable(false);
            idPresc.setText(Integer.toString(p.getIdPresc()));
            fkMedec.setText(p.getMd().getNomM() + ' ' + p.getMd().getPrenomM());
            fkPat.setText(p.getPt().getNomP() + ' ' + p.getPt().getPrenomP());
            date.setText(p.getDateP());
        } else {
            modifBtn.setDisable(true);
            suppBtn.setDisable(true);
            date.setDisable(true);
            date.clear();
        }
    }

    /**
     * recupere le texte de chaque textfield correspondant à l'ajout et ajoute
     * le nouveau medecin dans la BDD, affiche les erreurs s'il y en a sinon
     * affiche un msg de confirmation et met a jour la liste
     */
    @FXML
    public void ajout() {
    }

    /**
     * recupere le texte de chaque textfield correspondant à la modif, modifie
     * le medecin dans la BDD, affiche les erreurs s'il y en a sinon affiche un
     * msg de confirmation et met a jour la liste
     */
    @FXML
    public void modif() {
    }

    /**
     * recupere l'element selectionne et le supprime de la BDD, affiche les
     * erreurs s'il y en a sinon affiche un msg de confirmation et met a jour la
     * liste
     */
    @FXML
    public void supp() {
    }

    /**
     * recupere le texte du textfield correspondant a la rechercher et affiche
     * le(s) resultat(s) dans la tableview, recherche sur l'id et le nom ,
     * affiche les erreurs s'il y en a
     */
    @FXML
    public void recherche() {
        TextInputDialog recherche = new TextInputDialog();

        recherche.setTitle(Main.title);
        recherche.setHeaderText("Recherche sur l'id ou le nom :");
        recherche.setContentText("Id ou nom:");

        Optional<String> result = recherche.showAndWait();

        //LAMBDA
        result.ifPresent(rech -> {
            if (rech.trim().length() != 0) {
            } else {
                Alert erreur = new Alert(Alert.AlertType.ERROR);
                erreur.setTitle(Main.title);
                erreur.setHeaderText("Erreur de recherche");
                erreur.setContentText("Veuillez remplir le champ de recherche");
                erreur.show();
            }
        });
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
    public void pat() {
        mainControleur.setScreen(Main.patId);
    }

    @FXML
    public void refreshListe() {
        try {
            retourBtn.setVisible(false);
            prescTable.getSelectionModel().clearSelection();
            selectPresc();
            prescListe.clear();
            prescTable.setItems(prescListe);
            prescListe.addAll(mod.tousPresciptions());
        } catch (Exception ex) {
            Alert erreur = new Alert(Alert.AlertType.ERROR);
            erreur.setTitle(Main.title);
            erreur.setHeaderText("Erreur de lecture des prescriptions");
            erreur.show();
            accueil();
        }
    }

    @Override
    public void setMod(ModeleDAO mod) {
        this.mod = mod;
        refreshListe();
    }

}
