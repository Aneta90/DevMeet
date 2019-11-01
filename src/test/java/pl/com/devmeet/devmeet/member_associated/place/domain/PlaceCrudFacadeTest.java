package pl.com.devmeet.devmeet.member_associated.place.domain;

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
import pl.com.devmeet.devmeet.member_associated.availability.domain.AvailabilityDto;
import pl.com.devmeet.devmeet.member_associated.place.domain.PlaceCrudFacade;
import pl.com.devmeet.devmeet.member_associated.place.domain.PlaceCrudRepository;
import pl.com.devmeet.devmeet.member_associated.place.domain.PlaceDto;
import pl.com.devmeet.devmeet.member_associated.place.domain.status_and_exceptions.PlaceCrudStatusEnum;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberEntity;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberRepository;
import pl.com.devmeet.devmeet.user.domain.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;


@DataJpaTest
@RunWith(SpringRunner.class)

public class PlaceCrudFacadeTest {


    @Autowired
    private PlaceCrudRepository repository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private UserRepository userRepository;

    private PlaceCrudFacade placeCrudFacade;
    private MemberCrudFacade memberCrudFacade;
    private UserCrudFacade userCrudFacade;

    private PlaceDto testPlaceDto;
    private MemberDto testMemberDto;
    private UserDto testUserDto;
    private AvailabilityDto testAvailabilityDto;

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

