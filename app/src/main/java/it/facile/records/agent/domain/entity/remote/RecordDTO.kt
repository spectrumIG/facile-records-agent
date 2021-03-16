package it.facile.records.agent.domain.entity.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecordListDTO(
    @SerialName("records")
    val records: List<RecordDTO>
) {
    @Serializable
    data class RecordDTO(
        @SerialName("id")
        val id: Int,
        @SerialName("record_name")
        val recordName: String
    )
}