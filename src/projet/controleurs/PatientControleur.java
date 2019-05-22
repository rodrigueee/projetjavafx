package projet.controleurs;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import main.Main;
import projet.metier.Medicament;
import projet.metier.Patient;
import projet.metier.Prescription;
import projet.modele.ModeleDAO;

/**
 * controleur des patients
 *
 * @author rodri
 */
public class PatientControleur implements Initializable, ControleursInterface {

    /**
     * controleur principal de lapp
     */
    MainControleur mainControleur;
    /**
     * modeleDAO de l'app
     */
    ModeleDAO mod;
    /**
     * liste observable des patients(pour la tableview)
     */
    private ObservableList<Patient> patListe = FXCollections.observableArrayList();
    /**
     * tableview des patients
     */
    @FXML
    public TableView<Patient> patTable;
    /**
     * colonne du nom de la tableview patient
     */
    @FXML
    public TableColumn<Patient, String> nomColonne;
    /**
     * colonne du prenom de la tableview patient
     */
    @FXML
    public TableColumn<Patient, String> prenomColonne;
    /**
     * variables des textfields
     */
    @FXML
    public TextField nom, prenom, tel, nomAj, prenomAj, telAj;
    @FXML
    public Label idPat;
    /**
     * variables des boutons
     */
    @FXML
    public Button modifBtn, suppBtn, retourBtn, prescBtn;

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
        nomColonne.setCellValueFactory(new PropertyValueFactory<>("nomProp"));
        prenomColonne.setCellValueFactory(new PropertyValueFactory<>("prenomProp"));
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
    public void selectPat() {
        Patient p = patTable.getSelectionModel().getSelectedItem();
        if (p != null) {
            modifBtn.setDisable(false);
            suppBtn.setDisable(false);
            prescBtn.setDisable(false);
            nom.setDisable(false);
            prenom.setDisable(false);
            tel.setDisable(false);
            idPat.setText(Integer.toString(p.getIdpat()));
            nom.setText(p.getNomP());
            prenom.setText(p.getPrenomP());
            tel.setText(p.getTel());
        } else {
            modifBtn.setDisable(true);
            suppBtn.setDisable(true);
            prescBtn.setDisable(true);
            nom.setDisable(true);
            prenom.setDisable(true);
            tel.setDisable(true);
            nom.clear();
            prenom.clear();
            tel.clear();
        }
    }

