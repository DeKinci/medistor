package com.dekinci.filestor.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class FileOwner(
        @Id
        val name: String,
        val passwordHash: String
)