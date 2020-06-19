package com.dekinci.filestor.commons

import java.io.File
import java.nio.file.Files
import java.util.stream.Stream

fun ensureDirectoryExists(file: File) {
    if (file.exists()) {
        if (!file.isDirectory)
            throw IllegalStateException("$file exists and is not a directory")
    } else {
        if (!file.mkdirs())
            throw IllegalStateException("can not create $file")
    }
}

fun directoryFilesSize(file: File): Int {
    if (!file.exists() || !file.isDirectory)
        throw IllegalStateException()

    return file.streamFiles().count().toInt()
}

fun File.streamFiles(): Stream<File> {
    return Files.list(this.toPath()).map { it.toFile() }.filter { it != this }
}