    /**
     * recupere le texte de chaque textfield correspondant à l'ajout et ajoute
     * le nouveau patient dans la BDD, affiche les erreurs s'il y en a sinon
     * affiche un msg de confirmation et met a jour la liste
     */
    @FXML
    public void ajout() {
        if (nomAj.getText().trim().length() != 0 && prenomAj.getText().trim().length() != 0 && telAj.getText().trim().length() != 0) {
            Patient p = new Patient(nomAj.getText(), prenomAj.getText(), telAj.getText());
            try {
                mod.ajouterPatient(p);
                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle(Main.title);
                info.setHeaderText("Patient ajouté");
                info.show();
                nomAj.clear();
                prenomAj.clear();
                telAj.clear();
                refreshListe();
            } catch (SQLException e) {
                Alert erreur = new Alert(Alert.AlertType.ERROR);
                erreur.setTitle(Main.title);
                erreur.setHeaderText("Erreur d'ajout");
                erreur.setContentText("Telephone du patient déjà enregistré");
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
     * recupere le texte de chaque textfield correspondant à la modif, modifie
     * le patient dans la BDD, affiche les erreurs s'il y en a sinon affiche un
     * msg de confirmation et met a jour la liste
     */
    @FXML
    public void modif() {
        Patient p = patTable.getSelectionModel().getSelectedItem();
        String n, pn, t;
        n = p.getNomP();
        pn = p.getPrenomP();
        t = p.getTel();
        if (nom.getText().trim().length() != 0 && prenom.getText().trim().length() != 0 && tel.getText().trim().length() != 0) {
            p.setNomP(nom.getText());
            p.setPrenomP(prenom.getText());
            p.setTel(tel.getText());
            try {
                mod.modifPat(p);
                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle(Main.title);
                info.setHeaderText("Patient modifié");
                info.show();
                refreshListe();
            } catch (SQLException e) {
                p.setNomP(n);
                p.setPrenomP(pn);
                p.setTel(t);
                Alert erreur = new Alert(Alert.AlertType.ERROR);
                erreur.setTitle(Main.title);
                erreur.setHeaderText("Erreur de modification");
                erreur.setContentText("Telephone du patient déjà enregistré");
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
        Patient p = patTable.getSelectionModel().getSelectedItem();
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle(Main.title);
        confirmation.setHeaderText("Êtes-vous sur?");
        confirmation.setContentText("Cliquez sur 'OK' pour confirmer");
        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                mod.suppPat(p);
                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle(Main.title);
                info.setHeaderText("Patient supprimé");
                info.show();
                refreshListe();
            } catch (SQLException e) {
                Alert erreur = new Alert(Alert.AlertType.ERROR);
                erreur.setTitle(Main.title);
                erreur.setHeaderText("Erreur de suppression");
                erreur.setContentText("Impossible de supprimer :\nPatient utilisé pour une liaison (FK)");
                erreur.show();
            }
        }
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
                try {
                    int id = Integer.parseInt(rech);
                    Patient p = mod.getPatId(id);
                    retourBtn.setVisible(true);
                    patTable.getSelectionModel().clearSelection();
                    selectPat();
                    patListe.clear();
                    patListe.addAll(p);
                    patTable.setItems(patListe);
                } catch (SQLException e) {
                    Alert erreur = new Alert(Alert.AlertType.ERROR);
                    erreur.setTitle(Main.title);
                    erreur.setHeaderText("Erreur de recherche");
                    erreur.setContentText("Id du patient inconnu");
                    erreur.show();
                } catch (NumberFormatException e) {
                    try {
                        List<Patient> listePat = mod.getPatient(rech);
                        retourBtn.setVisible(true);
                        patTable.getSelectionModel().clearSelection();
                        selectPat();
                        patListe.clear();
                        patListe.addAll(listePat);
                        patTable.setItems(patListe);
                    } catch (SQLException ex) {
                        Alert erreur = new Alert(Alert.AlertType.ERROR);
                        erreur.setTitle(Main.title);
                        erreur.setHeaderText("Erreur de recherche");
                        erreur.setContentText("Aucun patient correspond à cette recherche");
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
     * affiche les prescriptions avec les details pour le patient selectionner
     */
    @FXML
    public void detPresc() {
        Patient p = patTable.getSelectionModel().getSelectedItem();
        try {
            List<Prescription> listePresc = mod.tousPresciptions();
            List<Prescription> lPresc = new ArrayList<>();
            listePresc.forEach((presc) -> {
                if (p.getIdpat() == presc.getPt().getIdpat()) {
                    lPresc.add(presc);
                }
            });
            Dialog dialog = new Dialog();
            dialog.setTitle(Main.title);
            dialog.setHeaderText("Prescription(s) de : " + p.getNomP() + " " + p.getPrenomP());
            GridPane grid = new GridPane();
            grid.setPadding(new Insets(20, 150, 10, 10));
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            int row = 1;
            grid.add(new Label("Date"), 0, 0);
            grid.add(new Label("Médecin"), 1, 0);
            grid.add(new Label("Médicaments"), 2, 0);
            for (Prescription presc : lPresc) {
                grid.add(new Label(presc.getDateP()), 0, row);
                grid.add(new Label(presc.getMd().toString()), 1, row);
                Label medicLabel = new Label();
                presc.getlMedic().forEach((medic) -> {
                    medicLabel.setText(medicLabel.getText() + "\n" + medic.toString());
                });
                grid.add(medicLabel, 2, row);
                row++;
            }
            dialog.getDialogPane().setContent(grid);
            dialog.show();
        } catch (Exception ex) {
            Alert erreur = new Alert(Alert.AlertType.ERROR);
            erreur.setTitle(Main.title);
            erreur.setHeaderText("Erreur de lecture des prescriptions");
            erreur.show();
        }
    }

    /**
     * mehode qui affiche l'ecran d'accueil
     */
    @FXML
    public void accueil() {
        mainControleur.setScreen(Main.mainId);
    }

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
        patTable.getSelectionModel().clearSelection();
        selectPat();
        patListe.clear();
        patListe.addAll(mod.tousPatients());
        patTable.setItems(patListe);
    }

}
