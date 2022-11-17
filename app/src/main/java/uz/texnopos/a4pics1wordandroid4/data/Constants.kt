package uz.texnopos.a4pics1wordandroid4.data

import uz.texnopos.a4pics1wordandroid4.R

object Constants {
    fun getQuestions(): List<Question> {
        return listOf(
            Question(
                id = 1,
                images = listOf(
                    R.drawable.photo1_1,
                    R.drawable.photo1_2,
                    R.drawable.photo1_3,
                    R.drawable.photo1_4
                ),
                answer = "ХОЛОД"
            )
        )
    }
}