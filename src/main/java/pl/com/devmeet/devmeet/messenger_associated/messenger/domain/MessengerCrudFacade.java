package pl.com.devmeet.devmeet.messenger_associated.messenger.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.devmeet.devmeet.domain_utils.CrudFacadeInterface;
import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudFacade;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudRepository;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberRepository;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberFoundButNotActiveException;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.messenger_associated.messenger.status_and_exceptions.MessengerAlreadyExistsException;
import pl.com.devmeet.devmeet.messenger_associated.messenger.status_and_exceptions.MessengerArgumentNotSpecified;
import pl.com.devmeet.devmeet.messenger_associated.messenger.status_and_exceptions.MessengerInfoStatusEnum;
import pl.com.devmeet.devmeet.messenger_associated.messenger.status_and_exceptions.MessengerNotFoundException;
import pl.com.devmeet.devmeet.user.domain.UserRepository;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessengerCrudFacade implements CrudFacadeInterface<MessengerDto, MessengerEntity> {

    private MessengerRepository messengerRepository;
    private UserRepository userRepository;
    private MemberRepository memberRepository;
    private GroupCrudRepository groupRepository;

    @Autowired
    public MessengerCrudFacade(MessengerRepository messengerRepository,
                               UserRepository userRepository,
                               MemberRepository memberRepository,
                               GroupCrudRepository groupRepository) {

        this.messengerRepository = messengerRepository;
        this.userRepository = userRepository;
        this.memberRepository = memberRepository;
        this.groupRepository = groupRepository;
    }

    private MessengerMemberFinder initMemberFinder() {
        return new MessengerMemberFinder(new MemberCrudFacade(memberRepository, userRepository));
    }

    private MessengerGroupFinder initGroupFinder() {
        return new MessengerGroupFinder(new GroupCrudFacade(groupRepository));
    }

    private MessengerCrudSaver initSaver(){
        return MessengerCrudSaver.builder()
                .memberRepository(memberRepository)
                .messengerRepository(messengerRepository)
                .build();
    }

    private MessengerCrudFinder initFinder() {
        return MessengerCrudFinder.builder()
                .messengerRepository(messengerRepository)
                .groupFinder(initGroupFinder())
                .memberFinder(initMemberFinder())
                .build();
    }

    private MessengerCrudCreator initCreator() {
        return MessengerCrudCreator.builder()
                .messengerCrudFinder(initFinder())
                .messengerCrudSaver(initSaver())
                .build();
    }

    private MessengerCrudDeleter deleterInit() {
        return MessengerCrudDeleter.builder()
                .messengerCrudFinder(initFinder())
                .messengerCrudSaver(initSaver())
                .build();
    }

    @Override
    public MessengerDto create(MessengerDto messengerDto) throws MessengerAlreadyExistsException, MessengerArgumentNotSpecified, MemberNotFoundException, UserNotFoundException, GroupNotFoundException {
        return map(initCreator().createEntity(messengerDto));
    }

    @Override
    public MessengerDto read(MessengerDto messengerDto) throws MessengerNotFoundException, MemberNotFoundException, UserNotFoundException, GroupNotFoundException {
        return map(findEntity(messengerDto));
    }

    @Override
    public List<MessengerDto> readAll(MessengerDto dto) throws MessengerNotFoundException {
        return mapToDtos(findEntities(dto));
    }

    @Override
    public MessengerDto update(MessengerDto oldDto, MessengerDto newDto) throws CrudException {
        throw new CrudException(MessengerInfoStatusEnum.METHOD_NOT_IMPLEMENTED.toString());
    }

    @Override
    public MessengerEntity findEntity(MessengerDto messengerDto) throws MessengerNotFoundException, MemberNotFoundException, UserNotFoundException, GroupNotFoundException {
        return initFinder().findEntity(messengerDto);
    }

    @Override
    public List<MessengerEntity> findEntities(MessengerDto dto) throws MessengerNotFoundException {
        return initFinder().findEntities(dto);
    }

    @Override
    public MessengerDto delete(MessengerDto messengerDto) throws UserNotFoundException, MemberNotFoundException, GroupNotFoundException, MessengerNotFoundException, MessengerAlreadyExistsException {
        return map(deleterInit().delete(messengerDto));
    }

    @Deprecated
    public boolean isExist(MessengerDto messengerDto) {
        return initFinder().isExist(messengerDto);
    }

    public static MessengerEntity map(MessengerDto messengerDto) {
        return MessengerMapper.map(messengerDto);
    }

    public static MessengerDto map(MessengerEntity messengerEntity) {
        return MessengerMapper.map(messengerEntity);
    }

    private List<MessengerDto> mapToDtos(List<MessengerEntity> entities){
        return entities.stream()
                .map(MessengerCrudFacade::map)
                .collect(Collectors.toList());
    }

}
