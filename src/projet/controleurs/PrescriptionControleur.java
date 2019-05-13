package projet.controleurs;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import main.Main;
import projet.metier.Medecin;
import projet.metier.Medicament;
import projet.metier.Patient;
import projet.metier.Prescription;
import projet.modele.ModeleDAO;

/**
 *
 * @author rodri
 */
public class PrescriptionControleur implements Initializable, ControleursInterface {

    /**
     * controleur principal de l'app
     */
    private MainControleur mainControleur;
    private ModeleDAO mod;

    /**
     * liste observable des medecins(pour la tableview)
     */
    private ObservableList<Prescription> prescListe = FXCollections.observableArrayList();
    private ObservableList<Medicament> medicListe = FXCollections.observableArrayList();
    private ObservableList<Medicament> medicListeAj = FXCollections.observableArrayList();
    private ObservableList<Patient> patListe = FXCollections.observableArrayList();
    private ObservableList<Medecin> medecListe = FXCollections.observableArrayList();

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
    public TextField date, dateAj;
    @FXML
    public Label idPresc, fkMedec, fkPat;
    @FXML
    public ListView<Medicament> listeMedic, listeMedicAj;
    @FXML
    public ListView<Patient> patAj;
    @FXML
    public ListView<Medecin> medecAj;
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
        listeMedicAj.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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
            medicListe.clear();
            modifBtn.setDisable(false);
            suppBtn.setDisable(false);
            date.setDisable(false);
            idPresc.setText(Integer.toString(p.getIdPresc()));
            fkMedec.setText(p.getMd().getNomM() + ' ' + p.getMd().getPrenomM());
            fkPat.setText(p.getPt().getNomP() + ' ' + p.getPt().getPrenomP());
            date.setText(p.getDateP());
            medicListe.addAll(p.getlMedic());
            listeMedic.setItems(medicListe);
        } else {
            modifBtn.setDisable(true);
            suppBtn.setDisable(true);
            date.setDisable(true);
            date.clear();
            idPresc.setText("");
            fkMedec.setText("");
            fkPat.setText("");
            medicListe.clear();
        }
    }

    @FXML
    public void detailsMedic() {
        Medicament m = listeMedicAj.getSelectionModel().getSelectedItem();
        if (m != null) {
            TextInputDialog details = new TextInputDialog();

            details.setTitle(Main.title);
            details.setHeaderText("Entrez la quantité du médicament :");
            details.setContentText("quantité :");

            Optional<String> result = details.showAndWait();

            //LAMBDA
            result.ifPresent(quantite -> {
                if (quantite.trim().length() != 0) {
                    try {
                        int q = Integer.parseInt(quantite);
                        m.setQuantite(q);
                    } catch (NumberFormatException e) {
                        Alert erreur = new Alert(Alert.AlertType.ERROR);
                        erreur.setTitle(Main.title);
                        erreur.setHeaderText("Erreur");
                        erreur.setContentText("Veuillez entrer un nombre correct");
                        erreur.show();
                    }
                } else {
                    Alert erreur = new Alert(Alert.AlertType.ERROR);
                    erreur.setTitle(Main.title);
                    erreur.setHeaderText("Erreur");
                    erreur.setContentText("Veuillez remplir le champ de la quantité");
                    erreur.show();
                }
            });
            details = new TextInputDialog();

            details.setTitle(Main.title);
            details.setHeaderText("Entrez l'unité du médicament :");
            details.setContentText("unité :");

            result = details.showAndWait();

            //LAMBDA
            result.ifPresent(unite -> {
                if (unite.trim().length() != 0) {
                    try {
                        int u = Integer.parseInt(unite);
                        m.setUnite(u);
                    } catch (NumberFormatException e) {
                        Alert erreur = new Alert(Alert.AlertType.ERROR);
                        erreur.setTitle(Main.title);
                        erreur.setHeaderText("Erreur");
                        erreur.setContentText("Veuillez entrer un nombre correct");
                        erreur.show();
                    }
                } else {
                    Alert erreur = new Alert(Alert.AlertType.ERROR);
                    erreur.setTitle(Main.title);
                    erreur.setHeaderText("Erreur");
                    erreur.setContentText("Veuillez remplir le champ de l'unité");
                    erreur.show();
                }
            });
        }
    }

    /**
     * recupere le texte de chaque textfield correspondant à l'ajout et ajoute
     * le nouveau medecin dans la BDD, affiche les erreurs s'il y en a sinon
     * affiche un msg de confirmation et met a jour la liste
     */
    @FXML
    public void ajout() {
        Medecin medec = medecAj.getSelectionModel().getSelectedItem();
        Patient pat = patAj.getSelectionModel().getSelectedItem();
        List<Medicament> listeMedic = listeMedicAj.getSelectionModel().getSelectedItems();
        if (dateAj.getText().trim().length() != 0 && medec != null && pat != null && listeMedic != null) {
            try {
                Prescription p = new Prescription.Builder().setDateP(dateAj.getText()).setMedec(medec).setPat(pat).setLMedic(listeMedic).build();
                mod.ajouterPresc(p);
                Alert erreur = new Alert(Alert.AlertType.INFORMATION);
                erreur.setTitle(Main.title);
                erreur.setHeaderText("Prescription ajoutée");
                erreur.show();
                dateAj.clear();
                medecAj.getSelectionModel().clearSelection();
                patAj.getSelectionModel().clearSelection();
                listeMedicAj.getSelectionModel().clearSelection();
                refreshListe();
            } catch (SQLException e) {
                Alert erreur = new Alert(Alert.AlertType.ERROR);
                erreur.setTitle(Main.title);
                erreur.setHeaderText("Erreur d'ajout");
                erreur.setContentText(e.getCause().toString());
                erreur.show();
            } catch (Exception e) {
                Alert erreur = new Alert(Alert.AlertType.ERROR);
                erreur.setTitle(Main.title);
                erreur.setHeaderText("Erreur d'ajout");
                erreur.setContentText(e.getMessage());
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
     * le medecin dans la BDD, affiche les erreurs s'il y en a sinon affiche un
     * msg de confirmation et met a jour la liste
     */
    @FXML
    public void modif() {
        Prescription p = prescTable.getSelectionModel().getSelectedItem();
        String d;
        d = p.getDateP();
        if (date.getText().trim().length() != 0) {
            p.setDateP(date.getText());
            try {
                mod.modifPresc(p);
                Alert erreur = new Alert(Alert.AlertType.INFORMATION);
                erreur.setTitle(Main.title);
                erreur.setHeaderText("Prescription modifié");
                erreur.show();
                refreshListe();
            } catch (SQLException e) {
                p.setDateP(d);
                Alert erreur = new Alert(Alert.AlertType.ERROR);
                erreur.setTitle(Main.title);
                erreur.setHeaderText("Erreur de modification");
                erreur.setContentText("Format de la date incorrect.\nFormats acceptés : \n000000\n00/00/00\n00000000\n00/00/0000\n\nVerifier que la date existe!");
                erreur.show();
            }
        } else {
            Alert erreur = new Alert(Alert.AlertType.ERROR);
            erreur.setTitle(Main.title);
            erreur.setHeaderText("Erreur de modification");
            erreur.setContentText("Veuillez remplir le champ date");
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
        Prescription p = prescTable.getSelectionModel().getSelectedItem();
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle(Main.title);
        confirmation.setHeaderText("Êtes-vous sur?");
        confirmation.setContentText("Cliquez sur 'OK' pour confirmer");
        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                mod.suppPresc(p);
                Alert erreur = new Alert(Alert.AlertType.INFORMATION);
                erreur.setTitle(Main.title);
                erreur.setHeaderText("Prescription supprimé");
                erreur.show();
                refreshListe();
            } catch (SQLException e) {
                Alert erreur = new Alert(Alert.AlertType.ERROR);
                erreur.setTitle(Main.title);
                erreur.setHeaderText("Erreur de suppression");
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
        recherche.setHeaderText("Recherche sur l'id :");
        recherche.setContentText("Id :");

        Optional<String> result = recherche.showAndWait();

        //LAMBDA
        result.ifPresent(rech -> {
            if (rech.trim().length() != 0) {
                try {
                    int id = Integer.parseInt(rech);
                    Prescription p = mod.getPresc(id);
                    retourBtn.setVisible(true);
                    prescTable.getSelectionModel().clearSelection();
                    selectPresc();
                    prescListe.clear();
                    prescListe.addAll(p);
                    prescTable.setItems(prescListe);
                } catch (NumberFormatException e) {
                    Alert erreur = new Alert(Alert.AlertType.ERROR);
                    erreur.setTitle(Main.title);
                    erreur.setHeaderText("Erreur de recherche");
                    erreur.setContentText("Veuillez entrer un chiffre correct");
                    erreur.show();
                } catch (Exception e) {
                    Alert erreur = new Alert(Alert.AlertType.ERROR);
                    erreur.setTitle(Main.title);
                    erreur.setHeaderText("Erreur de recherche");
                    erreur.setContentText("Id de la prescription inconnu");
                    erreur.show();
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
            medicListeAj.clear();
            patListe.clear();
            medecListe.clear();
            prescListe.addAll(mod.tousPresciptions());
            medicListeAj.addAll(mod.tousMedicaments());
            patListe.addAll(mod.tousPatients());
            medecListe.addAll(mod.tousMedecins());
            prescTable.setItems(prescListe);
            listeMedicAj.setItems(medicListeAj);
            patAj.setItems(patListe);
            medecAj.setItems(medecListe);
        } catch (Exception ex) {
            Alert erreur = new Alert(Alert.AlertType.ERROR);
            erreur.setTitle(Main.title);
            erreur.setHeaderText("Erreur de lecture des prescriptions");
            erreur.show();
            accueil();
        }
    }

    @Override
    public void setMod(ModeleDAO mod
    ) {
        this.mod = mod;
        refreshListe();
    }

}
