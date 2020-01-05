package pl.com.devmeet.devmeetcore.domain_utils;

import pl.com.devmeet.devmeetcore.domain_utils.exceptions.CrudException;

public interface CrudEntityUpdater<D, E> {

    E updateEntity(D oldDto, D newDto) throws CrudException;
}