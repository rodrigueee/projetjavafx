/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.modele;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import projet.DAO.InformationsDAO;
import projet.DAO.MedecinDAO;
import projet.DAO.MedicamentDAO;
import projet.DAO.PatientDAO;
import projet.DAO.PrescriptionDAO;
import projet.metier.Informations;
import projet.metier.Medecin;
import projet.metier.Medicament;
import projet.metier.Patient;
import projet.metier.Prescription;

/**
 *
 * @author rodri
 */
public class ModeleDAO {

    /**
     * variable d'instance de MedicamentDAO
     */
    private MedicamentDAO medicDAO;
    private MedecinDAO medecDAO;
    private PatientDAO patDAO;
    private InformationsDAO infoDAO;
    private PrescriptionDAO prescDAO;

    /**
     * instance du modele pour le singleton(recupère la même instance)
     */
    private static ModeleDAO instance = null;

    /**
     * instancie le modele si necessaire et retourne l'instance
     *
     * @param c la connexion a la BD
     * @return retourne l'instance du modele
     */
    public static ModeleDAO getInstance(Connection c) {
        if (instance == null) {
            instance = new ModeleDAO(c);
        }
        return instance;
    }

    /**
     * constructeur paramétre avec la connexion a la BD
     *
     * @param c la connexion a la BD
     */
    private ModeleDAO(Connection c) {
        medicDAO = new MedicamentDAO();
        medecDAO = new MedecinDAO();
        patDAO = new PatientDAO();
        infoDAO = new InformationsDAO();
        prescDAO = new PrescriptionDAO();
        medicDAO.setConnection(c);
        medecDAO.setConnection(c);
        patDAO.setConnection(c);
        infoDAO.setConnection(c);
        prescDAO.setConnection(c);
    }

    /**
     * récupère la liste de medic de la BD et la retourne
     *
     * @param desc description du medicament
     * @return la liste des médicaments correspondant a la description
     * @throws java.sql.SQLException
     */
    public List<Medicament> getMedic(String desc) throws SQLException {
        return medicDAO.read(desc);
    }

    /**
     * récupère la liste de medec de la BD et la retourne
     *
     * @param desc nom du medecin
     * @return la liste des medecins correspondant au nom
     * @throws java.sql.SQLException
     */
    public List<Medecin> getMedec(String desc) throws SQLException {
        return medecDAO.read(desc);
    }

    /**
     * récupère la liste de patient de la BD et la retourne
     *
     * @param desc nom du patient
     * @return la liste des patients correspondant au nom
     * @throws java.sql.SQLException
     */
    public List<Patient> getPatient(String desc) throws SQLException {
        return patDAO.read(desc);
    }

    /**
     * aide par Gaetan pour "interger.tostring" récupère les prescriptions dans
     * la BD par son id
     *
     * @param idpresc
     * @return retourne une liste d'info
     * @throws java.sql.SQLException
     */
    public Prescription getPresc(int idpresc) throws SQLException, Exception {
        return prescDAO.read(idpresc);
    }

    /**
     * envoie l'objet dans dao qui l'ajoute dans le BD
     *
     * @param md objet à inserer dans la BD
     * @throws java.sql.SQLException
     */
    public void ajouterMedic(Medicament md) throws SQLException {
        medicDAO.create(md);
    }

    /**
     * envoie l'objet dans dao qui l'ajoute dans le BD
     *
     * @param mc objet à inserer dans la BD
     * @return retourne le resultat
     * @throws java.sql.SQLException
     */
    public String ajouterMedecin(Medecin mc) throws SQLException {
        medecDAO.create(mc);
        if (true) {
            return "Medecin créé";
        }
        return "Medecin non crée";
    }

    /**
     * envoie l'objet dans dao qui l'ajoute dans le BD
     *
     * @param pt objet à inserer dans la BD
     * @return retourne le resultat
     * @throws java.sql.SQLException
     */
    public String ajouterPatient(Patient pt) throws SQLException {
        patDAO.create(pt);
        if (true) {
            return "Patient créé";
        }
        return "Patient non crée";
    }

    /**
     * recupère la liste des medicaments dans la BD
     *
     * @return retourne la liste de tous les medicaments
     */
    public List<Medicament> tousMedicaments() {
        return medicDAO.readall();
    }

    /**
     * recupère la liste des medecins dans la BD
     *
     * @return retourne la liste de tous les medecins
     */
    public List<Medecin> tousMedecins() {
        return medecDAO.readall();
    }

    /**
     * recupère la liste des patients dans la BD
     *
     * @return retourne la liste de tous les patiens
     */
    public List<Patient> tousPatients() {
        return patDAO.readall();
    }

    /**
     * récupère le médicament dans la BD par son id
     *
     * @param idMedic id du médicament
     * @return retourne l'objet trouvé
     * @throws java.sql.SQLException
     */
    public Medicament getMedicId(int idMedic) throws SQLException {
        return medicDAO.read(idMedic);
    }

    /**
     * récupère le medecin dans la BD par son id
     *
     * @param idMedec id du medecin
     * @return retourne l'objet trouvé
     * @throws java.sql.SQLException
     */
    public Medecin getMedecId(int idMedec) throws SQLException {
        return medecDAO.read(idMedec);
    }

    /**
     * récupère le patient dans la BD par son id
     *
     * @param idPat id du patient
     * @return retourne l'objet trouvé
     * @throws java.sql.SQLException
     */
    public Patient getPatId(int idPat) throws SQLException {
        return patDAO.read(idPat);
    }

    /**
     * supprime l'objet dans la BD
     *
     * @param medicSup objet à supprimer dans la BD
     * @throws java.sql.SQLException
     */
    public void suppMedic(Medicament medicSup) throws SQLException {
        medicDAO.delete(medicSup);
    }

    /**
     * supprime l'objet dans la BD
     *
     * @param medecSup objet à supprimer dans la BD
     * @return retourne le resultat de la suppression
     * @throws java.sql.SQLException
     */
    public void suppMedec(Medecin medecSup) throws SQLException {
        medecDAO.delete(medecSup);
    }

    /**
     * supprime l'objet dans la BD
     *
     * @param patSup objet à supprimer dans la BD
     * @return retourne le resultat de la suppression
     * @throws java.sql.SQLException
     */
    public void suppPat(Patient patSup) throws SQLException {
        patDAO.delete(patSup);
    }

    /**
     * modifie le medicament dans le BD
     *
     * @param medicModifie le medicament modifié
     */
    public void modifMedic(Medicament medicModifie) throws SQLException {
        medicDAO.update(medicModifie);
    }

    /**
     * modifie le medecin dans le BD
     *
     * @param medecModifie le medecin modifié
     * @return retourne le medecin modifié
     * @throws java.sql.SQLException
     */
    public void modifMedec(Medecin medecModifie) throws SQLException {
        medecDAO.update(medecModifie);
    }

    /**
     * modifie le patient dans le BD
     *
     * @param patModifie le patient modifié
     * @return retourne le patient modifié
     * @throws java.sql.SQLException
     */
    public void modifPat(Patient patModifie) throws SQLException {
        patDAO.update(patModifie);
    }

    public List<Prescription> tousPresciptions()throws SQLException, Exception {
        return prescDAO.readall();
    }

}
