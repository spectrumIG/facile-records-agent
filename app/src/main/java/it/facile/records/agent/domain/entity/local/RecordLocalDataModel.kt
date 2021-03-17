package it.facile.records.agent.domain.entity.local

interface LocalData

data class RecordDataModel(val id: Int, val recordName: String?) : LocalData {
    fun mapToBusiness(): RecordBusinessData {
        return RecordBusinessData(id = id, recordName = recordName)
    }
}
/**
 * Entity for business layer
 * */
data class RecordBusinessData(val id: Int, val recordName: String?) : LocalData {

//    fun mapTo(hasFile: Boolean): RecordForUi {
//        return RecordForUi(id = id, recordName = recordName,hasFile = hasFile)
fun mapTo(): RecordForUi {
    return RecordForUi(id = id, recordName = recordName)

}
}

/**
 * Entity for  UI layer
 * */
data class RecordForUi(val id: Int, val recordName: String?, val hasFile: Boolean = false) : LocalData

