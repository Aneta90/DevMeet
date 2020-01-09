package pl.com.devmeet.devmeetcore.group_associated.group.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.devmeet.devmeetcore.domain_utils.CrudFacadeInterface;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.status_and_exceptions.GroupAlreadyExistsException;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.status_and_exceptions.GroupException;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.status_and_exceptions.GroupFoundButNotActiveException;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberRepository;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.domain.MessengerCrudFacade;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.domain.MessengerRepository;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.status_and_exceptions.MessengerAlreadyExistsException;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.status_and_exceptions.MessengerArgumentNotSpecified;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.status_and_exceptions.MessengerNotFoundException;
import pl.com.devmeet.devmeetcore.user.domain.UserRepository;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserNotFoundException;

import java.util.List;

@Service
public class GroupCrudFacade implements CrudFacadeInterface<GroupDto, GroupEntity> {

    private GroupCrudRepository groupCrudRepository;
    private MemberRepository memberRepository;
    private UserRepository userRepository;
    private MessengerRepository messengerRepository;

    @Autowired
    public GroupCrudFacade(GroupCrudRepository groupCrudRepository, MemberRepository memberRepository, UserRepository userRepository, MessengerRepository messengerRepository) {
        this.groupCrudRepository = groupCrudRepository;
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
        this.messengerRepository = messengerRepository;
    }

    private GroupMemberFinder initMemberFinder() {
        return new GroupMemberFinder(new MemberCrudFacade(memberRepository, userRepository, messengerRepository, groupCrudRepository));
    }

    private GroupCrudSaver initSaver() {
        return new GroupCrudSaver(groupCrudRepository);
    }

    private GroupCrudCreator initCreator() {
        return GroupCrudCreator.builder()
                .groupCrudFinder(initFinder())
                .groupCrudSaver(initSaver())
                .groupMessengerCreator(initMessengerCreator())
                .build();
    }

    private GroupCrudFinder initFinder() {
        return GroupCrudFinder.builder()
                .groupCrudRepository(groupCrudRepository)
                .memberFinder(initMemberFinder())
                .build();
    }

    private GroupCrudUpdater initUpdater() {
        return GroupCrudUpdater.builder()
                .groupCrudFinder(initFinder())
                .groupCrudSaver(initSaver())
                .build();
    }

    private GroupCrudDeactivator initDeleter() {
        return GroupCrudDeactivator.builder()
                .groupCrudFinder(initFinder())
                .groupCrudSaver(initSaver())
                .groupMessengerDeactivator(initMessengerDeactivator())
                .build();
    }

    private MessengerCrudFacade initMessengerFacade(){
        return new MessengerCrudFacade(messengerRepository, userRepository, memberRepository, groupCrudRepository);
    }

    private GroupMessengerCreator initMessengerCreator() {
        return new GroupMessengerCreator(initMessengerFacade());
    }

    private GroupMessengerDeactivator initMessengerDeactivator(){
        return new GroupMessengerDeactivator(initMessengerFacade());
    }

    @Override
    public GroupDto add(GroupDto dto) throws GroupAlreadyExistsException, UserNotFoundException, MemberNotFoundException, GroupNotFoundException, MessengerAlreadyExistsException, MessengerArgumentNotSpecified {
        return map(initCreator().createEntity(dto));
    }

    public GroupDto findByGroupNameWebsiteAndDescription(String groupName, String website, String description) throws GroupNotFoundException {
        return map(findEntityByGroupNameAndWebsiteAndDescription(groupName, website, description));
    }

    public List<GroupDto> findAll() {
        return mapDtoList(findAllEntities());
    }


    public GroupEntity findEntityById(Long id) throws GroupNotFoundException {
        return initFinder().findById(id);
    }

    public GroupEntity findEntityByGroup(GroupDto groupDto) throws GroupNotFoundException {
        return initFinder().findEntityByGroup(groupDto);
    }

    public GroupEntity findEntityByGroupNameAndWebsiteAndDescription(String groupName, String website, String description) throws GroupNotFoundException {
        return initFinder().findEntityByGroupNameAndWebsiteAndDescription(groupName, website, description);
    }

    public List<GroupEntity> findAllEntities() {
        return initFinder().findAllEntities();
    }

    @Override
    public GroupDto update(GroupDto oldDto, GroupDto newDto) throws GroupException, GroupNotFoundException, GroupFoundButNotActiveException {
        return map(initUpdater().updateEntity(oldDto, newDto));
    }

    public GroupDto update(GroupDto oldDto, String groupName, String website, String description) throws GroupException, GroupNotFoundException, GroupFoundButNotActiveException {
        return map(initUpdater().updateEntity(oldDto, groupName, website, description));
    }

    @Override
    public GroupDto delete(GroupDto dto) throws GroupNotFoundException, GroupFoundButNotActiveException, UserNotFoundException, MemberNotFoundException, MessengerNotFoundException, MessengerAlreadyExistsException {
        return map(initDeleter().deleteEntity(dto));
    }

    public static GroupDto map(GroupEntity entity) {
        return GroupCrudMapper.map(entity);
    }

    public static List<GroupDto> mapDtoList(List<GroupEntity> entities) {
        return GroupCrudMapper.mapDtoList(entities);
    }

    public static GroupEntity map(GroupDto dto) {
        return GroupCrudMapper.map(dto);
    }

    public static List<GroupEntity> mapEntityList(List<GroupDto> dtos) {
        return GroupCrudMapper.mapEntityList(dtos);
    }

    public List<GroupDto> findBySearchText(String searchText) {
        if (searchText != null)
            return mapDtoList(groupCrudRepository.findAllBySearchText(searchText));
        else return mapDtoList(findAllEntities());
    }

    public List<GroupDto> findByActive(Boolean isActive) {
        return mapDtoList(groupCrudRepository.findAllByActive(isActive));
    }

    public GroupDto findById(Long id) throws GroupNotFoundException { // todo - może zwracać Optional<GroupDto> zamiast rzucać wyjątki???
        return map(findEntityById(id));
    }


}
