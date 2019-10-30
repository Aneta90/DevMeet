package pl.com.devmeet.devmeet.poll_associated.availability_vote.domain;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
import pl.com.devmeet.devmeet.poll_associated.poll.domain.PollCrudFacade;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.PollCrudRepository;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.PollDto;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.PollEntity;
import pl.com.devmeet.devmeet.user.domain.UserDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class AvailabilityVoteCrudFacadeTest {

    @Autowired
    private AvailabilityVoteCrudRepository availabilityVoteRepository;
    @Autowired
    private PollCrudRepository pollCrudRepository;
    @Autowired
    private GroupCrudRepository groupRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AvailabilityCrudRepository availabilityRepository;

    private AvailabilityVoteCrudFacade voteCrudFacade;
    private PollCrudFacade pollCrudFacade;
    private GroupCrudFacade groupCrudFacade;
    private MemberCrudFacade memberCrudFacade;
    private AvailabilityCrudFacade availabilityCrudFacade;

    private UserDto testUserDto;
    private MemberDto testMemberDto;
    private AvailabilityDto testAvailabilityDto;
    private GroupDto testGroupDto;
    private PollDto testPollDto;
    private AvailabilityVoteDto testVoteDto;

    public AvailabilityVoteCrudFacadeTest() {
    }

    @Before
    public void setUp() throws Exception {

        testUserDto = new UserDto().builder()
                .email("mailik@gmail.com")
                .phone("567566456")
                .password("multiPass")
                .isActive(true)
                .loggedIn(true)
                .build();

        testMemberDto = new MemberDto().builder()
                .user(testUserDto)
                .nick("Wasacz")
                .build();

        testAvailabilityDto = new AvailabilityDto().builder()
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
                .availability(testAvailabilityDto)
                .creationTime(null)
                .isActive(true)
                .build();
    }

    private AvailabilityVoteCrudFacade initVoteCrudFacade() {
        return new AvailabilityVoteCrudFacade(
                availabilityVoteRepository,
                pollCrudRepository,
                groupRepository,
                memberRepository,
                availabilityRepository);
    }

    private PollCrudFacade initPollCrudFacade() {
        return new PollCrudFacade(groupRepository, pollCrudRepository);
    }

    private GroupCrudFacade initGroupCrudFacade() {
        return new GroupCrudFacade(groupRepository);
    }

    private MemberCrudFacade initMemberCrudFacade() {
        return new MemberCrudFacade(memberRepository);
    }

    private AvailabilityCrudFacade initAvailabilityCrudFacade() {
        return new AvailabilityCrudFacade(availabilityRepository, memberRepository);
    }

    private boolean initTestDB() {
        voteCrudFacade = initVoteCrudFacade();
        pollCrudFacade = initPollCrudFacade();
        groupCrudFacade = initGroupCrudFacade();
        memberCrudFacade = initMemberCrudFacade();
        availabilityCrudFacade = initAvailabilityCrudFacade();

        MemberEntity memberEntity;
        AvailabilityEntity availabilityEntity;
        GroupEntity groupEntity;
        PollEntity pollEntity;
        AvailabilityVoteEntity voteEntity;

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