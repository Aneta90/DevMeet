package pl.com.devmeet.devmeet.member_associated.member.domain;

import org.springframework.beans.factory.annotation.Autowired;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeet.member_associated.place.domain.PlaceDto;

import java.util.List;
import java.util.Optional;

public class MemberCrudFacade implements MemberCrudInterface {

    @Autowired
    private MemberRepository memberRepository;

    public MemberCrudFacade(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    private MemberCrudFinder finderInit() {
        return new MemberCrudFinder(memberRepository);
    }

    private MemberCrudCreator creatorInit() {
        return new MemberCrudCreator(memberRepository);
    }

    private MemberCrudDeleter deleterInit() {
        return new MemberCrudDeleter(memberRepository);
    }

    private MemberCrudUpdater updateInit() {return new MemberCrudUpdater(memberRepository);}

    @Override
    public MemberDto create(MemberDto dto) throws MemberNotFoundException, MemberAlreadyExistsException {
        return map(creatorInit().createEntity(dto));
    }

    @Override
    public MemberDto read(MemberDto dto) throws MemberNotFoundException {
        return map(finderInit().findEntity(dto));
    }

    @Override
    public MemberDto update(MemberDto oldDto, MemberDto newDto) throws MemberNotFoundException {
        return map(updateInit().updateEntity(oldDto, newDto));
    }

    @Override
    public MemberEntity delete(MemberDto dto) throws MemberNotFoundException {
        return deleterInit().deleteEntity(dto);
    }

    @Override
    public boolean isActive(MemberDto memberDto) throws MemberNotFoundException {
        return finderInit().findEntity(memberDto).isActive();
    }

    @Override
    public boolean doesExist(MemberDto memberDto) throws MemberNotFoundException {
        return finderInit().isExist(memberDto);
    }

    @Override
    public MemberEntity findEntity(MemberDto dto) throws MemberNotFoundException {
        return finderInit().findEntity(dto);
    }

    public Optional<List<MemberEntity>> memberEntityList(GroupDto groupDto){
       return finderInit().memberListByGroup(groupDto);
    }

    public Optional<List<MemberEntity>> memberEntityList(PlaceDto placeDto){
        return finderInit().memberListByPlace(placeDto);
    }

    public static MemberEntity map(MemberDto dto) {
        return MemberMapper.map(dto);
    }

    public static MemberDto map(MemberEntity entity) {
        return MemberMapper.map(entity);
    }
}
