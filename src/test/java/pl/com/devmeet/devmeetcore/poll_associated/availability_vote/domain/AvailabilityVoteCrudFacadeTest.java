package pl.com.devmeet.devmeetcore.poll_associated.availability_vote.domain;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.GroupCrudFacade;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.GroupCrudRepository;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.GroupEntity;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.status_and_exceptions.GroupAlreadyExistsException;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeetcore.member_associated.availability.domain.AvailabilityCrudFacade;
import pl.com.devmeet.devmeetcore.member_associated.availability.domain.AvailabilityCrudRepository;
import pl.com.devmeet.devmeetcore.member_associated.availability.domain.AvailabilityDto;
import pl.com.devmeet.devmeetcore.member_associated.availability.domain.AvailabilityEntity;
import pl.com.devmeet.devmeetcore.member_associated.availability.domain.status_and_exceptions.AvailabilityAlreadyExistsException;
import pl.com.devmeet.devmeetcore.member_associated.availability.domain.status_and_exceptions.AvailabilityNotFoundException;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberEntity;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberRepository;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.status_and_exceptions.MemberAlreadyExistsException;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.domain.MessengerRepository;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.status_and_exceptions.MessengerAlreadyExistsException;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.status_and_exceptions.MessengerArgumentNotSpecified;
import pl.com.devmeet.devmeetcore.poll_associated.availability_vote.domain.status_and_exceptions.*;
import pl.com.devmeet.devmeetcore.poll_associated.poll.domain.PollCrudFacade;
import pl.com.devmeet.devmeetcore.poll_associated.poll.domain.PollCrudRepository;
import pl.com.devmeet.devmeetcore.poll_associated.poll.domain.PollDto;
import pl.com.devmeet.devmeetcore.poll_associated.poll.domain.PollEntity;
import pl.com.devmeet.devmeetcore.poll_associated.poll.domain.status_and_exceptions.PollAlreadyExistsException;
import pl.com.devmeet.devmeetcore.poll_associated.poll.domain.status_and_exceptions.PollNotFoundException;
import pl.com.devmeet.devmeetcore.user.domain.*;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserNotFoundException;

