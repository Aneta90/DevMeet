package pl.com.devmeet.devmeet.group_associated.group.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.*;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberRepository;
import pl.com.devmeet.devmeet.user.domain.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
public class GroupCrudFacadeTest {

    @Autowired
    private GroupCrudRepository groupRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private UserRepository userRepository;

    private GroupDto testGroup;

    @Before
    public void setUp() {

        testGroup = new GroupDto().builder()
                .groupName("Java test group")
                .website("www.testWebsite.com")
                .description("Welcome to test group")
                .membersLimit(5)
                .memberCounter(6)
                .meetingCounter(1)
                .creationTime(null)
                .modificationTime(null)
                .isActive(false)
                .build();
    }

    private GroupCrudFacade initGroupFacade() {
        return new GroupCrudFacade(groupRepository, memberRepository, userRepository);
    }

    private GroupDto createGroup() throws GroupAlreadyExistsException {
        return initGroupFacade().add(testGroup);
    }

    @Test
    public void WHEN_create_not_exist_group_THEN_return_group() throws GroupAlreadyExistsException {
        GroupDto created = createGroup();

        assertThat(created).isNotNull();
        assertThat(created.getCreationTime()).isNotNull();
        assertThat(created.isActive()).isTrue();
    }

    @Test
    public void WHEN_create_exist_group_THEN_return_IllegalArgumentException_group_already_exists() {
        try {
            createGroup();
        } catch (GroupAlreadyExistsException e) {
            Assert.fail();
        }

        try {
            initGroupFacade().add(testGroup);
            Assert.fail();
        } catch (GroupAlreadyExistsException e) {
            assertThat(e)
                    .isInstanceOf(GroupAlreadyExistsException.class)
                    .hasMessage(GroupCrudStatusEnum.GROUP_ALREADY_EXISTS.toString());
        }
    }

    @Test
    public void WHEN_create_exist_group_but_not_active_THEN_return_group() throws GroupAlreadyExistsException, GroupNotFoundException, GroupFoundButNotActiveException {
        GroupDto createdFirst = createGroup();
        GroupCrudFacade groupCrudFacade = initGroupFacade();
        GroupDto deletedFirst = groupCrudFacade.delete(testGroup);
        GroupDto createdSecond = groupCrudFacade.add(modifiedTestGroup(createdFirst));

        assertThat(createdFirst).isNotNull();
        assertThat(deletedFirst).isNotNull();

        assertThat(createdSecond).isNotNull();
        assertThat(createdSecond.getCreationTime()).isNotNull();
        assertThat(createdSecond.getModificationTime()).isNotNull();
        assertThat(createdSecond.isActive()).isTrue();
    }

    private GroupDto modifiedTestGroup(GroupDto dto) {
        dto.setDescription("ModifiedDesc");
        dto.setWebsite("www.modified.com.pl");
        return testGroup;
    }

    @Test
    public void WHEN_found_existing_group_THEN_return_group() throws GroupAlreadyExistsException, GroupNotFoundException {
        assertThat(createGroup()).isNotNull();
        assertThat(initGroupFacade().findEntityByGroup(testGroup)).isNotNull();
    }

    @Test
    public void WHEN_group_not_found_THEN_return_EntityNotFoundException_group_not_found() {
        try {
            initGroupFacade().findEntityByGroup(testGroup);
            Assert.fail();
        } catch (GroupNotFoundException e) {
            assertThat(e)
                    .isInstanceOf(CrudException.class)
                    .hasMessage(GroupCrudStatusEnum.GROUP_NOT_FOUND.toString());
        }
    }

    @Test
    public void findAll() throws GroupAlreadyExistsException {
        createGroup();
        List<GroupDto> foundGroups = initGroupFacade().findAll();
        int groupsSize = foundGroups.size();

        assertThat(foundGroups.size()).isEqualTo(groupsSize);
    }

    @Test
    public void WHEN_try_to_update_existing_group_THEN_return_group() throws GroupException, GroupNotFoundException, GroupFoundButNotActiveException, GroupAlreadyExistsException {
        GroupDto group = createGroup();
        GroupDto update = modifiedTestGroup(testGroup);

        GroupDto modifiedGroup = initGroupFacade().update(group, update);

        assertThat(modifiedGroup.getGroupName()).isEqualTo(update.getGroupName());
        assertThat(modifiedGroup.getWebsite()).isEqualTo(update.getWebsite());
        assertThat(modifiedGroup.getDescription()).isEqualTo(update.getDescription());

        assertThat(modifiedGroup.getMemberCounter()).isEqualTo(group.getMemberCounter());
        assertThat(modifiedGroup.getMembersLimit()).isEqualTo(group.getMembersLimit());
        assertThat(modifiedGroup.getMeetingCounter()).isEqualTo(group.getMeetingCounter());
        assertThat(modifiedGroup.isActive()).isEqualTo(group.isActive());

        assertThat(modifiedGroup.getModificationTime()).isNotNull();
    }

    @Test
    public void WHEN_try_to_update_not_existing_group_THEN_return_EntityNotFoundException_group_not_found() throws GroupException, GroupFoundButNotActiveException {
        GroupDto update = modifiedTestGroup(testGroup);
        try {
            initGroupFacade().update(testGroup, update);
        } catch (GroupNotFoundException e) {
            assertThat(e)
                    .isInstanceOf(CrudException.class)
                    .hasMessage(GroupCrudStatusEnum.GROUP_NOT_FOUND.toString());
        }
    }

    @Test
    public void WHEN_delete_existing_group_THEN_return_group() throws GroupAlreadyExistsException, GroupNotFoundException, GroupFoundButNotActiveException {
        GroupDto group = createGroup();
        GroupDto deleted = initGroupFacade().delete(group);

        assertThat(deleted).isNotNull();
        assertThat(deleted.isActive()).isNotEqualTo(group.isActive());
        assertThat(deleted.getModificationTime()).isNotNull();
    }

    @Test
    public void WHEN_try_to_delete_not_existing_group_THEN_return_return_EntityNotFoundException_group_not_found() throws GroupFoundButNotActiveException {
        try {
            initGroupFacade().delete(testGroup);
        } catch (GroupNotFoundException e) {
            assertThat(e)
                    .isInstanceOf(CrudException.class)
                    .hasMessage(GroupCrudStatusEnum.GROUP_NOT_FOUND.toString());
        }
    }


    @Test
    public void findEntity() throws GroupAlreadyExistsException, GroupNotFoundException {
        GroupDto created = createGroup();
        GroupEntity foundEntity = initGroupFacade().findEntityByGroup(created);

        assertThat(foundEntity).isNotNull();
        assertThat(foundEntity.getGroupName()).isEqualTo(created.getGroupName());
        assertThat(foundEntity.getCreationTime()).isEqualTo(created.getCreationTime());
    }
}