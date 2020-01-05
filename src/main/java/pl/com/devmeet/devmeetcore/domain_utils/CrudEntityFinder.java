package pl.com.devmeet.devmeetcore.domain_utils;

import pl.com.devmeet.devmeetcore.domain_utils.exceptions.CrudException;

import java.util.List;

public interface CrudEntityFinder<D, E> {

    E findEntity(D dto) throws CrudException;

    List<E> findEntities(D dto) throws CrudException;

    boolean isExist(D dto);
}
