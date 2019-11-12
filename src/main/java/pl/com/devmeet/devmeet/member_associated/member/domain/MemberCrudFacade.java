package pl.com.devmeet.devmeet.member_associated.member.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.devmeet.devmeet.domain_utils.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.user.domain.UserCrudFacade;
import pl.com.devmeet.devmeet.user.domain.UserRepository;

@Service
public class MemberCrudFacade implements MemberCrudInterface {

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

    private MemberCrudDeleter deleterInit() {
        return new MemberCrudDeleter(initFinder(),initSaver());
    }

    private MemberCrudUpdater updateInit() {
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
    public MemberDto update(MemberDto newDto, MemberDto oldDto) throws EntityNotFoundException {
        return map(updateInit().update(newDto, oldDto));
    }

    @Override
    public boolean delete(MemberDto dto) throws EntityNotFoundException {
        return deleterInit().delete(dto) != null;
    }

    @Override
    public boolean isActive(MemberDto memberDto) throws EntityNotFoundException {
        return initFinder().findEntity(memberDto).isActive();
    }

    @Override
    public boolean isExist(MemberDto memberDto) {
        return initFinder().isExist(memberDto);
    }

    @Override
    public MemberEntity findEntity(MemberDto dto) throws EntityNotFoundException {
        return initFinder().findEntity(dto);
    }

    public static MemberEntity map(MemberDto dto) {
        return MemberMapper.map(dto);
    }

    public static MemberDto map(MemberEntity entity) {
        return MemberMapper.map(entity);
    }
}