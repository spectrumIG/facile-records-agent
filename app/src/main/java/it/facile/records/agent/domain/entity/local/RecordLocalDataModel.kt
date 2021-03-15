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
) :
    LocalData

/**
 * Simple entity for a generic beer for UI layer
 * */
data class RecordForUi(val id: Int, val recordName: String?) : LocalData

/**
 * I know those above are exactly the same but just to keep an idea of architecture
 * */

/**
 * Mapper for Layer boundaries
 * */
//class FromDtoToSimpleBeerMapper : Mapper<RecordDTO, SimpleBeer> {
//    override fun mapFrom(from: RecordDTO): SimpleBeer {
//        return SimpleBeer(from.id!!, from.imageUrl, from.name!!, from.tagline!!, from.firstBrewed)
//    }
//}
//
//class FromSimpleToUiBeerMapper : Mapper<SimpleBeer, BeerForUi> {
//
//    override fun mapFrom(from: SimpleBeer): BeerForUi {
//        return BeerForUi(id = from.id, imageUrl = from.imageUrl, name = from.name, tagline = from.tagline, date = from.date)
//    }
//}
