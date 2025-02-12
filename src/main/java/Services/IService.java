package Services;

import java.util.List;

public interface IService <T> {
    public void ajouter(T t);
    public void modifier(T t);
    public void supprimer(int id);
    //public T getOne(T t);
    public List<T> getAll(T t);
    public T getOne(int id);

}
