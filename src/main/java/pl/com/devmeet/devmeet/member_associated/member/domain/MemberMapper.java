package pl.com.devmeet.devmeet.member_associated.member.domain;

import pl.com.devmeet.devmeet.messenger_associated.messenger.domain.MessengerCrudFacade;
import pl.com.devmeet.devmeet.user.domain.UserCrudFacade;

import java.util.List;
import java.util.stream.Collectors;

class MemberMapper {

    static MemberEntity map(MemberDto dto) {
        return dto != null ? new MemberEntity().builder()
                .user(UserCrudFacade.map(dto.getUser()))
                .nick(dto.getNick())
//                      .groups(dto.getGroups())
//                      .availabilities(dto.getAvailabilities())
//                      .places(dto.getPlaces())
                .messenger(MessengerCrudFacade.map(dto.getMessenger()))
                .creationTime(dto.getCreationTime())
                .modificationTime(dto.getModificationTime())
                .isActive(dto.isActive())
                .build() : null;
    }

    static MemberDto map(MemberEntity memberEntity) {
        return memberEntity != null ? new MemberDto().builder()
                .user(UserCrudFacade.map(memberEntity.getUser()))
                .nick(memberEntity.getNick())
                //      .groups(memberEntity.getGroups())
                //      .availabilities(memberEntity.getAvailabilities())
                //      .places(memberEntity.getPlaces())
                .messenger(MessengerCrudFacade.map(memberEntity.getMessenger()))
                .creationTime(memberEntity.getCreationTime())
                .modificationTime(memberEntity.getModificationTime())
                .isActive(memberEntity.isActive())
                .build() : null;
    }

    static List<MemberEntity> mapDtoList(List<MemberDto> memberDtoList) {
        return memberDtoList.stream()
                .map(MemberMapper::map)
                .collect(Collectors.toList());
    }

    static List<MemberDto> mapEntityList(List<MemberEntity> memberEntityList) {
        return memberEntityList.stream()
                .map(MemberMapper::map)
                .collect(Collectors.toList());
    }
}