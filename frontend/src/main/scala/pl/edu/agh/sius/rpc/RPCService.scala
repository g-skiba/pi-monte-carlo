package pl.edu.agh.sius.rpc

import pl.edu.agh.sius.services.PiService

class RPCService extends MainClientRPC {
  override def update(totalPoints: Int, pointsInCircle: Int): Unit =
    PiService.update(totalPoints, pointsInCircle)
}

       