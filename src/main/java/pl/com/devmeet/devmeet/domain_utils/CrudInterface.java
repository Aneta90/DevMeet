package pl.com.devmeet.devmeet.domain_utils;

import java.util.List;

public interface CrudInterface<D, E> {

    D create(D dto) throws EntityNotFoundException, EntityAlreadyExistsException;

    D read(D dto) throws EntityNotFoundException;

    List<D> readAll(D dto) throws EntityNotFoundException, UnsupportedOperationException;

    D update(D oldDto, D newDto) throws EntityNotFoundException, EntityAlreadyExistsException;

    D delete(D dto) throws EntityNotFoundException, EntityAlreadyExistsException;

    E findEntity(D dto) throws EntityNotFoundException;

    List<E> findEntities(D dto) throws EntityNotFoundException, UnsupportedOperationException;
}
