package pl.com.devmeet.devmeet.group_associated.group.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/groups")
class GroupApi {

    private GroupCrudRepository repository;
    private GroupCrudFacade facade;

    @Autowired
    public GroupApi(GroupCrudRepository repository, GroupCrudFacade facade) {
        this.repository = repository;
        this.facade = facade;
    }

    @GetMapping
    public ResponseEntity<Iterable<GroupEntity>> getGroups(@RequestParam(required = false) String searchText) {
        if (repository.findAll().iterator().hasNext())
            return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
