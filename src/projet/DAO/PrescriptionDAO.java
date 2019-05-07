/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import projet.metier.Informations;
import projet.metier.Medecin;
import projet.metier.Medicament;
import projet.metier.Patient;
import projet.metier.Prescription;

/**
 *
 * @author rodri
 */
public class PrescriptionDAO extends DAO<Prescription> {

    /**
     * Aider par Gaetan Récupère grace à l'id de la prescription les données de
     * la table prescription, médicament et la quantité et unité de
     * l'information et retourne la liste des données
     *
     * @param idpresc id de la prescription
     * @return liste des données trouvée ou liste vide
     * @throws java.sql.SQLException
     */
    @Override
    public Prescription read(int idpresc) throws SQLException, Exception {
        List<Medicament> lMedic = new ArrayList<>();
        MedecinDAO medecDAO = new MedecinDAO();
        PatientDAO patDAO = new PatientDAO();
        medecDAO.setConnection(dbConnect);
        patDAO.setConnection(dbConnect);
        PreparedStatement pstm = dbConnect.prepareStatement("SELECT p.*, m.*, i.quantite, i.unite FROM prescription p join informations i on p.idpresc = i.idpresc join medicament m on i.idmedic = m.idmedic where p.idpresc = ?");
        pstm.setInt(1, idpresc);
        ResultSet rs = pstm.executeQuery();
        String date = null;
        Medecin medec = null;
        Patient pat = null;
        while (rs.next()) {
            int idmedic = rs.getInt("IDMEDIC");
            String nom = rs.getString("NOM");
            String desc = rs.getString("DESCRIPTION");
            String code = rs.getString("CODE");
            int quantite = rs.getInt("QUANTITE");
            int unite = rs.getInt("UNITE");
            Medicament medic = new Medicament(idmedic, nom, desc, code, unite, quantite);
            lMedic.add(medic);
            date = rs.getString("DATEP");
            String annee = date.substring(0, 4);
            String mois = date.substring(5, 7);
            String jour = date.substring(8, 10);
            date = jour + "/" + mois + "/" + annee;
            int idmedec = rs.getInt("IDMED");
            medec = medecDAO.read(idmedec);
            int idpat = rs.getInt("IDPAT");
            pat = patDAO.read(idpat);
        }
        Prescription presc = new Prescription.Builder().setIdPresc(idpresc).setDateP(date).setMedec(medec).setPat(pat).setLMedic(lMedic).build();
        if (presc == null) {
            throw new SQLException("Aucune prescription correspondant à cette id");
        }
        return presc;
    }

    @Override
    public List<Prescription> read(String s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void create(Prescription obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Prescription obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Prescription obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Aider par Gaetan Récupère les données de la table prescription,
     * médicament et la quantité et unité de l'information et retourne la liste
     * des données
     *
     * @return liste des données trouvée ou liste vide
     * @throws java.sql.SQLException
     */
    @Override
    public List<Prescription> readall() throws SQLException, Exception {
        List<Medicament> lMedic = new ArrayList<>();
        List<Prescription> lPresc = new ArrayList<>();
        Set<Prescription> hPresc = new HashSet<>();
        MedecinDAO medecDAO = new MedecinDAO();
        PatientDAO patDAO = new PatientDAO();
        medecDAO.setConnection(dbConnect);
        patDAO.setConnection(dbConnect);
        PreparedStatement pstm = dbConnect.prepareStatement("SELECT p.*, m.*, i.quantite, i.unite FROM prescription p join informations i on p.idpresc = i.idpresc join medicament m on i.idmedic = m.idmedic", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = pstm.executeQuery();
        int idpresc = 0;
        String date = null;
        Medecin medec = null;
        Patient pat = null;
        while (rs.next()) {
            if (idpresc != rs.getInt("IDPRESC")) {
                //Car référence d'objet passée lors du building de la prescription donc si presc différente => nouvelle liste
                lMedic = new ArrayList<>();
            }
            idpresc = rs.getInt("IDPRESC");
            int idmedic = rs.getInt("IDMEDIC");
            String nom = rs.getString("NOM");
            String desc = rs.getString("DESCRIPTION");
            String code = rs.getString("CODE");
            int quantite = rs.getInt("QUANTITE");
            int unite = rs.getInt("UNITE");
            Medicament medic = new Medicament(idmedic, nom, desc, code, unite, quantite);
            lMedic.add(medic);
            date = rs.getString("DATEP");
            String annee = date.substring(0, 4);
            String mois = date.substring(5, 7);
            String jour = date.substring(8, 10);
            date = jour + "/" + mois + "/" + annee;
            int idmedec = rs.getInt("IDMED");
            medec = medecDAO.read(idmedec);
            int idpat = rs.getInt("IDPAT");
            pat = patDAO.read(idpat);
            Prescription presc = new Prescription.Builder().setIdPresc(idpresc).setDateP(date).setMedec(medec).setPat(pat).setLMedic(lMedic).build();
            hPresc.add(presc);
        }
        lPresc.addAll(hPresc);
        return lPresc;
    }

}
