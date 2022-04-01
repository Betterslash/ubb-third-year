package ro.ubb.petsmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping
    public CatDto addCat(@RequestBody CatDto dto){
        return service.save(dto);
    }

    @GetMapping("/{id}")
    public CatDto getCatById(@PathVariable String id){
        return service.getById(id);
    }
}
