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
import java.util.List;
import projet.metier.Patient;

/**
 *
 * @author rodri
 */
public class PatientDAO extends DAO<Patient> {

    /**
     * Récupère les records dans la BD des patient par nom
     *
     * @param d nom du patient
     * @return lPat retourne la liste des patients remplies ou vide
     * @throws java.sql.SQLException
     */
    @Override
    public List<Patient> read(String d) throws SQLException {
        List<Patient> lPat = new ArrayList<>();
        try (PreparedStatement pstm = dbConnect.prepareStatement("SELECT * FROM patient WHERE nom like UPPER(?) ")) {
            pstm.setString(1, "%" + d + "%");
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("IDPAT");
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                String tel = rs.getString("TEL");
                Patient p = new Patient(id, nom, prenom, tel);
                lPat.add(p);
            }
            if (lPat.isEmpty()) {
                throw new SQLException("Aucun patient correspond à cette recherche");
            }
            return lPat;
        }
    }

    /**
     * ajoute un record
     *
     * @param obj patient a ajouté dans la BD
     * @throws java.sql.SQLException
     */
    @Override
    public void create(Patient obj) throws SQLException {
        try (PreparedStatement pstm1 = dbConnect.prepareStatement("insert into PATIENT (NOM, PRENOM, TEL)values(?,?,?)")) {
            pstm1.setString(1, obj.getNomP().toUpperCase());
            pstm1.setString(2, obj.getPrenomP());
            pstm1.setString(3, obj.getTel());
            pstm1.executeUpdate();
        }
    }

    /**
     * modifié un record
     *
     * @param obj patient a modifié dans la BD
     * @throws java.sql.SQLException
     */
    @Override
    public void update(Patient obj) throws SQLException {
        try (PreparedStatement pstm = dbConnect.prepareStatement("update PATIENT set NOM=?,PRENOM=?,TEL=? where IDPAT =?")) {
            pstm.setString(1, obj.getNomP().toUpperCase());
            pstm.setString(2, obj.getPrenomP());
            pstm.setString(3, obj.getTel());
            pstm.setInt(4, obj.getIdpat());
            pstm.executeUpdate();
        }
    }

    /**
     * supprimé un record
     *
     * @param obj patient a supprimé dans le BD
     * @throws java.sql.SQLException
     */
    @Override
    public void delete(Patient obj) throws SQLException {
        try (PreparedStatement pstm1 = dbConnect.prepareStatement("DELETE from patient where IDPAT = ?")) {
            pstm1.setInt(1, obj.getIdpat());
            pstm1.executeUpdate();
        }
    }

    /**
     * recupére tous les records
     *
     * @return retourne la liste des records
     */
    @Override
    public List<Patient> readall() {
        List<Patient> lp = new ArrayList<>();
        try (PreparedStatement pstm = dbConnect.prepareStatement("select * from patient order by idpat")) {
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("IDPAT");
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                String tel = rs.getString("TEL");
                Patient pat = new Patient(id, nom, prenom, tel);
                lp.add(pat);
            }
        } catch (Exception e) {
            System.out.println("Erreur de lecture " + e.getMessage());
        }
        return lp;
    }

    /**
     * Récupère un record dans la BD des patients par id
     *
     * @param idpat id du patient a cherché
     * @return retourne null ou le patient s'il l'a trouvé
     * @throws java.sql.SQLException
     */
    @Override
    public Patient read(int idpat) throws SQLException {
        try (PreparedStatement pstm = dbConnect.prepareStatement("SELECT * FROM patient WHERE IDPAT = ? order by idpat")) {
            pstm.setInt(1, idpat);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                String tel = rs.getString("TEL");
                return new Patient(idpat, nom, prenom, tel);
            } else {
                throw new SQLException("id du patient inconnu");
            }
        }
    }
}
