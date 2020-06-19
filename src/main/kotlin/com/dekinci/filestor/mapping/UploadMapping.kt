package com.dekinci.filestor.mapping

import com.dekinci.filestor.model.FileMetaApi
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile

@RequestMapping(Mapping.upload)
interface UploadMapping {
    @PostMapping(consumes = ["multipart/form-data"])
    fun upload(@RequestParam("file") file: Array<MultipartFile>): List<FileMetaApi>
}