package com.dekinci.filestor.service

import com.twmacinta.util.MD5
import org.springframework.stereotype.Service
import java.io.File

@Service
class Md5HashService : HashService {
    init {
        MD5.initNativeLibrary()
    }

    override fun hash(file: File): String {
        return MD5.asHex(MD5.getHash(file))
    }
}