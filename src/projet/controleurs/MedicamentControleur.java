package projet.controleurs;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Main;
import projet.controleurs.ControleursInterface;
import projet.metier.Medicament;
import projet.modele.ModeleDAO;

/**
 * FXML Controller class
 *
 */
public class MedicamentControleur implements Initializable, ControleursInterface {

    MainControleur mainControleur;
    ModeleDAO mod;
    
    private ObservableList<Medicament> medicListe = FXCollections.observableArrayList();
    
    @FXML
    public TableView<Medicament> medicTable;
    @FXML
    public TableColumn<Medicament, Integer> idMedicColonne;
    @FXML
    public TableColumn<Medicament, String> codeColonne;
    
    @FXML
    public Label nom, desc;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the person table with the two columns.
        codeColonne.setCellValueFactory(new PropertyValueFactory<>("codeProp"));
        idMedicColonne.setCellValueFactory(new PropertyValueFactory<>("idMedicProp"));
    }   

    @Override
    public void setMainControleur(MainControleur mainControleur) {
        this.mainControleur = mainControleur;
    }
    
    @FXML
    public void selectMedic() {
        Medicament m = medicTable.getSelectionModel().getSelectedItem();
        if (m != null) {
            nom.setText(m.getNom());
            desc.setText(m.getDescription());
        }
    }
    
    @FXML
    public void accueil() {
        mainControleur.setScreen(Main.mainId);
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
    public void setMod(ModeleDAO mod) {
        this.mod = mod;
        medicListe.addAll(mod.tousMedicaments());
        medicTable.setItems(medicListe);
    }

}
