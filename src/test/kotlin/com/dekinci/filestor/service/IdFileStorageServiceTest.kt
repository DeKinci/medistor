package com.dekinci.filestor.service

import org.junit.jupiter.api.Test

internal class IdFileStorageServiceTest {


    @Test
    fun store() {
        val baseDir = createTempDir("medistor")
        val fileStorageService = IdFileStorageService(2, 4, 1.5, baseDir)
        var idGen = 0

        for (i in 0 until 1000) {
            val file = createTempFile("medistor_temp")
            fileStorageService.store(file, idGen++)
        }

        baseDir.deleteRecursively()
    }

    @Test
    fun retrieve() {
    }
}