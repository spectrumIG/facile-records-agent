package it.facile.records.agent.domain.entity.local

interface LocalData

/**
 * Simple entity for a generic beer
 * */
data class SimpleBeer(val id: Int, val imageUrl: String?, val name: String, val tagline: String?, val date: String?) : LocalData

/**
 * Simple entity for a generic beer for UI layer
 * */
data class BeerForUi(val id: Int, val imageUrl: String?, val name: String, val tagline: String?, val date: String?) : LocalData

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
