package pl.com.devmeet.devmeet.domain_utils;

import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityNotFoundException;

public interface CrudEntityCreator<D, E> {

    E createEntity(D dto) throws CrudException;
}