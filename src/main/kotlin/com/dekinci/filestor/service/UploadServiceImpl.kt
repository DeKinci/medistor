package com.dekinci.filestor.service

import com.dekinci.filestor.commons.ensureDirectoryExists
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File

@Service
class UploadServiceImpl(
        @Value("\${app.upload.baseDirectory}")
        private val uploadDir: File
) : UploadSevice {
    init {
        ensureDirectoryExists(uploadDir)
    }

    override fun save(file: MultipartFile): File {
        val outputFile = createTempFile(directory = uploadDir).absoluteFile
        file.transferTo(outputFile)
        return outputFile
    }
}