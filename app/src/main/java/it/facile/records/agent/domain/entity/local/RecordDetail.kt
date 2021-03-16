package it.facile.records.agent.domain.entity.local

import java.util.*

data class FileOfRecord(
    val filename: String,
    val fileSize: Int,
    val addingDate: Calendar,
) : LocalData {

    fun mapToBusiness(): FileOfRecordBusiness {
        return FileOfRecordBusiness(filename, fileSize, addingDate)
    }
}

data class FileOfRecordBusiness(
    val filename: String,
    val fileSize: Int,
    val addingDate: Calendar,
) {
    fun mapToUI(): FileOfRecordUI {
        return FileOfRecordUI(filename, fileSize, addingDate)
    }
}

data class FileOfRecordUI(
    val filename: String,
    val fileSize: Int,
    val addingDate: Calendar,
)
