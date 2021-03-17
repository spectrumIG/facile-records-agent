package it.facile.records.agent.domain.entity.local

import androidx.room.*
import java.util.*

@Entity(tableName = "record")
data class Record(
    @PrimaryKey
    @ColumnInfo(name = "record_id")
    val id: Int,
    @ColumnInfo(name = "record_name")
    val recordName: String?,
)

@Entity(tableName = "file_table")
data class RecordFile(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "file_id")
    val fileId : Long = 0,
    @ColumnInfo(name = "record_id")
    val recordId: Int,
    @ColumnInfo(name = "file_name")
    val filename: String,
    @ColumnInfo(name = "file_size")
    val fileSize: Long,
    @ColumnInfo(name = "file_add_date")
    val addingDate: Calendar = Calendar.getInstance()
)

data class RecordWithFiles(
    @Embedded val user: Record,
    @Relation(
        parentColumn = "record_id",
        entityColumn = "file_id"
    )

    val filesForRecords: List<RecordFile>
)