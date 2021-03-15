package it.facile.records.agent.domain.entity.local

import it.facile.records.agent.domain.entity.remote.BeersListDTOItem
import it.facile.records.agent.library.android.entity.Mapper

interface BeerData

/**
 * Simple entity for a generic beer
 * */
data class SimpleBeer(val id: Int, val imageUrl: String?, val name: String, val tagline: String?, val date: String?) : BeerData

/**
 * Simple entity for a generic beer for UI layer
 * */
data class BeerForUi(val id: Int, val imageUrl: String?, val name: String, val tagline: String?, val date: String?) : BeerData

/**
 * I know those above are exactly the same but just to keep an idea of architecture
 * */

/**
 * Mapper for Layer boundaries
 * */
class FromDtoToSimpleBeerMapper : Mapper<BeersListDTOItem, SimpleBeer> {
    override fun mapFrom(from: BeersListDTOItem): SimpleBeer {
        return SimpleBeer(from.id!!, from.imageUrl, from.name!!, from.tagline!!, from.firstBrewed)
    }
}

class FromSimpleToUiBeerMapper : Mapper<SimpleBeer, BeerForUi> {

    override fun mapFrom(from: SimpleBeer): BeerForUi {
        return BeerForUi(id = from.id, imageUrl = from.imageUrl, name = from.name, tagline = from.tagline, date = from.date)
    }
}
