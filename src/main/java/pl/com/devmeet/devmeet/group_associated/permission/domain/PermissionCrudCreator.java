package pl.com.devmeet.devmeet.group_associated.permission.domain;

import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityCreator;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudRepository;
import pl.com.devmeet.devmeet.group_associated.permission.domain.status.PermissionCrudInfoStatusEnum;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberRepository;

class PermissionCrudCreator implements CrudEntityCreator<PermissionDto, PermissionEntity> {

    private PermissionCrudSaver permissionCrudSaver;
    private PermissionCrudFinder permissionCrudFinder;

    public PermissionCrudCreator(PermissionCrudRepository permissionRepository, GroupCrudRepository groupRepository, MemberRepository memberRepository) {
        this.permissionCrudSaver = new PermissionCrudSaver(permissionRepository);
        this.permissionCrudFinder = new PermissionCrudFinder(permissionRepository, groupRepository, memberRepository);
    }

    @Override
    public PermissionEntity createEntity(PermissionDto dto) throws IllegalArgumentException, EntityNotFoundException {
        PermissionEntity permission;

        try {
            permission = permissionCrudFinder.findEntity(dto);

            if (!permission.isActive() && permission.getModificationTime() != null)
                return permissionCrudSaver.saveEntity(setDefaultValuesWhenPermissionExist(permission));

        } catch (IllegalArgumentException e) {
            return permissionCrudSaver.saveEntity(setDefaultValuesWhenPermissionNotExist(PermissionCrudFacade.map(dto)));
        }

        throw new IllegalArgumentException(PermissionCrudInfoStatusEnum.PERMISSION_ALREADY_EXISTS.toString());
    }

    private PermissionEntity setDefaultValuesWhenPermissionNotExist(PermissionEntity permission) {
        permission.setActive(true);
        permission.setCreationTime(DateTime.now());
        return permission;
    }

    private PermissionEntity setDefaultValuesWhenPermissionExist(PermissionEntity permission) {
        permission.setActive(true);
        permission.setModificationTime(DateTime.now());
        return permission;
    }
}
