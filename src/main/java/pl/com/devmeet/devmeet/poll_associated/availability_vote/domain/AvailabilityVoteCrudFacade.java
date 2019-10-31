package pl.com.devmeet.devmeet.poll_associated.availability_vote.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.devmeet.devmeet.domain_utils.CrudInterface;
import pl.com.devmeet.devmeet.domain_utils.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudRepository;
import pl.com.devmeet.devmeet.member_associated.availability.domain.AvailabilityCrudFacade;
import pl.com.devmeet.devmeet.member_associated.availability.domain.AvailabilityCrudRepository;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberRepository;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.PollCrudFacade;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.PollCrudRepository;

import java.util.List;

@Service
public class AvailabilityVoteCrudFacade implements CrudInterface<AvailabilityVoteDto, AvailabilityVoteEntity> {

    private AvailabilityVoteCrudRepository availabilityVoteRepository;
    private PollCrudRepository pollCrudRepository;
    private GroupCrudRepository groupRepository;
    private MemberRepository memberRepository;
    private AvailabilityCrudRepository availabilityRepository;

    @Autowired
    public AvailabilityVoteCrudFacade(AvailabilityVoteCrudRepository availabilityVoteRepository,
                                      PollCrudRepository pollCrudRepository,
                                      GroupCrudRepository groupRepository,
                                      MemberRepository memberRepository,
                                      AvailabilityCrudRepository availabilityRepository) {

        this.availabilityVoteRepository = availabilityVoteRepository;
        this.pollCrudRepository = pollCrudRepository;
        this.groupRepository = groupRepository;
        this.memberRepository = memberRepository;
        this.availabilityRepository = availabilityRepository;
    }

    private AvailabilityVotePollFinder initPollFinder() {
        return new AvailabilityVotePollFinder(new PollCrudFacade(groupRepository, pollCrudRepository));
    }

    private AvailabilityVoteMemberFinder initMemberFinder() {
        return new AvailabilityVoteMemberFinder(new MemberCrudFacade(memberRepository));
    }

    private AvailabilityVoteAvailabilityFinder initAvailabilityFinder() {
        return new AvailabilityVoteAvailabilityFinder(new AvailabilityCrudFacade(availabilityRepository, memberRepository));
    }

    private AvailabilityVoteCrudSaver initVoteSaver() {
        return AvailabilityVoteCrudSaver.builder()
                .availabilityVoteCrudRepository(availabilityVoteRepository)
                .pollFinder(initPollFinder())
                .memberFinder(initMemberFinder())
                .availabilityFinder(initAvailabilityFinder())
                .build();
    }

    private AvailabilityVoteCrudFinder initVoteFinder() {
        return AvailabilityVoteCrudFinder.builder()
                .voteRepository(availabilityVoteRepository)
                .pollFinder(initPollFinder())
                .memberFinder(initMemberFinder())
                .build();
    }

    private AvailabilityVoteCrudCreator initVoteCreator(){
        return AvailabilityVoteCrudCreator.builder()
                .voteCrudFinder(initVoteFinder())
                .voteCrudSaver(initVoteSaver())
                .build();
    }

    @Override
    public AvailabilityVoteDto create(AvailabilityVoteDto dto) throws EntityNotFoundException, EntityAlreadyExistsException {
        return map(initVoteCreator().createEntity(dto));
    }

    @Override
    public AvailabilityVoteDto read(AvailabilityVoteDto dto) throws IllegalArgumentException, EntityNotFoundException {
        return map(findEntity(dto));
    }

    @Override
    public List<AvailabilityVoteDto> readAll(AvailabilityVoteDto dto) throws IllegalArgumentException, EntityNotFoundException, UnsupportedOperationException {
        return mapToDtos(findEntities(dto));
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
        return initVoteFinder().findEntity(dto);
    }

    @Override
    public List<AvailabilityVoteEntity> findEntities(AvailabilityVoteDto dto) throws IllegalArgumentException, EntityNotFoundException, UnsupportedOperationException {
        return null;
    }

    public static AvailabilityVoteEntity map (AvailabilityVoteDto dto){
        return AvailabilityVoteCrudMapper.map(dto);
    }

    public static AvailabilityVoteDto map (AvailabilityVoteEntity entity){
        return AvailabilityVoteCrudMapper.map(entity);
    }

    public static List<AvailabilityVoteEntity> mapToEntities(List<AvailabilityVoteDto> dtos){
        return null;
    }

    public static List<AvailabilityVoteDto> mapToDtos(List<AvailabilityVoteEntity> entities){
        return null;
    }
}
