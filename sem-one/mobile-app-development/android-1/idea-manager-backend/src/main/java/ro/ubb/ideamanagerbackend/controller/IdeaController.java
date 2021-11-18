package ro.ubb.ideamanagerbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.ubb.ideamanagerbackend.dto.IdeaDto;
import ro.ubb.ideamanagerbackend.service.IdeaService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ideas")
public class IdeaController {

    private final IdeaService ideaService;

    @GetMapping
    public List<IdeaDto> getAllIdeas(){
        return ideaService.getIdeas();
    }

    @PostMapping
    public IdeaDto insertIdea(@RequestBody IdeaDto ideaDto){
        return ideaService.addIdea(ideaDto)
                .orElseThrow(RuntimeException::new);
    }

    @PutMapping("/{id}")
    public IdeaDto updateIdea(@PathVariable Long id, @RequestBody IdeaDto ideaDto){
        return ideaService.updateIdea(id, ideaDto)
                .orElseThrow(RuntimeException::new);
    }

    @DeleteMapping("/{id}")
    public IdeaDto deleteIdea(@PathVariable Long id){
        return ideaService.deleteIdeaById(id)
                .orElseThrow(RuntimeException::new);
    }
}
