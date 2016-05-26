package pl.edu.agh.sius.rpc

import com.avsystem.commons.rpc.RPC
import io.udash.rpc._
import scala.concurrent.Future

@RPC
trait MainServerRPC {
  def addPoints(totalPoints: Int, pointsInsideCircle: Int): Unit
}

       