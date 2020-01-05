package pl.com.devmeet.devmeetcore.member_associated.availability.domain;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.GroupCrudRepository;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeetcore.member_associated.availability.domain.status_and_exceptions.AvailabilityAlreadyExistsException;
import pl.com.devmeet.devmeetcore.member_associated.availability.domain.status_and_exceptions.AvailabilityCrudInfoStatusEnum;
import pl.com.devmeet.devmeetcore.member_associated.availability.domain.status_and_exceptions.AvailabilityException;
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
import pl.com.devmeet.devmeetcore.user.domain.*;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserNotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)

public class AvailabilityCrudFacadeTest {

    @Autowired
    private AvailabilityCrudRepository repository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessengerRepository messengerRepository;
    @Autowired
    private GroupCrudRepository groupCrudRepository;

    private AvailabilityCrudFacade availabilityCrudFacade;
    private MemberCrudFacade memberCrudFacade;
    private UserCrudFacade userCrudFacade;

    private AvailabilityDto testAvailabilityDto;
    private MemberDto testMemberDto;
    private UserDto testUserDto;
    //   private PlaceDto testPlaceDto;

    @Before
    public void setUp() {

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
                .availabilityVote(null)
                .remoteWork(true)
                .creationTime(null)
                .modificationTime(null)
                .isActive(true)
                .build();
    }

    private UserCrudFacade initUserCrudFacade() {
        return new UserCrudFacade(userRepository);
    }

    private MemberCrudFacade initMemberCrudFacade() {
        return new MemberCrudFacade(memberRepository, userRepository, messengerRepository, groupCrudRepository); // tworzy obiekt fasady
    }

    private AvailabilityCrudFacade initAvailabilityCrudFacade() {
        return new AvailabilityCrudFacade(repository, memberRepository, userRepository, messengerRepository, groupCrudRepository);
    }


    private boolean initTestDB() {
        userCrudFacade = initUserCrudFacade();
        memberCrudFacade = initMemberCrudFacade();

        UserEntity testUser = userCrudFacade
                .findEntityByEmail(userCrudFacade.create(testUserDto, DefaultUserLoginTypeEnum.PHONE));

        MemberEntity memberEntity = null;
        try {
            memberEntity = memberCrudFacade
                    .findEntity(memberCrudFacade.add(testMemberDto));
        } catch (MemberNotFoundException | MemberAlreadyExistsException | UserNotFoundException | GroupNotFoundException | MessengerAlreadyExistsException | MessengerArgumentNotSpecified e) {
            e.printStackTrace();
        }

        return testUser != null
                && memberEntity != null;
    }

    @Test
    public void USER_CRUD_FACADE_WR() {
        UserCrudFacade userCrudFacade = initUserCrudFacade();
        UserDto testUser = userCrudFacade.create(testUserDto, DefaultUserLoginTypeEnum.PHONE);
        UserEntity userEntity = userCrudFacade.findEntityByEmail(testUser);
        assertThat(userEntity).isNotNull();
    }

    @Test
    public void MEMBER_CRUD_FACADE_WR() throws UserNotFoundException, MemberAlreadyExistsException, MemberNotFoundException, GroupNotFoundException, MessengerArgumentNotSpecified, MessengerAlreadyExistsException {
        MemberCrudFacade memberCrudFacade = initMemberCrudFacade();
        initUserCrudFacade().create(testUserDto, DefaultUserLoginTypeEnum.EMAIL);
        MemberEntity memberEntity = memberCrudFacade.findEntity(memberCrudFacade.add(testMemberDto));
        assertThat(memberEntity).isNotNull();
    }

    @Test
    public void INIT_TEST_DB() {
        boolean initDB = initTestDB();
        assertThat(initDB).isTrue();
    }

    @Test
    public void WHEN_try_to_create_non_existing_availability_THEN_return_availability() throws MemberNotFoundException, AvailabilityAlreadyExistsException, UserNotFoundException {
        initTestDB();
        availabilityCrudFacade = initAvailabilityCrudFacade();
        AvailabilityDto created = availabilityCrudFacade.add(testAvailabilityDto);

        assertThat(created.getMember()).isNotNull();
        assertThat(created).isNotNull();
        assertThat(created.getCreationTime()).isNotNull();
        assertThat(created.getModificationTime()).isNull();
        assertThat(created.isActive()).isTrue();
    }

    @Test
    public void WHEN_try_to_create_existing_availability_THEN_EntityAlreadyExistsException() throws MemberNotFoundException, UserNotFoundException {
        initTestDB();
        availabilityCrudFacade = initAvailabilityCrudFacade();
        try {
            availabilityCrudFacade.add(testAvailabilityDto);
        } catch (AvailabilityAlreadyExistsException e) {
            Assert.fail();
        }
        try {
            availabilityCrudFacade.add(testAvailabilityDto);
            Assert.fail();
        } catch (AvailabilityAlreadyExistsException e) {
            assertThat(e)
                    .isInstanceOf(AvailabilityAlreadyExistsException.class)
                    .hasMessage(AvailabilityCrudInfoStatusEnum.AVAILABILITY_ALREADY_EXISTS.toString());
        }
    }

