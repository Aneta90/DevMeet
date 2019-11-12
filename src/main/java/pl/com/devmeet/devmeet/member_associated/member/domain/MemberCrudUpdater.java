package pl.com.devmeet.devmeet.member_associated.member.domain;

import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;

public class MemberCrudUpdater {

    private MemberCrudFinder memberCrudFinder;
    private MemberCrudSaver memberCrudSaver;

    MemberCrudUpdater(MemberRepository memberRepository) {
        this.memberCrudFinder = new MemberCrudFinder(memberRepository);
        this.memberCrudSaver = new MemberCrudSaver(memberRepository);
    }

    public MemberDto update(MemberDto newDto, MemberDto oldDto) throws EntityNotFoundException {

        MemberEntity oldEntity = memberCrudFinder.findEntity(oldDto);

        if (oldEntity.isActive()) {
            return memberCrudSaver.saveEntity(updateValues(newDto, oldEntity));
        }
        throw new MemberNotFoundException("Member is not found or is not active");
    }


    private MemberEntity updateValues(MemberDto updatedMember, MemberEntity oldMember) {
        String nick = updatedMember.getNick();
        boolean modification = false;

        if (nick != null) {
            oldMember.setNick(updatedMember.getNick());
            modification = true;
        }

        if (modification)
            oldMember.setModificationTime(DateTime.now());

        return oldMember;
    }
}
