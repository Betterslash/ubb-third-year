package com.microservices.pokemons.mapper;

import com.microservices.pokemons.dto.files.FileDto;
import com.microservices.pokemons.model.files.FileEntity;
import org.mapstruct.Mapper;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Objects;

@Mapper(componentModel = "spring")
public interface FileMapper {

    default FileEntity fromFileDtoToFileEntity(MultipartFile file) throws IOException {
        var fileEntity = new FileEntity();
        fileEntity.setName(StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())));
        fileEntity.setContentType(file.getContentType());
        fileEntity.setData(file.getBytes());
        fileEntity.setSize(file.getSize());
        return fileEntity;
    }

    default FileDto fromFileEntityToFileDto(FileEntity fileEntity){
        var downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/")
                .path(fileEntity.getId())
                .toUriString();
        var fileResponse = new FileDto();
        fileResponse.setId(fileEntity.getId());
        fileResponse.setName(fileEntity.getName());
        fileResponse.setContentType(fileEntity.getContentType());
        fileResponse.setSize(fileEntity.getSize());
        fileResponse.setUrl(downloadURL);

        return fileResponse;
    }
}
