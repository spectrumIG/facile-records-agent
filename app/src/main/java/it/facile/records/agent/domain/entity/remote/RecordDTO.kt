package it.facile.records.agent.domain.entity.remote

import it.facile.records.agent.domain.entity.local.RecordDataModel
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
        val recordName: String,
    ) {
        fun maptoRecord(): RecordDataModel {
            return RecordDataModel(id = id, recordName = recordName)
        }
    }


}