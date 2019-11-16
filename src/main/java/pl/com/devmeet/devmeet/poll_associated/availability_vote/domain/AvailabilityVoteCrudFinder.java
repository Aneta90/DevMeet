package pl.com.devmeet.devmeet.poll_associated.availability_vote.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityFinder;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityNotFoundException;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberEntity;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.poll_associated.availability_vote.domain.status_and_exceptions.AvailabilityVoteCrudStatusEnum;
import pl.com.devmeet.devmeet.poll_associated.availability_vote.domain.status_and_exceptions.AvailabilityVoteNotFoundException;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.PollDto;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.PollEntity;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.status_and_exceptions.PollNotFoundException;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class AvailabilityVoteCrudFinder implements CrudEntityFinder<AvailabilityVoteDto, AvailabilityVoteEntity> {

    private AvailabilityVoteCrudRepository voteRepository;
    private AvailabilityVotePollFinder pollFinder;
    private AvailabilityVoteMemberFinder memberFinder;

    @Override
    public AvailabilityVoteEntity findEntity(AvailabilityVoteDto dto) throws MemberNotFoundException, UserNotFoundException, AvailabilityVoteNotFoundException {
        return findVote(dto);
    }

    private AvailabilityVoteEntity findVote(AvailabilityVoteDto dto) throws MemberNotFoundException, UserNotFoundException, AvailabilityVoteNotFoundException {
        MemberEntity memberEntity = findMember(dto);

        Optional<AvailabilityVoteEntity> voteEntity = voteRepository.findByMemberAndIsActive(memberEntity, dto.isActive());

        if (voteEntity.isPresent())
            return voteEntity.get();

        throw new AvailabilityVoteNotFoundException(AvailabilityVoteCrudStatusEnum.AVAILABILITY_VOTE_NOT_FOUND.toString());
    }

    private MemberEntity findMember(AvailabilityVoteDto dto) throws MemberNotFoundException, UserNotFoundException {
        return memberFinder.findMember(dto.getMember());
    }

    @Override
    public List<AvailabilityVoteEntity> findEntities(AvailabilityVoteDto dto) throws GroupNotFoundException, AvailabilityVoteNotFoundException, PollNotFoundException {
        return findVotes(dto);
    }

    private List<AvailabilityVoteEntity> findVotes(AvailabilityVoteDto dto) throws AvailabilityVoteNotFoundException, GroupNotFoundException, PollNotFoundException {
        PollEntity pollEntity = findPoll(dto);

        Optional<List<AvailabilityVoteEntity>> voteEntities = voteRepository.findAllByPoll(pollEntity);

        if (voteEntities.isPresent())
            return voteEntities.get();

        throw new AvailabilityVoteNotFoundException(AvailabilityVoteCrudStatusEnum.AVAILABILITY_VOTES_NOT_FOUND.toString());
    }

    private PollEntity findPoll(AvailabilityVoteDto dto) throws GroupNotFoundException, PollNotFoundException {
        return pollFinder.findPoll(dto.getPoll());
    }

    @Override
    public boolean isExist(AvailabilityVoteDto dto) {
        try {
            findEntity(dto);
            return true;
        } catch (MemberNotFoundException | UserNotFoundException | AvailabilityVoteNotFoundException e) {
            return false;
        }
    }
}
