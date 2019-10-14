package pl.com.devmeet.devmeet.domain_utils;

import pl.com.devmeet.devmeet.member_associated.member.domain.MemberNotFoundException;

import java.util.List;

public interface CrudEntityFinder<D, E> {

    E findEntity(D dto) throws IllegalArgumentException, MemberNotFoundException;

    List<E> findEntities(D dto) throws IllegalArgumentException;

    boolean isExist(D dto) throws MemberNotFoundException;
}
