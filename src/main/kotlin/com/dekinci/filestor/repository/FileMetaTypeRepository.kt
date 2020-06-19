package com.dekinci.filestor.repository

import com.dekinci.filestor.model.FileMetaType
import com.dekinci.filestor.service.FileType
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface FileMetaTypeRepository : CrudRepository<FileMetaType, String> {
    fun findFileMetaTypesByInputTypes(fileType: FileType): List<FileMetaType>
}