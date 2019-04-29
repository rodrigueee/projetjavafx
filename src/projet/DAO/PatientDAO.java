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
     */
    @Override
    public List<Patient> read(String d) {
        List<Patient> lPat = new ArrayList<>();
        try (PreparedStatement pstm = dbConnect.prepareStatement("SELECT * FROM patient WHERE nom like UPPER(?) ")) {
            pstm.setString(1, "%" + d + "%");
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                while (rs.next()) {
                    int id = rs.getInt("IDPAT");
                    String nom = rs.getString("NOM");
                    String prenom = rs.getString("PRENOM");
                    String tel = rs.getString("TEL");
                    Patient p = new Patient(id, nom, prenom, tel);
                    lPat.add(p);
                }
            }
        } catch (Exception e) {
            System.out.println("Erreur de lecture " + e.getMessage());

        }
        return lPat;
    }

    /**
     * ajoute un record
     *
     * @param obj patient a ajouté dans la BD
     * @return obj s'il a été créé ou retourne null
     */
    @Override
    public Patient create(Patient obj) {
        try (
                PreparedStatement pstm1 = dbConnect.prepareStatement("insert into PATIENT (NOM, PRENOM, TEL)values(?,?,?)");
                PreparedStatement pstm2 = dbConnect.prepareStatement("SELECT idpat FROM patient WHERE nom LIKE UPPER(?)")) {
            pstm1.setString(1, obj.getNomP().toUpperCase());
            pstm1.setString(2, obj.getPrenomP());
            pstm1.setString(3, obj.getTel());
            int n = pstm1.executeUpdate();
            if (n != 1) {
                return null;
            }
            pstm2.setString(1, obj.getNomP());
            ResultSet rs = pstm2.executeQuery();
            rs.next();
            int idpat = rs.getInt("IDPAT");
            return read(idpat);

        } catch (Exception e) {
            System.out.println("erreur de creation" + e.getMessage());

        }
        return null;
    }

    /**
     * modifié un record
     *
     * @param obj patient a modifié dans la BD
     * @return obj s'il a été modifié ou retourne null
     */
    @Override
    public Patient update(Patient obj) {
        try (PreparedStatement pstm = dbConnect.prepareStatement("update PATIENT set NOM=?,PRENOM=?,TEL=? where IDPAT =?")) {
            pstm.setString(1, obj.getNomP().toUpperCase());
            pstm.setString(2, obj.getPrenomP());
            pstm.setString(3, obj.getTel());
            pstm.setInt(4, obj.getIdpat());
            int n = pstm.executeUpdate();
            if (n != 1) {
                return null;
            }
            return read(obj.getIdpat());

        } catch (Exception e) {
            System.out.println("erreur de mise à jour" + e.getMessage());

        }
        return null;
    }

    /**
     * supprimé un record
     *
     * @param obj patient a supprimé dans le BD
     * @return retourne un message de resultat
     */
    @Override
    public String delete(Patient obj) {
        try (
                PreparedStatement pstm1 = dbConnect.prepareStatement("DELETE from patient where IDPAT = ?");) {
            pstm1.setInt(1, obj.getIdpat());
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

            if (lp.isEmpty()) {
                throw new Exception("Aucun patient");
            }
            return lp;
        } catch (Exception e) {
            System.out.println("Erreur de lecture " + e.getMessage());
        }
        return null;
    }

    /**
     * Récupère un record dans la BD des patients par id
     *
     * @param idpat id du patient a cherché
     * @return retourne null ou le patient s'il l'a trouvé
     */
    @Override
    public Patient read(int idpat) {
        try (PreparedStatement pstm = dbConnect.prepareStatement("SELECT * FROM patient WHERE IDPAT = ? order by idpat")) {
            pstm.setInt(1, idpat);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                String tel = rs.getString("TEL");
                return new Patient(idpat, nom, prenom, tel);
            } else {
                System.out.println("id du patient inconnu");
            }
        } catch (Exception e) {
            System.out.println("Erreur de lecture " + e.getMessage());

        }
        return null;
    }

}
