package com.dekinci.filestor.controller

import com.dekinci.filestor.mapping.UploadMapping
import com.dekinci.filestor.model.FileMetaApi
import com.dekinci.filestor.service.FileSaver
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class UploadController(
        private val fileSaver: FileSaver
) : UploadMapping {

    @CrossOrigin
    override fun upload(file: Array<MultipartFile>): List<FileMetaApi> {
        return file.map { FileMetaApi.fromFileMeta(fileSaver.save(it)) }
    }
}