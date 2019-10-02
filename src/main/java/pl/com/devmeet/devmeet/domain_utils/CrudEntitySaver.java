package pl.com.devmeet.devmeet.domain_utils;

public interface CrudEntitySaver<D, E> {

    D saveEntity(E entity);
}
