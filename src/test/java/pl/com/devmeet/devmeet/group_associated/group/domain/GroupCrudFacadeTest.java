package pl.com.devmeet.devmeet.group_associated.group.domain;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.devmeet.devmeet.group_associated.group.domain.status.GroupCrudInfoStatusEnum;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
public class GroupCrudFacadeTest {

    @Autowired
    private GroupCrudRepository repository;

    private GroupCrudFacade facade;
    private GroupDto testGroup;

    @Before
    public void setUp() throws Exception {
        facade = new GroupCrudFacade(repository);

        testGroup = new GroupDto().builder()
                .groupName("Java test group")
                .website("www.testWebsite.com")
                .description("Welcome to test group")
//                .messenger(entity.getMessenger())
                .membersLimit(5)
                .memberCounter(6)
                .meetingCounter(1)
                .creationTime(null)
                .modificationTime(null)
                .isActive(false)
                .build();

    }

    private GroupDto createGroup(){
        return facade.create(testGroup);
    }

    @Test
    public void WHEN_create_not_exist_group_THEN_return_group() {
        GroupDto created = createGroup();

       assertThat(created).isNotNull();
       assertThat(created.getCreationTime()).isNotNull();
       assertThat(created.isActive()).isTrue();
    }

    @Test
    public void WHEN_create_exist_group_THEN_return_IllegalArgumentException_group_already_exists() {
        GroupDto createdFirst = createGroup();

        try {
            facade.create(testGroup);
            Assert.fail();
        } catch (IllegalArgumentException e){
            assertThat(e)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(GroupCrudInfoStatusEnum.GROUP_ALREADY_EXISTS.toString());
        }
    }

    @Test
    public void WHEN_create_exist_group_but_not_active_THEN_return_group() {
        GroupDto createdFirst = createGroup();
        GroupDto deletedFirst = facade.delete(testGroup);
        GroupDto createdSecond = facade.create(modifiedTestGroup(testGroup));

        assertThat(createdFirst).isNotNull();
        assertThat(deletedFirst).isNotNull();

        assertThat(createdSecond).isNotNull();
        assertThat(createdSecond.getCreationTime()).isNotNull();
        assertThat(createdSecond.getModificationTime()).isNotNull();
        assertThat(createdSecond.isActive()).isTrue();
    }

    private GroupDto modifiedTestGroup(GroupDto dto){
        testGroup.setDescription("ModifiedDesc");
        testGroup.setWebsite("www.modified.com.pl");
        return testGroup;
    }

    @Test
    public void WHEN_found_existing_group_THEN_return_group() {
        assertThat(createGroup()).isNotNull();
        assertThat(facade.read(testGroup)).isNotNull();
    }

    @Test
    public void readAll() {
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void findEntity() {
    }

    @Test
    public void findEntities() {
    }
}