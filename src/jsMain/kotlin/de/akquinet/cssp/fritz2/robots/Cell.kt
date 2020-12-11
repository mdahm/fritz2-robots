package de.akquinet.cssp.fritz2.robots

import dev.fritz2.binding.Store
import dev.fritz2.components.box
import dev.fritz2.dom.html.RenderContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map

class Cell(
  var color: String = "white",
  val position: Position,
  private val grid: Grid,
) {
  var content: Figure = Figure(FigureType.EMPTY, this)
    set(figure) {
      figure.cell = this
      field = figure
    }

  fun mayMoveLeft() = if (this.position.column > 0) {
    Position(this.position.row, this.position.column - 1)
  } else {
    null
  }

  fun mayMoveRight() = if (this.position.column < COLUMNS - 1) {
    Position(this.position.row, this.position.column + 1)
  } else {
    null
  }

  fun mayMoveUp() = if (this.position.row > 0) {
    Position(this.position.row - 1, this.position.column)
  } else {
    null
  }

  fun mayMoveDown() = if (this.position.row < ROWS - 1) {
    Position(this.position.row + 1, this.position.column)
  } else {
    null
  }

  fun name() = content.name()

  fun imageName() = this.name().toLowerCase() + "-small.png"

  fun empty() = content.type == FigureType.EMPTY

  fun clear() {
    color = "white"
  }

  override fun toString() = name().substring(0,1) //position.value()
}

enum class FigureType {
  EMPTY, ROBOT_ALIVE, ROBOT_TRASH, PLAYER
}

open class Figure(val type: FigureType, var cell: Cell) {
  fun name() = when (type) {
    FigureType.EMPTY -> "empty"
    FigureType.PLAYER -> "Player"
    FigureType.ROBOT_ALIVE -> "Robot"
    FigureType.ROBOT_TRASH -> "Trash"
  }
}

class Player(cell: Cell) : Figure(FigureType.PLAYER, cell)
class Robot(cell: Cell) : Figure(FigureType.ROBOT_ALIVE, cell)
data class Position(val row: Int, val column: Int) {
  fun value() = "$row:$column"

  override fun toString() = value()
}

@ExperimentalCoroutinesApi
fun RenderContext.cell(cellStore: Store<Cell>) = box({
  size { "48px" }

  border {
    width { "1pt" }
    color { "black" }
    style { solid }
  }

  background { color {
    val toString = cellStore.data.map { it.color }.toString()
    println("color:$toString")
    toString
  } }
  display { flex }
  justifyContent { center }
  alignItems { center }
}) {
  cellStore.data.asText()
}
