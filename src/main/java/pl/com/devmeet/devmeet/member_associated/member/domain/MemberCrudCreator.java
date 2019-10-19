package pl.com.devmeet.devmeet.member_associated.member.domain;

import javax.persistence.EntityExistsException;

public class MemberCrudCreator {

    private MemberCrudFinder memberCrudFinder;
    private MemberCrudSaver memberCrudSaver;

    MemberCrudCreator(MemberRepository repository) {
        this.memberCrudFinder = new MemberCrudFinder(repository);
        this.memberCrudSaver = new MemberCrudSaver(repository);
    }

  /*  public MemberEntity createEntity(MemberDto dto) throws IllegalArgumentException, EntityAlreadyExistsException, EntityNotFoundException {
        MemberEntity memberEntity = memberCrudFinder.findEntity(dto);
        if (memberEntity != null) {
            throw new MemberAlreadyExistsException("Member already exists in database");
        }
        return memberCrudSaver.saveEntity(MemberCrudFacade.map(dto));
    }*/

    public MemberDto create(MemberDto dto) throws EntityExistsException {

        if (memberCrudFinder.isExist(dto)) {
            throw new EntityExistsException();
        }

        return saveMemberEntity(MemberCrudFacade.map(dto));
    }

    private MemberDto saveMemberEntity(MemberEntity entity) {
        return memberCrudSaver.saveEntity(entity);
    }
}