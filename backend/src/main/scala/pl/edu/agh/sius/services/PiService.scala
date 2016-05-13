package pl.edu.agh.sius.services

import io.udash.rpc.AllClients
import pl.edu.agh.sius.rpc.ClientRPC

import scala.util.Random

object PiService {
  import scala.concurrent.ExecutionContext.Implicits.global

  //TODO remove [sius]
  private[sius] var pointsInsideCircle = 0
  private[sius] var totalPoints = 0

  global.execute(new Runnable {
    override def run(): Unit = {
      while(true) {
        ClientRPC(AllClients).update(totalPoints, pointsInsideCircle)
        Thread.sleep(1000)
      }
    }
  })
}
