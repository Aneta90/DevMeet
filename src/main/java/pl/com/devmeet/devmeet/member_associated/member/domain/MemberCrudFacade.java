package pl.com.devmeet.devmeet.member_associated.member.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.devmeet.devmeet.domain_utils.CrudFacadeInterface;
import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudRepository;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberAlreadyExistsException;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberCrudStatusEnum;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberFoundButNotActiveException;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.messenger_associated.messenger.domain.MessengerCrudFacade;
import pl.com.devmeet.devmeet.messenger_associated.messenger.domain.MessengerRepository;
import pl.com.devmeet.devmeet.messenger_associated.messenger.status_and_exceptions.MessengerAlreadyExistsException;
import pl.com.devmeet.devmeet.messenger_associated.messenger.status_and_exceptions.MessengerArgumentNotSpecified;
import pl.com.devmeet.devmeet.user.domain.UserCrudFacade;
import pl.com.devmeet.devmeet.user.domain.UserRepository;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

import java.util.List;

@Service
public class MemberCrudFacade implements CrudFacadeInterface<MemberDto, MemberEntity> {

    private MemberRepository memberRepository;
    private UserRepository userRepository;
    private MessengerRepository messengerRepository;
    private GroupCrudRepository groupCrudRepository;

    @Autowired
    public MemberCrudFacade(MemberRepository memberRepository, UserRepository userRepository, MessengerRepository messengerRepository, GroupCrudRepository groupCrudRepository) {
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
        this.messengerRepository = messengerRepository;
        this.groupCrudRepository = groupCrudRepository;
    }

    private MemberMessengerCreator initMessengerCreator() {
        return new MemberMessengerCreator(new MessengerCrudFacade(messengerRepository, userRepository, memberRepository, groupCrudRepository));
    }

    private MemberUserFinder initUserFinder() {
        return new MemberUserFinder(new UserCrudFacade(userRepository));
    }

    private MemberCrudSaver initSaver() {
        return new MemberCrudSaver(memberRepository);
    }

    private MemberCrudFinder initFinder() {
        return new MemberCrudFinder(memberRepository, initUserFinder());
    }

    private MemberCrudCreator initCreator() {
        return MemberCrudCreator.builder()
                .memberFinder(initFinder())
                .saver(initSaver())
                .memberMessengerCreator(initMessengerCreator())
                .memberUserFinder(initUserFinder())
                .build();
    }

    private MemberCrudDeleter initDeleter() {
        return new MemberCrudDeleter(initFinder(), initSaver());
    }

    private MemberCrudUpdater initUpdater() {
        return new MemberCrudUpdater(initFinder(), initSaver());
    }

    @Override
    public MemberDto add(MemberDto dto) throws MemberAlreadyExistsException, UserNotFoundException, MemberNotFoundException, GroupNotFoundException, MessengerAlreadyExistsException, MessengerArgumentNotSpecified {
        return map(initCreator().createEntity(dto));
    }


    public MemberDto find(MemberDto dto) throws MemberNotFoundException, UserNotFoundException {
        return map(findEntity(dto));
    }


    public List<MemberDto> findAll(MemberDto dto) throws CrudException {
        throw new CrudException(MemberCrudStatusEnum.METHOD_NOT_IMPLEMENTED.toString());
    }

    @Override
    public MemberDto update(MemberDto oldDto, MemberDto newDto) throws MemberNotFoundException, UserNotFoundException, MemberFoundButNotActiveException {
        return map(initUpdater().update(oldDto, newDto));
    }

    @Override
    public MemberDto delete(MemberDto dto) throws MemberNotFoundException, UserNotFoundException, MemberFoundButNotActiveException {
        return map(initDeleter().delete(dto));
    }

    public boolean isActive(MemberDto memberDto) {
        try {
            initFinder().findEntity(memberDto).isActive();
            return true;
        } catch (UserNotFoundException | MemberNotFoundException e) {
            return false;
        }
    }

    public boolean isExist(MemberDto memberDto) {
        return initFinder().isExist(memberDto);
    }


    public MemberEntity findEntity(MemberDto dto) throws MemberNotFoundException, UserNotFoundException {
        return initFinder().findEntity(dto);
    }


    public List<MemberEntity> findEntities(MemberDto dto) throws CrudException {
        throw new CrudException(MemberCrudStatusEnum.METHOD_NOT_IMPLEMENTED.toString());
    }

    public static MemberEntity map(MemberDto dto) {
        return MemberMapper.map(dto);
    }

    public static MemberDto map(MemberEntity entity) {
        return MemberMapper.map(entity);
    }
}