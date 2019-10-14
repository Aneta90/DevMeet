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
import pl.com.devmeet.devmeet.member_associated.availability.domain.status.AvailabilityCrudInfoStatusEnum;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeet.member_associated.place.domain.PlaceDto;

import static org.joda.time.DateTime.now;
import static org.junit.Assert.*;

@DataJpaTest
@RunWith(SpringRunner.class)

public class AvailabilityCrudFacadeTest {


    @Autowired
    private AvailabilityCrudRepository repository;
    private AvailabilityCrudFacade facade;
    private AvailabilityDto testAvailability;
    private MemberDto testMember;
    private PlaceDto testPlace;

    @Before
    public void setUp() throws Exception {
        facade = new AvailabilityCrudFacade(repository);


        testAvailability = new AvailabilityDto().builder()
                .member(testMember)
                .beginTime(2020, 3, 3, 15, 0, 0)
                .endTime(2020, 3, 3, 16, 0, 0, 0)
                .place(testPlace)
                .availabilityVote(null)
                .remoteWork(true)
                .creationTime(null)
                .modificationTime(null)
                .isActive(true)
                .build();
    }

    private AvailabilityDto createAvailability() {
        return facade.create(testAvailability);
    }

    @Test
    public void WHEN_create_not_exist_availability_THEN_return_availability() {
        AvailabilityDto created = createAvailability();
        assertThat(created).isNotNull();
        assertThat(created.getCreationTime()).isNotNull();
        assertThat(created.isActive()).isTrue();
    }

    @Test
    public void WHEN_create_exist_availability_THEN_return_IllegalArgumentException_availability_already_exists() {
        AvailabilityDto createdFirst = createAvailability();

        try {
            facade.create(testAvailability);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            assertThat(e)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(AvailabilityCrudInfoStatusEnum.AVAILABILITY_ALREADY_EXISTS.toString());
        }

    }

    @Test
    public void WHEN_create_exist_availability_but_not_active_THEN_return_availability() {
        AvailabilityDto createdFirst = createAvailability();
        AvailabilityDto deletedFirst = facade.delete(testAvailability);
        AvailabilityDto createdSecond = facade.create(modifiedTestAvailability(testAvailability))

        assertThat(createdFirst).isNotNull();
        assertThat(deletedFirst).isNotNull();
        assertThat(createdSecond).isNotNull();
        assertThat(createdSecond.getCreationTime()).isNotNull();
        assertThat(createdSecond.getModificationTime()).isNotNull();
        assertThat(createdSecond.isActive()).isTrue();
    }

    private AvailabilityDto modifiedTestAvailability(AvailabilityDto dto) {
        dto.setModificationTime(now());
        dto.setRemoteWork(false);
        return testAvailability;
    }

    @Test
    public void WHEN_found_existing_availability_THEN_return_availability() {
        assertThat(createAvailability()).isNotNull();
        assertThat(facade.read(testAvailability)).isNotNull();
    }

    @Test
    public void WHEN_availability_not_found_THEN_return_IllegalArgumentException_availability_not_found() {
        try {
            facade.findEntity(testAvailability);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            assertThat(e)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(AvailabilityCrudInfoStatusEnum.AVAILABILITIES_NOT_FOUND.toString());
        }
    }

    @Ignore
    @Test
    public void readAll() {
    }

    @Test
    public void WHEN_try_to_update_existing_availability_THEN_return_availability() {
        AvailabilityDto availability = createAvailability();
        AvailabilityDto update = modifiedTestAvailability(testAvailability);
        AvailabilityDto modifiedAvailability = facade.update(testAvailability, update);

        //co do czego porównywać?
        //availabilityVote?
        // ???    assertThat(modifiedAvailability.getModificationTime()).isEqualTo(update.getModificationTime());
        assertThat(modifiedAvailability.isRemoteWork()).isEqualTo(update.isRemoteWork());

        assertThat(modifiedAvailability.getMember()).isEqualTo(availability.getMember());
        assertThat(modifiedAvailability.getBeginTime()).isEqualTo(availability.getBeginTime());
        assertThat(modifiedAvailability.getEndTime()).isEqualTo(availability.getEndTime());
        assertThat(modifiedAvailability.getPlace()).isEqualTo(availability.getPlace());
        assertThat(modifiedAvailability.getAvailabilityVote()).isEqualTo(availability.getAvailabilityVote());
        assertThat(modifiedAvailability.getCreationTime()).isEqualTo(availability.getCreationTime());
        assertThat(modifiedAvailability.isActive()).isEqualTo(availability.isActive());
//???
        assertThat(modifiedAvailability.getModificationTime()).isNotNull();

    }

    @Test
    public void WHEN_try_to_update_not_existing_availability_THEN_return_IllegalArgumentException_availability_not_found() {
        AvailabilityDto update = modifiedTestAvailability(testAvailability);

        try {
            facade.update(testAvailability, update);
        } catch (IllegalArgumentException e) {
            assertThat(e)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(AvailabilityCrudInfoStatusEnum.AVAILABILITY_NOT_FOUND.toString());
        }
    }

    @Test
    public void WHEN_delete_existing_availability_THEN_return_availability() {
        AvailabilityDto availability = createAvailability();
        AvailabilityDto deleted = facade.delete(availability);

        assertThat(deleted).isNotNull();
        assertThat(deleted.isActive()).isNotEqualTo(availability.isActive());
        assertThat(deleted.getModificationTime()).isNotNull();
    }

    @Test
    public void WHEN_try_to_delete_not_existing_availability_THEN_return_return_IllegalArgumentException_availability_not_found() {
        try {
            facade.delete(testAvailability);
        } catch (IllegalArgumentException e) {
            assertThat(e)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(AvailabilityCrudInfoStatusEnum.AVAILABILITY_NOT_FOUND.toString());
        }
    }


    @Test
    public void findEntity() {
        AvailabilityDto created= createAvailability();
        AvailabilityEntity foundEntity= facade.findEntity(created);

        assertThat(foundEntity).isNotNull();

        assertThat(foundEntity.getBeginTime()).isEqualTo(created.getBeginTime());
        assertThat(foundEntity.getCreationTime()).isEqualTo(created.getCreationTime());
    }

    @Ignore
    @Test
    public void findEntities() {
    }
//
//    @Test
//    public void map() {
//    }
//
//    @Test
//    public void mapDtoList() {
//    }
//
//    @Test
//    public void testMap() {
//    }
//
//    @Test
//    public void mapEntityList() {
//    }
}