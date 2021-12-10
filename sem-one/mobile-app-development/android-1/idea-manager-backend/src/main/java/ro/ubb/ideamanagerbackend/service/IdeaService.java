package ro.ubb.ideamanagerbackend.service;

import ro.ubb.ideamanagerbackend.dto.IdeaDto;

import java.util.List;
import java.util.Optional;

public interface IdeaService {
    List<IdeaDto> getIdeas();
    Optional<IdeaDto> getIdeaById(Long id);
    Optional<IdeaDto> addIdea(IdeaDto dto);
    Optional<IdeaDto> updateIdea(Long id, IdeaDto dto);
    Optional<IdeaDto> deleteIdeaById(Long id);

    List<IdeaDto> syncIdeas(List<IdeaDto> ideas);
}
