package pl.com.devmeet.devmeet.member_associated.availability.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.devmeet.devmeet.domain_utils.CrudFacadeInterface;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityNotFoundException;
import pl.com.devmeet.devmeet.member_associated.availability.domain.status_and_exceptions.AvailabilityAlreadyExistsException;
import pl.com.devmeet.devmeet.member_associated.availability.domain.status_and_exceptions.AvailabilityException;
import pl.com.devmeet.devmeet.member_associated.availability.domain.status_and_exceptions.AvailabilityNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberRepository;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.user.domain.UserRepository;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

import java.util.List;

import static pl.com.devmeet.devmeet.member_associated.availability.domain.AvailabilityCrudMapper.mapDtoList;

@Service
public class AvailabilityCrudFacade implements CrudFacadeInterface<AvailabilityDto, AvailabilityEntity> {

    private AvailabilityCrudRepository availabilityRepository;
    private MemberRepository memberRepository;
    private UserRepository userRepository;

    @Autowired
    public AvailabilityCrudFacade(AvailabilityCrudRepository availabilityRepository, MemberRepository memberRepository, UserRepository userRepository) {
        this.availabilityRepository = availabilityRepository;
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
    }

    private AvailabilityMemberFinder initMemberFinder() {
        return  new AvailabilityMemberFinder().builder()
                .memberCrudFacade(new MemberCrudFacade(memberRepository, userRepository))
                .build();
    }


    private AvailabilityCrudCreator initCreator() {
        return new AvailabilityCrudCreator().builder()
                .availabilityCrudFinder(initFinder())
                .availabilityCrudSaver(initSaver())
                .build();
    }

    private AvailabilityCrudSaver initSaver() {
        return new AvailabilityCrudSaver().builder()
                .availabilityCrudRepository(availabilityRepository)
                .memberFinder(initMemberFinder())
                .build();
    }
    private AvailabilityCrudFinder initFinder() {
        return new AvailabilityCrudFinder().builder()
                .availabilityRepository(availabilityRepository)
                .memberFinder(initMemberFinder())
                .build();
    }

    private AvailabilityCrudUpdater initUpdater() {
        return new AvailabilityCrudUpdater().builder()
                .availabilityCrudFinder(initFinder())
                .availabilityCrudSaver(initSaver())
                .build();
    }

    private AvailabilityCrudDeleter initDeleter() {

        return new AvailabilityCrudDeleter().builder()
                .availabilityCrudFinder(initFinder())
                .availabilityCrudSaver(initSaver())
                .build();
    }


    @Override
    public AvailabilityDto create(AvailabilityDto dto) throws MemberNotFoundException, AvailabilityAlreadyExistsException, UserNotFoundException {
        return map(initCreator().createEntity(dto));
    }


    @Override
    public AvailabilityDto read(AvailabilityDto dto) throws MemberNotFoundException, AvailabilityNotFoundException, UserNotFoundException {
        return map(initFinder().findEntity(dto));
    }

    @Override
    public List<AvailabilityDto> readAll(AvailabilityDto dto) throws MemberNotFoundException, AvailabilityNotFoundException, UserNotFoundException {
        return mapDtoList(initFinder().findEntities(dto));
    }

    @Override
    public AvailabilityDto update(AvailabilityDto oldDto, AvailabilityDto newDto) throws UserNotFoundException, AvailabilityNotFoundException, AvailabilityException, MemberNotFoundException {
        return map(initUpdater().updateEntity(oldDto, newDto));
    }

    @Override
    public AvailabilityDto delete(AvailabilityDto dto) throws UserNotFoundException, AvailabilityNotFoundException, MemberNotFoundException, AvailabilityAlreadyExistsException {
        return map(initDeleter().deleteEntity(dto));
    }

    @Override
    public AvailabilityEntity findEntity(AvailabilityDto dto) throws MemberNotFoundException, AvailabilityNotFoundException, UserNotFoundException {
        return initFinder().findEntity(dto);
    }

    @Override
    public List<AvailabilityEntity> findEntities(AvailabilityDto dto) throws MemberNotFoundException, AvailabilityNotFoundException, UserNotFoundException {
        return initFinder().findEntities(dto);
    }

    public static AvailabilityDto map(AvailabilityEntity entity) {
        return AvailabilityCrudMapper.map(entity);
    }


    public static AvailabilityEntity map(AvailabilityDto dto) {
        return AvailabilityCrudMapper.map(dto);

    }
}
