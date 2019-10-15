package pl.com.devmeet.devmeet.member_associated.member.domain;

import org.springframework.beans.factory.annotation.Autowired;
import pl.com.devmeet.devmeet.domain_utils.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;

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

    private MemberCrudUpdater updateInit() {
        return new MemberCrudUpdater(memberRepository);
    }

    @Override
    public MemberDto create(MemberDto dto) throws EntityNotFoundException, EntityAlreadyExistsException {
        return map(creatorInit().createEntity(dto));
    }

    @Override
    public MemberDto read(MemberDto dto) throws EntityNotFoundException {
        return map(finderInit().findEntity(dto));
    }

    @Override
    public MemberDto update(MemberDto oldDto, MemberDto newDto) throws EntityNotFoundException{
        return map(updateInit().updateEntity(oldDto, newDto));
    }

    @Override
    public MemberEntity delete(MemberDto dto) throws EntityNotFoundException {
        return deleterInit().deleteEntity(dto);
    }

    @Override
    public boolean isActive(MemberDto memberDto) throws EntityNotFoundException {
        return finderInit().findEntity(memberDto).isActive();
    }

    @Override
    public boolean doesExist(MemberDto memberDto) throws EntityNotFoundException {
        return finderInit().isExist(memberDto);
    }

    @Override
    public MemberEntity findEntity(MemberDto dto) throws EntityNotFoundException {
        return finderInit().findEntity(dto);
    }

   /* public Optional<List<MemberEntity>> memberEntityList(GroupDto groupDto){
       return finderInit().memberListByGroup(groupDto);
    }

    public Optional<List<MemberEntity>> memberEntityList(PlaceDto placeDto){
        return finderInit().memberListByPlace(placeDto);
    }*/

    public static MemberEntity map(MemberDto dto) {
        return MemberMapper.map(dto);
    }

    public static MemberDto map(MemberEntity entity) {
        return MemberMapper.map(entity);
    }
}