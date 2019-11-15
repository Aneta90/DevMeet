package pl.com.devmeet.devmeet.poll_associated.availability_vote.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.CrudEntitySaver;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityNotFoundException;
import pl.com.devmeet.devmeet.member_associated.availability.domain.AvailabilityCrudFacade;
import pl.com.devmeet.devmeet.member_associated.availability.domain.AvailabilityEntity;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberEntity;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.PollCrudFacade;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.PollEntity;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

@Builder
@AllArgsConstructor
@NoArgsConstructor
class AvailabilityVoteCrudSaver implements CrudEntitySaver<AvailabilityVoteEntity, AvailabilityVoteEntity> {

    private AvailabilityVoteCrudRepository availabilityVoteCrudRepository;
    private AvailabilityVotePollFinder pollFinder;
    private AvailabilityVoteMemberFinder memberFinder;
    private AvailabilityVoteAvailabilityFinder availabilityFinder;

    @Override
    public AvailabilityVoteEntity saveEntity(AvailabilityVoteEntity entity) throws EntityNotFoundException {
        return availabilityVoteCrudRepository
                .save(
                        connectVoteWithMember(
                                connectVoteWithAvailability(
                                        connectVoteWithPoll(entity)
                                )
                        )
                );
    }

    private AvailabilityVoteEntity connectVoteWithMember(AvailabilityVoteEntity voteEntity) throws MemberNotFoundException, UserNotFoundException {
        MemberEntity memberEntity = voteEntity.getMember();

        if (memberEntity.getId() == null)
            memberEntity = memberFinder.findMember(MemberCrudFacade.map(memberEntity));

        voteEntity.setMember(memberEntity);
        return voteEntity;
    }

    private AvailabilityVoteEntity connectVoteWithAvailability(AvailabilityVoteEntity voteEntity) {
        AvailabilityEntity availabilityEntity = voteEntity.getAvailability();

        if (availabilityEntity.getId() == null)
            availabilityEntity = availabilityFinder.findAvailability(AvailabilityCrudFacade.map(availabilityEntity));

        voteEntity.setAvailability(availabilityEntity);
        return voteEntity;
    }

    private AvailabilityVoteEntity connectVoteWithPoll(AvailabilityVoteEntity voteEntity) {
        PollEntity pollEntity = voteEntity.getPoll();

        if (pollEntity.getId() == null)
            pollEntity = pollFinder.findPoll(PollCrudFacade.map(pollEntity));

        voteEntity.setPoll(pollEntity);
        return voteEntity;
    }
}
