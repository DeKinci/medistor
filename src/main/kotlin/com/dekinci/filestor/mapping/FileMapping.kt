package com.dekinci.filestor.mapping

import org.springframework.core.io.UrlResource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping(Mapping.file)
interface FileMapping {
    @CrossOrigin
    @GetMapping("/{id}")
    fun getFile(@PathVariable id: Int): ResponseEntity<UrlResource>
}