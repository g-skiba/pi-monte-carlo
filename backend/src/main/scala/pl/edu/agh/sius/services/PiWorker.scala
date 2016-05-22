package pl.edu.agh.sius.services

import akka.actor.{Actor, ActorRef, Props}
import pl.edu.agh.sius.MonteCarlo

import scala.concurrent.duration.DurationInt
import scala.language.postfixOps


object PiWorker {
  def props(piMaster: ActorRef, batchSize: Int): Props = Props(classOf[PiWorker], piMaster, batchSize)

  sealed trait Message
  case object Start extends Message
  case object ComputeBatch extends Message
}
class PiWorker(piMaster: ActorRef, batchSize: Int) extends Actor {
  import PiWorker._
  import context.dispatcher

  override def receive: Receive = {
    case Start =>
      context.system.scheduler.schedule(1000 millis, 1000 millis, self, ComputeBatch)
    case ComputeBatch =>
      piMaster ! PiMaster.UpdateValues(batchSize, MonteCarlo.pi(batchSize))
  }
}
