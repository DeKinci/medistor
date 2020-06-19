package com.dekinci.filestor.service

import java.io.File

class Directory(directory: File) : File(directory.path) {
    init {
        check(isDirectory)
    }
}