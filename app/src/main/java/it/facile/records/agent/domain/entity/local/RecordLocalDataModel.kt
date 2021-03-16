package it.facile.records.agent.domain.entity.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

interface LocalData

/**
 * Simple entity for a generic beer
 * */
@Entity
data class Record(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "record_name") val recordName: String?,
)

data class RecordDataModel(val id: Int, val recordName: String?) : LocalData {
    fun mapToBusiness(): RecordBusinessData {
        return RecordBusinessData(id = id, recordName = recordName)
    }
}
/**
 * Entity for business layer
 * */
data class RecordBusinessData(val id: Int, val recordName: String?) : LocalData {

    fun mapTo(): RecordForUi {
        return RecordForUi(id = id, recordName = recordName)
    }
}

/**
 * Entity for  UI layer
 * */
data class RecordForUi(val id: Int, val recordName: String?, val hasFile: Boolean = false) : LocalData


///**
// * I know those above are exactly the same but just to keep an idea of architecture
// * */
//
///**
// * Mapper for Layer boundaries
// * */
//class FromDtoToBusinessMapper : Mapper<RecordDTO, RecordBusinessData> {
//    override fun mapFrom(from: RecordDTO): RecordBusinessData {
//        return RecordBusinessData(from.id, from.recordName)
//    }
//}
//
///**
// * Mapper for Layer boundaries
// * */
//class FromBusinessToUiMapper : Mapper<RecordBusinessData,RecordForUi> {
//    override fun mapFrom(from: RecordBusinessData?): RecordForUi {
//        return RecordForUi(id = from.id, recordName =  from?.recordName?)
//    }
//}
//
//class FromSimpleToUiBeerMapper : Mapper<SimpleBeer, BeerForUi> {
//
//    override fun mapFrom(from: SimpleBeer): BeerForUi {
//        return BeerForUi(id = from.id, imageUrl = from.imageUrl, name = from.name, tagline = from.tagline, date = from.date)
//    }
//}
