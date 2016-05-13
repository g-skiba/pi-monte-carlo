package pl.edu.agh.sius.views

import io.udash._
import pl.edu.agh.sius.IndexState
import org.scalajs.dom.Element

object ErrorViewPresenter extends DefaultViewPresenterFactory[IndexState.type](() => new ErrorView)

class ErrorView extends View {
  import scalatags.JsDom.all._

  private val content = h3(
    "URL not found!"
  ).render

  override def getTemplate: Element = content

  override def renderChild(view: View): Unit = {}
}