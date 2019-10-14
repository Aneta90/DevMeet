package pl.com.devmeet.devmeet.member_associated.member.domain;

import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeet.member_associated.place.domain.PlaceDto;

import java.util.List;
import java.util.Optional;

public interface MemberCrudInterface {

    MemberDto create(MemberDto memberDto) throws MemberNotFoundException, MemberAlreadyExistsException;

    MemberEntity findEntity(MemberDto memberDto) throws MemberNotFoundException;

    MemberDto read(MemberDto memberDto) throws MemberNotFoundException;

    MemberDto update(MemberDto oldDto, MemberDto newDto) throws MemberNotFoundException;

    MemberEntity delete(MemberDto memberDto) throws MemberNotFoundException;

    boolean isActive(MemberDto memberDto) throws MemberNotFoundException;

    boolean doesExist(MemberDto memberDto) throws MemberNotFoundException;

   // Optional<List<MemberEntity>> memberEntityList(GroupDto groupDto);

   // Optional<List<MemberEntity>> memberEntityList(PlaceDto placeDto);

    static MemberDto map(MemberEntity memberEntity) {
        return MemberMapper.map(memberEntity);
    }

    static MemberEntity map(MemberDto memberDto) {
        return MemberMapper.map(memberDto);
    }
}