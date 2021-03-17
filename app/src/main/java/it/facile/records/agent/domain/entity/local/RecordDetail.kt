package it.facile.records.agent.domain.entity.local

import java.util.*

data class FileOfRecord(
    val filename: String,
    val fileSize: Long,
    val addingDate: Calendar,
) : LocalData

data class FileOfRecordBusiness(
    val filename: String,
    val fileSize: Long,
    val addingDate: Calendar,
)


data class FileOfRecordUI(
    val filename: String,
    val fileSize: Long,
    val addingDate: Calendar,
)
