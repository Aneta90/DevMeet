package pl.com.devmeet.devmeetcore.domain_utils;

import pl.com.devmeet.devmeetcore.domain_utils.exceptions.CrudException;

public interface CrudEntityDeleter<D, E> {

    E deleteEntity(D dto) throws CrudException;
}
