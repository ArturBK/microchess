package com.example.hellostream.impl

import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.example.hellostream.api.MicrochessStreamService
import com.example.hello.api.MicrochessService

import scala.concurrent.Future

/**
  * Implementation of the MicrochessStreamService.
  */
class MicrochessStreamServiceImpl(microchessService: MicrochessService) extends MicrochessStreamService {
  def stream = ServiceCall { hellos =>
    Future.successful(hellos.mapAsync(8)(microchessService.hello(_).invoke()))
  }
}
