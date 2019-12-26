package pl.com.devmeet.devmeet.poll_associated.poll.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.devmeet.devmeet.domain_utils.CrudErrorEnum;
import pl.com.devmeet.devmeet.domain_utils.CrudFacadeInterface;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudFacade;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudRepository;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberRepository;
import pl.com.devmeet.devmeet.messenger_associated.messenger.domain.MessengerRepository;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.status_and_exceptions.PollAlreadyExistsException;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.status_and_exceptions.PollNotFoundException;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.status_and_exceptions.PollUnsupportedOperationException;
import pl.com.devmeet.devmeet.user.domain.UserRepository;

import java.util.List;

@Service
public class PollCrudFacade implements CrudFacadeInterface<PollDto, PollEntity> {

    private PollCrudRepository pollCrudRepository;
    private GroupCrudRepository groupCrudRepository;
    private MemberRepository memberRepository;
    private UserRepository userRepository;
    private MessengerRepository messengerRepository;

    @Autowired
    public PollCrudFacade(PollCrudRepository pollCrudRepository, GroupCrudRepository groupCrudRepository, MemberRepository memberRepository, UserRepository userRepository, MessengerRepository messengerRepository) {
        this.pollCrudRepository = pollCrudRepository;
        this.groupCrudRepository = groupCrudRepository;
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
        this.messengerRepository = messengerRepository;
    }

    private PollGroupFinder initGroupFinder() {
        return new PollGroupFinder().builder()
                .groupCrudFacade(new GroupCrudFacade(groupCrudRepository, memberRepository, userRepository, messengerRepository))
                .build();
    }

    private PollCrudSaver initSaver() {
        return new PollCrudSaver().builder()
                .pollCrudRepository(pollCrudRepository)
                .pollGroupFinder(initGroupFinder())
                .build();
    }

    private PollCrudFinder initFinder() {
        return new PollCrudFinder().builder()
                .pollCrudRepository(pollCrudRepository)
                .groupFinder(initGroupFinder())
                .build();
    }

    private PollCrudCreator initCreator() {
        return new PollCrudCreator().builder()
                .pollCrudSaver(initSaver())
                .pollCrudFinder(initFinder())
                .build();
    }

    private PollCrudDeleter initDeleter() {
        return new PollCrudDeleter().builder()
                .pollCrudSaver(initSaver())
                .pollCrudFinder(initFinder())
                .build();
    }

    @Override
    public PollDto add(PollDto dto) throws PollAlreadyExistsException, GroupNotFoundException {
        return map(initCreator().createEntity(dto));
    }

    public PollDto find(PollDto dto) throws GroupNotFoundException, PollNotFoundException {
        return map(findEntity(dto));
    }

    public List<PollDto> findAll(PollDto dto) {
        throw new UnsupportedOperationException(CrudErrorEnum.METHOD_NOT_IMPLEMENTED.toString());
    }

    @Override
    public PollDto update(PollDto oldDto, PollDto newDto) throws PollUnsupportedOperationException {
        throw new PollUnsupportedOperationException(CrudErrorEnum.METHOD_NOT_IMPLEMENTED.toString());
    }

    @Override
    public PollDto delete(PollDto dto) throws GroupNotFoundException, PollNotFoundException, PollAlreadyExistsException {
        return map(initDeleter().deleteEntity(dto));
    }

    public PollEntity findEntity(PollDto dto) throws GroupNotFoundException, PollNotFoundException {
        return initFinder().findEntity(dto);
    }

    public List<PollEntity> findEntities(PollDto dto) throws PollUnsupportedOperationException {
        throw new PollUnsupportedOperationException(CrudErrorEnum.METHOD_NOT_IMPLEMENTED.toString());
    }

    public static PollDto map(PollEntity entity) {
        return PollCrudMapper.map(entity);
    }

    public static PollEntity map(PollDto dto) {
        return PollCrudMapper.map(dto);
    }

    public static List<PollDto> mapToDtos(List<PollEntity> entities) {
        return PollCrudMapper.mapToDtos(entities);
    }

    public static List<PollEntity> mapToEntities(List<PollDto> dtos) {
        return PollCrudMapper.mapToEntities(dtos);
    }
}
