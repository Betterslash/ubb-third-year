package ro.ubb.petsmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.ubb.petsmanager.dto.DogDto;
import ro.ubb.petsmanager.service.AnimalService;

import java.util.List;

@RestController
@RequestMapping("/api/dog")
@RequiredArgsConstructor
public class DogController {
    private final AnimalService<DogDto> service;

    @GetMapping
    public List<DogDto> getAll(){
        return service.getAll();
    }

    @PostMapping
    public DogDto addDog(@RequestBody DogDto dto){
        return service.save(dto);
    }
}