        testPlaceDto = new PlaceDto().builder()
                .member(testMemberDto)
                .placeName("Centrum Zarządzania Innowacjami i Transferem Technologii Politechniki Warszawskiej")
                .description("openspace koło Metra Politechniki")
                .website("cziitt.pw.edu.pl")
                .location("Rektorska 4, 00-614 Warszawa")
                .availability(testAvailabilityDto)
                .placeVotes(null)
                .creationTime(null)
                .modificationTime(null)
                .isActive(true)
                .build();
    }

    private UserCrudFacade initUserCrudFacade() {
        return new UserCrudFacade(userRepository);
    }

    private MemberCrudFacade initMemberCrudFacade() {
        return new MemberCrudFacade(memberRepository);
    }

    private PlaceCrudFacade initPlaceCrudFacade() {
        return new PlaceCrudFacade(repository, memberRepository);
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
    public void WHEN_try_to_create_non_existing_place_THEN_return_place() throws EntityAlreadyExistsException, EntityNotFoundException {
        initTestDB();
        placeCrudFacade = initPlaceCrudFacade();
        PlaceDto created = placeCrudFacade.create(testPlaceDto);
        assertThat(created.getMember()).isNotNull();
        assertThat(created).isNotNull();
        assertThat(created.getCreationTime()).isNotNull();
        assertThat(created.getModificationTime()).isNull();
        assertThat(created.isActive()).isTrue();
    }

    @Test
    public void WHEN_try_to_create_existing_place_THEN_EntityAlreadyExistsException() {
        initTestDB();
        placeCrudFacade = initPlaceCrudFacade();
        try {
            placeCrudFacade.create(testPlaceDto);
        } catch (EntityNotFoundException | EntityAlreadyExistsException e) {
            Assert.fail();
        }
        try {
            placeCrudFacade.create(testPlaceDto);
            Assert.fail();
        } catch (EntityNotFoundException e) {
            Assert.fail();
        } catch (EntityAlreadyExistsException e) {
            assertThat(e)
                    .isInstanceOf(EntityAlreadyExistsException.class)
                    .hasMessage(PlaceCrudStatusEnum.PLACE_ALREADY_EXISTS.toString());
        }
    }

    @Test
    public void WHEN_found_place_THEN_return_place() throws EntityNotFoundException, EntityAlreadyExistsException {
        initTestDB();
        PlaceDto created;
        PlaceDto found = null;
        placeCrudFacade = initPlaceCrudFacade();

        created = placeCrudFacade.create(testPlaceDto);
        found = placeCrudFacade.read(testPlaceDto);
        assertThat(found).isNotNull();
    }

    @Test
    public void WHEN_try_to_find_non_existing_place_THEN_return_EntityNotFoundException() {
        initTestDB();
        placeCrudFacade = initPlaceCrudFacade();
        try {
            placeCrudFacade.read(testPlaceDto);
            Assert.fail();
        } catch (EntityNotFoundException e) {
            assertThat(e)
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage(PlaceCrudStatusEnum.PLACE_NOT_FOUND.toString());
        }
    }

    @Test
    public void WHEN_try_to_find_all_places_THEN_return_places() throws EntityNotFoundException, EntityAlreadyExistsException {
        initTestDB();
        List<PlaceDto> found = null;
        PlaceCrudFacade placeCrudFacade = initPlaceCrudFacade();
        placeCrudFacade.create(testPlaceDto);
        found = placeCrudFacade.readAll(testPlaceDto);
        assertThat(found).isNotNull();
    }

    @Test
    public void WHEN_try_to_update_existing_place_THEN_return_place() throws EntityAlreadyExistsException, EntityNotFoundException {
        initTestDB();
        PlaceCrudFacade placeCrudFacade = initPlaceCrudFacade();
        PlaceDto created = placeCrudFacade.create(testPlaceDto);
        PlaceDto updated = placeCrudFacade.update(testPlaceDto, placeUpdatedValues(testPlaceDto));
        assertThat(updated.getMember()).isEqualToComparingFieldByField(created.getMember());
        assertThat(updated.getPlaceName()).isEqualTo(created.getPlaceName());
        assertThat(updated.getDescription()).isNotEqualTo(created.getDescription());
        assertThat(updated.getWebsite()).isNotEqualTo(created.getWebsite());
        assertThat(updated.getWebsite()).isEqualTo("www.pw.pl");
        assertThat(updated.getDescription()).isEqualTo("openspace");
        assertThat(updated.getLocation()).isEqualTo(created.getLocation());
        assertThat(updated.getAvailability()).isEqualTo(created.getAvailability());
        assertThat(updated.getPlaceVotes()).isEqualTo(created.getPlaceVotes());
        assertThat(updated.getCreationTime()).isEqualTo(created.getCreationTime());
        assertThat(updated.getModificationTime()).isNotEqualTo(created.getModificationTime());
        assertThat(updated.isActive()).isEqualTo(created.isActive());
    }

    private PlaceDto placeUpdatedValues(PlaceDto testPlaceDto) {
        testPlaceDto.setWebsite("www.pw.pl");
        testPlaceDto.setDescription("openspace");
        return testPlaceDto;
    }

    @Test
    public void WHEN_try_to_update_non_existing_place_THEN_return_EntityNotFoundException() {
        initTestDB();
        PlaceCrudFacade placeCrudFacade = initPlaceCrudFacade();
        try {
            placeCrudFacade.update(testPlaceDto, placeUpdatedValues(testPlaceDto));
        } catch (EntityNotFoundException e) {
            assertThat(e)
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage(PlaceCrudStatusEnum.PLACE_NOT_FOUND.toString());
        }
    }

    @Test
    public void WHEN_delete_existing_place_THEN_return_place() throws EntityAlreadyExistsException, EntityNotFoundException {
        initTestDB();
        PlaceCrudFacade placeCrudFacade = initPlaceCrudFacade();
        PlaceDto created = placeCrudFacade.create(testPlaceDto);
        PlaceDto deleted = placeCrudFacade.delete(testPlaceDto);

        assertThat(deleted).isNotNull();
        assertThat(deleted.isActive()).isNotEqualTo(created.isActive());
        assertThat(deleted.getCreationTime()).isEqualTo(created.getCreationTime());
        assertThat(deleted.getModificationTime()).isNotNull();
    }

    @Test
    public void WHEN_try_to_delete_non_existing_place_THEN_return_EntityNotFoundException() {
        initTestDB();
        PlaceCrudFacade placeCrudFacade = initPlaceCrudFacade();

        try {
            placeCrudFacade.delete(testPlaceDto);
        } catch (EntityNotFoundException | EntityAlreadyExistsException e) {
            assertThat(e)
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage(PlaceCrudStatusEnum.PLACE_NOT_FOUND.toString());
        }
    }
}