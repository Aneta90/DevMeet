package pl.com.devmeet.devmeetcore.poll_associated.place_vote.domain;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.devmeet.devmeetcore.domain_utils.exceptions.CrudException;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeetcore.member_associated.place.domain.PlaceDto;
import pl.com.devmeet.devmeetcore.poll_associated.place_vote.domain.status_and_exceptions.PlaceVoteAlreadyExistsException;
import pl.com.devmeet.devmeetcore.poll_associated.place_vote.domain.status_and_exceptions.PlaceVoteNotFoundException;
import pl.com.devmeet.devmeetcore.poll_associated.poll.domain.PollDto;
import pl.com.devmeet.devmeetcore.user.domain.UserDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
public class PlaceVoteCrudFacadeTest {

    @Autowired
    private PlaceVoteRepository placeVoteRepository;

    private PlaceVoteDto placeVoteDto;
    private PlaceVoteDto placeVoteDto1;
    private PlaceDto placeDto;
    private MemberDto memberDto;
    private PollDto pollDto;

    @Before
    public void setUp() {

        UserDto userDto = UserDto.builder()
                .email("test@test.pl")
                .phone("221234567")
                .password("testPass")
                .isActive(true)
                .loggedIn(true)
                .build();

        memberDto = MemberDto.builder()
                .nick("Sam")
                .user(userDto)
                .build();

        placeDto = new PlaceDto();
        placeDto.setMember(memberDto);
        placeDto.setPlaceName("Mc Donalds");

        PlaceDto placeDto1 = new PlaceDto();
        placeDto1.setMember(memberDto);
        placeDto1.setPlaceName("Costa");

        pollDto = PollDto.builder()
                .active(true)
                .build();

        placeVoteDto = PlaceVoteDto.builder()
                .member(memberDto)
                .place(placeDto)
                .poll(pollDto)
                .creationTime(DateTime.now())
                .isActive(true)
                .build();

        placeVoteDto1 = PlaceVoteDto.builder()
                .member(memberDto)
                .place(placeDto1)
                .poll(pollDto)
                .creationTime(DateTime.now())
                .isActive(true)
                .build();
    }


    private PlaceVoteCrudFacade initPlaceVoteCrudFacade() {
        return new PlaceVoteCrudFacade(placeVoteRepository);
    }

    private PlaceVoteDto createPlaceVote() throws CrudException {
        return initPlaceVoteCrudFacade().add(placeVoteDto);
    }

    @Test
    public void WHEN_creating_non_existing_place_vote_then_return_place_vote() throws CrudException {
        PlaceVoteDto placeVoteDto = createPlaceVote();
        List<PlaceVoteEntity> placeVoteEntity = initPlaceVoteCrudFacade().findEntityByMember(placeVoteDto);

        assertThat(placeVoteEntity).isNotNull();
        assertThat(placeVoteEntity.get(0).getPlace().getPlaceName()).isEqualTo("Mc Donalds");
        assertThat(placeVoteEntity.get(0).getMember().getNick()).isEqualTo("Sam");
    }

    @Test(expected = PlaceVoteAlreadyExistsException.class)
    public void WHEN_creating_existing_place_vote_then_throw_an_exception() throws CrudException {
        PlaceVoteDto placeVoteDto = createPlaceVote();
        initPlaceVoteCrudFacade().add(placeVoteDto);
    }

    @Test
    public void WHEN_find_existing_placeVote_by_member_then_return_placeVote() throws CrudException {

        PlaceVoteDto placeVoteDto = createPlaceVote();
        List<PlaceVoteEntity> placeVoteEntities = initPlaceVoteCrudFacade().findEntityByMember(placeVoteDto);
        assertThat(placeVoteEntities.get(0).getPlace().getPlaceName()).isEqualTo("Mc Donalds");
    }

    @Test
    public void WHEN_find_existing_placeVote_by_place_then_return_placeVote() throws CrudException {
        PlaceVoteDto placeVoteDto = createPlaceVote();
        List<PlaceVoteEntity> placeVoteEntities = initPlaceVoteCrudFacade().findEntityByPlace(placeVoteDto);
        assertThat(placeVoteEntities.get(0).getPlace().getPlaceName()).isEqualTo("Mc Donalds");
    }

    @Test(expected = PlaceVoteNotFoundException.class)
    public void WHEN_trying_to_find_non_existing_placeVote_then_throw_an_exception() throws CrudException {
        PlaceVoteDto placeVoteDto = new PlaceVoteDto();
        placeVoteDto.setActive(false);
        placeVoteDto.setCreationTime(DateTime.now());
        placeVoteDto.setMember(memberDto);
        placeVoteDto.setPlace(placeDto);
        placeVoteDto.setPoll(pollDto);
        initPlaceVoteCrudFacade().findEntityByMember(placeVoteDto);
    }

    @Test
    public void WHEN_placeVote_exists_then_return_true() throws CrudException {
        PlaceVoteDto placeVoteDto = createPlaceVote();
        initPlaceVoteCrudFacade().doesExist(placeVoteDto);
    }

    @Test
    public void WHEN_placeVote_does_not_exist_then_return_false() {
        PlaceVoteDto placeVoteDto = new PlaceVoteDto();
        placeVoteDto.setActive(false);
        placeVoteDto.setMember(memberDto);
        placeVoteDto.setPoll(pollDto);
        placeVoteDto.setPlace(placeDto);
        placeVoteDto.setCreationTime(DateTime.now());
        initPlaceVoteCrudFacade().doesExist(placeVoteDto);
    }

    @Test
    public void update() throws CrudException {
        PlaceVoteDto placeVoteDto = createPlaceVote();
        PlaceVoteDto updatedPlaceVoteDto = initPlaceVoteCrudFacade().update(placeVoteDto, placeVoteDto1);
        assertThat(updatedPlaceVoteDto.getPlace().getPlaceName()).isEqualTo("Costa");
    }

    @Test
    public void delete() throws CrudException {
        PlaceVoteDto placeVoteDto = createPlaceVote();
        initPlaceVoteCrudFacade().delete(placeVoteDto);
        assertThat(placeVoteDto.isActive()).isEqualTo(false);
    }
}