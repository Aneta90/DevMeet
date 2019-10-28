package pl.com.devmeet.devmeet.poll_associated.availability_vote.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberEntity;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.PollEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AvailabilityVoteCrudRepository extends PagingAndSortingRepository<AvailabilityVoteEntity, UUID> {

    Optional<List<AvailabilityVoteEntity>> findAllByPoll(PollEntity pollEntity);

//    Optional<AvailabilityVoteEntity> findByMemberAndActive(MemberEntity memberEntity, boolean active);
}
