package pl.edu.agh.sius.services

import scala.collection.mutable

object PiService {
  type Listener = (Int, Int) => Any

  private var totalPoints: Int = 0
  private var pointsInCircle: Int = 0
  private val listeners: mutable.Set[Listener] = mutable.Set.empty

  def update(totalPointsVal: Int, pointsInCircleVal: Int) = {
    totalPoints = totalPointsVal
    pointsInCircle = pointsInCircleVal
    listeners.foreach(_.apply(totalPoints, pointsInCircle))
  }

  def listen(listener: Listener): Unit = {
    listeners.add(listener)
    listener(totalPoints, pointsInCircle)
  }
}
