package pl.com.devmeet.devmeet.member_associated.place.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityFinder;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberEntity;
import pl.com.devmeet.devmeet.member_associated.place.domain.status_and_exceptions.PlaceCrudStatusEnum;

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
        public PlaceEntity findEntity(PlaceDto dto) throws EntityNotFoundException {
            Optional<PlaceEntity> place = findPlace(dto);
            if (place.isPresent())
                return place.get();
            else
                throw new EntityNotFoundException(PlaceCrudStatusEnum.PLACE_NOT_FOUND.toString());
        }

        private MemberEntity findMemberEntity(MemberDto member) throws EntityNotFoundException {
            return memberFinder.findMember(member);
        }

        private Optional<PlaceEntity> findPlace(PlaceDto dto) throws EntityNotFoundException {
            MemberEntity member;
            try {
                member = findMemberEntity(dto.getMember());
            } catch (EntityNotFoundException e) {
                throw new EntityNotFoundException(PlaceCrudStatusEnum.PLACE_MEMBER_NOT_FOUND.toString());
            }
            return placeRepository.findByMember(member);
        }

        @Override
        public List<PlaceEntity> findEntities(PlaceDto dto) throws EntityNotFoundException {
            Optional<List<PlaceEntity>> placeEntities = findPlaces(dto);

            if(placeEntities.isPresent())
                return placeEntities.get();
            else
                throw new EntityNotFoundException(PlaceCrudStatusEnum.PLACES_NOT_FOUND.toString());
        }

        private Optional<List<PlaceEntity>> findPlaces(PlaceDto dto) throws EntityNotFoundException {
            MemberEntity member;
            try {
                member = findMemberEntity(dto.getMember());
            } catch (EntityNotFoundException e) {
                throw new EntityNotFoundException(PlaceCrudStatusEnum.PLACE_MEMBER_NOT_FOUND.toString());
            }
            return placeRepository.findAllByMember(member);
        }


        @Override
        public boolean isExist(PlaceDto dto) {
            try {
                return findPlace(dto).isPresent();
            } catch (EntityNotFoundException e) {
                return false;
            }
        }

    }