package it.facile.records.agent.domain.entity.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import it.facile.records.agent.domain.entity.remote.RecordListDTO.RecordDTO
import it.facile.records.agent.library.android.entity.Mapper

interface LocalData

/**
 * Simple entity for a generic beer
 * */
@Entity
data class Record(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "record_name") val recordName: String?,
) : LocalData


/**
 * Entity for business layer
 * */
data class RecordBusinessData(val id: Int, val recordName: String?) : LocalData

/**
 * Entity for  UI layer
 * */
data class RecordForUi(val id: Int, val recordName: String?, val hasFile: Boolean) : LocalData

/**
 * I know those above are exactly the same but just to keep an idea of architecture
 * */

/**
 * Mapper for Layer boundaries
 * */
class FromDtoToBusinessMapper : Mapper<RecordDTO, RecordBusinessData> {
    override fun mapFrom(from: RecordDTO): RecordBusinessData {
        return RecordBusinessData(from.id, from.recordName)
    }
}
//
//class FromSimpleToUiBeerMapper : Mapper<SimpleBeer, BeerForUi> {
//
//    override fun mapFrom(from: SimpleBeer): BeerForUi {
//        return BeerForUi(id = from.id, imageUrl = from.imageUrl, name = from.name, tagline = from.tagline, date = from.date)
//    }
//}
