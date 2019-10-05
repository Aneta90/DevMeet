package pl.com.devmeet.devmeet.domain_utils;

public interface CrudEntityCreator<D, E> {

    E createEntity(D dto);

}
