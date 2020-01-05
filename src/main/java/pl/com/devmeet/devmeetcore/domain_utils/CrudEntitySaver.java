package pl.com.devmeet.devmeetcore.domain_utils;

import pl.com.devmeet.devmeetcore.domain_utils.exceptions.CrudException;

public interface CrudEntitySaver<D, E> {

    D saveEntity(E entity) throws CrudException;
}
