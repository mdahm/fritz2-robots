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
      columns { repeat(5) { "1fr" } }
      gap { none }
      /* further styles */
      border {
        color { dark }
        this@gridBox.size { "2em" }
        style { solid }
      }
    }) {
      cell("Hallo")
      cell("one")
      // all following items without styling for better readability!
      cell("two")
      cell("three")
      cell("four")
      cell("five")
      cell("six")
      cell("seven")
    }
  }.mount("target")
}
