package pl.edu.agh.sius.rpc

import com.avsystem.commons.rpc.RPC

@RPC
trait MainClientRPC {
  def update(totalPoints: Double, pointsInCircle: Double): Unit
}
       