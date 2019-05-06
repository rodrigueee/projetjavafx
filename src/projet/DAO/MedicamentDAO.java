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
import projet.metier.Medicament;

/**
 *
 * @author rodri
 */
public class MedicamentDAO extends DAO<Medicament> {

    /**
     * Récupère les records dans la BD des medics par description
     *
     * @param d description du medicament
     * @return lMedic retourne la liste des medicaments remplies ou vide
     * @throws java.sql.SQLException
     */
    @Override
    public List<Medicament> read(String d) throws SQLException {
        List<Medicament> lMedic = new ArrayList<>();
        PreparedStatement pstm = dbConnect.prepareStatement("SELECT * FROM medicament WHERE description LIKE UPPER(?) order by description");
        pstm.setString(1, "%" + d + "%");
        ResultSet rs = pstm.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("IDMEDIC");
            String nom = rs.getString("NOM");
            String description = rs.getString("DESCRIPTION");
            String code = rs.getString("CODE");
            Medicament med = new Medicament(id, nom, description, code);
            lMedic.add(med);
        }
        if (lMedic.isEmpty()) {
            throw new SQLException("Aucun médicament ne contient en description cette recherche");
        }
        return lMedic;
    }

    /**
     * ajoute un record
     *
     * @param obj medicament a ajouté dans la BD
     * @throws java.sql.SQLException
     */
    @Override
    public void create(Medicament obj) throws SQLException {
        PreparedStatement pstm1 = dbConnect.prepareStatement("insert into MEDICAMENT (NOM, DESCRIPTION, CODE)values(?,?,?)");
        pstm1.setString(1, obj.getNom());
        pstm1.setString(2, obj.getDescription().toUpperCase());
        pstm1.setString(3, obj.getCode());
        pstm1.executeUpdate();
    }

    /**
     * modifié un record
     *
     * @param obj medicament a modifié dans la BD
     * @throws java.sql.SQLException
     */
    @Override
    public void update(Medicament obj) throws SQLException {
        PreparedStatement pstm = dbConnect.prepareStatement("update MEDICAMENT set NOM=?,DESCRIPTION=?,CODE=? where IDMEDIC=?");
        pstm.setString(1, obj.getNom());
        pstm.setString(2, obj.getDescription().toUpperCase());
        pstm.setString(3, obj.getCode());
        pstm.setInt(4, obj.getIdmedic());
        pstm.executeUpdate();
    }

    /**
     * supprimé un record
     *
     * @param obj medicament a supprimé dans le BD
     * @throws java.sql.SQLException
     */
    @Override
    public void delete(Medicament obj) throws SQLException {
        PreparedStatement pstm1 = dbConnect.prepareStatement("DELETE from medicament where IDMEDIC = ?");
        pstm1.setInt(1, obj.getIdmedic());
        pstm1.executeUpdate();
    }

    /**
     * recupére tous les records
     *
     * @return retourne la liste des records
     */
    @Override
    public List<Medicament> readall() {
        List<Medicament> lm = new ArrayList<>();
        try (PreparedStatement pstm = dbConnect.prepareStatement("select * from medicament order by idmedic")) {
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("IDMEDIC");
                String nom = rs.getString("NOM");
                String description = rs.getString("DESCRIPTION");
                String code = rs.getString("CODE");
                Medicament med = new Medicament(id, nom, description, code);
                lm.add(med);
            }
        } catch (Exception e) {
            System.out.println("Erreur de lecture " + e.getMessage());
        }
        return lm;
    }

    /**
     * Récupère un record dans la BD des medics par id
     *
     * @param idmedic id du medic a cherché
     * @return retourne null ou le medic s'il l'a trouvé
     * @throws java.sql.SQLException
     */
    @Override
    public Medicament read(int idmedic) throws SQLException {
        PreparedStatement pstm = dbConnect.prepareStatement("SELECT * FROM medicament WHERE IDMEDIC = ? order by idmedic");
        pstm.setInt(1, idmedic);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            String nom = rs.getString("NOM");
            String desc = rs.getString("DESCRIPTION");
            String code = rs.getString("CODE");
            return new Medicament(idmedic, nom, desc, code);
        } else {
            throw new SQLException("Id du médicament inconnu");
        }
    }
}
