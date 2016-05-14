package pl.edu.agh.sius

import akka.actor.ActorSystem
import pl.edu.agh.sius.jetty.ApplicationServer
import pl.edu.agh.sius.services.PiMaster

object Launcher {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("piSystem")
    val piMaster = system.actorOf(PiMaster.props)

    val server = new ApplicationServer(8080, "backend/target/UdashStatic/WebContent", piMaster)
    server.start()
    piMaster ! PiMaster.Start
  }
}
