package pl.com.devmeet.devmeet.poll_associated.poll.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.devmeet.devmeet.domain_utils.CrudErrorEnum;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityNotFoundException;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudFacade;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudRepository;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupEntity;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupAlreadyExistsException;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.status_and_exceptions.PollAlreadyExistsException;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.status_and_exceptions.PollCrudStatusEnum;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.status_and_exceptions.PollNotFoundException;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.status_and_exceptions.PollUnsupportedOperationException;

import static org.assertj.core.api.Assertions.*;

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
                .messenger(null)
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

        GroupEntity groupEntity = null;
        try {
            groupEntity = groupCrudFacade
                    .findEntity(groupCrudFacade
                            .create(testGroupDto));
        } catch (GroupNotFoundException e) {
            e.printStackTrace();
        } catch (GroupAlreadyExistsException e) {
            e.printStackTrace();
        }

        return groupEntity != null;
    }

    @Test
    public void INIT_TEST_DB() {
        boolean initDB = initTestDB();
        assertThat(initDB).isTrue();
    }

    @Test
    public void WHEN_create_not_existing_poll_THEN_return_poll() throws PollAlreadyExistsException, GroupNotFoundException {
        initTestDB();
        PollDto pollDto = initPollCrudFacade().create(testPollDto);

        assertThat(pollDto).isNotNull();
        assertThat(pollDto.getGroup()).isNotNull();
        assertThat(pollDto.getCreationTime()).isNotNull();
        assertThat(pollDto.isActive()).isTrue();
    }

    @Test
    public void WHEN_try_to_create_existing_poll_THEN_return_EntityAlreadyExistsException() throws GroupNotFoundException {
        initTestDB();
        PollCrudFacade pollCrudFacade = initPollCrudFacade();
        try {
            pollCrudFacade.create(testPollDto);
        } catch (PollAlreadyExistsException | GroupNotFoundException e) {
            Assert.fail();
        }

        try {
            pollCrudFacade.create(testPollDto);
            Assert.fail();
        } catch (PollAlreadyExistsException e) {
            assertThat(e)
                    .isInstanceOf(PollAlreadyExistsException.class)
                    .hasMessage(PollCrudStatusEnum.POLL_ALREADY_EXISTS.toString());
        }
    }

    @Test
    public void WHEN_found_active_poll_THEN_return_poll() throws PollAlreadyExistsException, GroupNotFoundException, PollNotFoundException {
        initTestDB();
        PollCrudFacade pollCrudFacade = initPollCrudFacade();
        PollDto pollDto = pollCrudFacade.create(testPollDto);
        PollDto found = pollCrudFacade.read(pollDto);

        assertThat(found).isNotNull();
        assertThat(found.isActive()).isTrue();
    }

    @Test
    public void WHEN_try_to_find_not_existing_poll_THEN_return_EntityNotFoundException() throws GroupNotFoundException {
        initTestDB();
        try {
            initPollCrudFacade().read(testPollDto);
            Assert.fail();
        } catch (PollNotFoundException e) {
            assertThat(e)
                    .isInstanceOf(PollNotFoundException.class)
                    .hasMessage(PollCrudStatusEnum.POLL_NOT_FOUND.toString());
        }
    }

    @Test
    public void readAll_not_support() {
        PollCrudFacade pollCrudFacade = initPollCrudFacade();
        try {
            pollCrudFacade.update(testPollDto, testPollDto);
            Assert.fail();
        } catch (PollUnsupportedOperationException e) {
            assertThat(e)
                    .isInstanceOf(PollUnsupportedOperationException.class)
                    .hasMessage(CrudErrorEnum.METHOD_NOT_IMPLEMENTED.toString());
        }
    }


    @Test
    public void update_not_support() {
        PollCrudFacade pollCrudFacade = initPollCrudFacade();
        try {
            pollCrudFacade.update(testPollDto, testPollDto);
            Assert.fail();
        } catch (PollUnsupportedOperationException e) {
            assertThat(e)
                    .isInstanceOf(UnsupportedOperationException.class)
                    .hasMessage(CrudErrorEnum.METHOD_NOT_IMPLEMENTED.toString());
        }
    }

    @Test
    public void WHEN_delete_active_poll_THEN_return_poll() throws PollAlreadyExistsException, GroupNotFoundException, PollNotFoundException {
        initTestDB();
        PollCrudFacade pollCrudFacade = initPollCrudFacade();
        pollCrudFacade.create(testPollDto);
        PollDto deleted = pollCrudFacade.delete(testPollDto);

        assertThat(deleted).isNotNull();
        assertThat(deleted.isActive()).isFalse();
    }

    @Test
    public void WHEN_delete_not_active_poll_THEN_return_EntityAlreadyExistsException() throws GroupNotFoundException {
        initTestDB();
        PollCrudFacade pollCrudFacade = initPollCrudFacade();

        try {
            pollCrudFacade.create(testPollDto);
        } catch (PollAlreadyExistsException e) {
            Assert.fail();
        }

        try {
            pollCrudFacade.delete(testPollDto);
        } catch (PollNotFoundException e) {
            Assert.fail();
        }

        try {
            initPollCrudFacade().delete(testPollDto);
        } catch (PollNotFoundException e) {
            assertThat(e)
                    .isInstanceOf(PollNotFoundException.class)
                    .hasMessage(PollCrudStatusEnum.POLL_FOUND_BUT_NOT_ACTIVE.toString());
        }
    }
}