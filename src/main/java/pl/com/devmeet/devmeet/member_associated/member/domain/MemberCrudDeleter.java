package pl.com.devmeet.devmeet.member_associated.member.domain;

import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;

public class MemberCrudDeleter {

    private MemberCrudFinder memberCrudFinder;
    private MemberCrudSaver memberCrudSaver;

    MemberCrudDeleter(MemberRepository memberRepository) {
        this.memberCrudFinder = new MemberCrudFinder(memberRepository);
        this.memberCrudSaver = new MemberCrudSaver(memberRepository);
    }

    boolean deleteEntity(MemberDto memberDto) throws IllegalArgumentException, EntityNotFoundException {

        MemberEntity memberEntity = memberCrudFinder.findEntity(memberDto);
        if (memberEntity != null && memberEntity.isActive()) {
            memberEntity.setActive(false);
            memberEntity.setAvailabilities(null);
            memberEntity.setModificationTime(DateTime.now());
            memberEntity.setGroups(null);

            return memberCrudSaver.saveEntity(memberEntity) != null;
        } else {
            throw new MemberNotFoundException("Member is not found in database");
        }
    }
}