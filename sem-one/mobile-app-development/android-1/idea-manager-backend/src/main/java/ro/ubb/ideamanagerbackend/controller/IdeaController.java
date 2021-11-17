package ro.ubb.ideamanagerbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
