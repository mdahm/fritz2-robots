import de.akquinet.cssp.fritz2.robots.*
import dev.fritz2.binding.RootStore
import dev.fritz2.components.gridBox
import dev.fritz2.dom.html.render
import dev.fritz2.dom.mount
import dev.fritz2.dom.values
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.w3c.dom.events.KeyboardEvent

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
fun main() {
  val stringStore = object : RootStore<String>("Robots ") {
    val append = handle<String> { model, action: String ->
      "$model$action"
    }

    val keyDown = handle<KeyboardEvent> { _, action: KeyboardEvent ->
      "${action.keyCode}"
    }

    val clear = handle { "" }
  }

  GridStore.data.value.populate()

  render {
    p {
      stringStore.data.asText()
    }

    input {
      value(stringStore.data)
      changes.values() handledBy stringStore.update
      keydowns.events handledBy stringStore.keyDown
    }

    gridBox({
      columns { repeat(COLUMNS) { "1fr" } }
      gap { none }
      size { "48px" }
    }) {


      for (row in 0 until ROWS) {
        for (column in 0 until COLUMNS) {
          cell(GridStore.sub(cellLens(row, column)))
//          cell(dataModel.data.value.cell(row, column))
        }
      }

      keydowns.events handledBy GridStore.keyDown
    }
  }.mount("target")
}

