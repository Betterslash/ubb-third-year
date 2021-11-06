package com.microservices.pokemons.service;

import com.microservices.pokemons.dto.files.FileDto;
import com.microservices.pokemons.mapper.FileMapper;
import com.microservices.pokemons.model.files.FileEntity;
import com.microservices.pokemons.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    public FileDto save(MultipartFile file) throws IOException {
        var fileEntity = new FileEntity();
        fileEntity.setName(StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())));
        fileEntity.setContentType(file.getContentType());
        fileEntity.setData(file.getBytes());
        fileEntity.setSize(file.getSize());
        return this.fileMapper.fromFileEntityToFileDto(fileRepository.save(fileEntity));
    }

    public Optional<FileEntity> getFile(String id) {
        return fileRepository.findById(id);
    }

    public List<FileDto> getAllFiles() {
        return fileRepository.findAll()
                .stream()
                .map(fileMapper::fromFileEntityToFileDto)
                .collect(Collectors.toList());
    }}
