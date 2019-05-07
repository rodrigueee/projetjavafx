package projet.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * classe abstraite qui défini les méthodes à implémenter
 *
 * @author rodri
 * @param <Type> type générique
 */
public abstract class DAO<Type> {

    /**
     * variable d'ins(ance de connexion
     */
    protected Connection dbConnect;

    /**
     * permet la connexion
     * @param nouvdbConnect la connexion a la BDD
     */
    public void setConnection(Connection nouvdbConnect) {
        dbConnect = nouvdbConnect;
    }

    /**
     * recherche partielle sur la caractéristique
     *
     * @param s caractéristique de l'objet à rechercher
     * @return retourne une liste de Type
     * @throws java.lang.Exception
     */
    public abstract List<Type> read(String s) throws Exception;

    /**
     *recherche sur l'id
     * @param id id de l'objet à rechercher
     * @return retourne l'objet
     * @throws java.lang.Exception
     *
     */
    public abstract Type read(int id) throws Exception;

    /**
     * crée un objet
     * @param obj l'objet à inserer dans la BD
     * @throws java.lang.Exception
     *
     */
    public abstract void create(Type obj) throws Exception;

    /**
     * modifie un objet
     * @param obj l'objet à modifier dans la BD
     * @throws java.lang.Exception
     *
     */
    public abstract void update(Type obj) throws Exception;

    /**
     *supprime un objet
     * @param obj l'objet à supprimer dans la BD
     * @throws java.lang.Exception
     *
     */
    public abstract void delete(Type obj) throws Exception;

    /**
     * recupère tous les records de la BD
     * @return retourne une liste d'objet de l'ensemble de la classe
     * @throws java.lang.Exception
     *
     */
    public abstract List<Type> readall() throws Exception;

}
