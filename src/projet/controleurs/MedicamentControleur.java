package projet.controleurs;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Main;
import projet.metier.Medicament;
import projet.modele.ModeleDAO;

/**
 * controleur des medicaments
 *
 * @author rodri
 */
public class MedicamentControleur implements Initializable, ControleursInterface {

    /**
     * controleur principal de l'app
     */
    MainControleur mainControleur;
    /**
     * modeleDAO de l'app
     */
    ModeleDAO mod;
    /**
     * liste observable des medicaments(pour la tableview)
     */
    private ObservableList<Medicament> medicListe = FXCollections.observableArrayList();
    /**
     * tableview des medicaments
     */
    @FXML
    public TableView<Medicament> medicTable;
    /**
     * colonne de l'id de la tableview medicament
     */
    @FXML
    public TableColumn<Medicament, Integer> idMedicColonne;
    /**
     * colonne du code de la tableview medicament
     */
    @FXML
    public TableColumn<Medicament, String> codeColonne;
    /**
     * variables des textfields
     */
    @FXML
    public TextField code, nom, desc, codeAj, nomAj, descAj;
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
        codeColonne.setCellValueFactory(new PropertyValueFactory<>("codeProp"));
        idMedicColonne.setCellValueFactory(new PropertyValueFactory<>("idMedicProp"));
    }

    /**
     * setteur du controleur principal de l'app
     *
     * @param mainControleur le controleur principal
     */
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
    public void selectMedic() {
        Medicament m = medicTable.getSelectionModel().getSelectedItem();
        if (m != null) {
            modifBtn.setDisable(false);
            suppBtn.setDisable(false);
            code.setDisable(false);
            nom.setDisable(false);
            desc.setDisable(false);
            code.setText(m.getCode());
            nom.setText(m.getNom());
            desc.setText(m.getDescription());
        } else {
            modifBtn.setDisable(true);
            suppBtn.setDisable(true);
            code.setDisable(true);
            nom.setDisable(true);
            desc.setDisable(true);
            code.clear();
            nom.clear();
            desc.clear();
        }
    }

    /**
     * recupere le texte de chaque textfield correspondant à l'ajout et ajoute
     * le nouveau medic dans la BDD, affiche les erreurs s'il y en a sinon
     * affiche un msg de confirmation et met a jour la liste
     */
    @FXML
    public void ajout() {
        if (codeAj.getText().trim().length() != 0 && nomAj.getText().trim().length() != 0 && descAj.getText().trim().length() != 0) {
            Medicament m = new Medicament(nomAj.getText(), descAj.getText(), codeAj.getText());
            try {
                mod.ajouterMedic(m);
                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle(Main.title);
                info.setHeaderText("Médicament ajouté");
                info.show();
                codeAj.clear();
                nomAj.clear();
                descAj.clear();
                refreshListe();
            } catch (SQLException e) {
                Alert erreur = new Alert(Alert.AlertType.ERROR);
                erreur.setTitle(Main.title);
                erreur.setHeaderText("Erreur d'ajout");
                erreur.setContentText("Code du médicament déjà enregistré");
                erreur.show();
            }
        } else {
            Alert erreur = new Alert(Alert.AlertType.ERROR);
            erreur.setTitle(Main.title);
            erreur.setHeaderText("Erreur de modification");
            erreur.setContentText("Veuillez remplir tous les champs");
            erreur.show();
        }
    }

    /**
     * recupere le texte de chaque textfield correspondant à la modif modifie le
     * medic dans la BDD, affiche les erreurs s'il y en a sinon affiche un msg
     * de confirmation et met a jour la liste
     */
    @FXML
    public void modif() {
        Medicament m = medicTable.getSelectionModel().getSelectedItem();
        String c, n, d;
        c = m.getCode();
        n = m.getNom();
        d = m.getDescription();
        if (code.getText().trim().length() != 0 && nom.getText().trim().length() != 0 && desc.getText().trim().length() != 0) {
            m.setCode(code.getText());
            m.setNom(nom.getText());
            m.setDescription(desc.getText());
            try {
                mod.modifMedic(m);
                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle(Main.title);
                info.setHeaderText("Médicament modifié");
                info.show();
                refreshListe();
            } catch (SQLException e) {
                m.setCode(c);
                m.setNom(n);
                m.setDescription(d);
                Alert erreur = new Alert(Alert.AlertType.ERROR);
                erreur.setTitle(Main.title);
                erreur.setHeaderText("Erreur de modification");
                erreur.setContentText("Code du médicament déjà enregistré");
                erreur.show();
            }
        } else {
            Alert erreur = new Alert(Alert.AlertType.ERROR);
            erreur.setTitle(Main.title);
            erreur.setHeaderText("Erreur de modification");
            erreur.setContentText("Veuillez remplir tous les champs");
            erreur.show();
        }
    }

    /**
     * recupere l'element selectionne et le supprime de la BDD, affiche les
     * erreurs s'il y en a sinon affiche un msg de confirmation et met a jour la
     * liste
     */
    @FXML
    public void supp() {
        Medicament m = medicTable.getSelectionModel().getSelectedItem();
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle(Main.title);
        confirmation.setHeaderText("Êtes-vous sur?");
        confirmation.setContentText("Cliquez sur 'OK' pour confirmer");
        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                mod.suppMedic(m);
                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle(Main.title);
                info.setHeaderText("Médicament supprimé");
                info.show();
                refreshListe();
            } catch (SQLException e) {
                Alert erreur = new Alert(Alert.AlertType.ERROR);
                erreur.setTitle(Main.title);
                erreur.setHeaderText("Erreur de suppression");
                erreur.setContentText("Impossible de supprimer :\nMédicament utilisé pour une liaison (FK)");
                erreur.show();
            }
        }
    }

    /**
     * recupere le texte du textfield correspondant a la rechercher et affiche
     * le(s) resultat(s) dans la tableview, recherche sur l'id et la
     * description, affiche les erreurs s'il y en a
     */
    @FXML
    public void recherche() {
        TextInputDialog recherche = new TextInputDialog();

        recherche.setTitle(Main.title);
        recherche.setHeaderText("Recherche sur l'id ou la description :");
        recherche.setContentText("Id ou description:");

        Optional<String> result = recherche.showAndWait();

        //LAMBDA
        result.ifPresent(rech -> {
            if (rech.trim().length() != 0) {
                try {
                    int id = Integer.parseInt(rech);
                    Medicament m = mod.getMedicId(id);
                    retourBtn.setVisible(true);
                    medicTable.getSelectionModel().clearSelection();
                    selectMedic();
                    medicListe.clear();
                    medicListe.addAll(m);
                    medicTable.setItems(medicListe);
                } catch (SQLException e) {
                    Alert erreur = new Alert(Alert.AlertType.ERROR);
                    erreur.setTitle(Main.title);
                    erreur.setHeaderText("Erreur de recherche");
                    erreur.setContentText("Id du médicament inconnu");
                    erreur.show();
                } catch (NumberFormatException e) {
                    try {
                        List<Medicament> listeMedic = mod.getMedic(rech);
                        retourBtn.setVisible(true);
                        medicTable.getSelectionModel().clearSelection();
                        selectMedic();
                        medicListe.clear();
                        medicListe.addAll(listeMedic);
                        medicTable.setItems(medicListe);
                    } catch (SQLException ex) {
                        Alert erreur = new Alert(Alert.AlertType.ERROR);
                        erreur.setTitle(Main.title);
                        erreur.setHeaderText("Erreur de recherche");
                        erreur.setContentText("Aucun médicament ne contient en description cette recherche");
                        erreur.show();
                    }
                }
            } else {
                Alert erreur = new Alert(Alert.AlertType.ERROR);
                erreur.setTitle(Main.title);
                erreur.setHeaderText("Erreur de recherche");
                erreur.setContentText("Veuillez remplir le champ de recherche");
                erreur.show();
            }
        });
    }

    /**
     * methode qui affiche l'ecran d'accueil
     */
    @FXML
    public void accueil() {
        mainControleur.setScreen(Main.mainId);
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
     * methode qui affiche l'ecran prescription
     */
    @FXML
    public void presc() {
        mainControleur.setScreen(Main.prescId);
    }

    /**
     * setteur du modeleDAO de l'app
     *
     * @param mod modeleDAO de l'app
     */
    @Override
    public void setMod(ModeleDAO mod) {
        this.mod = mod;
        refreshListe();
    }

    /**
     * methode qui met à jour la tableview et cache le bouton retour si
     * necessaire
     */
    @FXML
    @Override
    public void refreshListe() {
        retourBtn.setVisible(false);
        medicTable.getSelectionModel().clearSelection();
        selectMedic();
        medicListe.clear();
        medicListe.addAll(mod.tousMedicaments());
        medicTable.setItems(medicListe);
    }

}
