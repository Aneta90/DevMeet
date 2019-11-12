package pl.com.devmeet.devmeet.group_associated.permission.domain;

import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;

class PermissionCrudMapper {

    public static PermissionDto map (PermissionEntity entity){
        return entity != null ? new PermissionDto().builder()
                .member(MemberCrudFacade.map(entity.getMember()))
                .group(GroupCrudFacade.map(entity.getGroup()))
                .possibleToVote(entity.isPossibleToVote())
                .possibleToMessaging(entity.isPossibleToMessaging())
                .possibleToChangeGroupName(entity.isPossibleToChangeGroupName())
                .possibleToBanMember(entity.isPossibleToBanMember())
                .memberBaned(entity.isMemberBaned())
                .creationTime(entity.getCreationTime())
                .modificationTime(entity.getModificationTime())
                .isActive(entity.isActive())
                .build() : null;
    }

    public static PermissionEntity map (PermissionDto dto){
        return dto != null ? new PermissionEntity().builder()
                .member(MemberCrudFacade.map(dto.getMember()))
                .group(GroupCrudFacade.map(dto.getGroup()))
                .possibleToVote(dto.isPossibleToVote())
                .possibleToMessaging(dto.isPossibleToMessaging())
                .possibleToChangeGroupName(dto.isPossibleToChangeGroupName())
                .possibleToBanMember(dto.isPossibleToBanMember())
                .memberBaned(dto.isMemberBaned())
                .creationTime(dto.getCreationTime())
                .modificationTime(dto.getModificationTime())
                .isActive(dto.isActive())
                .build() : null;
    }
}
