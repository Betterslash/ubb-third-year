package ro.ubb.petsmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.ubb.petsmanager.dto.AdoptRequest;
import ro.ubb.petsmanager.dto.CatDto;
import ro.ubb.petsmanager.dto.DogDto;
import ro.ubb.petsmanager.service.AnimalService;

import java.util.List;

@RestController
@RequestMapping("/api/dogs")
@RequiredArgsConstructor
public class DogController extends ApiController{
    private final AnimalService<DogDto> service;

    @GetMapping
    public List<DogDto> getAll(){
        return service.getAll();
    }

    @PostMapping
    public DogDto addDog(@RequestBody DogDto dto){
        return service.save(dto);
    }

    @PutMapping("/adopt")
    public UserResponse<Object> adopt(@RequestBody AdoptRequest adoptRequest){
        return UserResponse.builder()
                .body(service.adopt(adoptRequest))
                .build();
    }

    @GetMapping("/{id}")
    public DogDto getCatById(@PathVariable String id){
        return service.getById(id);
    }

}
