package pl.com.devmeet.devmeetcore.messenger_associated.messenger.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeetcore.domain_utils.CrudEntitySaver;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberEntity;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberRepository;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class MessengerCrudSaver implements CrudEntitySaver<MessengerEntity, MessengerEntity> {

    private MessengerRepository messengerRepository;
    private MemberRepository memberRepository;

    public MemberEntity saveMessengerByMember(MemberEntity entity){
        return memberRepository.save(entity);
    }

    @Override
    public MessengerEntity saveEntity(MessengerEntity entity) {
        return messengerRepository.save(entity);
    }
}