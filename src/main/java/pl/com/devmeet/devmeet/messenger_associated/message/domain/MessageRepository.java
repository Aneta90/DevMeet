package pl.com.devmeet.devmeet.messenger_associated.message.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends PagingAndSortingRepository<MessageEntity,UUID> {

    @Query("select m from MessageEntity m where m.fromMember.nick = :memberNick")
    List<MessageEntity> findAllByFromMember(@Param("memberNick")String memberNick); //znajdź wszystkie wiadomości wysłane przez danego Membera

    @Query("select m from MessageEntity m where m.toMember.nick = :memberNick")
    List<MessageEntity> findAllByToMember(@Param("memberNick") String memberNick); //znajdź wszystkie wiadomości wysłane do danego Membera

}