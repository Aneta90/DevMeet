package pl.com.devmeet.devmeet.member_associated.member.domain;

import java.util.List;
import java.util.stream.Collectors;

class MemberMapper {

    static MemberEntity map(MemberDto dto) {

        return new MemberEntity().builder()
                //     .user(dto.getUser())
                .nick(dto.getNick())
                //      .groups(dto.getGroups())
                //      .availabilities(dto.getAvailabilities())
                //      .places(dto.getPlaces())
                //      .messenger(dto.getMessenger())
                .creationTime(dto.getCreationTime())
                .modificationTime(dto.getModificationTime())
                .isActive(dto.isActive())
                .build();
    }

    static MemberDto map(MemberEntity memberEntity) {

        return new MemberDto().builder()
                //    .user(memberEntity.getUser())
                .nick(memberEntity.getNick())
                //      .groups(memberEntity.getGroups())
                //      .availabilities(memberEntity.getAvailabilities())
                //      .places(memberEntity.getPlaces())
                //      .messenger(memberEntity.getMessenger())
                .creationTime(memberEntity.getCreationTime())
                .modificationTime(memberEntity.getModificationTime())
                .isActive(memberEntity.isActive())
                .build();
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