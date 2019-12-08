package pl.com.devmeet.devmeet.group_associated.group.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.devmeet.devmeet.domain_utils.CrudFacadeInterface;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.*;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberRepository;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.user.domain.UserRepository;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
public class GroupCrudFacade implements CrudFacadeInterface<GroupDto, GroupEntity> {

    private GroupCrudRepository groupCrudRepository;
    private MemberRepository memberRepository;
    private UserRepository userRepository;

    @Autowired
    public GroupCrudFacade(GroupCrudRepository groupCrudRepository, MemberRepository memberRepository, UserRepository userRepository) {
        this.groupCrudRepository = groupCrudRepository;
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
    }

    private GroupMemberFinder initMemberFinder() {
        return new GroupMemberFinder(new MemberCrudFacade(memberRepository, userRepository));
    }

    private GroupCrudSaver initSaver() {
        return new GroupCrudSaver(groupCrudRepository);
    }

    private GroupCrudCreator initCreator() {
        return GroupCrudCreator.builder()
                .groupCrudFinder(initFinder())
                .groupCrudSaver(initSaver())
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

    private GroupCrudDeleter initDeleter() {
        return GroupCrudDeleter.builder()
                .groupCrudFinder(initFinder())
                .groupCrudSaver(initSaver())
                .build();
    }

    @Override
    public GroupDto add(GroupDto dto) throws GroupAlreadyExistsException {
        return map(initCreator().createEntity(dto));
    }

    public GroupDto findByGroupNameWebsiteAndDescription(String groupName, String website, String description) throws GroupNotFoundException {
        return map(findEntityByGroupNameAndWebsiteAndDescription(groupName, website, description));
    }

    public List<GroupDto> findAll() {
        return mapDtoList(findAllEntities());
    }


    public GroupEntity findById(Long id) throws GroupNotFoundException {
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
    public GroupDto delete(GroupDto dto) throws GroupNotFoundException, GroupFoundButNotActiveException {
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
}
