package com.waid.model

import akka.actor.ActorRef

/**
 * Created with IntelliJ IDEA.
 * User: whatamidoing
 * Date: 02/06/14
 * Time: 14:23
 * To change this template use File | Settings | File Templates.
 */
object Value {
      case class UpdateDetails(val videoId: String, val name: String, val sender: ActorRef)
}