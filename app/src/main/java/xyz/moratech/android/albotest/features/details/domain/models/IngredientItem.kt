package xyz.moratech.android.albotest.features.details.domain.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class IngredientItem (
	@SerializedName("name")
	val name : String,

	@SerializedName("amount")
	val amount : IngredientAmount
) : Serializable
