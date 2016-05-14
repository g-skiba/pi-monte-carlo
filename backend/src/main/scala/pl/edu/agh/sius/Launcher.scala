package pl.edu.agh.sius

import akka.actor.ActorSystem
import pl.edu.agh.sius.jetty.ApplicationServer
import pl.edu.agh.sius.services.PiMaster

object Launcher {
  val workersCount: Int = 1
  val batchSize: Int = 100000

  def main(args: Array[String]): Unit = {
    val system = ActorSystem("piSystem")
    val piMaster = system.actorOf(PiMaster.props(workersCount, batchSize))

    val server = new ApplicationServer(8080, "backend/target/UdashStatic/WebContent", piMaster)
    server.start()
    piMaster ! PiMaster.Start
  }
}
