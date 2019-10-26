package pl.com.devmeet.devmeet.poll_associated.poll.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
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
import pl.com.devmeet.devmeet.poll_associated.poll.domain.status.PollCrudStatusEnum;

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
                .active(true)
                .build();
    }

    private GroupCrudFacade initGroupCrudFacade() {
        return new GroupCrudFacade(groupCrudRepository);
    }

    private PollCrudFacade initPollCrudFacade() {
        return new PollCrudFacade(groupCrudRepository, pollCrudRepository);
    }

    private boolean initTestDB() {
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
    public void WHEN_create_not_existing_poll_THEN_return_poll() throws EntityAlreadyExistsException, EntityNotFoundException {
        initTestDB();
        PollDto pollDto = initPollCrudFacade().create(testPollDto);

        assertThat(pollDto).isNotNull();
        assertThat(pollDto.getGroup()).isNotNull();
        assertThat(pollDto.getCreationTime()).isNotNull();
        assertThat(pollDto.getModificationTime()).isNull();
        assertThat(pollDto.isActive()).isTrue();
    }

    @Test
    public void WHEN_try_to_create_existing_poll_THEN_return_EntityAlreadyExistsException() {
        initTestDB();
        PollCrudFacade pollCrudFacade = initPollCrudFacade();
        try {
            pollCrudFacade.create(testPollDto);
        } catch (EntityNotFoundException | EntityAlreadyExistsException e) {
            Assert.fail();
        }

        try {
            pollCrudFacade.create(testPollDto);
        } catch (EntityNotFoundException | EntityAlreadyExistsException e) {
            assertThat(e)
                    .isInstanceOf(EntityAlreadyExistsException.class)
                    .hasMessage(PollCrudStatusEnum.POLL_ALREADY_EXISTS.toString());
        }
    }

    @Test
    public void WHEN_found_active_poll_THEN_return_poll() throws EntityAlreadyExistsException, EntityNotFoundException {
        initTestDB();
        PollCrudFacade pollCrudFacade = initPollCrudFacade();
        PollDto pollDto = pollCrudFacade.create(testPollDto);
        PollDto found = pollCrudFacade.read(pollDto);

        assertThat(found).isNotNull();
        assertThat(found.isActive()).isTrue();
    }

    @Test
    public void WHEN_try_to_find_not_existing_poll_THEN_return_EntityNotFoundException() {
        initTestDB();
        try {
            initPollCrudFacade().read(testPollDto);
        } catch (EntityNotFoundException e) {
            assertThat(e)
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage(PollCrudStatusEnum.POLL_NOT_FOUND.toString());
        }
    }

    @Ignore
    @Test
    public void readAll() {
    }

    @Ignore
    @Test
    public void update() {
    }

    @Ignore
    @Test
    public void delete() {
    }

    @Ignore
    @Test
    public void findEntity() {
    }

    @Ignore
    @Test
    public void findEntities() {
    }
}