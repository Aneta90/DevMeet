package pl.com.devmeet.devmeet.messenger_associated.messenger.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.devmeet.devmeet.domain_utils.CrudFacadeInterface;
import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudFacade;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudRepository;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberRepository;
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
        return new MessengerMemberFinder(new MemberCrudFacade(memberRepository, userRepository, messengerRepository, groupRepository));
    }

    private MessengerGroupFinder initGroupFinder() {
        return new MessengerGroupFinder(new GroupCrudFacade(groupRepository, memberRepository, userRepository, messengerRepository));
    }

    private MessengerCrudSaver initSaver() {
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

    public MessengerDto addToMember(MemberDto memberDto) throws UserNotFoundException, MessengerArgumentNotSpecified, GroupNotFoundException, MemberNotFoundException, MessengerAlreadyExistsException {
        return add(MessengerDto.builder()
                .member(memberDto)
                .build());
    }

    public MessengerDto addToGroup(GroupDto groupDto) throws UserNotFoundException, MessengerArgumentNotSpecified, GroupNotFoundException, MemberNotFoundException, MessengerAlreadyExistsException {
        return add(MessengerDto.builder()
                .group(groupDto)
                .build());
    }

    @Deprecated
    @Override
    public MessengerDto add(MessengerDto messengerDto) throws MessengerAlreadyExistsException, MessengerArgumentNotSpecified, MemberNotFoundException, UserNotFoundException, GroupNotFoundException {
        return map(initCreator().createEntity(messengerDto));
    }

    public MessengerDto findByMember(MemberDto memberDto) throws UserNotFoundException, MessengerNotFoundException, GroupNotFoundException, MemberNotFoundException {
        return map(findEntityByMember(memberDto));
    }

    public MessengerDto findByGroup(GroupDto groupDto) throws UserNotFoundException, MessengerNotFoundException, GroupNotFoundException, MemberNotFoundException {
        return map(findEntityByGroup(groupDto));
    }

    @Deprecated
    public MessengerDto find(MessengerDto messengerDto) throws MessengerNotFoundException, MemberNotFoundException, UserNotFoundException, GroupNotFoundException {
        return map(findEntity(messengerDto));
    }

    @Override
    public MessengerDto update(MessengerDto oldDto, MessengerDto newDto) throws CrudException {
        throw new CrudException(MessengerInfoStatusEnum.METHOD_NOT_IMPLEMENTED.toString());
    }

    public MessengerEntity findEntityByMember(MemberDto memberDto) throws UserNotFoundException, MemberNotFoundException, MessengerNotFoundException, GroupNotFoundException {
        MessengerDto messengerDto = MessengerDto.builder()
                .member(memberDto)
                .build();
        return findEntity(messengerDto);
    }

    public MessengerEntity findEntityByGroup(GroupDto groupDto) throws UserNotFoundException, MemberNotFoundException, MessengerNotFoundException, GroupNotFoundException {
        MessengerDto messengerDto = MessengerDto.builder()
                .group(groupDto)
                .build();
        return findEntity(messengerDto);
    }

    @Deprecated
    public MessengerEntity findEntity(MessengerDto messengerDto) throws MessengerNotFoundException, MemberNotFoundException, UserNotFoundException, GroupNotFoundException {
        return initFinder().findEntity(messengerDto);
    }

    public MessengerDto deactivateMembersMessenger(MemberDto memberDto) throws UserNotFoundException, MessengerNotFoundException, GroupNotFoundException, MemberNotFoundException, MessengerAlreadyExistsException {
        return delete(MessengerDto.builder()
                .member(memberDto)
                .build());
    }

    public MessengerDto deactivateGroupsMessenger(GroupDto groupDto) throws UserNotFoundException, MessengerNotFoundException, GroupNotFoundException, MemberNotFoundException, MessengerAlreadyExistsException {
        return delete(MessengerDto.builder()
                .group(groupDto)
                .build());
    }

    @Deprecated
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

    private List<MessengerDto> mapToDtos(List<MessengerEntity> entities) {
        return entities.stream()
                .map(MessengerCrudFacade::map)
                .collect(Collectors.toList());
    }

}
