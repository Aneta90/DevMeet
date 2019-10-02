package pl.com.devmeet.devmeet.domain_utils;

public interface CrudEntityFinder<D, E> {

    E findEntity(D dto);

    boolean isExist(D dto);
}
