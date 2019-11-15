package pl.com.devmeet.devmeet.group_associated.permission.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityNotFoundException;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudFacade;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupEntity;
import pl.com.devmeet.devmeet.group_associated.permission.domain.status_and_exceptions.PermissionCrudStatusEnum;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class PermissionGroupFinder {

    private GroupCrudFacade groupCrudFacade;

    public GroupEntity findGroup(GroupDto dto) throws EntityNotFoundException {
        try {
            return groupCrudFacade.findEntity(dto);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(PermissionCrudStatusEnum.PERMISSION_GROUP_NOT_FOUND.toString());
        }
    }
}
