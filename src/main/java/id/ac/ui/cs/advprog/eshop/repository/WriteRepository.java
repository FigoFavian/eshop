package id.ac.ui.cs.advprog.eshop.repository;

public interface WriteRepository<T> {
    T create(T object);

    T update(String id, T object);

    T delete(String id);
}