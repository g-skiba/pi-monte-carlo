package pl.edu.agh.sius.rpc

import pl.edu.agh.sius.services.PiService

class RPCService extends MainClientRPC {
  override def update(totalPoints: Double, pointsInCircle: Double): Unit =
    PiService.update(totalPoints, pointsInCircle)
}

       