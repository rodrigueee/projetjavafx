package projet.DAO;

import java.sql.Connection;
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
     */
    public void setConnection(Connection nouvdbConnect) {
        dbConnect = nouvdbConnect;
    }

    /**
     * recherche partielle sur la caractéristique
     *
     * @param s caractéristique de l'objet à rechercher
     * @return retourne une liste de Type
     */
    public abstract List<Type> read(String s);

    /**
     *recherche sur l'id
     * @param id id de l'objet à rechercher
     * @return retourne l'objet
     *
     */
    public abstract Type read(int id);

    /**
     * crée un objet
     * @param obj l'objet à inserer dans la BD
     * @return retourne l'objet
     *
     */
    public abstract Type create(Type obj);

    /**
     * modifie un objet
     * @param obj l'objet à modifier dans la BD
     * @return retourne l'objet
     *
     */
    public abstract Type update(Type obj);

    /**
     *supprime un objet
     * @param obj l'objet à supprimer dans la BD
     * @return retourne l'objet
     *
     */
    public abstract String delete(Type obj);

    /**
     * recupère tous les records de la BD
     * @return retourne une liste d'objet de l'ensemble de la classe
     *
     */
    public abstract List<Type> readall();

}
