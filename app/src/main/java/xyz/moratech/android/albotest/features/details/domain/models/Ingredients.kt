package xyz.moratech.android.albotest.features.details.domain.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Ingredients (
	@SerializedName("malt")
	val malt : List<IngredientItem>,

	@SerializedName("hops")
	val hops : List<IngredientItem>,

	@SerializedName("yeast")
	val yeast : String
) : Serializable
