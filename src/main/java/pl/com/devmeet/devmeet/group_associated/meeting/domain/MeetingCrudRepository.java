package pl.com.devmeet.devmeet.group_associated.meeting.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MeetingCrudRepository extends PagingAndSortingRepository<MeetingEntity,UUID> {

    @Query("SELECT m from MeetingEntity m where m.meetingNumber = ?1")
    Optional <MeetingEntity> findMeetingByMeetingNumber(Integer meetingNumber);

    @Query("SELECT m from MeetingEntity m where m.group.groupName = ?1") //like?
    Optional <List<MeetingEntity>> findMeetingByGroup(String groupName);
}
