package pl.com.devmeet.devmeet.poll_associated.availability_vote.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.devmeet.devmeet.domain_utils.CrudFacadeInterface;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityNotFoundException;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudRepository;
import pl.com.devmeet.devmeet.member_associated.availability.domain.AvailabilityCrudFacade;
import pl.com.devmeet.devmeet.member_associated.availability.domain.AvailabilityCrudRepository;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberRepository;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.PollCrudFacade;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.PollCrudRepository;
import pl.com.devmeet.devmeet.user.domain.UserRepository;

import java.util.List;

@Service
public class AvailabilityVoteCrudFacade implements CrudFacadeInterface<AvailabilityVoteDto, AvailabilityVoteEntity> {

    private AvailabilityVoteCrudRepository availabilityVoteRepository;
    private PollCrudRepository pollCrudRepository;
    private GroupCrudRepository groupRepository;
    private AvailabilityCrudRepository availabilityRepository;
    private MemberRepository memberRepository;
    private UserRepository userRepository;

    @Autowired
    public AvailabilityVoteCrudFacade(AvailabilityVoteCrudRepository availabilityVoteRepository,
                                      PollCrudRepository pollCrudRepository,
                                      GroupCrudRepository groupRepository,
                                      AvailabilityCrudRepository availabilityRepository,
                                      MemberRepository memberRepository,
                                      UserRepository userRepository) {

        this.availabilityVoteRepository = availabilityVoteRepository;
        this.pollCrudRepository = pollCrudRepository;
        this.groupRepository = groupRepository;
        this.availabilityRepository = availabilityRepository;
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
    }

    private AvailabilityVotePollFinder initPollFinder() {
        return new AvailabilityVotePollFinder(new PollCrudFacade(groupRepository, pollCrudRepository));
    }

    private AvailabilityVoteMemberFinder initMemberFinder() {
        return new AvailabilityVoteMemberFinder(new MemberCrudFacade(memberRepository, userRepository));
    }

    private AvailabilityVoteAvailabilityFinder initAvailabilityFinder() {
        return new AvailabilityVoteAvailabilityFinder(new AvailabilityCrudFacade(availabilityRepository, memberRepository, userRepository));
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

    private AvailabilityVoteCrudCreator initVoteCreator() {
        return AvailabilityVoteCrudCreator.builder()
                .voteCrudFinder(initVoteFinder())
                .voteCrudSaver(initVoteSaver())
                .build();
    }

    private AvailabilityVoteCrudUpdater initVoteUpdater() {
        return AvailabilityVoteCrudUpdater.builder()
                .voteCrudFinder(initVoteFinder())
                .voteCrudSaver(initVoteSaver())
                .build();
    }

    private AvailabilityVoteCrudDeleter initVoteDeleter() {
        return AvailabilityVoteCrudDeleter.builder()
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
        return map(initVoteUpdater().updateEntity(oldDto, newDto));
    }

    @Override
    public AvailabilityVoteDto delete(AvailabilityVoteDto dto) throws IllegalArgumentException, EntityNotFoundException, EntityAlreadyExistsException {
        return map(initVoteDeleter().deleteEntity(dto));
    }

    @Override
    public AvailabilityVoteEntity findEntity(AvailabilityVoteDto dto) throws IllegalArgumentException, EntityNotFoundException {
        return initVoteFinder().findEntity(dto);
    }

    @Override
    public List<AvailabilityVoteEntity> findEntities(AvailabilityVoteDto dto) throws IllegalArgumentException, EntityNotFoundException, UnsupportedOperationException {
        return initVoteFinder().findEntities(dto);
    }

    public static AvailabilityVoteEntity map(AvailabilityVoteDto dto) {
        return AvailabilityVoteCrudMapper.map(dto);
    }

    public static AvailabilityVoteDto map(AvailabilityVoteEntity entity) {
        return AvailabilityVoteCrudMapper.map(entity);
    }

    public static List<AvailabilityVoteEntity> mapToEntities(List<AvailabilityVoteDto> dtos) {
        return AvailabilityVoteCrudMapper.mapToEntities(dtos);
    }

    public static List<AvailabilityVoteDto> mapToDtos(List<AvailabilityVoteEntity> entities) {
        return AvailabilityVoteCrudMapper.mapToDtos(entities);
    }
}
