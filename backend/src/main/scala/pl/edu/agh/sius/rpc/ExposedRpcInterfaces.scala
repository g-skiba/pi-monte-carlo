package pl.edu.agh.sius.rpc

import akka.actor.ActorRef
import io.udash.rpc._
import pl.edu.agh.sius.services.PiMaster

class ExposedRpcInterfaces(piMaster: ActorRef)(implicit clientId: ClientId) extends MainServerRPC {

  override def addPoints(totalPoints: Double, pointsInsideCircle: Double): Unit = {
    piMaster ! PiMaster.UpdateValues(totalPoints, pointsInsideCircle)
  }
}

       