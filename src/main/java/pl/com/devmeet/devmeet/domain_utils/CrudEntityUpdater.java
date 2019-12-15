package pl.com.devmeet.devmeet.domain_utils;

import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;

public interface CrudEntityUpdater<D, E> {

    E updateEntity(D oldDto, D newDto) throws CrudException;
}