package pl.com.devmeet.devmeetcore.poll_associated.availability_vote.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.devmeet.devmeetcore.domain_utils.CrudFacadeInterface;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.GroupCrudRepository;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeetcore.member_associated.availability.domain.AvailabilityCrudFacade;
import pl.com.devmeet.devmeetcore.member_associated.availability.domain.AvailabilityCrudRepository;
import pl.com.devmeet.devmeetcore.member_associated.availability.domain.status_and_exceptions.AvailabilityNotFoundException;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberRepository;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.domain.MessengerRepository;
import pl.com.devmeet.devmeetcore.poll_associated.availability_vote.domain.status_and_exceptions.AvailabilityVoteAlreadyExistsException;
import pl.com.devmeet.devmeetcore.poll_associated.availability_vote.domain.status_and_exceptions.AvailabilityVoteException;
import pl.com.devmeet.devmeetcore.poll_associated.availability_vote.domain.status_and_exceptions.AvailabilityVoteFoundButNotActiveException;
import pl.com.devmeet.devmeetcore.poll_associated.availability_vote.domain.status_and_exceptions.AvailabilityVoteNotFoundException;
import pl.com.devmeet.devmeetcore.poll_associated.poll.domain.PollCrudFacade;
import pl.com.devmeet.devmeetcore.poll_associated.poll.domain.PollCrudRepository;
import pl.com.devmeet.devmeetcore.poll_associated.poll.domain.status_and_exceptions.PollNotFoundException;
import pl.com.devmeet.devmeetcore.user.domain.UserRepository;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserNotFoundException;

import java.util.List;

@Service
public class AvailabilityVoteCrudFacade implements CrudFacadeInterface<AvailabilityVoteDto, AvailabilityVoteEntity> {

    private AvailabilityVoteCrudRepository availabilityVoteRepository;
    private PollCrudRepository pollCrudRepository;
    private GroupCrudRepository groupRepository;
    private AvailabilityCrudRepository availabilityRepository;
    private MemberRepository memberRepository;
    private UserRepository userRepository;
    private MessengerRepository messengerRepository;

    @Autowired
    public AvailabilityVoteCrudFacade(AvailabilityVoteCrudRepository availabilityVoteRepository, PollCrudRepository pollCrudRepository, GroupCrudRepository groupRepository, AvailabilityCrudRepository availabilityRepository, MemberRepository memberRepository, UserRepository userRepository, MessengerRepository messengerRepository) {
        this.availabilityVoteRepository = availabilityVoteRepository;
        this.pollCrudRepository = pollCrudRepository;
        this.groupRepository = groupRepository;
        this.availabilityRepository = availabilityRepository;
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
        this.messengerRepository = messengerRepository;
    }

    private AvailabilityVotePollFinder initPollFinder() {
        return new AvailabilityVotePollFinder(new PollCrudFacade(pollCrudRepository, groupRepository, memberRepository, userRepository, messengerRepository));
    }

    private AvailabilityVoteMemberFinder initMemberFinder() {
        return new AvailabilityVoteMemberFinder(new MemberCrudFacade(memberRepository, userRepository, messengerRepository, groupRepository));
    }

    private AvailabilityVoteAvailabilityFinder initAvailabilityFinder() {
        return new AvailabilityVoteAvailabilityFinder(new AvailabilityCrudFacade(availabilityRepository, memberRepository, userRepository, messengerRepository, groupRepository));
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
                .availabilityFinder(initAvailabilityFinder())
                .build();
    }

    private AvailabilityVoteCrudDeleter initVoteDeleter() {
        return AvailabilityVoteCrudDeleter.builder()
                .voteCrudFinder(initVoteFinder())
                .voteCrudSaver(initVoteSaver())
                .build();
    }

    @Override
    public AvailabilityVoteDto add(AvailabilityVoteDto dto) throws AvailabilityNotFoundException, GroupNotFoundException, AvailabilityVoteAlreadyExistsException, PollNotFoundException, MemberNotFoundException, UserNotFoundException {
        return map(initVoteCreator().createEntity(dto));
    }

    public AvailabilityVoteDto find(AvailabilityVoteDto dto) throws MemberNotFoundException, UserNotFoundException, AvailabilityVoteNotFoundException, GroupNotFoundException, PollNotFoundException {
        return map(findEntity(dto));
    }

    public List<AvailabilityVoteDto> findAll(AvailabilityVoteDto dto) throws GroupNotFoundException, AvailabilityVoteNotFoundException, PollNotFoundException {
        return mapToDtos(findEntities(dto));
    }

    @Override
    public AvailabilityVoteDto update(AvailabilityVoteDto oldDto, AvailabilityVoteDto newDto) throws AvailabilityVoteNotFoundException, GroupNotFoundException, UserNotFoundException, AvailabilityVoteException, MemberNotFoundException, AvailabilityNotFoundException, PollNotFoundException {
        return map(initVoteUpdater().updateEntity(oldDto, newDto));
    }

    @Override
    public AvailabilityVoteDto delete(AvailabilityVoteDto dto) throws AvailabilityNotFoundException, GroupNotFoundException, UserNotFoundException, AvailabilityVoteNotFoundException, MemberNotFoundException, AvailabilityVoteFoundButNotActiveException, PollNotFoundException {
        return map(initVoteDeleter().deleteEntity(dto));
    }

    public AvailabilityVoteEntity findEntity(AvailabilityVoteDto dto) throws MemberNotFoundException, UserNotFoundException, AvailabilityVoteNotFoundException, GroupNotFoundException, PollNotFoundException {
        return initVoteFinder().findEntity(dto);
    }

    public List<AvailabilityVoteEntity> findEntities(AvailabilityVoteDto dto) throws GroupNotFoundException, AvailabilityVoteNotFoundException, PollNotFoundException {
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
