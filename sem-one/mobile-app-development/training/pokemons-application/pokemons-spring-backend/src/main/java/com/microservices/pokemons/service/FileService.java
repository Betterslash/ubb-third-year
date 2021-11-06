package com.microservices.pokemons.service;

import com.microservices.pokemons.dto.files.FileDto;
import com.microservices.pokemons.model.files.FileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface FileService {
    FileDto save(MultipartFile file) throws IOException;
    Optional<FileEntity> getFile(String id);
    List<FileDto> getAllFiles();
}
