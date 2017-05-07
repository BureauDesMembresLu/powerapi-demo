package org.keyboardplaying.demo.monitor

import org.keyboardplaying.demo.runtime.OsInformation
import org.powerapi.{PowerMeter, PowerMonitoring}
import org.powerapi.module.cpu.simple.{ProcFSCpuSimpleModule, SigarCpuSimpleModule}

import scala.concurrent.duration._

/**
  * @author Cyrille Chopelet
  */
class Monitor(pid: Int, os: OsInformation, out: org.powerapi.PowerDisplay) {

  private val SCANNING_INTERVAL = 5.second
  //val SCANNING_INTERVAL: FiniteDuration = 100.millisecond

  private val module = if (os.isWindows) SigarCpuSimpleModule() else ProcFSCpuSimpleModule()
  private val cpu = PowerMeter.loadModule(module)

  private var monitoring: PowerMonitoring = _

  def startMonitoring(): Unit = {
    monitoring = cpu.monitor(pid).every(SCANNING_INTERVAL) to out
  }

  def stopMonitoring(): Unit = {
    monitoring.cancel
    cpu.shutdown
  }
}
