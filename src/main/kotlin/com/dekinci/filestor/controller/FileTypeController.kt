package com.dekinci.filestor.controller

import com.dekinci.filestor.mapping.FileTypeMapping
import com.dekinci.filestor.model.FileMetaType
import com.dekinci.filestor.repository.FileMetaTypeRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FileTypeController(private val repository: FileMetaTypeRepository) : FileTypeMapping {
    @GetMapping
    override fun list(): List<FileMetaType> {
        return repository.findAll().toList()
    }

    @PostMapping
    override fun add(type: FileMetaType): FileMetaType {
        return repository.save(type)
    }
}