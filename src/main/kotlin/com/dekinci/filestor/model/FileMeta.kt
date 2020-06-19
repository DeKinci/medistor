package com.dekinci.filestor.model

import java.time.Instant
import javax.persistence.*

@Entity
data class FileMeta(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Int = 0,
        val name: String,
        val hash: String,
        val size: Long,
        @ManyToOne
        val fileMetaType: FileMetaType,
        @ManyToMany
        val fileOwners: List<FileOwner>,
        val addedTs: Instant,
        val fileCreationTs: Instant,
        val lastGetTs: Instant
)