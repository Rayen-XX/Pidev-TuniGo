package services;

import java.util.List;

public interface Crud<T>{
    //Creation
    void create(T obj) throws Exception;
    //Mise a jour
    void update(T obj) throws Exception;
    //Supprimer
    void delete(T obj) throws Exception;
    //Affichage
    List<T> getAll() throws Exception;
}
