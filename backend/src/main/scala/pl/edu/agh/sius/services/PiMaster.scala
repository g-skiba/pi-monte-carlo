package pl.edu.agh.sius.services

import akka.actor.{Actor, ActorRef, ActorSelection, Props}
import io.udash.rpc.AllClients
import pl.edu.agh.sius.rpc.ClientRPC

import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

object PiMaster {
  def props(): Props = Props(classOf[PiMaster])

  sealed trait Message
  case class UpdateValues(totalPoints: Int, pointsInCircle: Int) extends Message
  case object GetValues extends Message
}

class PiMaster() extends Actor{
  import PiMaster._

  private var totalPoints = 0
  private var pointsInsideCircle = 0

  override def receive: Receive = {
    case UpdateValues(addTotalPoints, addPointsInCircle) =>
      totalPoints += addTotalPoints
      pointsInsideCircle += addPointsInCircle
    case GetValues =>
      sender() ! UpdateValues(totalPoints, pointsInsideCircle)
  }
}
