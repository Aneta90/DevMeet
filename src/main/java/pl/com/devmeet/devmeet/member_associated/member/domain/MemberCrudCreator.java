package pl.com.devmeet.devmeet.member_associated.member.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntityCreator;
import pl.com.devmeet.devmeet.domain_utils.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;

public class MemberCrudCreator implements CrudEntityCreator<MemberDto, MemberEntity> {

    private MemberCrudFinder memberCrudFinder;
    private MemberCrudSaver memberCrudSaver;

    MemberCrudCreator(MemberRepository repository) {
        this.memberCrudFinder = new MemberCrudFinder(repository);
        this.memberCrudSaver = new MemberCrudSaver(repository);
    }

    @Override
    public MemberEntity createEntity(MemberDto dto) throws IllegalArgumentException, EntityAlreadyExistsException, EntityNotFoundException {
        MemberEntity memberEntity = memberCrudFinder.findEntity(dto);
        if (memberEntity != null) {
            throw new MemberAlreadyExistsException("Member already exists in database");
        }
        return memberCrudSaver.saveEntity(MemberCrudFacade.map(dto));
    }
}