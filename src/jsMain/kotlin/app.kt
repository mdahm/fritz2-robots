import de.akquinet.cssp.fritz2.robots.COLUMNS
import de.akquinet.cssp.fritz2.robots.ROWS
import de.akquinet.cssp.fritz2.robots.cell
import dev.fritz2.binding.Handler
import dev.fritz2.binding.RootStore
import dev.fritz2.binding.SimpleHandler
import dev.fritz2.binding.storeOf
import dev.fritz2.components.gridBox
import dev.fritz2.dom.html.render
import dev.fritz2.dom.mount
import dev.fritz2.dom.values
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.forEach
import org.w3c.dom.events.KeyboardEvent

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
fun main() {
  val store = object : RootStore<String>("Robots ") {
    val append = handle<String> { model, action: String ->
      "$model$action"
    }

    val keyDown = handle<KeyboardEvent> { _, action: KeyboardEvent ->
      "${action.keyCode}"
    }

    val clear = handle { "" }
  }

  render {
    p {
      store.data.asText()
    }

    input {
      value(store.data)
      changes.values() handledBy store.update
      keydowns.events handledBy store.keyDown
    }

    gridBox({
      columns { repeat(COLUMNS) { "1fr" } }
      gap { none }
      size { "48px" }
    }) {
      for (row in 1..ROWS) {
        for (column in 1..COLUMNS) {
          cell("${row * column}")
        }
      }

//      keydowns.events handledBy SimpleHandler<KeyboardEvent> { flow, _ -> flow.collect() }
    }
  }.mount("target")
}

