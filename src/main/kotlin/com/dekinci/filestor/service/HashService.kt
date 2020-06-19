package com.dekinci.filestor.service

import java.io.File

interface HashService {
    fun hash(file: File): String
}