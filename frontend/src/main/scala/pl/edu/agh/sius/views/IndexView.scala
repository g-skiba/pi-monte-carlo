package pl.edu.agh.sius.views

import io.udash._
import io.udash.core.Presenter
import org.scalajs.dom
import org.scalajs.dom.{Element, Event}
import pl.edu.agh.sius._
import pl.edu.agh.sius.services.PiService

import scala.concurrent.ExecutionContext.Implicits.global

trait PiModel {
  def totalPoints: Double
  def pointsInCircle: Double
}

trait ResultsModel {
  def serverResult: PiModel
  def localResult: PiModel

  def pointsToCalculate: Int
}

object IndexViewPresenter extends ViewPresenter[IndexState.type] {
  override def create(): (View, Presenter[IndexState.type]) = {
    val model = ModelProperty[ResultsModel]
    model.subProp(_.pointsToCalculate).set(0)

    val presenter = new IndexPresenter(model)
    val view = new IndexView(model, presenter)
    (view, presenter)
  }
}

class IndexPresenter(model: ModelProperty[ResultsModel]) extends Presenter[IndexState.type] {
  import Context._
  PiService.listen((totalPoints, pointsInCircle) => {
    val serverModel = model.subModel(_.serverResult)
    serverModel.subProp(_.totalPoints).set(totalPoints)
    serverModel.subProp(_.pointsInCircle).set(pointsInCircle)
  })

  val handler = dom.window.setInterval(() => {
    val count = Math.min(10000, model.subProp(_.pointsToCalculate).get)
    if (count > 0) {
      val inside = MonteCarlo.pi(count)
      val localModel = model.subModel(_.localResult)
      localModel.subProp(_.totalPoints).set(localModel.subProp(_.totalPoints).get + count)
      localModel.subProp(_.pointsInCircle).set(localModel.subProp(_.pointsInCircle).get + inside)
      serverRpc.addPoints(count, inside)
      model.subProp(_.pointsToCalculate).set(model.subProp(_.pointsToCalculate).get - count)
    }
  }, 1000)

  override def handleState(state: IndexState.type): Unit = ()

  override def onClose() = dom.window.clearInterval(handler)

  def addPointsToCalculate(count: Int): Unit = {
    val points: Property[Int] = model.subProp(_.pointsToCalculate)
    points.set(points.get + count)
  }

  def pi(totalPoints: Double, pointsInCircle: Double): Double =
    4.0 * pointsInCircle / totalPoints
}

class IndexView(model: ModelProperty[ResultsModel], presenter: IndexPresenter) extends View {
  import scalatags.JsDom.all._

  def resultsTemplate(resultModel: ModelProperty[PiModel]) =
    ul(
      li(
        "Aktualna wartość PI: ",
        bind(resultModel.transform((piModel) => presenter.pi(piModel.totalPoints, piModel.pointsInCircle)))
      ),
      li(
        "Liczba punktów: ",
        bind(resultModel.subProp(_.totalPoints))
      ),
      li(
        "Liczba punktów wewnątrz koła: ",
        bind(resultModel.subProp(_.pointsInCircle))
      )
    )

  private val content = div(
    h3("Wynik z serwera"),
    resultsTemplate(model.subModel(_.serverResult)),
    h3("Wynik lokalny"),
    resultsTemplate(model.subModel(_.localResult)),
    h3("Obliczenia"),
    p("Pozostało ", bind(model.subProp(_.pointsToCalculate)), " punktów do przeliczenia."),
    button(onclick := ((_: Event) => presenter.addPointsToCalculate(10000)))("Dodaj 10 tys. punktów")
  ).render

  override def getTemplate: Element = content

  override def renderChild(view: View): Unit = {}
}