package xyz.moratech.android.albotest.features.details.domain.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PreparationMash (
	@SerializedName("temp")
	val temp : Temperature,

	@SerializedName("duration")
	val duration : Int
) : Serializable