package pl.com.devmeet.devmeet.member_associated.availability.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailabilityCrudRepository extends PagingAndSortingRepository<AvailabilityEntity, Long> {
}
