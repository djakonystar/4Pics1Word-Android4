package uz.texnopos.a4pics1wordandroid4.data

import androidx.annotation.DrawableRes

data class Question(
    val id: Int,
    @DrawableRes val images: List<Int>,
    val answer: String
)
