package com.dekinci.filestor.model

import com.dekinci.filestor.service.FileType
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class FileMetaType(
        @Id
        val name: String,

        @ElementCollection
        val inputTypes: List<FileType>,

        val storedType: FileType,

        @ElementCollection
        val outputTypes: List<FileType>
)
