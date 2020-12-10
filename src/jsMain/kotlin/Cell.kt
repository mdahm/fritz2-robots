import dev.fritz2.components.box
import dev.fritz2.dom.html.RenderContext
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
fun RenderContext.cell(value: String) = box({
  size { "60px" }
  background { color { warning } }
  display { flex }
  justifyContent { center }
  alignItems { center }
}) { +value }
