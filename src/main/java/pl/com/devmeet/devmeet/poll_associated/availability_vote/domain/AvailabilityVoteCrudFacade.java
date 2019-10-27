package pl.com.devmeet.devmeet.poll_associated.availability_vote.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.devmeet.devmeet.domain_utils.CrudInterface;
import pl.com.devmeet.devmeet.domain_utils.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.member_associated.availability.domain.AvailabilityCrudRepository;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberRepository;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.PollCrudRepository;

import java.util.List;

@Service
public class AvailabilityVoteCrudFacade implements CrudInterface<AvailabilityVoteDto, AvailabilityVoteEntity> {

    private AvailabilityVoteCrudRepository availabilityVoteRepository;
    private PollCrudRepository pollCrudRepository;
    private MemberRepository memberRepository;
    private AvailabilityCrudRepository availabilityRepository;

    @Autowired
    public AvailabilityVoteCrudFacade(AvailabilityVoteCrudRepository availabilityVoteRepository,
                                      PollCrudRepository pollCrudRepository,
                                      MemberRepository memberRepository,
                                      AvailabilityCrudRepository availabilityRepository) {

        this.availabilityVoteRepository = availabilityVoteRepository;
        this.pollCrudRepository = pollCrudRepository;
        this.memberRepository = memberRepository;
        this.availabilityRepository = availabilityRepository;
    }

    private AvailabilityVotePollFinder initPollFinder() {
        return null;
    }

    private AvailabilityVoteMemberFinder initMemberFinder() {
        return null;
    }

    private AvailabilityVoteAvailabilityFinder initAvailabilityFinder() {
        return null;
    }

    private AvailabilityVoteCrudSaver initVoteSaver(){
        return AvailabilityVoteCrudSaver.builder()
                .availabilityVoteCrudRepository(availabilityVoteRepository)
                .pollFinder(initPollFinder())
                .memberFinder(initMemberFinder())
                .availabilityFinder(initAvailabilityFinder())
                .build();
    }

    private AvailabilityVoteCrudFinder initVoteFinder (){
        return null;
    }

    @Override
    public AvailabilityVoteDto create(AvailabilityVoteDto dto) throws EntityNotFoundException, EntityAlreadyExistsException {
        return null;
    }

    @Override
    public AvailabilityVoteDto read(AvailabilityVoteDto dto) throws IllegalArgumentException, EntityNotFoundException {
        return null;
    }

    @Override
    public List<AvailabilityVoteDto> readAll(AvailabilityVoteDto dto) throws IllegalArgumentException, EntityNotFoundException, UnsupportedOperationException {
        return null;
    }

    @Override
    public AvailabilityVoteDto update(AvailabilityVoteDto oldDto, AvailabilityVoteDto newDto) throws IllegalArgumentException, EntityNotFoundException, EntityAlreadyExistsException {
        return null;
    }

    @Override
    public AvailabilityVoteDto delete(AvailabilityVoteDto dto) throws IllegalArgumentException, EntityNotFoundException, EntityAlreadyExistsException {
        return null;
    }

    @Override
    public AvailabilityVoteEntity findEntity(AvailabilityVoteDto dto) throws IllegalArgumentException, EntityNotFoundException {
        return null;
    }

    @Override
    public List<AvailabilityVoteEntity> findEntities(AvailabilityVoteDto dto) throws IllegalArgumentException, EntityNotFoundException, UnsupportedOperationException {
        return null;
    }
}
