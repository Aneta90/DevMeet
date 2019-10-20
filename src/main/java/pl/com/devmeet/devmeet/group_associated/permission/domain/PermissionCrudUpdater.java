package pl.com.devmeet.devmeet.group_associated.permission.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityUpdater;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class PermissionCrudUpdater implements CrudEntityUpdater<PermissionDto, PermissionEntity> {

    private PermissionCrudFinder permissionCrudFinder;
    private PermissionCrudSaver permissionCrudSaver;

    @Override
    public PermissionEntity updateEntity(PermissionDto oldDto, PermissionDto newDto) throws EntityNotFoundException {
        PermissionEntity oldPermission = findPermissionEntity(oldDto);
        PermissionEntity newPermission = mapDtoToEntity(checkMemberAndGroup(oldDto, newDto));

        return permissionCrudSaver.saveEntity(updateAllowedParameters(oldPermission, newPermission));
    }

    public PermissionEntity changeMemberBanInGroupStatus(PermissionDto oldDto, boolean memberBanInGroupStatus) throws EntityNotFoundException {
        PermissionEntity foundPermission = findPermissionEntity(oldDto);

        if (foundPermission.isMemberBaned() != memberBanInGroupStatus) {
            foundPermission.setMemberBaned(memberBanInGroupStatus);
            foundPermission.setModificationTime(DateTime.now());
            return permissionCrudSaver.saveEntity(foundPermission);
        }
        return foundPermission;
    }

    private PermissionEntity findPermissionEntity(PermissionDto oldDto) throws EntityNotFoundException {
        return permissionCrudFinder.findEntity(oldDto);
    }

    private PermissionDto checkMemberAndGroup(PermissionDto oldDto, PermissionDto newDto) {
        return null;
    }

    private PermissionEntity mapDtoToEntity(PermissionDto dto) {
        return PermissionCrudFacade.map(dto);
    }

    private PermissionEntity updateAllowedParameters(PermissionEntity oldEntity, PermissionEntity newEntity) {
        oldEntity.setPossibleToVote(newEntity.isPossibleToVote());
        oldEntity.setPossibleToMessaging(newEntity.isPossibleToMessaging());
        oldEntity.setPossibleToChangeGroupName(newEntity.isPossibleToChangeGroupName());
        oldEntity.setMemberBaned(newEntity.isMemberBaned());

        oldEntity.setMemberBaned(newEntity.isMemberBaned());

        oldEntity.setModificationTime(DateTime.now());
        return oldEntity;
    }
}