package pl.com.devmeet.devmeetcore.poll_associated.place_vote.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.com.devmeet.devmeetcore.poll_associated.poll.domain.PollEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlaceVoteRepository extends PagingAndSortingRepository<PlaceVoteEntity, UUID> {

    @Query("SELECT p from PlaceVoteEntity p where p.place.placeName = ?1")
    Optional<List<PlaceVoteEntity>> findByPlace(String placeName);

    @Query("SELECT p from PlaceVoteEntity p where p.poll = ?1")
    Optional<List<PlaceVoteEntity>> findByPoll(PollEntity poll);

    @Query("SELECT p from PlaceVoteEntity p where p.member.nick = ?1")
    Optional <List<PlaceVoteEntity>> findByMember(String memberNick);

    @Query("SELECT p from PlaceVoteEntity p where p.isActive =: isActive")
    Optional <List<PlaceVoteEntity>> findAllActive(Boolean isActive);
}