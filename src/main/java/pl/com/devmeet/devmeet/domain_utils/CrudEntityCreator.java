package pl.com.devmeet.devmeet.domain_utils;

import pl.com.devmeet.devmeet.member_associated.member.domain.MemberAlreadyExistsException;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberNotFoundException;

public interface CrudEntityCreator<D, E> {

    E createEntity(D dto) throws IllegalArgumentException, MemberNotFoundException, MemberAlreadyExistsException;

}
