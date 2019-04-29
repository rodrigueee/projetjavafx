/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import projet.metier.Informations;
import projet.metier.Medecin;
import projet.metier.Medicament;
import projet.metier.Patient;
import projet.metier.Prescription;

/**
 *
 * @author rodri
 */
public class InformationsDAO extends DAO<Informations> {
/**
 * Aider par Gaetan 
 * Récupère les données de la table prescription, médicament et la quantité et unité de l'information et retourne la liste des données
 * @param idpresc id de la prescription
 * @return liste des données trouvée ou liste vide
 */
    @Override
    public List<Informations> read(String idpresc) {
        List<Informations> lInfos = new ArrayList<>();
        try (PreparedStatement pstm = dbConnect.prepareStatement("SELECT p.*, m.*, i.quantite, i.unite FROM prescription p join informations i on p.idpresc = i.idpresc join medicament m on i.idmedic = m.idmedic where p.idpresc = ?")) {
            pstm.setInt(1, Integer.parseInt(idpresc));
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                int idmedic = rs.getInt("IDMEDIC");
                String nom = rs.getString("NOM");
                String desc = rs.getString("DESCRIPTION");
                String code = rs.getString("CODE");
                Medicament medic = new Medicament(idmedic, nom, desc, code);
                String date = rs.getString("DATEP");
                int idmedec = rs.getInt("IDMED");
                Medecin medec = new Medecin();
                medec.setIdmed(idmedec);
                int idpat = rs.getInt("IDPAT");
                Patient pat = new Patient();
                pat.setIdpat(idpat);
                String annee = date.substring(0, 4);
                String mois = date.substring(5, 7);
                String jour = date.substring(8, 10);
                date = jour + "/" + mois + "/" + annee;
                Prescription presc = new Prescription(date, medec, pat);
                presc.setIdPresc(Integer.parseInt(idpresc));
                int quantite = rs.getInt("QUANTITE");
                int unite = rs.getInt("UNITE");
                Informations info = new Informations(quantite, unite, presc, medic);
                lInfos.add(info);
            }
        } catch (Exception e) {
            System.out.println("Erreur de lecture " + e.getMessage());

        }
        return lInfos;
    }

    @Override
    public Informations read(int idpresc) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Informations create(Informations obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Informations update(Informations obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String delete(Informations obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Informations> readall() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
