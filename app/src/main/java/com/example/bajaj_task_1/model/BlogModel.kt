package com.example.bajaj_task_1.model

data class BlogModel(
    val title: String,
    val content: String,
    val author: String,
    val userUID: String
) {
    constructor() : this("", "", "", "")
}