    @Test
    public void WHEN_found_availability_THEN_return_availability() throws MemberNotFoundException, AvailabilityAlreadyExistsException, UserNotFoundException, AvailabilityNotFoundException {
        initTestDB();
        AvailabilityDto created;
        AvailabilityDto found = null;
        availabilityCrudFacade = initAvailabilityCrudFacade();

        created = availabilityCrudFacade.add(testAvailabilityDto);
        found = availabilityCrudFacade.find(testAvailabilityDto);
        assertThat(found).isNotNull();
    }

    @Test
    public void WHEN_try_to_find_non_existing_availability_THEN_return_EntityNotFoundException() throws MemberNotFoundException, UserNotFoundException {
        initTestDB();
        availabilityCrudFacade = initAvailabilityCrudFacade();
        try {
            availabilityCrudFacade.find(testAvailabilityDto);
            Assert.fail();
        } catch (AvailabilityNotFoundException e) {
            assertThat(e)
                    .isInstanceOf(AvailabilityNotFoundException.class)
                    .hasMessage(AvailabilityCrudInfoStatusEnum.AVAILABILITY_NOT_FOUND.toString());
        }
    }

    @Test
    public void WHEN_try_to_find_all_availabilities_THEN_return_availabilities() throws MemberNotFoundException, AvailabilityAlreadyExistsException, UserNotFoundException, AvailabilityNotFoundException {
        initTestDB();
        List<AvailabilityDto> found = null;
        AvailabilityCrudFacade availabilityCrudFacade = initAvailabilityCrudFacade();
        availabilityCrudFacade.add(testAvailabilityDto);
        found = availabilityCrudFacade.findAll(testAvailabilityDto);
        assertThat(found).isNotNull();
    }

    @Test
    public void WHEN_try_to_update_existing_availability_THEN_return_availability() throws MemberNotFoundException, AvailabilityAlreadyExistsException, UserNotFoundException, AvailabilityNotFoundException, AvailabilityException {
        initTestDB();
        AvailabilityCrudFacade availabilityCrudFacade = initAvailabilityCrudFacade();
        AvailabilityDto created = availabilityCrudFacade.add(testAvailabilityDto);
        AvailabilityDto updated = availabilityCrudFacade.update(testAvailabilityDto, availabilityUpdatedValues(testAvailabilityDto));


        assertThat(updated.isRemoteWork()).isFalse();

        assertThat(updated.getMember()).isEqualToComparingFieldByField(created.getMember());
        assertThat(updated.getBeginTime()).isEqualTo(created.getBeginTime());
        assertThat(updated.getEndTime()).isEqualTo(created.getEndTime());
        assertThat(updated.getAvailabilityVote()).isEqualTo(created.getAvailabilityVote());
        assertThat(updated.getCreationTime()).isEqualTo(created.getCreationTime());
        assertThat(updated.isActive()).isEqualTo(created.isActive());
        //    assertThat(updated.getModificationTime()).isNotEqualTo(created.getModificationTime());
    }

    private AvailabilityDto availabilityUpdatedValues(AvailabilityDto testAvailabilityDto) {
        testAvailabilityDto.setRemoteWork(false);
        return testAvailabilityDto;
    }

    @Test
    public void WHEN_try_to_update_non_existing_availability_THEN_return_EntityNotFoundException() throws UserNotFoundException, MemberNotFoundException, AvailabilityException {
        initTestDB();
        AvailabilityCrudFacade availabilityCrudFacade = initAvailabilityCrudFacade();
        try {
            availabilityCrudFacade.update(testAvailabilityDto, availabilityUpdatedValues(testAvailabilityDto));
        } catch (AvailabilityNotFoundException e) {
            assertThat(e)
                    .isInstanceOf(AvailabilityNotFoundException.class)
                    .hasMessage(AvailabilityCrudInfoStatusEnum.AVAILABILITY_NOT_FOUND.toString());
        }
    }

    @Test
    public void WHEN_delete_existing_availability_THEN_return_availability() throws MemberNotFoundException, AvailabilityAlreadyExistsException, UserNotFoundException, AvailabilityNotFoundException {
        initTestDB();
        AvailabilityCrudFacade availabilityCrudFacade = initAvailabilityCrudFacade();
        AvailabilityDto created = availabilityCrudFacade.add(testAvailabilityDto);
        AvailabilityDto deleted = availabilityCrudFacade.delete(testAvailabilityDto);

        assertThat(deleted).isNotNull();
        assertThat(deleted.isActive()).isNotEqualTo(created.isActive());
        assertThat(deleted.getCreationTime()).isEqualTo(created.getCreationTime());
        assertThat(deleted.getModificationTime()).isNotNull();
    }

    @Test
    public void WHEN_try_to_delete_non_existing_availability_THEN_return_EntityNotFoundException() throws UserNotFoundException, AvailabilityAlreadyExistsException, MemberNotFoundException {
        initTestDB();
        AvailabilityCrudFacade availabilityCrudFacade = initAvailabilityCrudFacade();

        try {
            availabilityCrudFacade.delete(testAvailabilityDto);
        } catch (AvailabilityNotFoundException e) {
            assertThat(e)
                    .isInstanceOf(AvailabilityNotFoundException.class)
                    .hasMessage(AvailabilityCrudInfoStatusEnum.AVAILABILITY_NOT_FOUND.toString());
        }
    }
}