package pl.com.devmeet.devmeet.member_associated.availability.domain;

import org.joda.time.DateTime;
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
import pl.com.devmeet.devmeet.member_associated.availability.domain.status_and_exceptions.AvailabilityCrudInfoStatusEnum;
import pl.com.devmeet.devmeet.member_associated.member.domain.*;
import pl.com.devmeet.devmeet.member_associated.place.domain.PlaceDto;
import pl.com.devmeet.devmeet.user.domain.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


import static org.joda.time.DateTime.now;

@DataJpaTest
@RunWith(SpringRunner.class)

public class AvailabilityCrudFacadeTest {


    @Autowired
    private AvailabilityCrudRepository repository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private UserRepository userRepository;

    private AvailabilityCrudFacade availabilityCrudFacade;
    private MemberCrudFacade memberCrudFacade;
    private UserCrudFacade userCrudFacade;

    private AvailabilityDto testAvailabilityDto;
    private MemberDto testMemberDto;
    private UserDto testUserDto;
    private PlaceDto testPlaceDto;

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

        testAvailabilityDto.builder()
                .member(testMemberDto)
                .beginTime(new DateTime(2020, 3, 3, 15, 0, 0))
                .endTime(new DateTime(2020, 3, 3, 16, 0, 0))
                .place(testPlaceDto)
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
        return new MemberCrudFacade(memberRepository); // tworzy obiekt fasady
    }

    private AvailabilityCrudFacade initAvailabilityCrudFacade() {
        return new AvailabilityCrudFacade(repository, memberRepository);
    }


    private boolean initTestDB() {
        userCrudFacade = initUserCrudFacade();
        memberCrudFacade = initMemberCrudFacade();

        UserEntity testUser = userCrudFacade
                .findEntity(userCrudFacade.create(testUserDto, DefaultUserLoginTypeEnum.PHONE));

        MemberEntity memberEntity;
        try {
            memberEntity = memberCrudFacade
                    .findEntity(memberCrudFacade.create(testMemberDto));
        } catch (EntityNotFoundException e) {
            memberEntity = null;
        } catch (EntityAlreadyExistsException e) {
            memberEntity = null;
        }
        return testUser != null
                && memberEntity != null;
    }

