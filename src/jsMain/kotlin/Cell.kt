import dev.fritz2.components.box
import dev.fritz2.dom.html.RenderContext
import kotlinx.coroutines.ExperimentalCoroutinesApi

const val COLUMNS = 30
const val ROWS = 15
const val ROBOTS = 5

@ExperimentalCoroutinesApi
fun RenderContext.cell(value: String) = box({
  size { "48px" }

  border {
    width { "1pt" }
    color { "black" }
    style { solid }
  }

  background { color { "white" } }
  display { flex }
  justifyContent { center }
  alignItems { center }
}) { +value }
