package pl.edu.agh.sius

import scala.util.Random

object MonteCarlo {
  def pi(points: Int): Int =
    Stream.fill(points)((Random.nextDouble(), Random.nextDouble()))
      .count({
        case (x, y) =>
          x*x + y*y < 1
      })
}
