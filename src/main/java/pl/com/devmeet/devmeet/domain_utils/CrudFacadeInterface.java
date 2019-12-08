package pl.com.devmeet.devmeet.domain_utils;

import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberFoundButNotActiveException;

import java.util.List;

public interface CrudFacadeInterface<D, E> {

    D add(D dto) throws CrudException;

//    D find(D dto) throws CrudException;
//
//    List<D> findAll(D dto) throws CrudException;

    D update(D oldDto, D newDto) throws CrudException;

    D delete(D dto) throws CrudException;

//    E findEntity(D dto) throws CrudException;
//
//    List<E> findEntities(D dto) throws CrudException;
}
