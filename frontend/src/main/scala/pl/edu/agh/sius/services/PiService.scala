package pl.edu.agh.sius.services

import scala.collection.mutable

object PiService {
  type Listener = (Double, Double) => Any

  private var totalPoints: Double = 0
  private var pointsInCircle: Double = 0
  private val listeners: mutable.Set[Listener] = mutable.Set.empty

  def update(totalPointsVal: Double, pointsInCircleVal: Double) = {
    totalPoints = totalPointsVal
    pointsInCircle = pointsInCircleVal
    listeners.foreach(_.apply(totalPoints, pointsInCircle))
  }

  def listen(listener: Listener): Unit = {
    listeners.add(listener)
    listener(totalPoints, pointsInCircle)
  }
}
