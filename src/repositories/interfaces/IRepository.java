package repositories.interfaces;

import java.util.List;

public interface IRepository<T> {
    boolean add(T entity);
    boolean update(T entity);
    boolean delete(int id);
    T getById(int id);
    List<T> getAll();
}
