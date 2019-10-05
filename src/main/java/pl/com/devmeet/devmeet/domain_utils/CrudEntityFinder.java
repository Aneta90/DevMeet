package pl.com.devmeet.devmeet.domain_utils;

import java.util.List;

public interface CrudEntityFinder<D, E> {

    E findEntity(D dto);

    List<E> findEntities(D dto);

    boolean isExist(D dto);
}
