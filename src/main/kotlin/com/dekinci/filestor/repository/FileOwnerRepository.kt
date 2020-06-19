package com.dekinci.filestor.repository

import com.dekinci.filestor.model.FileOwner
import org.springframework.data.repository.CrudRepository

interface FileOwnerRepository : CrudRepository<FileOwner, String> {
}