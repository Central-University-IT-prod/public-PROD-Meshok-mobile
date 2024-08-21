package com.trifcdr.data.mappers

import com.trifcdr.data.network.models.FormAnswer
import com.trifcdr.data.network.models.user.User
import com.trifcdr.domain.models.AnswersHistoryData
import com.trifcdr.domain.models.FormAnswerData
import com.trifcdr.domain.models.FormData
import com.trifcdr.domain.models.HistoryAnswerData
import com.trifcdr.domain.models.QuestionData
import com.trifcdr.domain.models.UserData
import org.json.JSONArray
import org.json.JSONObject
import java.util.Collections

fun mapToDomainUser(json: String): UserData {
    val user = JSONObject(json).getJSONObject("user")
    val auth = JSONObject(json).getJSONObject("auth")
    return UserData(
        email = user.getString("email"),
        age = user.getInt("age"),
        gender = user.getString("gender"),
        accessToken = auth.getString("token")
    )

}

fun mapToDomainUserSerializable(user: User): UserData {
    return UserData(
        email = user.email,
        age = user.age!!,
        gender = user.gender!!,
        nextLevel = user.points?.nextLevel,
        currentLevel = user.points?.currentLevel,
        minXp = user.points?.minXp,
        maxXp = user.points?.maxXp,
        currentXp = user.points?.currentXp
    )
}

fun mapToDomainForm(json: String): FormData {
    val obj = JSONObject(json)
    val result = obj.getJSONArray("questions")
    val questions: MutableList<QuestionData> = mutableListOf()
    for (i in 0 until result.length()) {
        val question = result.getJSONObject(i)
        questions.add(
            QuestionData(
                id = question.getInt("id"),
                description = question.getString("description"),
                type = question.getString("type"),
                category = question.getString("category"),
                orgId = question.getInt("org_id")
            )
        )
    }
    return FormData(
        obj.getString("org_name"),
        Collections.unmodifiableList(questions)
    )
}

fun mapToDataForm(form: List<FormAnswerData>): List<FormAnswer> {
    val result: MutableList<FormAnswer> = mutableListOf()
    form.forEach {
        result.add(
            FormAnswer(
                id = it.questionId,
                answer = it.answer
            )
        )
    }
    return Collections.unmodifiableList(result)
}

fun mapToDomainHistory(json: String): AnswersHistoryData {
    val array = JSONArray(json)
    val historyMap: MutableMap<String, MutableList<HistoryAnswerData>> = mutableMapOf()
    for (i in 0 until array.length()) {
        val obj = array.getJSONObject(i)
        val question = obj.getJSONObject("question")
        val org = question.getJSONObject("organization").getString("name")
        if (historyMap[org] == null) {
            historyMap[org] = mutableListOf()
            historyMap[org]?.add(
                HistoryAnswerData(
                    id = question.getInt("id"),
                    description = question.getString("description"),
                    type = question.getString("type"),
                    answer = obj.getInt("answer")
                )
            )
        } else {
            historyMap[org]?.add(
                HistoryAnswerData(
                    id = question.getInt("id"),
                    description = question.getString("description"),
                    type = question.getString("type"),
                    answer = obj.getInt("answer")
                )
            )
        }
    }
    return AnswersHistoryData(
        organizationsAns = historyMap
    )
}