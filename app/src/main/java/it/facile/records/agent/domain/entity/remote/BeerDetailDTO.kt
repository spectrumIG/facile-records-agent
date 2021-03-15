package it.facile.records.agent.domain.entity.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BeerDetailDTO(
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
    val firstBrewed: String,
    @SerialName("food_pairing")
    val foodPairing: List<String>?,
    @SerialName("ibu")
    val ibu: Double?,
    @SerialName("id")
    val id: Int,
    @SerialName("image_url")
    val imageUrl: String,
    @SerialName("ingredients")
    val ingredientsDTO: IngredientsDTO?,
    @SerialName("method")
    val methodDTO: MethodDTO?,
    @SerialName("name")
    val name: String,
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

