package pl.com.devmeet.devmeet.group_associated.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudFacade;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupAlreadyExistsException;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;

import java.util.List;

@RestController
@RequestMapping("/api/v1/groups")
class GroupApi {

    private GroupCrudFacade group;
    private MemberCrudFacade member;

    @Autowired
    public GroupApi(GroupCrudFacade group, MemberCrudFacade member) {
        this.group = group;
        this.member = member;
    }

    @GetMapping
    public ResponseEntity<List<GroupDto>> getGroups(@RequestParam(required = false) String searchText) throws GroupNotFoundException, GroupAlreadyExistsException {
        return new ResponseEntity<>(group.readAll(group.create(new GroupDto("G1"))), HttpStatus.OK); //new GroupDto to be removed
    }

}
