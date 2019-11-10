package pl.com.devmeet.devmeet.poll_associated.availability_vote.domain;

import org.joda.time.DateTime;
import org.junit.Assert;
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
import pl.com.devmeet.devmeet.member_associated.availability.domain.AvailabilityCrudFacade;
import pl.com.devmeet.devmeet.member_associated.availability.domain.AvailabilityCrudRepository;
import pl.com.devmeet.devmeet.member_associated.availability.domain.AvailabilityDto;
import pl.com.devmeet.devmeet.member_associated.availability.domain.AvailabilityEntity;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberEntity;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberRepository;
import pl.com.devmeet.devmeet.poll_associated.availability_vote.domain.status.AvailabilityVoteCrudStatusEnum;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.PollCrudFacade;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.PollCrudRepository;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.PollDto;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.PollEntity;
import pl.com.devmeet.devmeet.user.domain.*;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
public class AvailabilityVoteCrudFacadeTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupCrudRepository groupRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AvailabilityCrudRepository availabilityRepository;
    @Autowired
    private PollCrudRepository pollCrudRepository;
    @Autowired
    private AvailabilityVoteCrudRepository availabilityVoteRepository;

    private UserCrudFacade userCrudFacade;
    private MemberCrudFacade memberCrudFacade;
    private AvailabilityCrudFacade availabilityCrudFacade;
    private GroupCrudFacade groupCrudFacade;
    private PollCrudFacade pollCrudFacade;
    private AvailabilityVoteCrudFacade voteCrudFacade;

    private UserDto testUserDto;
    private MemberDto testMemberDto;
    private AvailabilityDto testAvailabilityDtoFirst;
    private AvailabilityDto testAvailabilityDtoSecond;
    private GroupDto testGroupDto;
    private PollDto testPollDto;
    private AvailabilityVoteDto testVoteDto;

    public AvailabilityVoteCrudFacadeTest() {
    }

    @Before
    public void setUp() throws Exception {

        testUserDto = new UserDto().builder()
                .email("test@test.pl")
                .phone("221234567")
                .password("testPass")
                .isActive(true)
                .loggedIn(true)
                .build();

        testMemberDto = new MemberDto().builder()
                .user(testUserDto)
                .nick("Wasacz")
                .build();

        testAvailabilityDtoFirst = new AvailabilityDto().builder()
                .member(testMemberDto)
                .beginTime(new DateTime(2020, 3, 3, 15, 0, 0))
                .endTime(new DateTime(2020, 3, 3, 16, 0, 0))
                .place(null)
                .availabilityVote(null)
                .remoteWork(true)
                .creationTime(null)
                .modificationTime(null)
                .isActive(true)
                .build();

        testAvailabilityDtoSecond = new AvailabilityDto().builder()
                .member(testMemberDto)
                .beginTime(new DateTime(2020, 3, 1, 18, 0, 0))
                .endTime(new DateTime(2020, 3, 1, 20, 0, 0))
                .place(null)
                .availabilityVote(null)
                .remoteWork(true)
                .creationTime(null)
                .modificationTime(null)
                .isActive(true)
                .build();

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

        testVoteDto = AvailabilityVoteDto.builder()
                .poll(testPollDto)
                .member(testMemberDto)
                .availability(testAvailabilityDtoFirst)
                .creationTime(null)
                .isActive(true)
                .build();
    }

    private UserCrudFacade initUserCrudFacade() {
        return new UserCrudFacade(userRepository);
    }

    private MemberCrudFacade initMemberCrudFacade() {
        return new MemberCrudFacade(memberRepository);
    }

    private AvailabilityCrudFacade initAvailabilityCrudFacade() {
        return new AvailabilityCrudFacade(availabilityRepository, memberRepository);
    }

    private GroupCrudFacade initGroupCrudFacade() {
        return new GroupCrudFacade(groupRepository);
    }

    private PollCrudFacade initPollCrudFacade() {
        return new PollCrudFacade(groupRepository, pollCrudRepository);
    }

    private AvailabilityVoteCrudFacade initVoteCrudFacade() {
        return new AvailabilityVoteCrudFacade(
                availabilityVoteRepository,
                pollCrudRepository,
                groupRepository,
                memberRepository,
                availabilityRepository);
    }

    private boolean initTestDB() {
        userCrudFacade = initUserCrudFacade();
        memberCrudFacade = initMemberCrudFacade();
        groupCrudFacade = initGroupCrudFacade();
        availabilityCrudFacade = initAvailabilityCrudFacade();
        pollCrudFacade = initPollCrudFacade();
        voteCrudFacade = initVoteCrudFacade();

        UserEntity userEntity;
        MemberEntity memberEntity;
        AvailabilityEntity availabilityEntity;
        GroupEntity groupEntity;
        PollEntity pollEntity;

        userEntity = userCrudFacade.findEntity(userCrudFacade.create(testUserDto, DefaultUserLoginTypeEnum.EMAIL));

        try {
            memberEntity = memberCrudFacade.findEntity(memberCrudFacade.create(testMemberDto));
        } catch (EntityAlreadyExistsException | EntityNotFoundException e) {
            memberEntity = null;
        }

        try {
            availabilityEntity = availabilityCrudFacade.findEntity(availabilityCrudFacade.create(testAvailabilityDtoFirst));
        } catch (EntityNotFoundException | EntityAlreadyExistsException e) {
            availabilityEntity = null;
        }

        try {
            groupEntity = groupCrudFacade.findEntity(groupCrudFacade.create(testGroupDto));
        } catch (EntityAlreadyExistsException | EntityNotFoundException e) {
            groupEntity = null;
        }

        try {
            pollEntity = pollCrudFacade.findEntity(pollCrudFacade.create(testPollDto));
        } catch (EntityNotFoundException | EntityAlreadyExistsException e) {
            pollEntity = null;
        }

        return userEntity != null
                && memberEntity != null
                && availabilityEntity != null
                && groupEntity != null
                && pollEntity != null;
    }

    @Test
    public void INIT_TEST_DB() {
        boolean initDB = initTestDB();
        assertThat(initDB).isTrue();
    }

    @Test
    public void WHERE_create_not_existing_vote_THEN_return_vote() throws EntityAlreadyExistsException, EntityNotFoundException {
        initTestDB();
        AvailabilityVoteCrudFacade voteCrudFacade = initVoteCrudFacade();
        AvailabilityVoteDto voteEntity = voteCrudFacade.create(testVoteDto);

        assertThat(voteEntity).isNotNull();

        assertThat(voteEntity.getPoll()).isNotNull();
        assertThat(voteEntity.getAvailability()).isNotNull();
        assertThat(voteEntity.getMember()).isNotNull();

        assertThat(voteEntity.getCreationTime()).isNotNull();
        assertThat(voteEntity.isActive()).isTrue();
    }

    @Test
    public void WHERE_try_to_create_already_existing_vote_THEN_return_EntityAlreadyExistsException() {
        initTestDB();
        AvailabilityVoteCrudFacade voteCrudFacade = initVoteCrudFacade();

        try {
            voteCrudFacade.create(testVoteDto);
        } catch (EntityAlreadyExistsException | EntityNotFoundException e) {
            Assert.fail();
        }

        try {
            voteCrudFacade.create(testVoteDto);
            Assert.fail();
        } catch (EntityAlreadyExistsException | EntityNotFoundException e) {
            assertThat(e)
                    .isInstanceOf(EntityAlreadyExistsException.class)
                    .hasMessage(AvailabilityVoteCrudStatusEnum.AVAILABILITY_VOTE_ALREADY_EXISTS.toString());
        }
    }

    @Test
    public void WHERE_found_vote_THEN_return_vote() throws EntityAlreadyExistsException, EntityNotFoundException {
        initTestDB();
        AvailabilityVoteCrudFacade voteCrudFacade = initVoteCrudFacade();
        AvailabilityVoteDto created = voteCrudFacade.create(testVoteDto);
        AvailabilityVoteDto found = voteCrudFacade.read(testVoteDto);

        System.out.println("time in created:\t" + created.getCreationTime() + "\ntime in found:\t\t" + found.getCreationTime());

        assertThat(found).isNotNull();

        assertThat(found).isEqualToComparingFieldByFieldRecursively(created);
    }

    @Test
    public void WHEN_cant_find_vote_THEN_return_EntityNotFoundException() {
        initTestDB();
        AvailabilityVoteCrudFacade voteCrudFacade = initVoteCrudFacade();

        try {
            voteCrudFacade.read(testVoteDto);
        } catch (EntityNotFoundException e) {
            assertThat(e)
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage(AvailabilityVoteCrudStatusEnum.AVAILABILITY_VOTE_NOT_FOUND.toString());
        }
    }

    @Test
    public void readAll() {
    }

    @Test
    public void update() {
        initTestDB();
        AvailabilityVoteCrudFacade voteCrudFacade = initVoteCrudFacade();
        try {
            AvailabilityVoteDto created = voteCrudFacade.create(testVoteDto);
        } catch (EntityNotFoundException | EntityAlreadyExistsException e) {
            Assert.fail();
        }



    }

    private AvailabilityVoteDto updateVote(AvailabilityVoteDto voteDto){
        voteDto.setAvailability(testAvailabilityDtoSecond);
        voteDto.setCreationTime(new DateTime());
        return voteDto;
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