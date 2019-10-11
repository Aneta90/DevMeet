package pl.com.devmeet.devmeet.member_associated.member.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends PagingAndSortingRepository<MemberEntity,UUID> {

    Optional<MemberEntity> findByNick(String nick);

   // @Query("Select m from MemberEntity m where m.getGroups.groupName = ?1") // do sprawd.
    Optional<List<MemberEntity>> findByGroup(String groupName);

  //  @Query("Select m from MemberEntity m where m.getPlaces.getPlaceName = ?1") //do sprawdz.
    Optional<List<MemberEntity>>findByPlace(String placeName);

  /*  public static void getName() {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.getGroups().get(0).get
    }*/

}
