package com.dekinci.filestor.service

import com.dekinci.filestor.commons.directoryFilesSize
import com.dekinci.filestor.commons.ensureDirectoryExists
import com.dekinci.filestor.commons.streamFiles
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write
import kotlin.math.round
import kotlin.streams.toList

@Service
class IdFileStorageService(
        @Value("\${app.storage.defaultBucketsAmount}")
        private val defaultBucketsAmount: Int,
        @Value("\${app.storage.bucketMaxSize}")
        private val maxBucketSize: Int,
        @Value("\${app.storage.scalingFactor}")
        private val scalingFactor: Double,
        @Value("\${app.storage.baseDirectory}")
        private val baseDirectory: File
) : FileStorageService {
    private var lock = ReentrantReadWriteLock()
    private var bucketsAmount = 0
    private val bucketSizeMap = HashMap<Int, Int>()

    init {
        ensureDirectoryExists(baseDirectory)
        if (baseDirectory.streamFiles().count().toInt() == 0)
            initBuckets()

        loadBuckets()
    }

    private fun initBuckets() {
        bucketsAmount = defaultBucketsAmount
        ensureAllBucketsExist()
    }

    private fun loadBuckets() {
        val dirsOnly = baseDirectory.streamFiles()
                .allMatch { it.isDirectory }
        if (!dirsOnly)
            throw IllegalStateException("not a dir")

        bucketsAmount = baseDirectory.streamFiles()
                .mapToInt { it.name.toString().toInt(16) }
                .max().orElse(defaultBucketsAmount - 1) + 1

        ensureAllBucketsExist()

        baseDirectory.streamFiles()
                .map { it.name.toString().toInt(16) to directoryFilesSize(it) }
                .toList().toMap(bucketSizeMap)
    }

    override fun store(file: File, id: Int) {
        lock.read {
            val bucket = calculateBucket(id)
            resizeBucketsIfNecessary(bucket)
            moveFileToDedicatedBucket(file, id)
            registerAdditionToBucket(bucket)
        }
    }

    override fun retrieve(id: Int): File {
        val bucketId = calculateBucket(id)
        val bucketFile = getBucketFile(bucketId)
        val fileIdName = Integer.toHexString(id)

        return File(bucketFile, fileIdName)
    }

    private fun resizeBucketsIfNecessary(bucket: Int) {
        if (bucketSizeMap[bucket]!! > maxBucketSize)
            resizeBucketsTransactional()
    }

    private fun resizeBucketsTransactional() {
        lock.write {
            val oldBucketsAmount = bucketsAmount

            try {
                bucketsAmount = round(bucketsAmount * scalingFactor).toInt()
                ensureAllBucketsExist()
                ensureAllFilesInAppropriateBuckets()
            } catch (e: Exception) {
                bucketsAmount = oldBucketsAmount
                ensureAllBucketsExist()
                ensureAllFilesInAppropriateBuckets()
                ensureBaseDirIsClean()
            }
        }
    }

    private fun ensureAllFilesInAppropriateBuckets() {
        baseDirectory.streamFiles().forEach { bucketDirectory ->
            bucketDirectory.streamFiles().toList().forEach { ensureFileInAppropriateBucket(it) }
        }
    }

    private fun ensureFileInAppropriateBucket(file: File) {
        check(file.parentFile.parentFile == baseDirectory)

        val id = file.name.toInt(16)
        val currentBucket = file.parentFile.name.toInt(16)
        val expectedBucket = calculateBucket(id)

        if (currentBucket != expectedBucket) {
            moveFileToDedicatedBucket(file, id)
            registerRemovalFromBucket(currentBucket)
            registerAdditionToBucket(expectedBucket)
        }
    }

    private fun moveFileToDedicatedBucket(file: File, id: Int) {
        val bucketId = calculateBucket(id)
        val bucketFile = getBucketFile(bucketId)
        val fileIdName = Integer.toHexString(id)

        val destination = File(bucketFile, fileIdName)
        Files.move(file.toPath(), destination.toPath())
    }

    private fun ensureAllBucketsExist() {
        (0 until bucketsAmount).forEach { getBucketFile(it) }
    }

    private fun ensureBaseDirIsClean() {
        baseDirectory.streamFiles()
                .filter { getBucketId(it) !in 0 until bucketsAmount }
                .forEach { it.delete() }
    }

    private fun registerRemovalFromBucket(bucketId: Int) {
        bucketSizeMap[bucketId] = bucketSizeMap[bucketId]!! - 1
    }

    private fun registerAdditionToBucket(bucketId: Int) {
        bucketSizeMap.compute(bucketId) { _, value -> (value ?: 0) + 1 }
    }

    private fun getBucketId(bucketFile: File): Int = bucketFile.name.toInt(16)

    private fun getBucketFile(bucketId: Int): File {
        val bucketName = Integer.toHexString(bucketId)
        val bucketFile = File(baseDirectory, bucketName)
        ensureDirectoryExists(bucketFile)
        return bucketFile
    }

    private fun calculateBucket(id: Int) = id % bucketsAmount
}