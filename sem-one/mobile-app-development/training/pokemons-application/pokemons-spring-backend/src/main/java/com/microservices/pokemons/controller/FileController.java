package com.microservices.pokemons.controller;

import com.microservices.pokemons.dto.files.FileDto;
import com.microservices.pokemons.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;


    @PostMapping
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        try {
            var result = fileService.save(file);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(result.getId());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping
    public List<FileDto> list() {
        return fileService.getAllFiles();
    }



    @GetMapping("{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        var fileEntityOptional = fileService.getFile(id);

        if (fileEntityOptional.isEmpty()) {
            return ResponseEntity.notFound()
                    .build();
        }

        var fileEntity = fileEntityOptional.get();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getName() + "\"")
                .contentType(MediaType.valueOf("image/png"))
                .body(fileEntity.getData());
    }
}
