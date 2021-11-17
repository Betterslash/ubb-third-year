package ro.ubb.ideamanagerbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.ubb.ideamanagerbackend.dto.IdeaDto;
import ro.ubb.ideamanagerbackend.mapper.IdeaMapper;
import ro.ubb.ideamanagerbackend.repository.IdeaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IdeaServiceImpl implements IdeaService {

    private final IdeaRepository ideaRepository;
    private final IdeaMapper ideaMapper;

    @Override
    public List<IdeaDto> getIdeas() {
        return ideaRepository.findAll()
                .stream()
                .map(ideaMapper::fromEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<IdeaDto> getIdeaById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<IdeaDto> addIdea(IdeaDto dto) {
        return Optional.empty();
    }

    @Override
    public Optional<IdeaDto> updateIdea(Long id, IdeaDto dto) {
        return Optional.empty();
    }

    @Override
    public Optional<IdeaDto> deleteIdeaById(Long id) {
        return Optional.empty();
    }
}
