package com.dekinci.filestor.service

import java.io.File

interface FileStorageService {
    fun store(file: File, id: Int)
    fun retrieve(id: Int): File
}