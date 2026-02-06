package com.ritesh.pathfinder.feature.model

data class Node(
    val x: Int, val y: Int,
    var terrainCost: Int = 1,
    var isWall: Boolean = false,
    var gCost: Int = Int.MAX_VALUE,
    var hCost: Int = 0,
    var parent: Node? = null
) {
    val fCost: Int get() = if (gCost == Int.MAX_VALUE) Int.MAX_VALUE else gCost + hCost
}