package pl.edu.agh.sius.services

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSelection, Props}
import akka.event.LoggingReceive
import io.udash.rpc.AllClients
import pl.edu.agh.sius.rpc.ClientRPC

import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

object PiBroadcaster {
  def props(piMaster: ActorRef): Props =
    Props(classOf[PiBroadcaster], piMaster)

  sealed trait Message
  case object AskForPi extends Message
}

class PiBroadcaster(piMaster: ActorRef) extends Actor with ActorLogging {
  import PiBroadcaster._

  import context.dispatcher

  context.system.scheduler.schedule(1000 millis, 1000 millis, self, AskForPi)

  override def receive: Receive = LoggingReceive {
    case AskForPi =>
      piMaster ! PiMaster.GetValues
    case PiMaster.UpdateValues(totalPoints, pointsInCircle) =>
      ClientRPC(AllClients).update(totalPoints, pointsInCircle)
  }
}
