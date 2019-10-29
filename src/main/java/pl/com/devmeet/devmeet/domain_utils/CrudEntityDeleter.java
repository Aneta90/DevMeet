package pl.com.devmeet.devmeet.domain_utils;

public interface CrudEntityDeleter<D, E> {

    E deleteEntity(D dto) throws IllegalArgumentException, EntityNotFoundException, EntityAlreadyExistsException;
}
