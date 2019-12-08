package pl.com.devmeet.devmeet.group_associated.meeting.domain;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudFacade;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudRepository;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupAlreadyExistsException;
import pl.com.devmeet.devmeet.group_associated.meeting.domain.status_and_exceptions.MeetingAlreadyExistsException;
import pl.com.devmeet.devmeet.group_associated.meeting.domain.status_and_exceptions.MeetingNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberRepository;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.member_associated.place.domain.PlaceCrudFacade;
import pl.com.devmeet.devmeet.member_associated.place.domain.PlaceCrudRepository;
import pl.com.devmeet.devmeet.member_associated.place.domain.PlaceDto;
import pl.com.devmeet.devmeet.member_associated.place.domain.status_and_exceptions.PlaceAlreadyExistsException;
import pl.com.devmeet.devmeet.user.domain.UserRepository;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
public class MeetingCrudFacadeTest {

    @Autowired
    private GroupCrudRepository groupCrudRepository;

    @Autowired
    private MeetingCrudRepository meetingCrudRepository;

    @Autowired
    private PlaceCrudRepository placeCrudRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    UserRepository userRepository;

    private GroupCrudFacade groupCrudFacade;
    private MeetingCrudFacade meetingCrudFacade;
    private PlaceCrudFacade placeCrudFacade;

    private MeetingDto meetingDto;
    private MeetingDto secondMeeting;
    private GroupDto groupDto;
    private PlaceDto placeDto;

    List<MeetingDto> meetingDtosList = new ArrayList<>();

    @Before
    public void setUp() {

        groupDto = new GroupDto().builder()
                .groupName("Dancing group")
                .description("We dance only salsa")
                .meetings(meetingDtosList)
                .isActive(true)
                .build();

        placeDto = new PlaceDto().builder()
                .placeName("Warsaw City")
                .build();

        meetingDto = new MeetingDto().builder()
                .meetingNumber(1)
                .beginTime(DateTime.now())
                .endTime(DateTime.now().plusHours(2))
                .group(groupDto)
                .isActive(true)
                .place(placeDto)
                .build();

        secondMeeting = new MeetingDto().builder()
                .meetingNumber(2)
                .beginTime(DateTime.now())
                .endTime(DateTime.now().plusHours(2))
                .group(groupDto)
                .isActive(true)
                .place(placeDto)
                .build();

        groupCrudFacade = initGroupCruFacade();
        placeCrudFacade = initPlaceCrudFacade();
        meetingCrudFacade = initMeetingCrudFacade();

    }

    private MeetingDto createMeeting() throws MeetingAlreadyExistsException {
        return initMeetingCrudFacade().add(meetingDto);
    }

    private MeetingDto createSecondMeeting() throws MeetingAlreadyExistsException {
        return initMeetingCrudFacade().add(secondMeeting);
    }

    private GroupDto createGroup() throws GroupAlreadyExistsException {
        return initGroupCruFacade().add(groupDto);
    }

    private PlaceDto createPlace() throws PlaceAlreadyExistsException, MemberNotFoundException, UserNotFoundException {
        return initPlaceCrudFacade().add(placeDto);
    }


    private GroupCrudFacade initGroupCruFacade() {
        return new GroupCrudFacade(groupCrudRepository, memberRepository, userRepository);
    }

    private PlaceCrudFacade initPlaceCrudFacade() {
        return new PlaceCrudFacade(placeCrudRepository, memberRepository, userRepository);
    }

    private MeetingCrudFacade initMeetingCrudFacade() {
        return new MeetingCrudFacade(meetingCrudRepository);
    }

    @Test
    public void when_create_non_existing_meeting_then_create_meeting() throws MeetingAlreadyExistsException {

        MeetingDto createdMeetingDto = createMeeting();
        assertThat(createdMeetingDto).isNotNull();
        assertThat(createdMeetingDto.getGroup().getGroupName()).isEqualTo("Dancing group");
        assertThat(createdMeetingDto.getMeetingNumber()).isEqualTo(1);
    }

    @Test
    public void when_create_existing_meeting_then_throw_an_exception() {

        MeetingDto existingMeetingDto = null;

        try {
            existingMeetingDto = createMeeting();
        } catch (MeetingAlreadyExistsException e) {
            Assert.fail();
        }

        try {
            meetingCrudFacade.add(existingMeetingDto);
            Assert.fail();
        } catch (MeetingAlreadyExistsException e) {
            assertThat(e)
                    .hasMessage("Meeting already exists");
        }
    }

    @Test
    public void when_try_to_read_existing_meeting_then_read() throws MeetingNotFoundException, MeetingAlreadyExistsException {
        MeetingDto createdMeetingDto = createMeeting();
        MeetingDto foundMeeting = meetingCrudFacade.find(createdMeetingDto);
        assertThat(foundMeeting).isNotNull();
        assertThat(foundMeeting.getGroup().getGroupName()).isEqualTo("Dancing group");
        assertThat(foundMeeting.getMeetingNumber()).isEqualTo(1);
    }

