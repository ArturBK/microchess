package com.example.hellostream.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import play.api.libs.ws.ahc.AhcWSComponents
import com.example.hellostream.api.MicrochessStreamService
import com.example.hello.api.MicrochessService
import com.softwaremill.macwire._

class MicrochessStreamLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new MicrochessStreamApplication(context) {
      override def serviceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new MicrochessStreamApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[MicrochessStreamService])
}

abstract class MicrochessStreamApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer = serverFor[MicrochessStreamService](wire[MicrochessStreamServiceImpl])

  // Bind the MicrochessService client
  lazy val microchessService = serviceClient.implement[MicrochessService]
}
