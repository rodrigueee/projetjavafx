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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Main;
import projet.metier.Medecin;
import projet.modele.ModeleDAO;

/**
 * controleur des medecins
 *
 * @author rodri
 */
public class MedecinControleur implements Initializable, ControleursInterface {

    /**
     * controleur principal de l'app
     */
    MainControleur mainControleur;
    /**
     * modeleDAO de l'app
     */
    ModeleDAO mod;
    /**
     * liste observable des medecins(pour la tableview)
     */
    private ObservableList<Medecin> medecListe = FXCollections.observableArrayList();
    /**
     * tableview des medecins
     */
    @FXML
    public TableView<Medecin> medecTable;
    /**
     * colonne du nom de la tableview medecin
     */
    @FXML
    public TableColumn<Medecin, String> nomColonne;
    /**
     * colonne du prenom de la tableview medecin
     */
    @FXML
    public TableColumn<Medecin, String> prenomColonne;
    /**
     * variables des textfields
     */
    @FXML
    public TextField nom, prenom, mat, tel, nomAj, prenomAj, matAj, telAj;
    @FXML
    public Label idMedec;
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
    public void selectMedec() {
        Medecin m = medecTable.getSelectionModel().getSelectedItem();
        if (m != null) {
            modifBtn.setDisable(false);
            suppBtn.setDisable(false);
            nom.setDisable(false);
            prenom.setDisable(false);
            mat.setDisable(false);
            tel.setDisable(false);
            idMedec.setText(Integer.toString(m.getIdmed()));
            nom.setText(m.getNomM());
            prenom.setText(m.getPrenomM());
            mat.setText(m.getMatricule());
            tel.setText(m.getTel());
        } else {
            modifBtn.setDisable(true);
            suppBtn.setDisable(true);
            nom.setDisable(true);
            prenom.setDisable(true);
            mat.setDisable(true);
            tel.setDisable(true);
            nom.clear();
            prenom.clear();
            mat.clear();
            tel.clear();
        }
    }

    /**
     * recupere le texte de chaque textfield correspondant à l'ajout et ajoute
     * le nouveau medecin dans la BDD, affiche les erreurs s'il y en a sinon
     * affiche un msg de confirmation et met a jour la liste
     */
    @FXML
    public void ajout() {
        if (nomAj.getText().trim().length() != 0 && prenomAj.getText().trim().length() != 0 && matAj.getText().trim().length() != 0 && telAj.getText().trim().length() != 0) {
            Medecin m = new Medecin(nomAj.getText(), prenomAj.getText(), matAj.getText(), telAj.getText());
            try {
                mod.ajouterMedecin(m);
                Alert erreur = new Alert(Alert.AlertType.INFORMATION);
                erreur.setTitle(Main.title);
                erreur.setHeaderText("Medecin ajouté");
                erreur.show();
                nomAj.clear();
                prenomAj.clear();
                matAj.clear();
                telAj.clear();
                refreshListe();
            } catch (SQLException e) {
                Alert erreur = new Alert(Alert.AlertType.ERROR);
                erreur.setTitle(Main.title);
                erreur.setHeaderText("Erreur d'ajout");
                erreur.setContentText("Matricule du medecin déjà enregistré");
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
        Medecin m = medecTable.getSelectionModel().getSelectedItem();
        String n, pn, ma, t;
        n = m.getNomM();
        pn = m.getPrenomM();
        ma = m.getMatricule();
        t = m.getTel();
        if (nom.getText().trim().length() != 0 && prenom.getText().trim().length() != 0 && mat.getText().trim().length() != 0 && tel.getText().trim().length() != 0) {
            m.setNomM(nom.getText());
            m.setPrenomM(prenom.getText());
            m.setMatricule(mat.getText());
            m.setTel(tel.getText());
            try {
                mod.modifMedec(m);
                Alert erreur = new Alert(Alert.AlertType.INFORMATION);
                erreur.setTitle(Main.title);
                erreur.setHeaderText("Medecin modifié");
                erreur.show();
                refreshListe();
            } catch (SQLException e) {
                m.setNomM(n);
                m.setPrenomM(pn);
                m.setMatricule(ma);
                m.setTel(t);
                Alert erreur = new Alert(Alert.AlertType.ERROR);
                erreur.setTitle(Main.title);
                erreur.setHeaderText("Erreur de modification");
                erreur.setContentText("Matricule du medecin déjà enregistré");
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
        Medecin m = medecTable.getSelectionModel().getSelectedItem();
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle(Main.title);
        confirmation.setHeaderText("Êtes-vous sur?");
        confirmation.setContentText("Cliquez sur 'OK' pour confirmer");
        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                mod.suppMedec(m);
                Alert erreur = new Alert(Alert.AlertType.INFORMATION);
                erreur.setTitle(Main.title);
                erreur.setHeaderText("Medecin supprimé");
                erreur.show();
                refreshListe();
            } catch (SQLException e) {
                Alert erreur = new Alert(Alert.AlertType.ERROR);
                erreur.setTitle(Main.title);
                erreur.setHeaderText("Erreur de suppression");
                erreur.setContentText("Impossible de supprimer :\nMedecin utilisé pour une liaison (FK)");
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
                    Medecin m = mod.getMedecId(id);
                    retourBtn.setVisible(true);
                    medecTable.getSelectionModel().clearSelection();
                    selectMedec();
                    medecListe.clear();
                    medecListe.addAll(m);
                    medecTable.setItems(medecListe);
                } catch (SQLException e) {
                    Alert erreur = new Alert(Alert.AlertType.ERROR);
                    erreur.setTitle(Main.title);
                    erreur.setHeaderText("Erreur de recherche");
                    erreur.setContentText("Id du medecin inconnu");
                    erreur.show();
                } catch (NumberFormatException e) {
                    try {
                        List<Medecin> listeMedec = mod.getMedec(rech);
                        retourBtn.setVisible(true);
                        medecTable.getSelectionModel().clearSelection();
                        selectMedec();
                        medecListe.clear();
                        medecListe.addAll(listeMedec);
                        medecTable.setItems(medecListe);
                    } catch (SQLException ex) {
                        Alert erreur = new Alert(Alert.AlertType.ERROR);
                        erreur.setTitle(Main.title);
                        erreur.setHeaderText("Erreur de recherche");
                        erreur.setContentText("Aucun medecin correspond à cette recherche");
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
     * methode qui affiche l'ecran medicament
     */
    @FXML
    public void medic() {
        mainControleur.setScreen(Main.medicId);
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
    public void refreshListe() {
        retourBtn.setVisible(false);
        medecTable.getSelectionModel().clearSelection();
        selectMedec();
        medecListe.clear();
        medecListe.addAll(mod.tousMedecins());
        medecTable.setItems(medecListe);
    }
}
