package pl.edu.agh.sius.views

import io.udash._
import pl.edu.agh.sius.RootState
import org.scalajs.dom.Element
import scalatags.JsDom.tags2.main
import pl.edu.agh.sius.views.components.{Footer, Header}
import pl.edu.agh.sius.styles.{DemoStyles, GlobalStyles}
import scalacss.ScalatagsCss._

object RootViewPresenter extends DefaultViewPresenterFactory[RootState.type](() => new RootView)

class RootView extends View {
  import pl.edu.agh.sius.Context._
  import scalatags.JsDom.all._

  private var child: Element = div().render

  private val content = div(
    Header.getTemplate,
    main(GlobalStyles.main)(
      div(GlobalStyles.body)(
        h1("pi-monte-carlo"),
        child
      )
    )
,Footer.getTemplate
  ).render

  override def getTemplate: Element = content

  override def renderChild(view: View): Unit = {
    import io.udash.wrappers.jquery._
    val newChild = view.getTemplate
    jQ(child).replaceWith(newChild)
    child = newChild
  }
}