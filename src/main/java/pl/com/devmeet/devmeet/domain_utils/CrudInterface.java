package pl.com.devmeet.devmeet.domain_utils;

import java.util.List;

public interface CrudInterface<D, E> {

    D create(D dto) throws EntityNotFoundException;

    D read(D dto) throws IllegalArgumentException, EntityNotFoundException;

    List<D> readAll(D dto) throws IllegalArgumentException;

    D update(D oldDto, D newDto) throws IllegalArgumentException;

    D delete(D dto) throws IllegalArgumentException;

    E findEntity(D dto) throws IllegalArgumentException, EntityNotFoundException;

    List<E> findEntities(D dto) throws IllegalArgumentException;
}
