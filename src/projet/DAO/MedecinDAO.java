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
import projet.metier.Medecin;
import projet.metier.Patient;
import projet.metier.Prescription;

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
     * @throws java.sql.SQLException
     */
    @Override
    public List<Medecin> read(String d) throws SQLException {
        try {
            List<Medecin> lme = new ArrayList<>();
            PrescriptionDAO prescDAO = new PrescriptionDAO();
            prescDAO.setConnection(dbConnect);
            List<Medecin> lMedec = new ArrayList<>();
            try (PreparedStatement pstm = dbConnect.prepareStatement("SELECT * FROM medecin WHERE nom LIKE UPPER(?)")) {
                pstm.setString(1, "%" + d + "%");
                ResultSet rs = pstm.executeQuery();
                List<Prescription> listePresc = prescDAO.readall();
                while (rs.next()) {
                    Set<Patient> listePat = new HashSet<>();
                    int id = rs.getInt("IDMED");
                    String nom = rs.getString("NOM");
                    String prenom = rs.getString("PRENOM");
                    String matricule = rs.getString("MATRICULE");
                    String tel = rs.getString("TEL");
                    Medecin med = new Medecin(id, nom, prenom, matricule, tel);
                    listePresc.forEach((presc) -> {
                        if (presc.getMd().getIdmed() == id) {
                            listePat.add(presc.getPt());
                        }
                    });
                    listePat.forEach((pt) -> {
                        med.add(pt);
                    });
                    lMedec.add(med);
                }
                if (lMedec.isEmpty()) {
                    throw new SQLException("Aucun medecin correspond à cette recherche");
                }
                return lMedec;
            }
        } catch (Exception ex) {
            System.out.println("");
            throw new SQLException("Erreur de lecture");
        }

    }

    /**
     * ajoute un record
     *
     * @param obj medecin a ajouté dans la BD
     * @throws java.sql.SQLException
     */
    @Override
    public void create(Medecin obj) throws SQLException {
        try (PreparedStatement pstm1 = dbConnect.prepareStatement("insert into MEDECIN (NOM, PRENOM, MATRICULE, TEL)values(?,?,?,?)")) {
            pstm1.setString(1, obj.getNomM().toUpperCase());
            pstm1.setString(2, obj.getPrenomM());
            pstm1.setString(3, obj.getMatricule());
            pstm1.setString(4, obj.getTel());
            pstm1.executeUpdate();
        }
    }

    /**
     * modifié un record
     *
     * @param obj medecin a modifié dans la BD
     * @throws java.sql.SQLException
     *
     */
    @Override
    public void update(Medecin obj) throws SQLException {
        try (PreparedStatement pstm = dbConnect.prepareStatement("update MEDECIN set NOM=?,PRENOM=?,MATRICULE=?,TEL=? where IDMED=?")) {
            pstm.setString(1, obj.getNomM().toUpperCase());
            pstm.setString(2, obj.getPrenomM());
            pstm.setString(3, obj.getMatricule());
            pstm.setString(4, obj.getTel());
            pstm.setInt(5, obj.getIdmed());
            pstm.executeUpdate();
        }
    }

    /**
     * supprimé un record
     *
     * @param obj medecin a supprimé dans le BD
     * @throws java.sql.SQLException
     */
    @Override
    public void delete(Medecin obj) throws SQLException {
        try (PreparedStatement pstm1 = dbConnect.prepareStatement("DELETE from medecin where IDMED = ?")) {
            pstm1.setInt(1, obj.getIdmed());
            pstm1.executeUpdate();
        }
    }

    /**
     * recupére tous les records
     *
     * @return retourne la liste des records
     */
    @Override
    public List<Medecin> readall() {
        List<Medecin> lme = new ArrayList<>();
        PrescriptionDAO prescDAO = new PrescriptionDAO();
        prescDAO.setConnection(dbConnect);
        try (PreparedStatement pstm = dbConnect.prepareStatement("select * from medecin order by idmed")) {
            List<Prescription> listePresc = prescDAO.readall();
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Set<Patient> listePat = new HashSet<>();
                int id = rs.getInt("IDMED");
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                String matricule = rs.getString("MATRICULE");
                String tel = rs.getString("TEL");
                Medecin md = new Medecin(id, nom, prenom, matricule, tel);
                listePresc.forEach((presc) -> {
                    if (presc.getMd().getIdmed() == id) {
                        listePat.add(presc.getPt());
                    }
                });
                listePat.forEach((pt) -> {
                    md.add(pt);
                });
                lme.add(md);
            }
        } catch (Exception e) {
            System.out.println("Erreur de lecture " + e.getMessage());
        }
        return lme;
    }

    /**
     * Récupère un record dans la BD des medecins par id
     *
     * @param idmed id du medecin a cherché
     * @return retourne null ou le medecin s'il l'a trouvé
     * @throws java.sql.SQLException
     */
    @Override
    public Medecin read(int idmed) throws SQLException {
        try {
            PatientDAO patDAO = new PatientDAO();
            patDAO.setConnection(dbConnect);
            PreparedStatement pstm = dbConnect.prepareStatement("SELECT * FROM medecin WHERE IDMED = ? order by idmed");
            PreparedStatement pstm2 = dbConnect.prepareStatement("SELECT IDPAT FROM prescription WHERE IDMED = ?");
            pstm2.setInt(1, idmed);
            ResultSet rs = pstm2.executeQuery();
            Set<Patient> listePat = new HashSet<>();
            while (rs.next()) {
                Patient pat = patDAO.read(rs.getInt("IDPAT"));
                listePat.add(pat);
            }
            pstm.setInt(1, idmed);
            rs = pstm.executeQuery();
            if (rs.next()) {
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                String matricule = rs.getString("MATRICULE");
                String tel = rs.getString("TEL");
                Medecin md = new Medecin(idmed, nom, prenom, matricule, tel);
                listePat.forEach((pt) -> {
                    md.add(pt);
                });
                pstm.close();
                return md;
            } else {
                throw new SQLException("id du medecin inconnu");
            }

        } catch (SQLException ex) {
            throw new SQLException("Erreur de lecture");
        }
    }
}
