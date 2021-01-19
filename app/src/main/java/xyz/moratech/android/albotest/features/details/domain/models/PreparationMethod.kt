package xyz.moratech.android.albotest.features.details.domain.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PreparationMethod(
	@SerializedName("mash_temp")
	val preparationMash : List<PreparationMash>,

	@SerializedName("fermentation")
	val fermentation : PreparationFermentation,

	@SerializedName("twist")
	val twist : String
) : Serializable
