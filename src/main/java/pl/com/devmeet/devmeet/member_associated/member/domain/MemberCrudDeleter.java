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

    boolean delete(MemberDto memberDto) throws IllegalArgumentException, EntityNotFoundException {

        MemberEntity memberEntity = memberCrudFinder.findEntity(memberDto);
        if (memberEntity.isActive()) {

            memberEntity.setActive(false);
            memberEntity.setModificationTime(DateTime.now());

            return saveMemberEntity(memberEntity) != null;
        } else {
            throw new MemberNotFoundException("Member is not found in database or is not active");
        }
    }

    private MemberDto saveMemberEntity(MemberEntity entity) {
        return memberCrudSaver.saveEntity(entity);
    }
}