package services;
import java.util.List;

public interface IService<T> {
    void ajouter(T entity);
    void modifier(T entity);
    void supprimer(int id);
    T getOne(int id);
    List<T> getAll();
}
