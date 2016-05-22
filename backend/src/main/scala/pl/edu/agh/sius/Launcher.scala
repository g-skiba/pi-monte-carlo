package pl.edu.agh.sius

import akka.actor.{ActorSystem, PoisonPill}
import akka.cluster.singleton.{ClusterSingletonManager, ClusterSingletonManagerSettings, ClusterSingletonProxy, ClusterSingletonProxySettings}
import com.typesafe.config.ConfigFactory
import pl.edu.agh.sius.jetty.ApplicationServer
import pl.edu.agh.sius.services.{PiBroadcaster, PiMaster, PiWorker}

object Launcher {
  val workersCount: Int = 8
  val batchSize: Int = 100000

  def main(args: Array[String]): Unit = {
    val udashConfig = ConfigFactory.load("udash.conf")
    val system = ActorSystem("piSystem")

    val piMasterManager = system.actorOf(ClusterSingletonManager.props(
      singletonProps = PiMaster.props(),
      terminationMessage = PoisonPill,
      settings = ClusterSingletonManagerSettings(system)),
      name = "piMaster")
    val piMaster = system.actorOf(ClusterSingletonProxy.props(
      singletonManagerPath = "/user/piMaster",
      settings = ClusterSingletonProxySettings(system)),
      name = "piMasterProxy")

    val piBroadcaster = system.actorOf(PiBroadcaster.props(piMaster), "broadcaster")
    val piWorkers = Seq.fill(workersCount)(system.actorOf(PiWorker.props(piMaster, batchSize)))

    val server = new ApplicationServer(udashConfig.getInt("udash.server.port"), "backend/target/UdashStatic/WebContent", piMaster)
    server.start()
    piWorkers.foreach(_ ! PiWorker.Start)
  }
}
