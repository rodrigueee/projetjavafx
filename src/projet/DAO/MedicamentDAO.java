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
     */
    @Override
    public List<Medicament> read(String d) {
        List<Medicament> lMedic = new ArrayList<>();
        try (PreparedStatement pstm = dbConnect.prepareStatement("SELECT * FROM medicament WHERE description LIKE UPPER(?) order by description")) {
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
        } catch (Exception e) {
            System.out.println("Erreur de lecture " + e.getMessage());
        }
        return lMedic;
    }

    /**
     * ajoute un record
     *
     * @param obj medicament a ajouté dans la BD
     * @return obj s'il a été créé ou retourne null
     */
    @Override
    public Medicament create(Medicament obj) {
        try (
                PreparedStatement pstm1 = dbConnect.prepareStatement("insert into MEDICAMENT (NOM, DESCRIPTION, CODE)values(?,?,?)");
                PreparedStatement pstm2 = dbConnect.prepareStatement("select idmedic from medicament  WHERE description LIKE UPPER(?) ")) {
            pstm1.setString(1, obj.getNom());
            pstm1.setString(2, obj.getDescription().toUpperCase());
            pstm1.setString(3, obj.getCode());
            int n = pstm1.executeUpdate();
            if (n != 1) {
                return null;
            }
            pstm2.setString(1, obj.getDescription());
            ResultSet rs = pstm2.executeQuery();
            rs.next();
            int idmedic = rs.getInt("IDMEDIC");
            return read(idmedic);

        } catch (Exception e) {
            System.out.println("erreur d'ajout : code du medicament deja utilisé ");
            
            

        }
        return null;
    }

    /**
     * modifié un record
     *
     * @param obj medicament a modifié dans la BD
     * @return obj s'il a été modifié ou retourne null
     */
    @Override
    public Medicament update(Medicament obj) {
        try (PreparedStatement pstm = dbConnect.prepareStatement("update MEDICAMENT set NOM=?,DESCRIPTION=?,CODE=? where IDMEDIC=?")) {
            pstm.setString(1, obj.getNom());
            pstm.setString(2, obj.getDescription().toUpperCase());
            pstm.setString(3, obj.getCode());
            pstm.setInt(4, obj.getIdmedic());
            int n = pstm.executeUpdate();
            if (n != 1) {
                return null;
            }
            return read(obj.getIdmedic());

        } catch (Exception e) {
            System.out.println("erreur de mise à jour" + e.getMessage());
        }
        return null;
    }

    /**
     * supprimé un record
     *
     * @param obj medicament a supprimé dans le BD
     * @return retourne un message de resultat
     */
    @Override
    public String delete(Medicament obj) {
        try (
                PreparedStatement pstm1 = dbConnect.prepareStatement("DELETE from medicament where IDMEDIC = ?");) {
            pstm1.setInt(1, obj.getIdmedic());
            int n = pstm1.executeUpdate();
            if (n != 1) {
                return "Pas supprimé";
            }
        } catch (Exception e) {
            return "erreur de suppression: " + e.getMessage();
        }
        return "Supprimé";
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
     */
    @Override
    public Medicament read(int idmedic) {
        try (PreparedStatement pstm = dbConnect.prepareStatement("SELECT * FROM medicament WHERE IDMEDIC = ? order by idmedic")) {
            pstm.setInt(1, idmedic);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                String nom = rs.getString("NOM");
                String desc = rs.getString("DESCRIPTION");
                String code = rs.getString("CODE");
                return new Medicament(idmedic, nom, desc, code);
            } else {
                System.out.println("id du médicament inconnu");
            }
        } catch (Exception e) {
            System.out.println("Erreur de lecture " + e.getMessage());

        }
        return null;
    }
}
