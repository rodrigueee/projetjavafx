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
import projet.metier.Medecin;

/**
 *
 * @author rodri
 */
public class MedecinDAO extends DAO<Medecin> {

    /**
     * Récupère les records dans la BD des medecins par nom
     *
     * @param d nom du medecin
     * @return lMedec retourne la liste des medecins remplies ou vide
     */
    @Override
    public List<Medecin> read(String d) {
        List<Medecin> lMedec = new ArrayList<>();
        try (PreparedStatement pstm = dbConnect.prepareStatement("SELECT * FROM medecin WHERE nom LIKE UPPER(?)")) {
            pstm.setString(1, "%" + d + "%");
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                while (rs.next()) {
                    int id = rs.getInt("IDMED");
                    String nom = rs.getString("NOM");
                    String prenom = rs.getString("PRENOM");
                    String matricule = rs.getString("MATRICULE");
                    String tel = rs.getString("TEL");

                    Medecin med = new Medecin(id, nom, prenom, matricule,tel);
                    lMedec.add(med);
                }
            }
        } catch (Exception e) {
            System.out.println("Erreur de lecture " + e.getMessage());

        }
        return lMedec;
    }

    /**
     * ajoute un record
     *
     * @param obj medecin a ajouté dans la BD
     * @return obj s'il a été créé ou retourne null
     */
    @Override
    public Medecin create(Medecin obj) {
        try (
                PreparedStatement pstm1 = dbConnect.prepareStatement("insert into MEDECIN (NOM, PRENOM, MATRICULE, TEL)values(?,?,?,?)");
                PreparedStatement pstm2 = dbConnect.prepareStatement("select idmed from medecin WHERE nom LIKE UPPER(?)")) {
            pstm1.setString(1, obj.getNomM().toUpperCase());
            pstm1.setString(2, obj.getPrenomM());
            pstm1.setString(3, obj.getMatricule());
            pstm1.setString(4, obj.getTel());
            int n = pstm1.executeUpdate();
            if (n != 1) {
                return null;
            }
            pstm2.setString(1, obj.getNomM());
            ResultSet rs = pstm2.executeQuery();
            rs.next();
            int idmedec = rs.getInt("IDMED");
            return read(idmedec);

        } catch (Exception e) {
            System.out.println("erreur de creation" + e.getMessage());

        }
        return null;
    }

    /**
     * modifié un record
     *
     * @param obj medecin a modifié dans la BD
     * @return obj s'il a été modifié ou retourne null
     */
    @Override
    public Medecin update(Medecin obj) {
        try (PreparedStatement pstm = dbConnect.prepareStatement("update MEDECIN set NOM=?,PRENOM=?,MATRICULE=?,TEL=? where IDMED=?")) {
            pstm.setString(1, obj.getNomM().toUpperCase());
            pstm.setString(2, obj.getPrenomM());
            pstm.setString(3, obj.getMatricule());
            pstm.setString(4, obj.getTel());
            pstm.setInt(5, obj.getIdmed());
            int n = pstm.executeUpdate();
            if (n != 1) {
                return null;
            }
            return read(obj.getIdmed());

        } catch (Exception e) {
            System.out.println("erreur de mise à jour" + e.getMessage());

        }
        return null;
    }

    /**
     * supprimé un record
     *
     * @param obj medecin a supprimé dans le BD
     * @return retourne un message de resultat
     */
    @Override
    public String delete(Medecin obj) {
        try (
                PreparedStatement pstm1 = dbConnect.prepareStatement("DELETE from medecin where IDMED = ?");) {
            pstm1.setInt(1, obj.getIdmed());
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
    public List<Medecin> readall() {
        List<Medecin> lme = new ArrayList<>();
        try (PreparedStatement pstm = dbConnect.prepareStatement("select * from medecin order by idmed")) {
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("IDMED");
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                String matricule = rs.getString("MATRICULE");
                String tel = rs.getString("TEL");
                Medecin md = new Medecin(id, nom, prenom, matricule, tel);
                lme.add(md);
            }
            if (lme.isEmpty()) {
                throw new Exception("Aucun medecin");
            }
            return lme;
        } catch (Exception e) {
            System.out.println("Erreur de lecture " + e.getMessage());
        }
        return null;
    }

    /**
     * Récupère un record dans la BD des medecins par id
     *
     * @param idmed id du medecin a cherché
     * @return retourne null ou le medecin s'il l'a trouvé
     */
    @Override
    public Medecin read(int idmed) {
        try (PreparedStatement pstm = dbConnect.prepareStatement("SELECT * FROM medecin WHERE IDMED = ? order by idmed")) {
            pstm.setInt(1, idmed);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                String matricule = rs.getString("MATRICULE");
                String tel = rs.getString("TEL");
                return new Medecin(idmed, nom, prenom, matricule, tel);
            } else {
                System.out.println("id du medecin inconnu");
            }
        } catch (Exception e) {
            System.out.println("Erreur de lecture " + e.getMessage());

        }
        return null;
    }

}
