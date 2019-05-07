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

    @Override
    public List<Informations> read(String idpresc) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Informations read(int idpresc) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void create(Informations obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Informations obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Informations obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Informations> readall() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
