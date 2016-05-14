package pl.edu.agh.sius.services

import akka.actor.{Actor, Props}
import pl.edu.agh.sius.MonteCarlo

import scala.concurrent.duration.DurationInt
import scala.language.postfixOps


object PiWorker {
  def props(batchSize: Int): Props = Props(classOf[PiWorker], batchSize)

  sealed trait Message
  case object Start extends Message
  case object ComputeBatch extends Message
}
class PiWorker(batchSize: Int) extends Actor {
  import PiWorker._
  import context.dispatcher

  override def receive: Receive = {
    case Start =>
      context.system.scheduler.schedule(1000 millis, 1000 millis, self, ComputeBatch)
    case ComputeBatch =>
      context.parent ! PiMaster.UpdateValues(batchSize, MonteCarlo.pi(batchSize))
  }
}
