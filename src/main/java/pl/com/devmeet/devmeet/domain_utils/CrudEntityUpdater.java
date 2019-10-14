package pl.com.devmeet.devmeet.domain_utils;

import pl.com.devmeet.devmeet.member_associated.member.domain.MemberNotFoundException;

public interface CrudEntityUpdater<D, E> {

    E updateEntity(D oldDto, D newDto) throws IllegalArgumentException, MemberNotFoundException;
}
