package pl.edu.agh.sius.services

import akka.actor.Props
import akka.persistence.PersistentActor

import scala.language.postfixOps

object PiMaster {
  def props(): Props = Props(classOf[PiMaster])

  sealed trait Message
  case class UpdateValues(totalPoints: Double, pointsInCircle: Double) extends Message
  case object GetValues extends Message

  sealed trait Event
  case class UpdateValuesEvent(totalPoints: Double, pointsInCircle: Double) extends Event
}

class PiMaster extends PersistentActor {
  import PiMaster._

  override val persistenceId: String = "piMaster"

  private var totalPoints: Double = 0
  private var pointsInsideCircle: Double = 0

  override def receiveCommand: Receive = {
    case UpdateValues(addTotalPoints, addPointsInCircle) =>
      persist(UpdateValuesEvent(addTotalPoints, addPointsInCircle))(updateState)
    case GetValues =>
      sender() ! UpdateValues(totalPoints, pointsInsideCircle)
  }

  def updateState(event: Event): Unit = event match {
    case UpdateValuesEvent(addTotalPoints, addPointsInCircle) =>
      totalPoints += addTotalPoints
      pointsInsideCircle += addPointsInCircle
  }

  override def receiveRecover: Receive = {
    case event: Event => updateState(event)
  }

}
