package pl.com.devmeet.devmeet.member_associated.place.domain;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.devmeet.devmeet.domain_utils.CrudFacadeInterface;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberRepository;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.member_associated.place.domain.status_and_exceptions.PlaceAlreadyExistsException;
import pl.com.devmeet.devmeet.member_associated.place.domain.status_and_exceptions.PlaceFoundButNotActiveException;
import pl.com.devmeet.devmeet.member_associated.place.domain.status_and_exceptions.PlaceNotFoundException;
import pl.com.devmeet.devmeet.user.domain.UserRepository;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

import java.util.List;

import static pl.com.devmeet.devmeet.member_associated.place.domain.PlaceCrudMapper.mapDtoList;

@Service
public class PlaceCrudFacade implements CrudFacadeInterface<PlaceDto, PlaceEntity> {

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
    public PlaceDto add(PlaceDto dto) throws MemberNotFoundException, UserNotFoundException, PlaceAlreadyExistsException {
        return map(initCreator().createEntity(dto));
    }

    public PlaceDto find(PlaceDto dto) throws MemberNotFoundException, PlaceNotFoundException, UserNotFoundException {
        return map(initFinder().findEntity(dto));
    }

    public List<PlaceDto> findAll(PlaceDto dto) throws MemberNotFoundException, PlaceNotFoundException, UserNotFoundException {
        return mapDtoList(initFinder().findEntities(dto));
    }

    @Override
    public PlaceDto update(PlaceDto oldDto, PlaceDto newDto) throws MemberNotFoundException, PlaceNotFoundException, UserNotFoundException {
        return map(initUpdater().updateEntity(oldDto, newDto));
    }

    @Override
    public PlaceDto delete(PlaceDto dto) throws UserNotFoundException, MemberNotFoundException, PlaceNotFoundException, PlaceFoundButNotActiveException {
        return map(initDeleter().deleteEntity(dto));
    }

    public PlaceEntity findEntity(PlaceDto dto) throws MemberNotFoundException, PlaceNotFoundException, UserNotFoundException {
        return initFinder().findEntity(dto);
    }

    public List<PlaceEntity> findEntities(PlaceDto dto) throws MemberNotFoundException, PlaceNotFoundException, UserNotFoundException {
        return initFinder().findEntities(dto);
    }

    public static PlaceDto map(PlaceEntity entity) {
        return PlaceCrudMapper.map(entity);
    }


    public static PlaceEntity map(PlaceDto dto) {
        return PlaceCrudMapper.map(dto);

    }
}
