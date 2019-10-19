package pl.com.devmeet.devmeet.domain_utils;

import java.util.List;

public interface CrudEntityFinder<D, E> {

    E findEntity(D dto) throws IllegalArgumentException, EntityNotFoundException;

    List<E> findEntities(D dto) throws IllegalArgumentException;

    boolean isExist(D dto);
}
