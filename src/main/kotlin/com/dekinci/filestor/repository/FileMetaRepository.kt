package com.dekinci.filestor.repository

import com.dekinci.filestor.model.FileMeta
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface FileMetaRepository : PagingAndSortingRepository<FileMeta, Int> {
}