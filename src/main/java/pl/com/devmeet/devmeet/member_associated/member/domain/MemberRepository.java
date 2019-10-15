package pl.com.devmeet.devmeet.member_associated.member.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends PagingAndSortingRepository<MemberEntity, UUID> {

    Optional<MemberEntity> findByNick(String nick);

    //@Query("Select m from Entity m where m.groups. = ?1") // do sprawd.
    //Optional<List<MemberEntity>> findByPlace(String nick);

    //@Query("Select g from GroupEntity g where g.members.nick = ?1") //do sprawdz.
    //Optional<List<MemberEntity>>findByGroup(String nick);
}