import java.util.List;

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
    @Autowired
    private MessengerRepository messengerRepository;

    private UserCrudFacade userCrudFacade;
    private MemberCrudFacade memberCrudFacade;
    private AvailabilityCrudFacade availabilityCrudFacade;
    private GroupCrudFacade groupCrudFacade;
    private PollCrudFacade pollCrudFacade;
    private AvailabilityVoteCrudFacade voteCrudFacade;

    private UserDto testUserDtoFirst;
    private UserDto testUserDtoSecond;
    private MemberDto testMemberDtoFirst;
    private MemberDto testMemberDtoSecond;
    private AvailabilityDto testAvailabilityDtoFirst;
    private AvailabilityDto testAvailabilityDtoSecond;
    private GroupDto testGroupDto;
    private PollDto testPollDto;
    private AvailabilityVoteDto testVoteDto;

    @Before
    public void setUp() throws Exception {

        testUserDtoFirst = UserDto.builder()
                .email("user1@test.pl")
                .password("testPass")
                .isActive(true)
                .build();

        testUserDtoSecond = UserDto.builder()
                .email("1resu@test.pl")
                .password("passTest")
                .isActive(true)
                .build();

        testMemberDtoFirst = MemberDto.builder()
                .user(testUserDtoFirst)
                .nick("Wasacz")
                .build();

        testMemberDtoSecond = MemberDto.builder()
                .user(testUserDtoSecond)
                .nick("Pieniacz")
                .build();

        testAvailabilityDtoFirst = AvailabilityDto.builder()
                .member(testMemberDtoFirst)
                .beginTime(new DateTime(2020, 3, 3, 15, 0, 0))
                .endTime(new DateTime(2020, 3, 3, 16, 0, 0))
                .availabilityVote(null)
                .remoteWork(true)
                .creationTime(null)
                .modificationTime(null)
                .isActive(true)
                .build();

        testAvailabilityDtoSecond = AvailabilityDto.builder()
                .member(testMemberDtoFirst)
                .beginTime(new DateTime(2020, 2, 2, 17, 0, 0))
                .endTime(new DateTime(2020, 2, 2, 18, 0, 0))
                .availabilityVote(null)
                .remoteWork(false)
                .creationTime(null)
                .modificationTime(null)
                .isActive(true)
                .build();

        testAvailabilityDtoSecond = AvailabilityDto.builder()
                .member(testMemberDtoSecond)
                .beginTime(new DateTime(2020, 3, 1, 18, 0, 0))
                .endTime(new DateTime(2020, 3, 1, 20, 0, 0))
                .availabilityVote(null)
                .remoteWork(true)
                .creationTime(null)
                .modificationTime(null)
                .isActive(true)
                .build();

        testGroupDto = GroupDto.builder()
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

        testPollDto = PollDto.builder()
                .group(testGroupDto)
                .placeVotes(null)
                .availabilityVotes(null)
                .creationTime(null)
                .active(true)
                .build();

        testVoteDto = AvailabilityVoteDto.builder()
                .poll(testPollDto)
                .member(testMemberDtoFirst)
                .availability(testAvailabilityDtoFirst)
                .creationTime(null)
                .isActive(true)
                .build();
    }

    private UserCrudFacade initUserCrudFacade() {
        return new UserCrudFacade(userRepository);
    }

    private MemberCrudFacade initMemberCrudFacade() {
        return new MemberCrudFacade(memberRepository, userRepository, messengerRepository, groupRepository);
    }

    private AvailabilityCrudFacade initAvailabilityCrudFacade() {
        return new AvailabilityCrudFacade(availabilityRepository, memberRepository, userRepository, messengerRepository, groupRepository);
    }

    private GroupCrudFacade initGroupCrudFacade() {
        return new GroupCrudFacade(groupRepository, memberRepository, userRepository, messengerRepository);
    }

    private PollCrudFacade initPollCrudFacade() {
        return new PollCrudFacade(pollCrudRepository, groupRepository, memberRepository, userRepository, messengerRepository);
    }

    private AvailabilityVoteCrudFacade initVoteCrudFacade() {
        return new AvailabilityVoteCrudFacade(
                availabilityVoteRepository,
                pollCrudRepository,
                groupRepository,
                availabilityRepository,
                memberRepository,
                userRepository,
                messengerRepository);
    }

    private boolean initTestDB() {
        userCrudFacade = initUserCrudFacade();
        memberCrudFacade = initMemberCrudFacade();
        groupCrudFacade = initGroupCrudFacade();
        availabilityCrudFacade = initAvailabilityCrudFacade();
        pollCrudFacade = initPollCrudFacade();
        voteCrudFacade = initVoteCrudFacade();

        UserEntity userEntityFirst = userCrudFacade.findEntityByEmail(userCrudFacade.create(testUserDtoFirst, DefaultUserLoginTypeEnum.EMAIL));
        UserEntity userEntitySecond = userCrudFacade.findEntityByEmail(userCrudFacade.create(testUserDtoSecond, DefaultUserLoginTypeEnum.EMAIL));

        MemberEntity memberEntityFirst = null;
        try {
            memberEntityFirst = memberCrudFacade.findEntity(memberCrudFacade.add(testMemberDtoFirst));
        } catch (MemberNotFoundException | MemberAlreadyExistsException | UserNotFoundException | GroupNotFoundException | MessengerAlreadyExistsException | MessengerArgumentNotSpecified e) {
            e.printStackTrace();
        }

        MemberEntity memberEntitySecond = null;
        try {
            memberEntitySecond = memberCrudFacade.findEntity(memberCrudFacade.add(testMemberDtoSecond));
        } catch (MemberNotFoundException | MemberAlreadyExistsException | UserNotFoundException | GroupNotFoundException | MessengerAlreadyExistsException | MessengerArgumentNotSpecified e) {
            e.printStackTrace();
        }

        AvailabilityEntity availabilityEntityFirst = null;
        try {
            availabilityEntityFirst = availabilityCrudFacade.findEntity(availabilityCrudFacade.add(testAvailabilityDtoFirst));
        } catch (MemberNotFoundException | AvailabilityAlreadyExistsException | UserNotFoundException | AvailabilityNotFoundException e) {
            e.printStackTrace();
        }

        AvailabilityEntity availabilityEntitySecond = null;
        try {
            availabilityEntitySecond = availabilityCrudFacade.findEntity(availabilityCrudFacade.add(testAvailabilityDtoSecond));
        } catch (MemberNotFoundException | AvailabilityAlreadyExistsException | UserNotFoundException | AvailabilityNotFoundException e) {
            e.printStackTrace();
        }

        GroupEntity groupEntity = null;
        try {
            groupEntity = groupCrudFacade.findEntityByGroup(groupCrudFacade.add(testGroupDto));
        } catch (GroupNotFoundException | GroupAlreadyExistsException | UserNotFoundException | MemberNotFoundException | MessengerAlreadyExistsException | MessengerArgumentNotSpecified e) {
            e.printStackTrace();
        }

        PollEntity pollEntity = null;
        try {
            pollEntity = pollCrudFacade.findEntity(pollCrudFacade.add(testPollDto));
        } catch (GroupNotFoundException | PollAlreadyExistsException | PollNotFoundException e) {
            e.printStackTrace();
        }

        return userEntityFirst != null
                && userEntitySecond != null
                && memberEntityFirst != null
                && memberEntitySecond != null
                && availabilityEntityFirst != null
                && availabilityEntitySecond != null
                && groupEntity != null
                && pollEntity != null;
    }

    @Test
    public void INIT_TEST_DB() {
        boolean initDB = initTestDB();
        assertThat(initDB).isTrue();
    }

    @Test
    public void WHERE_create_not_existing_vote_THEN_return_vote() throws UserNotFoundException, AvailabilityNotFoundException, GroupNotFoundException, AvailabilityVoteAlreadyExistsException, MemberNotFoundException, PollNotFoundException {
        initTestDB();
        AvailabilityVoteCrudFacade voteCrudFacade = initVoteCrudFacade();
        AvailabilityVoteDto voteEntity = voteCrudFacade.add(testVoteDto);

        assertThat(voteEntity).isNotNull();

        assertThat(voteEntity.getPoll()).isNotNull();
        assertThat(voteEntity.getAvailability()).isNotNull();
        assertThat(voteEntity.getMember()).isNotNull();

        assertThat(voteEntity.getCreationTime()).isNotNull();
        assertThat(voteEntity.isActive()).isTrue();
    }

    @Test
    public void WHERE_try_to_create_already_existing_vote_THEN_return_EntityAlreadyExistsException() throws AvailabilityNotFoundException, MemberNotFoundException, GroupNotFoundException, UserNotFoundException, PollNotFoundException {
        initTestDB();
        AvailabilityVoteCrudFacade voteCrudFacade = initVoteCrudFacade();

        try {
            voteCrudFacade.add(testVoteDto);
        } catch (AvailabilityNotFoundException | GroupNotFoundException | AvailabilityVoteAlreadyExistsException | PollNotFoundException | MemberNotFoundException | UserNotFoundException e) {
            Assert.fail();
        }

        try {
            voteCrudFacade.add(testVoteDto);
            Assert.fail();
        } catch (AvailabilityVoteAlreadyExistsException e) {
            assertThat(e)
                    .isInstanceOf(AvailabilityVoteAlreadyExistsException.class)
                    .hasMessage(AvailabilityVoteCrudStatusEnum.AVAILABILITY_VOTE_ALREADY_EXISTS.toString());
        }
    }

    @Test
    public void WHERE_found_vote_THEN_return_vote() throws UserNotFoundException, AvailabilityNotFoundException, GroupNotFoundException, AvailabilityVoteAlreadyExistsException, MemberNotFoundException, PollNotFoundException, AvailabilityVoteNotFoundException {
        initTestDB();
        AvailabilityVoteCrudFacade voteCrudFacade = initVoteCrudFacade();
        AvailabilityVoteDto created = voteCrudFacade.add(testVoteDto);
        AvailabilityVoteDto found = voteCrudFacade.find(testVoteDto);

        System.out.println("time in created:\t" + created.getCreationTime() + "\ntime in found:\t\t" + found.getCreationTime());

        assertThat(found).isNotNull();

        assertThat(found).isEqualToComparingFieldByFieldRecursively(created);
    }

    @Test
    public void WHEN_cant_find_vote_THEN_return_EntityNotFoundException() throws MemberNotFoundException, UserNotFoundException, GroupNotFoundException, PollNotFoundException {
        initTestDB();
        AvailabilityVoteCrudFacade voteCrudFacade = initVoteCrudFacade();

        try {
            voteCrudFacade.find(testVoteDto);
        } catch (AvailabilityVoteNotFoundException e) {
            assertThat(e)
                    .isInstanceOf(AvailabilityVoteNotFoundException.class)
                    .hasMessage(AvailabilityVoteCrudStatusEnum.AVAILABILITY_VOTE_NOT_FOUND.toString());
        }
    }

    @Test
    public void WHEN_try_to_read_all_votes_in_the_poll_THEN_return_votes() {
        initTestDB();
        AvailabilityVoteCrudFacade voteCrudFacade = initVoteCrudFacade();
        AvailabilityVoteDto createdVote = null;
        List<AvailabilityVoteDto> foundVotes = null;

        try {
            createdVote = voteCrudFacade.add(testVoteDto);
        } catch (AvailabilityNotFoundException | GroupNotFoundException | AvailabilityVoteAlreadyExistsException | PollNotFoundException | MemberNotFoundException | UserNotFoundException e) {
            Assert.fail();
        }

        try {
            foundVotes = voteCrudFacade.findAll(testVoteDto);
        } catch (GroupNotFoundException | AvailabilityVoteNotFoundException | PollNotFoundException e) {
            Assert.fail();
        }

        assertThat(foundVotes.get(0)).isEqualToComparingFieldByFieldRecursively(createdVote);
    }

    @Test
    public void WHEN_try_to_read_all_votes_in_the_poll_if_there_are_none_THEN_return_AvailabilityVoteNotFoundException() throws GroupNotFoundException, PollNotFoundException {
        initTestDB();
        AvailabilityVoteCrudFacade voteCrudFacade = initVoteCrudFacade();

        try {
            voteCrudFacade.findAll(testVoteDto);
        } catch (AvailabilityVoteNotFoundException e) {
            assertThat(e)
                    .isInstanceOf(AvailabilityVoteNotFoundException.class)
                    .hasMessage(AvailabilityVoteCrudStatusEnum.AVAILABILITY_VOTES_NOT_FOUND.toString());
        }
    }

    @Test
    public void WHEN_update_exist_vote_THEN_return_vote() throws AvailabilityVoteNotFoundException, GroupNotFoundException, UserNotFoundException, AvailabilityVoteException, MemberNotFoundException, AvailabilityNotFoundException, PollNotFoundException {
        initTestDB();
        AvailabilityVoteCrudFacade voteCrudFacade = initVoteCrudFacade();
        AvailabilityVoteDto created = null;
        AvailabilityVoteDto fakeUpdatedDto = updateVote(testVoteDto);
        AvailabilityVoteDto updated;

        try {
            created = voteCrudFacade.add(testVoteDto);
        } catch (AvailabilityNotFoundException | GroupNotFoundException | AvailabilityVoteAlreadyExistsException | PollNotFoundException | MemberNotFoundException | UserNotFoundException e) {
            Assert.fail();
        }

        updated = voteCrudFacade.update(testVoteDto, fakeUpdatedDto);

        assertThat(updated.getMember()).isEqualToComparingFieldByFieldRecursively(created.getMember());
        assertThat(updated.getCreationTime()).isEqualTo(created.getCreationTime());

        assertThat(updated.getAvailability().getMember().getUser().getEmail()).isEqualTo(fakeUpdatedDto.getAvailability().getMember().getUser().getEmail());
        assertThat(updated.getAvailability().getBeginTime()).isEqualTo(fakeUpdatedDto.getAvailability().getBeginTime());
        assertThat(updated.getAvailability().getEndTime()).isEqualTo(fakeUpdatedDto.getAvailability().getEndTime());
        assertThat(updated.getAvailability().isActive()).isEqualTo(fakeUpdatedDto.getAvailability().isActive());
        assertThat(updated.getAvailability().isRemoteWork()).isEqualTo(fakeUpdatedDto.getAvailability().isRemoteWork());
    }

    private AvailabilityVoteDto updateVote(AvailabilityVoteDto voteDto) {
        voteDto.setAvailability(testAvailabilityDtoSecond);
        voteDto.setCreationTime(new DateTime());
        return voteDto;
    }

    @Test
    public void WHEN_delete_vote_THEN_return_vote() throws UserNotFoundException, AvailabilityNotFoundException, GroupNotFoundException, AvailabilityVoteAlreadyExistsException, MemberNotFoundException, PollNotFoundException, AvailabilityVoteFoundButNotActiveException, AvailabilityVoteNotFoundException {
        initTestDB();
        AvailabilityVoteCrudFacade voteCrudFacade = initVoteCrudFacade();
        AvailabilityVoteDto created = voteCrudFacade.add(testVoteDto);
        AvailabilityVoteDto deleted = voteCrudFacade.delete(testVoteDto);

        assertThat(deleted.isActive()).isNotEqualTo(created.isActive());
    }
}