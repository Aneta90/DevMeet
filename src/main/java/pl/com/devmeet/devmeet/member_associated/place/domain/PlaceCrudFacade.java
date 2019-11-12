package pl.com.devmeet.devmeet.member_associated.place.domain;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.devmeet.devmeet.domain_utils.CrudFacadeMode;
import pl.com.devmeet.devmeet.domain_utils.CrudInterface;
import pl.com.devmeet.devmeet.domain_utils.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberRepository;
import pl.com.devmeet.devmeet.member_associated.place.domain.PlaceCrudCreator;
import pl.com.devmeet.devmeet.member_associated.place.domain.PlaceCrudRepository;
import pl.com.devmeet.devmeet.member_associated.place.domain.PlaceCrudSaver;
import pl.com.devmeet.devmeet.member_associated.place.domain.PlaceCrudUpdater;
import pl.com.devmeet.devmeet.user.domain.UserRepository;

import java.util.List;

import static pl.com.devmeet.devmeet.member_associated.place.domain.PlaceCrudMapper.mapDtoList;

@Service
public class PlaceCrudFacade implements CrudInterface<PlaceDto, PlaceEntity> {

    private PlaceCrudRepository placeRepository;
    private MemberRepository memberRepository;
    private UserRepository userRepository;

    @Autowired
    public PlaceCrudFacade(PlaceCrudRepository placeRepository, MemberRepository memberRepository, UserRepository userRepository) {
        this.placeRepository = placeRepository;
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
    }

    private PlaceMemberFinder initMemberFinder() {
        return  new PlaceMemberFinder().builder()
                .memberCrudFacade(new MemberCrudFacade(memberRepository, userRepository))
                .build();
    }


    private PlaceCrudCreator initCreator() {
        return new PlaceCrudCreator().builder()
                .placeCrudFinder(initFinder())
                .placeCrudSaver(initSaver())
                .build();
    }

    private PlaceCrudSaver initSaver() {
        return new PlaceCrudSaver().builder()
                .placeCrudRepository(placeRepository)
                .memberFinder(initMemberFinder())
                .build();
    }
    private PlaceCrudFinder initFinder() {
        return new PlaceCrudFinder().builder()
                .placeRepository(placeRepository)
                .memberFinder(initMemberFinder())
                .build();
    }

    private PlaceCrudUpdater initUpdater() {
        return new PlaceCrudUpdater().builder()
                .placeCrudFinder(initFinder())
                .placeCrudSaver(initSaver())
                .build();
    }

    private PlaceCrudDeleter initDeleter() {

        return new PlaceCrudDeleter().builder()
                .placeCrudFinder(initFinder())
                .placeCrudSaver(initSaver())
                .build();
    }


    @Override
    public PlaceDto create(PlaceDto dto) throws EntityAlreadyExistsException, EntityNotFoundException {
        return map(initCreator().createEntity(dto));
    }


    @Override
    public PlaceDto read(PlaceDto dto) throws EntityNotFoundException {
        return map(initFinder().findEntity(dto));
    }

    @Override
    public List<PlaceDto> readAll(PlaceDto dto) throws EntityNotFoundException {
        return mapDtoList(initFinder().findEntities(dto));
    }

    @Override
    public PlaceDto update(PlaceDto oldDto, PlaceDto newDto) throws EntityNotFoundException {
        return map(initUpdater().updateEntity(oldDto, newDto));
    }

    @Override
    public PlaceDto delete(PlaceDto dto) throws EntityNotFoundException, EntityAlreadyExistsException {
        return map(initDeleter().deleteEntity(dto));
    }

    @Override
    public PlaceEntity findEntity(PlaceDto dto) throws EntityNotFoundException {
        return initFinder().findEntity(dto);
    }

    @Override
    public List<PlaceEntity> findEntities(PlaceDto dto) throws UnsupportedOperationException, EntityNotFoundException {
        return initFinder().findEntities(dto);
    }

    public static PlaceDto map(PlaceEntity entity) {
        return PlaceCrudMapper.map(entity);
    }


    public static PlaceEntity map(PlaceDto dto) {
        return PlaceCrudMapper.map(dto);

    }
}
