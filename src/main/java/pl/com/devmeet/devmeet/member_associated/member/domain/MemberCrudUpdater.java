package pl.com.devmeet.devmeet.member_associated.member.domain;

import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityUpdater;

class MemberCrudUpdater implements CrudEntityUpdater<MemberDto, MemberEntity> {

    private MemberCrudFinder memberCrudFinder;
    private MemberCrudSaver memberCrudSaver;

    MemberCrudUpdater(MemberRepository memberRepository) {
        this.memberCrudFinder = new MemberCrudFinder(memberRepository);
        this.memberCrudSaver = new MemberCrudSaver(memberRepository);
    }

    @Override
    public MemberEntity updateEntity(MemberDto oldDto, MemberDto newDto) throws IllegalArgumentException, MemberNotFoundException {
        MemberEntity oldEntity = memberCrudFinder.findEntity(oldDto);
        MemberEntity updatedEntity = mapDtoToEntity(newDto);

        return memberCrudSaver.saveEntity(updateValues(oldEntity, updatedEntity));
    }

    private MemberEntity mapDtoToEntity(MemberDto dto) {
        return MemberCrudFacade.map(dto);
    }


    private MemberEntity updateValues(MemberEntity oldMemberEntity, MemberEntity newMemberEntity) {
        newMemberEntity.setId(oldMemberEntity.getId());
        newMemberEntity.setModificationTime(DateTime.now());
        return newMemberEntity;
    }
}
