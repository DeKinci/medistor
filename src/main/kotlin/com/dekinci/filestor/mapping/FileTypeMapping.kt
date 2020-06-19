package com.dekinci.filestor.mapping

import com.dekinci.filestor.model.FileMetaType
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@CrossOrigin
@RequestMapping(Mapping.filetype)
interface FileTypeMapping {
    @GetMapping
    fun list(): List<FileMetaType>

    @PostMapping
    fun add(type: FileMetaType): FileMetaType
}