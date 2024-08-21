package com.trifcdr.domain.models

data class AnswersHistoryData(
    val organizationsAns: Map<String, List<HistoryAnswerData>>
)