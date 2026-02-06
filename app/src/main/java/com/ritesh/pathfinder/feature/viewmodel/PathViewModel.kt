package com.ritesh.pathfinder.feature.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ritesh.pathfinder.feature.model.Node
import java.util.PriorityQueue
import kotlin.math.abs

class PathViewModel : ViewModel() {
    var grid by mutableStateOf<List<List<Node>>>(emptyList())
    var playerPath = mutableStateListOf<Node>()
    var optimizedPath by mutableStateOf<List<Node>?>(null)

    var currentScore by mutableIntStateOf(100)
    var timeLeft by mutableIntStateOf(60)
    var undoCharges by mutableIntStateOf(2)
    var isGameOver by mutableStateOf(false)

    private var originalOptimalCost = 0
    private val gridSize = 15

    init { resetGame() }

    fun resetGame() {
        val newGrid = generateSolvableMap()
        grid = newGrid
        playerPath.clear()
        playerPath.add(newGrid[0][0])
        undoCharges = 2
        timeLeft = 60
        isGameOver = false
        optimizedPath = null

        val path = findAStarPath(newGrid, newGrid[0][0], newGrid.last().last())
        originalOptimalCost = path.sumOf { it.terrainCost }
        currentScore = 100
    }
    fun handleTap(tx: Int, ty: Int) {
        if (isGameOver) return
        val current = playerPath.last()

        val isHorizontal = current.y == ty && current.x != tx
        val isVertical = current.x == tx && current.y != ty

        if (isHorizontal || isVertical) {
            val pathSegment = mutableListOf<Node>()
            val xRange = if (current.x < tx) (current.x + 1)..tx else (current.x - 1) downTo tx
            val yRange = if (current.y < ty) (current.y + 1)..ty else (current.y - 1) downTo ty

            if (isHorizontal) {
                for (x in xRange) {
                    val node = grid[x][ty]
                    if (node.isWall || playerPath.contains(node)) return
                    pathSegment.add(node)
                }
            } else {
                for (y in yRange) {
                    val node = grid[tx][y]
                    if (node.isWall || playerPath.contains(node)) return
                    pathSegment.add(node)
                }
            }
            playerPath.addAll(pathSegment)
            updateDynamicScore()

            if (tx == gridSize - 1 && ty == gridSize - 1) finishGame()
        } else {
            handleAdjacentMove(tx, ty)
        }
    }

    private fun handleAdjacentMove(tx: Int, ty: Int) {
        val current = playerPath.last()

        val isAdjacent = abs(current.x - tx) + abs(current.y - ty) == 1

        if (isAdjacent) {
            val targetNode = grid[tx][ty]

            if (playerPath.contains(targetNode)) {
                return
            }

            if (targetNode.isWall) return

            playerPath.add(targetNode)
            updateDynamicScore()

            if (tx == gridSize - 1 && ty == gridSize - 1) {
                finishGame()
            }
        }
    }

    fun undo() {
        if (undoCharges > 0 && playerPath.size > 1) {
            playerPath.removeAt(playerPath.size - 1)
            undoCharges--

            updateDynamicScore()
        }
    }

    private fun updateDynamicScore() {
        if (playerPath.isEmpty()) return

        val current = playerPath.last()

        val costSpent = playerPath.drop(1).sumOf { it.terrainCost }

        val remainingPath = findAStarPath(grid, current, grid.last().last())

        val remainingCost = remainingPath.drop(1).sumOf { it.terrainCost }

        val projectedTotal = costSpent + remainingCost

        val costDifference = projectedTotal - originalOptimalCost
        currentScore = (100 - costDifference).coerceIn(0, 100)
    }

    private fun finishGame() {
        isGameOver = true
        optimizedPath = findAStarPath(grid, grid[0][0], grid.last().last())
    }

    fun giveUp() {
        finishGame()
    }

    private fun findAStarPath(grid: List<List<Node>>, start: Node, goal: Node): List<Node> {
        val openSet = PriorityQueue<Node>(compareBy { it.fCost })
        val closedSet = mutableSetOf<Node>()

        grid.flatten().forEach { it.gCost = Int.MAX_VALUE; it.parent = null }

        start.gCost = 0
        start.hCost = abs(start.x - goal.x) + abs(start.y - goal.y)
        openSet.add(start)

        while (openSet.isNotEmpty()) {
            val current = openSet.poll() ?: break
            if (current == goal) return reconstruct(goal)
            closedSet.add(current)

            val neighbors = listOf(0 to 1, 0 to -1, 1 to 0, -1 to 0)
            for ((dx, dy) in neighbors) {
                val nx = current.x + dx
                val ny = current.y + dy
                if (nx in 0 until gridSize && ny in 0 until gridSize) {
                    val neighbor = grid[nx][ny]
                    if (neighbor.isWall || closedSet.contains(neighbor)) continue

                    val tentativeG = current.gCost + neighbor.terrainCost
                    if (tentativeG < neighbor.gCost) {
                        neighbor.parent = current
                        neighbor.gCost = tentativeG
                        neighbor.hCost = abs(nx - goal.x) + abs(ny - goal.y)
                        if (!openSet.contains(neighbor)) openSet.add(neighbor)
                    }
                }
            }
        }
        return emptyList()
    }

    private fun reconstruct(node: Node): List<Node> {
        val path = mutableListOf<Node>()
        var curr: Node? = node
        while (curr != null) { path.add(curr); curr = curr.parent }
        return path.reversed()
    }

    private fun generateSolvableMap(): List<List<Node>> {
        var validGrid: List<List<Node>>
        do {
            validGrid = List(gridSize) { x -> List(gridSize) { y ->
                val n = Node(x, y)
                val rand = Math.random()

                when {
                    (x == 0 && y == 0) || (x == gridSize-1 && y == gridSize-1) -> n.terrainCost = 1

                    rand < 0.15 -> n.isWall = true
                    rand < 0.30 -> n.terrainCost = 7
                    rand < 0.45 -> n.terrainCost = 3
                    rand < 0.55 -> n.terrainCost = 15
                    else -> n.terrainCost = 1
                }
                n
            }}
        } while (findAStarPath(validGrid, validGrid[0][0], validGrid.last().last()).isEmpty())
        return validGrid
    }
}