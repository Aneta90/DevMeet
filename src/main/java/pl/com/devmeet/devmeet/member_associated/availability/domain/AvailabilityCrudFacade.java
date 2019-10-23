package pl.com.devmeet.devmeet.member_associated.availability.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.devmeet.devmeet.domain_utils.CrudFacadeMode;
import pl.com.devmeet.devmeet.domain_utils.CrudInterface;
import pl.com.devmeet.devmeet.domain_utils.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.member_associated.availability.domain.status_and_exceptions.AvailabilityCrudInfoStatusEnum;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberRepository;

import java.util.List;

import static pl.com.devmeet.devmeet.member_associated.availability.domain.AvailabilityCrudMapper.mapDtoList;

@Service
public class AvailabilityCrudFacade implements CrudInterface<AvailabilityDto, AvailabilityEntity> {

    private AvailabilityCrudRepository availabilityRepository;
    private MemberRepository memberRepository;

    @Autowired
    public AvailabilityCrudFacade(AvailabilityCrudRepository availabilityRepository, MemberRepository memberRepository) {
        this.availabilityRepository = availabilityRepository;
        this.memberRepository = memberRepository;
    }

    private AvailabilityMemberFinder initMemberFinder() {
        return  new AvailabilityMemberFinder().builder()
                .memberCrudFacade(new MemberCrudFacade(memberRepository))
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
    public AvailabilityDto create(AvailabilityDto dto) throws EntityAlreadyExistsException, EntityNotFoundException {
        return map(initCreator().createEntity(dto));
    }


    @Override
    public AvailabilityDto read(AvailabilityDto dto) throws EntityNotFoundException {
        return map(initFinder().findEntity(dto));
    }

    @Override
    public List<AvailabilityDto> readAll(AvailabilityDto dto) {
        return mapDtoList(initFinder().findEntities(dto));
    }

    @Override
    public AvailabilityDto update(AvailabilityDto oldDto, AvailabilityDto newDto) throws EntityNotFoundException {
        return map(initUpdater().updateEntity(oldDto, newDto));
    }

    @Override
    public AvailabilityDto delete(AvailabilityDto dto) throws EntityNotFoundException, EntityAlreadyExistsException {
        return map(initDeleter().deleteEntity(dto));
    }

    @Override
    public AvailabilityEntity findEntity(AvailabilityDto dto) throws EntityNotFoundException {
        return initFinder().findEntity(dto);
    }

    @Override
    public List<AvailabilityEntity> findEntities(AvailabilityDto dto) throws UnsupportedOperationException {
        return initFinder().findEntities(dto);
    }

    public static AvailabilityDto map(AvailabilityEntity entity) {
        return AvailabilityCrudMapper.map(entity);
    }


    public static AvailabilityEntity map(AvailabilityDto dto) {
        return AvailabilityCrudMapper.map(dto);

    }
}
