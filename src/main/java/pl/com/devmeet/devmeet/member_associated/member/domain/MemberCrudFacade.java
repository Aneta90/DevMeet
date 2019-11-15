package pl.com.devmeet.devmeet.member_associated.member.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.devmeet.devmeet.domain_utils.CrudInterface;
import pl.com.devmeet.devmeet.domain_utils.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.status.MemberCrudStatusEnum;
import pl.com.devmeet.devmeet.user.domain.UserCrudFacade;
import pl.com.devmeet.devmeet.user.domain.UserRepository;

import java.util.List;

@Service
public class MemberCrudFacade implements CrudInterface<MemberDto, MemberEntity> {

    private MemberRepository memberRepository;
    private UserRepository userRepository;

    @Autowired
    public MemberCrudFacade(MemberRepository memberRepository, UserRepository userRepository) {
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
    }

    private MemberUserFinder initUserFinder() {
        return new MemberUserFinder(new UserCrudFacade(userRepository));
    }

    private MemberCrudSaver initSaver(){
        return new MemberCrudSaver(memberRepository);
    }

    private MemberCrudFinder initFinder() {
        return new MemberCrudFinder(memberRepository, initUserFinder());
    }

    private MemberCrudCreator initCreator() {
        return new MemberCrudCreator(initFinder(), initSaver());
    }

    private MemberCrudDeleter initDeleter() {
        return new MemberCrudDeleter(initFinder(),initSaver());
    }

    private MemberCrudUpdater initUpdater() {
        return new MemberCrudUpdater(initFinder(), initSaver());
    }

    @Override
    public MemberDto create(MemberDto dto) throws EntityAlreadyExistsException, EntityNotFoundException {
        return map(initCreator().createEntity(dto));
    }

    @Override
    public MemberDto read(MemberDto dto) throws EntityNotFoundException {
        return map(findEntity(dto));
    }

    @Override
    public List<MemberDto> readAll(MemberDto dto) throws UnsupportedOperationException {
        throw new UnsupportedOperationException(MemberCrudStatusEnum.METHOD_NOT_IMPLEMENTED.toString());
    }

    @Override
    public MemberDto update(MemberDto newDto, MemberDto oldDto) throws EntityNotFoundException {
        return map(initUpdater().update(newDto, oldDto));
    }

    @Override
    public MemberDto delete(MemberDto dto) throws EntityNotFoundException {
        return map(initDeleter().delete(dto));
    }

    public boolean isActive(MemberDto memberDto) throws EntityNotFoundException {
        return initFinder().findEntity(memberDto).isActive();
    }

    public boolean isExist(MemberDto memberDto) {
        return initFinder().isExist(memberDto);
    }

    @Override
    public MemberEntity findEntity(MemberDto dto) throws EntityNotFoundException {
        return initFinder().findEntity(dto);
    }

    @Override
    public List<MemberEntity> findEntities(MemberDto dto) throws UnsupportedOperationException {
        throw new UnsupportedOperationException(MemberCrudStatusEnum.METHOD_NOT_IMPLEMENTED.toString());
    }

    public static MemberEntity map(MemberDto dto) {
        return MemberMapper.map(dto);
    }

    public static MemberDto map(MemberEntity entity) {
        return MemberMapper.map(entity);
    }
}