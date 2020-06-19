package com.dekinci.filestor.service

import org.springframework.web.multipart.MultipartFile
import java.io.File

interface UploadSevice {
    fun save(file: MultipartFile): File
}