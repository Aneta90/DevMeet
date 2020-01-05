package pl.com.devmeet.devmeetcore.domain_utils;

import pl.com.devmeet.devmeetcore.domain_utils.exceptions.CrudException;

public interface CrudEntityCreator<D, E> {

    E createEntity(D dto) throws CrudException;
}