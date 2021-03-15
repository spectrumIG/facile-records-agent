package it.facile.records.agent.domain.entity.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BeersListDTOItem(

    @SerialName("abv")
    val abv: Double?,

    @SerialName("attenuation_level")
    val attenuationLevel: Double?,

    @SerialName("boil_volume")
    val boilVolumeDTO: BoilVolumeDTO?,

    @SerialName("brewers_tips")
    val brewersTips: String?,

    @SerialName("contributed_by")
    val contributedBy: String?,

    @SerialName("description")
    val description: String?,

    @SerialName("ebc")
    val ebc: Double?,

    @SerialName("first_brewed")
    val firstBrewed: String?,

    @SerialName("food_pairing")
    val foodPairing: List<String?>?,

    @SerialName("ibu")
    val ibu: Double?,

    @SerialName("id")
    val id: Int?,

    @SerialName("image_url")
    val imageUrl: String?,

    @SerialName("ingredients")
    val ingredientsDTO: IngredientsDTO?,

    @SerialName("method")
    val methodDTO: MethodDTO?,

    @SerialName("name")
    val name: String?,
    @SerialName("ph")
    val ph: Double?,
    @SerialName("srm")
    val srm: Double?,
    @SerialName("tagline")
    val tagline: String?,
    @SerialName("target_fg")
    val targetFg: Double?,
    @SerialName("target_og")
    val targetOg: Double?,
    @SerialName("volume")
    val volumeDTO: VolumeDTO?,
)

@Serializable
data class BoilVolumeDTO(
    @SerialName("unit")
    val unit: String?,
    @SerialName("value")
    val value: Int?,
)

@Serializable
data class IngredientsDTO(
    @SerialName("hops")
    val hops: List<HopsDTO?>?,
    @SerialName("malt")
    val maltDTO: List<MaltDTO?>?,
    @SerialName("yeast")
    val yeast: String?,
)

@Serializable
data class MaltDTO(
    @SerialName("amount")
    val amountDTO: AmountDTO?,
    @SerialName("name")
    val name: String?,
)

@Serializable
data class AmountDTO(
    @SerialName("unit")
    val unit: String?,
    @SerialName("value")
    val value: Double?,
)

@Serializable
data class MethodDTO(
    @SerialName("fermentation")
    val fermentationDTO: FermentationDTO?,
    @SerialName("mash_temp")
    val mashTempDTO: List<MashTempDTO?>?,
    @SerialName("twist")
    val twist: String?,
)

@Serializable
data class FermentationDTO(
    @SerialName("temp")
    val tempDTO: TempDTO?,
)

@Serializable
data class TempDTO(
    @SerialName("unit")
    val unit: String?,
    @SerialName("value")
    val value: Double?,
)

@Serializable
data class MashTempDTO(
    @SerialName("duration")
    val duration: Int?,
    @SerialName("temp")
    val tempDTO: TempDTO?,
)

@Serializable
data class VolumeDTO(
    @SerialName("unit")
    val unit: String?,
    @SerialName("value")
    val value: Int?,
)

@Serializable
data class HopsDTO(
    @SerialName("add")
    val add: String?,
    @SerialName("amount")
    val amountDTO: AmountDTO?,
    @SerialName("attribute")
    val attribute: String?,
    @SerialName("name")
    val name: String?,
)
