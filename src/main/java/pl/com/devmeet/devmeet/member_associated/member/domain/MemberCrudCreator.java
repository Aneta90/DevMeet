package pl.com.devmeet.devmeet.member_associated.member.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntityCreator;

public class MemberCrudCreator implements CrudEntityCreator<MemberDto, MemberEntity> {

    private MemberCrudFinder memberCrudFinder;
    private MemberCrudSaver memberCrudSaver;

    MemberCrudCreator(MemberRepository repository) {
        this.memberCrudFinder = new MemberCrudFinder(repository);
        this.memberCrudSaver = new MemberCrudSaver(repository);
    }

    @Override
    public MemberEntity createEntity(MemberDto dto) throws IllegalArgumentException, MemberAlreadyExistsException, MemberNotFoundException {
        MemberEntity memberEntity = memberCrudFinder.findEntity(dto);
        if (memberEntity != null) {
            throw new MemberAlreadyExistsException();
        }
        return memberCrudSaver.saveEntity(MemberCrudFacade.map(dto));
    }
}