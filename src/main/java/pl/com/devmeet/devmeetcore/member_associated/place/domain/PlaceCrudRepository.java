package pl.com.devmeet.devmeetcore.member_associated.place.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlaceCrudRepository extends PagingAndSortingRepository<PlaceEntity, UUID> {


    Optional<PlaceEntity> findByMember(MemberEntity member);
    Optional<List<PlaceEntity>> findAllByMember(MemberEntity member);}