    @Test
    public void when_try_to_readAll_meetings_for_given_group_then_readAll() throws MeetingNotFoundException, GroupAlreadyExistsException, MeetingAlreadyExistsException {

        GroupDto groupDto = createGroup();
        MeetingDto createdMeetingDto = createMeeting();
        MeetingDto createdSecondDto = createSecondMeeting();

        meetingDtosList.add(createdMeetingDto);
        meetingDtosList.add(createdSecondDto);

        List<MeetingDto> meetingEntityList = meetingCrudFacade.readAll(groupDto);
        assertThat(meetingEntityList).isNotNull();
        assertThat(meetingEntityList.size()).isEqualTo(2);
        assertThat(meetingEntityList.get(0).getMeetingNumber()).isEqualTo(1);
        assertThat(meetingEntityList.get(1).getMeetingNumber()).isEqualTo(2);
    }

    @Test
    public void when_try_to_update_existing_meeting_then_update() throws MeetingNotFoundException, MeetingAlreadyExistsException {

        MeetingDto oldMeetingDto = createMeeting();

        MeetingDto newDto = new MeetingDto().builder()
                .beginTime(DateTime.now())
                .endTime(DateTime.now().plusHours(4))
                .group(groupDto)
                .isActive(true)
                .place(placeDto)
                .build();

        MeetingDto updatedMeetingDto = meetingCrudFacade.update(oldMeetingDto, newDto);
        assertThat(updatedMeetingDto.getMeetingNumber()).isEqualTo(1);
        assertThat(updatedMeetingDto.getEndTime().toLocalDate()).isEqualTo(DateTime.now().plusHours(4).toLocalDate());
    }

    @Test
    public void when_try_to_update_existing_meeting_with_meeting_number_of_other_entity_then_update_with_previous_meeting_number() throws MeetingNotFoundException, MeetingAlreadyExistsException { //nie może być dwóch meetingów o tym samym numerze

        MeetingDto oldMeetingDto = createMeeting();

        MeetingDto newDto = new MeetingDto().builder()
                .meetingNumber(2)
                .beginTime(DateTime.now())
                .endTime(DateTime.now().plusHours(4))
                .group(groupDto)
                .isActive(true)
                .place(placeDto)
                .build();

        MeetingDto updatedMeetingDto = meetingCrudFacade.update(oldMeetingDto, newDto);
        assertThat(updatedMeetingDto.getMeetingNumber()).isEqualTo(1);
        assertThat(updatedMeetingDto.getEndTime().toLocalDate()).isEqualTo(DateTime.now().plusHours(4).toLocalDate());
    }

    @Test
    public void when_try_to_delete_existing_meeting_then_delete_meeting() throws MeetingAlreadyExistsException, MeetingNotFoundException {

        MeetingDto deletedMeeting = createMeeting();
        MeetingDto meetingDto = meetingCrudFacade.delete(deletedMeeting);
        assertThat(meetingDto.isActive()).isFalse();
        assertThat(meetingDto.getEndTime()).isNull();
        assertThat(meetingDto.getBeginTime()).isNull();

    }

    @Test
    public void when_try_to_delete_non_existing_meeting_then_throw_an_exception() {

        try {
            meetingCrudFacade.delete(meetingDto);
            Assert.fail();
        } catch (MeetingNotFoundException e) {
            assertThat(e)
                    .hasMessage("Meeting is not found in our database");
        }
    }

    @Test
    public void findEntity() throws MeetingAlreadyExistsException, MeetingNotFoundException {
        MeetingDto meetingDto = createMeeting();
        MeetingEntity foundMeeting = meetingCrudFacade.findEntity(meetingDto);
        assertThat(foundMeeting).isNotNull();
        assertThat(foundMeeting.getMeetingNumber()).isEqualTo(1);
    }

    @Test
    public void findEntities() throws MeetingNotFoundException, GroupAlreadyExistsException, MeetingAlreadyExistsException {
        GroupDto groupDto = createGroup();
        MeetingDto createdMeetingDto = createMeeting();
        MeetingDto createdSecondDto = createSecondMeeting();

        meetingDtosList.add(createdMeetingDto);
        meetingDtosList.add(createdSecondDto);
        List<MeetingEntity> meetingEntityList = meetingCrudFacade.findEntities(groupDto);
        assertThat(meetingEntityList).isNotNull();
        assertThat(meetingEntityList.size()).isEqualTo(2);
        assertThat(meetingEntityList.get(0).getMeetingNumber()).isEqualTo(1);
        assertThat(meetingEntityList.get(1).getMeetingNumber()).isEqualTo(2);
    }

    @Test
    public void WHEN_meeting_exists_THEN_return_true() throws MeetingAlreadyExistsException {

        MeetingDto meetingDto = createMeeting();

        boolean memberExists = meetingCrudFacade.isExist(meetingDto);
        assertThat(memberExists).isTrue();
    }

    @Test
    public void WHEN_member_does_not_exist_THEN_return_false() {
        boolean memberDoesNotExist = meetingCrudFacade.isExist(meetingDto);
        assertThat(memberDoesNotExist).isFalse();
    }
}