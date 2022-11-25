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
            ),
            Question(
                id = 1,
                images = listOf(
                    R.drawable.photo2_1,
                    R.drawable.photo2_2,
                    R.drawable.photo2_3,
                    R.drawable.photo2_4
                ),
                answer = "ГРОМКО"
            ),
            Question(
                id = 1,
                images = listOf(
                    R.drawable.photo3_1,
                    R.drawable.photo3_2,
                    R.drawable.photo3_3,
                    R.drawable.photo3_4
                ),
                answer = "ГОРЯЧИЙ"
            ),
            Question(
                id = 1,
                images = listOf(
                    R.drawable.photo4_1,
                    R.drawable.photo4_2,
                    R.drawable.photo4_3,
                    R.drawable.photo4_4
                ),
                answer = "МУЗЫКА"
            ),
            Question(
                id = 1,
                images = listOf(
                    R.drawable.photo5_1,
                    R.drawable.photo5_2,
                    R.drawable.photo5_3,
                    R.drawable.photo5_4
                ),
                answer = "ТОЧКА"
            )
        )
    }
}