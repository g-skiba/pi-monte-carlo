package pl.edu.agh.sius.rpc

import com.avsystem.commons.rpc.RPC

@RPC
trait MainClientRPC {
  def update(totalPoints: Int, pointsInCircle: Int): Unit
}
       