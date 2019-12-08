package pl.com.devmeet.devmeet.group_associated.group.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupArgumentsNotSpecifiedException;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupCrudStatusEnum;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberEntity;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class GroupCrudFinder {

    private GroupCrudRepository groupCrudRepository;
    private GroupMemberFinder memberFinder;

    public GroupEntity findById(Long id) throws GroupNotFoundException {
        Optional<GroupEntity> found = groupCrudRepository.findById(id);

        if (found.isPresent())
            return found.get();

        throw new GroupNotFoundException(GroupCrudStatusEnum.GROUP_NOT_FOUND.toString());
    }

    public GroupEntity findEntityByGroup(GroupDto dto) throws GroupNotFoundException {
        String groupName;
        String website;
        String description;

        groupName = dto.getGroupName();
        website = dto.getWebsite();
        description = dto.getDescription();

        return findEntityByGroupNameAndWebsiteAndDescription(groupName, website, description);
    }


    public GroupEntity findEntityByGroupNameAndWebsiteAndDescription(String groupName, String website, String description) throws GroupNotFoundException {
        Optional<GroupEntity> group = groupCrudRepository.findByGroupNameAndWebsiteAndDescription(groupName, website, description);

        if (group.isPresent())
            return group.get();
        else
            throw new GroupNotFoundException(GroupCrudStatusEnum.GROUP_NOT_FOUND.toString());
    }

    public boolean isExist(GroupDto dto) {
        try {
            return findEntityByGroup(dto) != null;
        } catch (GroupNotFoundException e) {
            return false;
        }
    }

    public List<GroupEntity> findAllEntities() {
        List<GroupEntity> entities = new ArrayList<>();
        groupCrudRepository.findAll().forEach(entities::add);
        return entities;
    }

    private MemberEntity findMember(MemberDto memberDto) throws MemberNotFoundException, UserNotFoundException {
        return memberFinder.findMember(memberDto);
    }

    private void checkIsGroupNameNotEmpty(String groupName) throws GroupArgumentsNotSpecifiedException {
        if (groupName.isEmpty())
            throw new GroupArgumentsNotSpecifiedException(GroupCrudStatusEnum.ARGUMENTS_NOT_SPECIFIED.toString());
    }
}
