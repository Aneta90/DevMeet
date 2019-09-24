package pl.com.devmeet.devmeet.domain_utils;

import java.util.List;

public interface CrudInterface<D, E> {

    D create (D dto);
    D read (D dto);
    List<D> readAll (D dto);
    D update(D oldDto, D newDto);
    D delete (D dto);

    E findEntity(D dto);
    List<E> findEntities(D dto);
}
