import dev.fritz2.components.box
import dev.fritz2.components.gridBox
import dev.fritz2.dom.html.render
import dev.fritz2.dom.mount
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
fun main() {
  render {
    p {
      +"FRITZ2"
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
    }
  }.mount("target")
}
