package pl.com.devmeet.devmeet.domain_utils;

import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityNotFoundException;

public interface CrudEntityDeleter<D, E> {

    E deleteEntity(D dto) throws CrudException;
}
