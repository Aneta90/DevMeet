package pl.com.devmeet.devmeet.group_associated.permission.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityUpdater;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeet.group_associated.permission.domain.status_and_exceptions.PermissionCrudStatusEnum;
import pl.com.devmeet.devmeet.group_associated.permission.domain.status_and_exceptions.PermissionException;
import pl.com.devmeet.devmeet.group_associated.permission.domain.status_and_exceptions.PermissionNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class PermissionCrudUpdater implements CrudEntityUpdater<PermissionDto, PermissionEntity> {

    private PermissionCrudFinder permissionCrudFinder;
    private PermissionCrudSaver permissionCrudSaver;

    @Override
    public PermissionEntity updateEntity(PermissionDto oldDto, PermissionDto newDto) throws GroupNotFoundException, MemberNotFoundException, UserNotFoundException, PermissionException, PermissionNotFoundException {
        PermissionEntity oldPermission = findPermissionEntity(oldDto);
        PermissionEntity newPermission = mapDtoToEntity(checkMemberAndGroup(oldDto, newDto));

        return permissionCrudSaver.saveEntity(updateAllowedParameters(oldPermission, newPermission));
    }

    public PermissionEntity changeMemberBanInGroupStatus(PermissionDto oldDto, boolean memberBanInGroupStatus) throws GroupNotFoundException, MemberNotFoundException, UserNotFoundException, PermissionNotFoundException {
        PermissionEntity foundPermission = findPermissionEntity(oldDto);

        if (foundPermission.isMemberBaned() != memberBanInGroupStatus) {
            foundPermission.setMemberBaned(memberBanInGroupStatus);
            foundPermission.setModificationTime(DateTime.now());

            return permissionCrudSaver.saveEntity(foundPermission);
        }
        return foundPermission;
    }

    private PermissionEntity findPermissionEntity(PermissionDto oldDto) throws UserNotFoundException, GroupNotFoundException, MemberNotFoundException, PermissionNotFoundException {
        return permissionCrudFinder.findEntity(oldDto);
    }

    private PermissionDto checkMemberAndGroup(PermissionDto oldDto, PermissionDto newDto) throws PermissionException {
        if (oldDto.getMember().getNick().equals(newDto.getMember().getNick()))
            if(oldDto.getGroup().getGroupName().equals(newDto.getGroup().getGroupName()))
                return newDto;
        throw new PermissionException(PermissionCrudStatusEnum.INCORRECT_MEMBER_OR_GROUP.toString());
    }

    private PermissionEntity mapDtoToEntity(PermissionDto dto) {
        return PermissionCrudFacade.map(dto);
    }

    private PermissionEntity updateAllowedParameters(PermissionEntity oldEntity, PermissionEntity newEntity) {
        oldEntity.setPossibleToVote(newEntity.isPossibleToVote());
        oldEntity.setPossibleToMessaging(newEntity.isPossibleToMessaging());
        oldEntity.setPossibleToChangeGroupName(newEntity.isPossibleToChangeGroupName());
        oldEntity.setPossibleToBanMember(newEntity.isPossibleToBanMember());

        oldEntity.setMemberBaned(newEntity.isMemberBaned());

        oldEntity.setModificationTime(DateTime.now());
        return oldEntity;
    }
}
