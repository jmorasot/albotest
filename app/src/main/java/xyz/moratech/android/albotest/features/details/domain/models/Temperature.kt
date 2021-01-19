package xyz.moratech.android.albotest.features.details.domain.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Temperature (
	@SerializedName("value")
	val value : Int,

	@SerializedName("unit")
	val unit : String
) : Serializable
