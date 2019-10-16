package pl.com.devmeet.devmeet.member_associated.member.domain;

import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;

class MemberCrudDeleter {

    private MemberCrudFinder memberCrudFinder;
    private MemberCrudSaver memberCrudSaver;

    MemberCrudDeleter(MemberRepository memberRepository) {
        this.memberCrudFinder = new MemberCrudFinder(memberRepository);
        this.memberCrudSaver = new MemberCrudSaver(memberRepository);
    }

    MemberEntity deleteEntity(MemberDto memberDto) throws IllegalArgumentException, EntityNotFoundException {

        MemberEntity memberEntity = memberCrudFinder.findEntity(memberDto);
        if (memberEntity != null) {
            memberEntity.setActive(false);
            memberEntity.setAvailabilities(null);
            memberEntity.setModificationTime(DateTime.now());
            memberEntity.setGroups(null);
            return memberCrudSaver.saveEntity(memberEntity);
        } else {
            throw new MemberNotFoundException("Member is not found in database");
        }
    }
}