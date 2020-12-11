package de.akquinet.cssp.fritz2.robots

import dev.fritz2.binding.RootStore
import org.w3c.dom.events.KeyboardEvent

object GridStore : RootStore<Grid>(Grid()) {
  val keyDown = handle<KeyboardEvent> { model, action: KeyboardEvent ->
    val cell = model.cell(3, 3)
    cell.content = Figure(FigureType.ROBOT_TRASH, cell)
    model
  }
}
