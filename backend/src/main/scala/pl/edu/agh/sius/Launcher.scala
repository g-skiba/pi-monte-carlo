package pl.edu.agh.sius

import pl.edu.agh.sius.jetty.ApplicationServer
import pl.edu.agh.sius.services.PiService

object Launcher {
  def main(args: Array[String]): Unit = {
    val server = new ApplicationServer(8080, "backend/target/UdashStatic/WebContent")
    println(PiService)
    server.start()
  }
}
