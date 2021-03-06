/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.keyboardplaying.demo.monitor

import org.keyboardplaying.demo.OsInformation
import org.powerapi.module.cpu.simple.{ProcFSCpuSimpleModule, SigarCpuSimpleModule}
import org.powerapi.{PowerDisplay, PowerMeter, PowerMonitoring}

import scala.concurrent.duration._

/**
  * @author Cyrille Chopelet
  */
class Monitor(os: OsInformation, interval: FiniteDuration) {

  private val module = if (os.isWindows) SigarCpuSimpleModule() else ProcFSCpuSimpleModule()
  private val cpu = PowerMeter.loadModule(module)

  private var monitoring: PowerMonitoring = _

  def startMonitoring(pid: Int, out: PowerDisplay): Unit = {
    monitoring = cpu.monitor(pid).every(interval) to out
  }

  def stopMonitoring(): Unit = {
    monitoring.cancel
  }

  def shutdown(): Unit = {
    cpu.shutdown
  }
}
