package com.dekinci.filestor.model

import java.time.Instant

data class FileMetaApi(
        val id: Int = 0,
        val name: String,
        val hash: String,
        val size: Long,
        val fileMetaTypeName: String,
        val fileOwnersNames: List<String>,
        val addedTs: Instant,
        val fileCreationTs: Instant,
        val lastGetTs: Instant
) {
    companion object {
        fun fromFileMeta(fileMeta: FileMeta): FileMetaApi {
            return FileMetaApi(
                    fileMeta.id,
                    fileMeta.name,
                    fileMeta.hash,
                    fileMeta.size,
                    fileMeta.fileMetaType.name,
                    fileMeta.fileOwners.map { it.name },
                    fileMeta.addedTs,
                    fileMeta.fileCreationTs,
                    fileMeta.lastGetTs
            )
        }
    }
}