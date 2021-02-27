//package jupiter.backend.core;
//
//import org.springframework.data.repository.Repository;
//
//import java.util.Optional;
//
//public interface CrudRepository<T, String> extends Repository<T, String> {
//
//    <S extends T> S save(S entity);
//
//    Optional<T> findById(String id);
//
//    Iterable<T> findAll();
//
//    long count();
//
//    void delete(T entity);
//
//    boolean existsById(String id);
//
//    // … more functionality omitted.
//}
