package pl.com.devmeet.devmeetcore.member_associated.place.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeetcore.domain_utils.CrudEntityFinder;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberEntity;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeetcore.member_associated.place.domain.status_and_exceptions.PlaceCrudStatusEnum;
import pl.com.devmeet.devmeetcore.member_associated.place.domain.status_and_exceptions.PlaceNotFoundException;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class PlaceCrudFinder implements CrudEntityFinder<PlaceDto, PlaceEntity> {

    private PlaceCrudSaver placeCrudSaver;
    private PlaceCrudRepository placeRepository;
    private PlaceMemberFinder memberFinder;

    @Override
    public PlaceEntity findEntity(PlaceDto dto) throws PlaceNotFoundException, MemberNotFoundException, UserNotFoundException {
        Optional<PlaceEntity> place = findPlace(dto);
        if (place.isPresent())
            return place.get();
        else
            throw new PlaceNotFoundException(PlaceCrudStatusEnum.PLACE_NOT_FOUND.toString());
    }

    private MemberEntity findMemberEntity(MemberDto member) throws MemberNotFoundException, UserNotFoundException {
        return memberFinder.findMember(member);
    }

    private Optional<PlaceEntity> findPlace(PlaceDto dto) throws MemberNotFoundException, UserNotFoundException {
        MemberEntity member = findMemberEntity(dto.getMember());

        return placeRepository.findByMember(member);
    }

    @Override
    public List<PlaceEntity> findEntities(PlaceDto dto) throws PlaceNotFoundException, MemberNotFoundException, UserNotFoundException {
        Optional<List<PlaceEntity>> placeEntities = findPlaces(dto);

        if (placeEntities.isPresent())
            return placeEntities.get();
        else
            throw new PlaceNotFoundException(PlaceCrudStatusEnum.PLACES_NOT_FOUND.toString());
    }

    private Optional<List<PlaceEntity>> findPlaces(PlaceDto dto) throws MemberNotFoundException, UserNotFoundException {
        MemberEntity member = findMemberEntity(dto.getMember());

        return placeRepository.findAllByMember(member);
    }


    @Override
    public boolean isExist(PlaceDto dto) {
        try {
            findEntity(dto);
            return true;
        } catch (PlaceNotFoundException | MemberNotFoundException | UserNotFoundException e) {
            return false;
        }
    }

}