//    private AvailabilityDto createAvailability() {
//        testAvailabilityDto.setMember(createdTestMember());
//        return availabilityCrudFacade.create(testAvailabilityDto);
//    }
//
//        private MemberDto createdTestMember() { // nie bedziemy w AvailabilityCrudFacadeTest testowali innej fasady; potrzebujemy tylko info czy ta inna fasada dziala
//        try {
//            return initMemberCrudFacade().create(testMemberDto); // fasada tworzy membera w bazie danych
//        } catch (EntityAlreadyExistsException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private MemberEntity findTestMember(MemberDto memberDto) { // nie bedziemy w AvailabilityCrudFacadeTest testowali innej fasady; potrzebujemy tylko info czy ta inna fasada dziala
//        try {
//            return initMemberCrudFacade().findEntity(memberDto);
//        } catch (EntityNotFoundException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    @Test
    public void USER_CRUD_FACADE_WR() {
        UserCrudFacade userCrudFacade = initUserCrudFacade();
        UserDto testUser = userCrudFacade.create(testUserDto, DefaultUserLoginTypeEnum.PHONE);
        UserEntity userEntity = userCrudFacade.findEntity(testUser);
        assertThat(userEntity).isNotNull();
    }

    @Test
    public void MEMBER_CRUD_FACADE_WR() throws EntityAlreadyExistsException, EntityNotFoundException {
        MemberCrudFacade memberCrudFacade = initMemberCrudFacade();
        MemberEntity memberEntity = memberCrudFacade.findEntity(memberCrudFacade.create(testMemberDto));
        assertThat(memberEntity).isNotNull();
    }

    @Test
    public void INIT_TEST_DB() {
        boolean initDB = initTestDB();
        assertThat(initDB).isTrue();
    }

    @Test
    public void WHEN_try_to_create_non_existing_availability_THEN_return_availability() throws EntityAlreadyExistsException, EntityNotFoundException {
        initTestDB();
        availabilityCrudFacade = initAvailabilityCrudFacade();
        AvailabilityDto created = availabilityCrudFacade.create(testAvailabilityDto);
        assertThat(created.getMember()).isNotNull();
        assertThat(created).isNotNull();
        assertThat(created.getCreationTime()).isNotNull();
        assertThat(created.getModificationTime()).isNotNull();
        assertThat(created.isActive()).isTrue();
    }

    @Test
    public void WHEN_try_to_create_existing_availability_THEN_EntityAlreadyExistsException() {
        initTestDB();
        availabilityCrudFacade = initAvailabilityCrudFacade();
        try {
            availabilityCrudFacade.create(testAvailabilityDto);
        } catch (EntityNotFoundException | EntityAlreadyExistsException e) {
            Assert.fail();
        }
        try {
            availabilityCrudFacade.create(testAvailabilityDto);
            Assert.fail();
        } catch (EntityNotFoundException e) {
            Assert.fail();
        } catch (EntityAlreadyExistsException e) {
            assertThat(e)
                    .isInstanceOf(EntityAlreadyExistsException.class)
                    .hasMessage(AvailabilityCrudInfoStatusEnum.AVAILABILITY_ALREADY_EXISTS.toString());
        }
    }

    @Test
    public void WHEN_found_availability_THEN_return_availability() throws EntityNotFoundException, EntityAlreadyExistsException {
        initTestDB();
        AvailabilityDto created;
        AvailabilityDto found = null;
        availabilityCrudFacade = initAvailabilityCrudFacade();

        created = availabilityCrudFacade.create(testAvailabilityDto);
        found = availabilityCrudFacade.read(testAvailabilityDto);
        assertThat(found).isNotNull();
    }

    @Test
    public void WHEN_try_to_find_non_existing_availability_THEN_return_EntityNotFoundException() {
        initTestDB();
        availabilityCrudFacade = initAvailabilityCrudFacade();
        try {
            availabilityCrudFacade.read(testAvailabilityDto);
            Assert.fail();
        } catch (EntityNotFoundException e) {
            assertThat(e)
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage(AvailabilityCrudInfoStatusEnum.AVAILABILITY_NOT_FOUND.toString());
        }
    }

    @Test
    public void WHEN_try_to_find_all_availabilities_THEN_return_availabilities() {
        initTestDB();
        AvailabilityDto created;
        List<AvailabilityDto> found = null;
        AvailabilityCrudFacade availabilityCrudFacade = initAvailabilityCrudFacade();
        found = availabilityCrudFacade.readAll(testAvailabilityDto);
        assertThat(found).isNotNull();
    }

    @Test
    public void WHEN_try_to_update_existing_availability_THEN_return_availability() throws EntityAlreadyExistsException, EntityNotFoundException {
        initTestDB();
        AvailabilityCrudFacade availabilityCrudFacade = initAvailabilityCrudFacade();
        AvailabilityDto created = availabilityCrudFacade.create(testAvailabilityDto);
        AvailabilityDto updated = availabilityCrudFacade.update(testAvailabilityDto, availabilityUpdatedValues(testAvailabilityDto));


        assertThat(updated.isRemoteWork()).isFalse();

        assertThat(updated.getMember()).isEqualToComparingFieldByField(created.getMember());
        assertThat(updated.getBeginTime()).isEqualTo(created.getBeginTime());
        assertThat(updated.getEndTime()).isEqualTo(created.getEndTime());
        assertThat(updated.getPlace()).isEqualTo(created.getPlace());
        assertThat(updated.getAvailabilityVote()).isEqualTo(created.getAvailabilityVote());
        assertThat(updated.getCreationTime()).isEqualTo(created.getCreationTime());
        assertThat(updated.isActive()).isEqualTo(created.isActive());
        assertThat(updated.getModificationTime()).isNotEqualTo(created.getModificationTime());
    }

    private AvailabilityDto availabilityUpdatedValues(AvailabilityDto testAvailabilityDto) {
        testAvailabilityDto.setRemoteWork(false);
        return testAvailabilityDto;
    }

    @Test
    public void WHEN_try_to_update_non_existing_availability_THEN_return_EntityNotFoundException() {
        initTestDB();
        AvailabilityCrudFacade availabilityCrudFacade = initAvailabilityCrudFacade();
        try {
            availabilityCrudFacade.update(testAvailabilityDto, availabilityUpdatedValues(testAvailabilityDto));
        } catch (EntityNotFoundException e) {
            assertThat(e)
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage(AvailabilityCrudInfoStatusEnum.AVAILABILITY_NOT_FOUND.toString());
        }
    }

    @Test
    public void WHEN_delete_existing_availability_THEN_return_availability() throws EntityAlreadyExistsException, EntityNotFoundException {
        initTestDB();
        AvailabilityCrudFacade availabilityCrudFacade = initAvailabilityCrudFacade();
        AvailabilityDto created = availabilityCrudFacade.create(testAvailabilityDto);
        AvailabilityDto deleted = availabilityCrudFacade.delete(testAvailabilityDto);

        assertThat(deleted).isNotNull();
        assertThat(deleted.isActive()).isNotEqualTo(created.isActive());
        assertThat(deleted.getCreationTime()).isEqualTo(created.getCreationTime());
        assertThat(deleted.getModificationTime()).isNotNull();
    }

    @Test
    public void WHEN_try_to_delete_non_existing_availability_THEN_return_EntityNotFoundException() {
        initTestDB();
        AvailabilityCrudFacade availabilityCrudFacade = initAvailabilityCrudFacade();

        try {
            availabilityCrudFacade.delete(testAvailabilityDto);
        } catch (EntityNotFoundException | EntityAlreadyExistsException e) {
            assertThat(e)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(AvailabilityCrudInfoStatusEnum.AVAILABILITY_NOT_FOUND.toString());
        }
    }

    @Ignore
    @Test
    public void findEntities() {
    }

}