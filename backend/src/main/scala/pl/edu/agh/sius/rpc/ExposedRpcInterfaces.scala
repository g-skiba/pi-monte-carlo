package pl.edu.agh.sius.rpc

import io.udash.rpc._
import pl.edu.agh.sius.services.PiService

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class ExposedRpcInterfaces(implicit clientId: ClientId) extends MainServerRPC {
  override def addPoints(totalPoints: Int, pointsInsideCircle: Int): Unit = {
    //TODO send message
    PiService.totalPoints += totalPoints
    PiService.pointsInsideCircle += pointsInsideCircle
  }
}

       