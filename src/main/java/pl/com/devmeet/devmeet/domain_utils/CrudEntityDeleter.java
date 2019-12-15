package pl.com.devmeet.devmeet.domain_utils;

import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;

public interface CrudEntityDeleter<D, E> {

    E deleteEntity(D dto) throws CrudException;
}
