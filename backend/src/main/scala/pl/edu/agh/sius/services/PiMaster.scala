package pl.edu.agh.sius.services

import akka.actor.{Actor, ActorRef, Props}
import io.udash.rpc.AllClients
import pl.edu.agh.sius.rpc.ClientRPC

import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

object PiMaster {
  def props(workersCount: Int, batchSize: Int): Props = Props(classOf[PiMaster], workersCount, batchSize)

  sealed trait Message
  case object Start extends Message
  case class UpdateValues(totalPoints: Int, pointsInCircle: Int) extends Message
  case object SendValues extends Message
}

class PiMaster(workersCount: Int, batchSize: Int) extends Actor{
  import context.dispatcher
  import PiMaster._

  private var totalPoints = 0
  private var pointsInsideCircle = 0

  private val workers: Seq[ActorRef] = Seq.fill(workersCount)(context.actorOf(PiWorker.props(batchSize)))

  override def receive: Receive = {
    case Start =>
      workers.foreach(_ ! PiWorker.Start)
      context.system.scheduler.schedule(1000 millis, 1000 millis, self, SendValues)
    case UpdateValues(addTotalPoints, addPointsInCircle) =>
      totalPoints += addTotalPoints
      pointsInsideCircle += addPointsInCircle
    case SendValues =>
      ClientRPC(AllClients).update(totalPoints, pointsInsideCircle)
  }
}
