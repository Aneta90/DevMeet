package pl.com.devmeet.devmeet.domain_utils;

public interface CrudEntityUpdater<D, E> {

    E updateEntity(D dto);

}
