package ro.ubb.ideamanagerbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ro.ubb.ideamanagerbackend.dto.IdeaDto;
import ro.ubb.ideamanagerbackend.exception.IdeaServiceException;
import ro.ubb.ideamanagerbackend.mapper.IdeaMapper;
import ro.ubb.ideamanagerbackend.model.IdeaEntity;
import ro.ubb.ideamanagerbackend.model.UserEntity;
import ro.ubb.ideamanagerbackend.repository.IdeaRepository;
import ro.ubb.ideamanagerbackend.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IdeaServiceImpl implements IdeaService {

    private final IdeaRepository ideaRepository;
    private final IdeaMapper ideaMapper;
    private final UserRepository userRepository;

    private UserEntity getUserForIdea(Long id){
        UserEntity user;
        if(id != null){
            user = userRepository.getById(id);
        }else{
            try{
                var currentUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                user = userRepository.getById(currentUser.getId());
            }catch (Exception e){
                user = userRepository.getById(1L);
            }
        }
        return user;
    }

    private IdeaDto createIdeaModel(IdeaEntity e, IdeaDto dto, UserEntity user) {
        e.setRateCount(dto.getRateCount());
        e.setDomain(dto.getDomain());
        e.setLocalDate(dto.getLocalDate());
        e.setCurrentBudget(dto.getCurrentBudget());
        e.setNeededBudget(dto.getNeededBudget());
        e.setText(dto.getText());
        e.setTitle(dto.getTitle());
        e.setUser(user);
        return ideaMapper.fromEntityToDto(ideaRepository.save(e));
    }

    @Override
    public List<IdeaDto> getIdeas() {
        return ideaRepository.findAll()
                .stream()
                .map(ideaMapper::fromEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<IdeaDto> getIdeaById(Long id) {
        var resource = this.ideaRepository.findById(id)
                .orElseThrow(() -> {
                    throw new IdeaServiceException(String.format("Couldn't find resource with id : %d", id));});
        return Optional.of(ideaMapper.fromEntityToDto(resource));
    }

    @Override
    public Optional<IdeaDto> addIdea(IdeaDto dto) {
        var user = getUserForIdea(dto.getUser());
        var entity = ideaMapper.fromDtoToEntity(dto, user);
        var resource = ideaMapper.fromEntityToDto(this.ideaRepository.save(entity));
        return Optional.of(resource);
    }

    @Override
    public Optional<IdeaDto> updateIdea(Long id, IdeaDto dto) {
        var user = getUserForIdea(dto.getUser());
        var entity = ideaRepository.findById(id);
        var resource = new AtomicReference<Optional<IdeaDto>>();
        entity.ifPresentOrElse(e -> {
                    var result = createIdeaModel(e, dto, user);
                    resource.set(Optional.of(result));},
                () -> resource.set(addIdea(dto)));
        return resource.get();
    }

    @Override
    public Optional<IdeaDto> deleteIdeaById(Long id) {
        var resource = ideaMapper.fromEntityToDto(ideaRepository
                .findById(id)
                .orElse(null));
        ideaRepository.deleteById(id);
        return Optional.of(resource);
    }

    @Override
    public List<IdeaDto> syncIdeas(List<IdeaDto> ideas) {
        var result = new ArrayList<IdeaDto>();
        ideas.forEach(e -> {
            if(e.getId() != null){
                this.updateIdea(e.getId(), e);
            }else{
                this.addIdea(e);
            }
            result.add(e);
        });
        return result;
    }
}
