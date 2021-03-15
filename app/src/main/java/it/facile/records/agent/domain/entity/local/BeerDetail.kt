package it.facile.records.agent.domain.entity.local

import it.facile.records.agent.domain.entity.remote.BeerDetailDTO
import it.facile.records.agent.library.android.entity.Mapper

/**
 * Data class for Detail of a Beer. In this case, since it's a requisite to keep all the information, I'm gonna copy pretty much everything from
 * the DTO
 * */
data class BeerDetail(
    val abv: Double?,
    val attenuationLevel: Double?,
    val boilVolume: BoilVolume?,
    val brewersTips: String?,
    val contributedBy: String?,
    val description: String?,
    val ebc: Double?,
    val firstBrewed: String?,
    val foodPairing: List<String?>?,
    val ibu: Double?,
    val id: Int?,
    val imageUrl: String?,
    val ingredients: Ingredients?,
    val method: Method?,
    val name: String?,
    val ph: Double?,
    val srm: Double?,
    val tagline: String?,
    val targetFg: Double?,
    val targetOg: Double?,
    val volume: Volume?,
)

data class BoilVolume(
    val unit: String?,
    val value: Int?,
) {
    override fun toString(): String {
        val unit = if(unit == "litres") {
            "l"
        } else {
            "cl"
        }
        return "$value $unit"
    }
}


data class Ingredients(
    val hops: List<Hops?>?,
    val malt: List<Malt?>?,
    val yeast: String?,
)

data class Malt(
    val amount: Amount?,
    val name: String?,
) {
    override fun toString(): String {
        return "$name $amount"
    }
}

data class Amount(
    val unit: String?,
    val value: Double?,
) {
    override fun toString(): String {
        val unit = if(unit == "kilograms") {
            "Kg"
        } else {
            "g"
        }
        return "${value.toString()}$unit"
    }

}


data class Method(
    val fermentation: Fermentation?,
    val mashTemp: List<MashTemp?>?,
    val twist: String?,
)

data class Fermentation(
    val temp: Temp?,

) {
    override fun toString(): String {
        return "$temp"
    }
}

data class Temp(
    val unit: String?,
    val value: Double?,
) {
    override fun toString(): String {
        val unit = if(unit == "celsius") {
            "°C"
        } else {
            "°F"
        }
        return "$value$unit"
    }
}

data class MashTemp(
    val duration: Int?,
    val temp: Temp?,

    ) {
    override fun toString(): String {
        return "$temp for $duration minutes"
    }
}


data class Volume(
    val unit: String?,
    val value: Int?,
) {
    override fun toString(): String {
        val unit = if(unit == "litres") {
            "l"
        } else {
            "cl"
        }
        return "$value $unit"
    }
}

data class Hops(
    val add: String?,
    val amount: Amount?,
    val attribute: String?,
    val name: String?,
){
    override fun toString(): String {
        return "$name $amount Add: $add, Attribute: $attribute"
    }
}

data class BeerDetailUI(
    val abv: Double?,
    val attenuationLevel: Double?,
    val boilVolume: BoilVolume?,
    val brewersTips: String?,
    val contributedBy: String?,
    val description: String?,
    val ebc: Double?,
    val firstBrewed: String?,
    val foodPairing: List<String?>?,
    val ibu: Double?,
    val id: Int?,
    val imageUrl: String?,
    val ingredients: Ingredients?,
    val method: Method?,
    val name: String?,
    val ph: Double?,
    val srm: Double?,
    val tagline: String?,
    val targetFg: Double?,
    val targetOg: Double?,
    val volume: Volume?,
)

class FromDTOToBeerDetailMapper : Mapper<BeerDetailDTO, BeerDetail> {
    override fun mapFrom(from: BeerDetailDTO): BeerDetail {
        val mashTemps = from.methodDTO?.mashTempDTO?.map {
            MashTemp(it?.duration, Temp(it?.tempDTO?.unit, it?.tempDTO?.value))
        }

        val method = Method(Fermentation(Temp(from.methodDTO?.fermentationDTO?.tempDTO?.unit,
            from.methodDTO?.fermentationDTO?.tempDTO?.value)), mashTemps, from.methodDTO?.twist)

        val hops = from.ingredientsDTO?.hops?.map {
            Hops(it?.add, Amount(it?.amountDTO?.unit, it?.amountDTO?.value), it?.attribute, it?.name)
        }

        val malt = from.ingredientsDTO?.maltDTO?.map {
            Malt(Amount(it?.amountDTO?.unit, it?.amountDTO?.value), it?.name)
        }

        val ingredients = Ingredients(hops, malt, from.ingredientsDTO?.yeast)

        return BeerDetail(
            abv = from.abv,
            attenuationLevel = from.attenuationLevel,
            boilVolume = BoilVolume(from.boilVolumeDTO?.unit, from.boilVolumeDTO?.value),
            brewersTips = from.brewersTips,
            contributedBy = from.contributedBy,
            description = from.description,
            ebc = from.ebc,
            firstBrewed = from.firstBrewed,
            foodPairing = from.foodPairing,
            ibu = from.ibu,
            id = from.id,
            imageUrl = from.imageUrl,
            ingredients = ingredients,
            method = method,
            name = from.name,
            ph = from.ph,
            srm = from.srm,
            tagline = from.tagline,
            targetFg = from.targetFg,
            targetOg = from.targetOg,
            volume = Volume(from.volumeDTO?.unit, from.volumeDTO?.value))
    }
}

class FromBeerDetailToDetailUI : Mapper<BeerDetail, BeerDetailUI> {

    override fun mapFrom(from: BeerDetail): BeerDetailUI {
        val mashTemps = from.method?.mashTemp?.map {
            MashTemp(it?.duration, Temp(it?.temp?.unit, it?.temp?.value))
        }

        val method = Method(Fermentation(Temp(from.method?.fermentation?.temp?.unit,
            from.method?.fermentation?.temp?.value)), mashTemps, from.method?.twist)

        val hops = from.ingredients?.hops?.map {
            Hops(it?.add, Amount(it?.amount?.unit, it?.amount?.value), it?.attribute, it?.name)
        }

        val malt = from.ingredients?.malt?.map {
            Malt(Amount(it?.amount?.unit, it?.amount?.value), it?.name)
        }

        val ingredients = Ingredients(hops, malt, from.ingredients?.yeast)

        return BeerDetailUI(
            abv = from.abv,
            attenuationLevel = from.attenuationLevel,
            boilVolume = from.boilVolume,
            brewersTips = from.brewersTips,
            contributedBy = from.contributedBy,
            description = from.description,
            ebc = from.ebc,
            firstBrewed = from.firstBrewed,
            foodPairing = from.foodPairing,
            ibu = from.ibu,
            id = from.id,
            imageUrl = from.imageUrl,
            ingredients = ingredients,
            method = method,
            name = from.name,
            ph = from.ph,
            srm = from.srm,
            tagline = from.tagline,
            targetFg = from.targetFg,
            targetOg = from.targetOg,
            volume = from.volume)
    }

}
