package de.akquinet.cssp.fritz2.robots

import kotlin.random.Random

const val COLUMNS = 30
const val ROWS = 15
const val ROBOTS = 5

class Grid {
  private val cells: Array<Array<Cell>> = Array(ROWS) { Array(COLUMNS) { Cell("white", Position(0, 0), this) } }
  private var player: Player = Player(Cell("white", Position(0, 0), this))

  fun rows() = this.cells

  fun columns(row: Int) = cells[row]

  fun cell(row: Int, column: Int) = cells[row][column]

  fun allCells(): List<Cell> = cells.flatten()

  fun populate() {
    clear();

    placeFigures(ROBOTS) { cell -> Robot(cell) }
    this.player = placeFigures(1) { cell -> Player(cell) } as Player
  }

  private fun clear() {
    for (row in 0 until ROWS) {
      for (column in 0 until COLUMNS) {
        cells[row][column] = Cell("white", Position(row, column), this)
      }
    }
  }

  private fun placeFigures(number: Int, creator: (cell: Cell) -> Figure): Figure {
    var figuresLeft = number

    while (figuresLeft > 0) {
      val row = Random.nextInt(0, ROWS)
      val column = Random.nextInt(0, COLUMNS)
      val cell = cell(row, column)

      if (cell.empty()) {
        cell.content = creator(cell)
        figuresLeft--
      }

      if (figuresLeft == 0) {
        return cell.content
      }
    }

    throw IllegalStateException("No return value!!?")
  }

  fun findEmptyPosition(): Position {
    while (true) {
      val row = Random.nextInt(0, ROWS)
      val column = Random.nextInt(0, COLUMNS)

      if (cell(row, column).empty()) {
        return Position(row, column);
      }
    }
  }

  fun updatePlayerPosition(newPlayerPosition: Position) {
    val oldPosition = this.player.cell.position
    val oldCell = this.cell(oldPosition.row, oldPosition.column)
    val newCell = this.cell(newPlayerPosition.row, newPlayerPosition.column)

    oldCell.content = Figure(FigureType.EMPTY, oldCell)
    newCell.content = this.player
  }

  fun updateRobotPosition(robot: Robot, newRobotPosition: Position) {
    val oldPosition = robot.cell.position
    val oldCell = this.cell(oldPosition.row, oldPosition.column)
    val newCell = this.cell(newRobotPosition.row, newRobotPosition.column)
    val newFigureType = computeNewFigure(newCell)
    val newFigure = if (newFigureType == FigureType.ROBOT_ALIVE) robot else Figure(newFigureType, newCell)

    oldCell.content = Figure (FigureType.EMPTY, oldCell)
    newCell.content = newFigure

    if (newRobotPosition == this.player.cell.position) {
//      alert('Game over!!!');
      gameOver = true
    }
  }

  private fun computeNewFigure(cell: Cell) = when (cell.content.type) {
    FigureType.ROBOT_TRASH,
    FigureType.ROBOT_ALIVE -> FigureType.ROBOT_TRASH
    FigureType.EMPTY, FigureType.PLAYER -> FigureType.ROBOT_ALIVE
  }

  private var gameOver: Boolean = false

  fun gameOver() = gameOver
}

