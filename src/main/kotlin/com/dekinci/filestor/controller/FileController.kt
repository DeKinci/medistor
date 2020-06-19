package com.dekinci.filestor.controller

import com.dekinci.filestor.mapping.FileMapping
import com.dekinci.filestor.repository.FileMetaRepository
import com.dekinci.filestor.service.FileStorageService
import org.springframework.core.io.UrlResource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class FileController(
        private val fileStorageService: FileStorageService,
        private val fileMetaRepository: FileMetaRepository
) : FileMapping {
    override fun getFile(id: Int): ResponseEntity<UrlResource> {
        val entity = fileStorageService.retrieve(id).absoluteFile.toURI()
        val resource = UrlResource(entity)

        val e = fileMetaRepository.findById(id).orElseThrow()
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(e.fileMetaType.outputTypes.first()))
                .body(resource)
    }
}