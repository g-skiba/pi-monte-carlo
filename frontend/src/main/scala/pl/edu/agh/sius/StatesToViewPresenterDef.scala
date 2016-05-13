package pl.edu.agh.sius

import io.udash._
import pl.edu.agh.sius.views._

class StatesToViewPresenterDef extends ViewPresenterRegistry[RoutingState] {
  def matchStateToResolver(state: RoutingState): ViewPresenter[_ <: RoutingState] = state match {
    case RootState => RootViewPresenter
    case IndexState => IndexViewPresenter
    case _ => ErrorViewPresenter
  }
}