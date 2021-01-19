package xyz.moratech.android.albotest.features.details.domain.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class IngredientAmount (
	@SerializedName("value")
	val value : Double,

	@SerializedName("unit")
	val unit : String
) : Serializable
