package ro.ubb.petsmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.petsmanager.dto.CatDto;
import ro.ubb.petsmanager.service.AnimalService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cats")
public class CatController {
    private final AnimalService<CatDto> service;

    @GetMapping
    public List<CatDto> getAll(){
        return service.getAll();
    }
}
