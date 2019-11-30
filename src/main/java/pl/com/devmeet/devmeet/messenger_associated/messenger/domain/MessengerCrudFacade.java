package pl.com.devmeet.devmeet.messenger_associated.messenger.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudFacadeInterface;
import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudFacade;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudRepository;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberRepository;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberFoundButNotActiveException;
import pl.com.devmeet.devmeet.messenger_associated.messenger.status_and_exceptions.MessengerAlreadyExistsException;
import pl.com.devmeet.devmeet.messenger_associated.messenger.status_and_exceptions.MessengerInfoStatusEnum;
import pl.com.devmeet.devmeet.messenger_associated.messenger.status_and_exceptions.MessengerNotFoundException;
import pl.com.devmeet.devmeet.user.domain.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

public class MessengerCrudFacade implements CrudFacadeInterface<MessengerDto, MessengerEntity> {

    private MessengerRepository messengerRepository;
    private UserRepository userRepository;
    private MemberRepository memberRepository;
    private GroupCrudRepository groupRepository;

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
        return new MessengerCrudSaver(messengerRepository);
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
    public MessengerDto create(MessengerDto messengerDto) throws MessengerAlreadyExistsException {
        return map(initCreator().createEntity(messengerDto));
    }

    @Override
    public MessengerDto read(MessengerDto messengerDto) throws MessengerNotFoundException {
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
    public MessengerEntity findEntity(MessengerDto messengerDto) throws MessengerNotFoundException {
        return initFinder().findEntity(messengerDto);
    }

    @Override
    public List<MessengerEntity> findEntities(MessengerDto dto) throws MessengerNotFoundException {
        return initFinder().findEntities(dto);
    }

    @Override
    public MessengerDto delete(MessengerDto messengerDto) throws CrudException {
        return map(deleterInit().delete(messengerDto));
    }

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
