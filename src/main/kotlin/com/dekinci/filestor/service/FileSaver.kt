package com.dekinci.filestor.service

import com.dekinci.filestor.model.FileMeta
import com.dekinci.filestor.repository.FileMetaRepository
import com.dekinci.filestor.repository.FileMetaTypeRepository
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.Instant
import javax.transaction.Transactional

@Service
class FileSaver(
        private val uploadSevice: UploadSevice,
        private val hashService: HashService,
        private val fileStorageService: FileStorageService,
        private val fileMetaTypeRepository: FileMetaTypeRepository,
        private val fileMetaRepository: FileMetaRepository
) {
    @Transactional
    fun save(file: MultipartFile): FileMeta {
        val loadedFile = uploadSevice.save(file)
        val hash = hashService.hash(loadedFile)

        val mediaMetaUnsaved = createMediaMetaForFile(file, hash)
        val mediaMeta = fileMetaRepository.save(mediaMetaUnsaved)
        fileStorageService.store(loadedFile, mediaMeta.id)
        return mediaMeta
    }

    private fun createMediaMetaForFile(file: MultipartFile, hash: String): FileMeta {
        val mediaType = MediaType.valueOf(file.contentType!!).toString()
        val mediaMetaType = fileMetaTypeRepository.findFileMetaTypesByInputTypes(mediaType).first()

        return FileMeta(0, file.originalFilename ?: file.name,
                hash, file.size, mediaMetaType, listOf(),
                Instant.now(), Instant.now(), Instant.now())
    }
}