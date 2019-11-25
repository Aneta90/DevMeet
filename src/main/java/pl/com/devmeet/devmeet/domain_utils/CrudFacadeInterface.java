package pl.com.devmeet.devmeet.domain_utils;

import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberFoundButNotActiveException;

import java.util.List;

public interface CrudFacadeInterface<D, E> {

    D create(D dto) throws CrudException;

    D read(D dto) throws CrudException;

    List<D> readAll(D dto) throws CrudException;

    D update(D oldDto, D newDto) throws CrudException, MemberFoundButNotActiveException;

    D delete(D dto) throws CrudException, MemberFoundButNotActiveException;

    E findEntity(D dto) throws CrudException;

    List<E> findEntities(D dto) throws CrudException;
}
