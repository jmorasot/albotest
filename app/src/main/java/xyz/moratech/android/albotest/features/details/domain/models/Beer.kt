package xyz.moratech.android.albotest.features.details.domain.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Beer (
	@SerializedName("id")
	val id : Int,

	@SerializedName("name")
	val name : String,

	@SerializedName("tagline")
	val tag : String,

	@SerializedName("first_brewed")
	val firstBrewedDate : String,

	@SerializedName("description")
	val description : String,

	@SerializedName("image_url")
	val imageUrl : String,

	@SerializedName("method")
	val method : PreparationMethod,

	@SerializedName("ingredients")
	val ingredients : Ingredients,

	@SerializedName("food_pairing")
	val foodPairing : List<String>,

	@SerializedName("brewers_tips")
	val brewersTips : String,

	@SerializedName("contributed_by")
	val contributedBy : String
) : Serializable
