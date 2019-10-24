package pl.com.devmeet.devmeet.poll_associated.poll.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.devmeet.devmeet.domain_utils.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudFacade;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudRepository;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupEntity;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
public class PollCrudFacadeTest {

    @Autowired
    private PollCrudRepository pollCrudRepository;

    @Autowired
    private GroupCrudRepository groupCrudRepository;

    private PollCrudFacade pollCrudFacade;
    private GroupCrudFacade groupCrudFacade;

    private PollDto testPollDto;
    private GroupDto testGroupDto;

    @Before
    public void setUp() {

        testGroupDto = new GroupDto().builder()
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

        testPollDto = new PollDto().builder()
                .group(testGroupDto)
                .placeVotes(null)
                .availabilityVotes(null)
                .modificationTime(null)
                .creationTime(null)
                .isActive(true)
                .build();
    }

    private GroupCrudFacade initGroupCrudFacade() {
        return new GroupCrudFacade(groupCrudRepository);
    }

    private PollCrudFacade initPollCrudFacade() {
        return new PollCrudFacade(groupCrudRepository, pollCrudRepository);
    }

    private boolean initTestDB(){
        groupCrudFacade = initGroupCrudFacade();

        GroupEntity groupEntity;
        try {
            groupEntity = groupCrudFacade
                    .findEntity(groupCrudFacade
                            .create(testGroupDto));
        } catch (EntityNotFoundException | EntityAlreadyExistsException e) {
            groupEntity = null;
        }

        return groupEntity != null;
    }

    @Test
    public void INIT_TEST_DB() {
        boolean initDB = initTestDB();
        assertThat(initDB).isTrue();
    }

    @Test
    public void create() {
    }

    @Test
    public void read() {
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