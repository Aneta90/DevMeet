package pl.com.devmeet.devmeet.member_associated.place.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlaceCrudRepository extends PagingAndSortingRepository<PlaceEntity, UUID> {


    //po memberze- i np. do encji i DTO isUsed~~~
    // Optional<AvailabilityEntity> findByDateTime(DateTime beginTime, DateTime endTime);
    // Optional<AvailabilityEntity> findByBeginTime(DateTime beginTime);
    // Optional<AvailabilityEntity> findByEndTime(DateTime endTime);

    Optional<List<PlaceEntity>> findAllByMember(PlaceEntity member);
}