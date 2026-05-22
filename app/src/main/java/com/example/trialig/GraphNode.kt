package com.example.trialig

data class GraphNode(

    val id: Int,

    val parentId: Int?,

    val amount: Double,

    val type: String,

    val branchName: String,

    val status: String,

    val depth: Int
)