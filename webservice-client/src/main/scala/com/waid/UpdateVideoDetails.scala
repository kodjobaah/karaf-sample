package com.waid;

import akka.actor.{Props, ActorSystem, ActorLogging, Actor}
import akka.util.Timeout
import com.waid.model.Value.UpdateDetails
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory
import org.apache.cxf.endpoint.{Endpoint, ClientImpl, Client}
import java.lang.reflect.Method
import javax.xml.namespace.QName
import org.apache.cxf.common.classloader.ClassLoaderUtils
import org.apache.cxf.service.model._
import com.waid.model.Value.UpdateDetails
import java.util
import java.beans.PropertyDescriptor
import scala.Predef._
import com.waid.model.Value.UpdateDetails
import scala.reflect.ClassTag
import com.waid.utils.InvokeCXF
import spray.http.{HttpEntity, HttpResponse}
import spray.http.MediaTypes._
import spray.http.HttpResponse
import com.waid.model.Value.UpdateDetails

/**
 * Created with IntelliJ IDEA.
 * User: whatamidoing
 * Date: 02/06/14
 * Time: 14:11
 * To change this template use File | Settings | File Templates.
 */
class UpdateVideoDetails  extends Actor with ActorLogging {

  import spray.json.DefaultJsonProtocol

  case class Results(val name: String);

  object MyJsonProtocol extends DefaultJsonProtocol {

    import spray.json.JsonFormat
    implicit def resultsFormat = jsonFormat1(Results)
  }


    import scala.concurrent.duration._
    implicit val timeout: Timeout= 1.second // for the actor 'asks'
    import context.dispatcher // ExecutionContext for the futures and scheduler

    def receive = {


      case UpdateDetails(videoId, name,sender) =>
        import spray.http.ContentType
        val invokeCXF = new InvokeCXF()
        val m: util.Map[_,_] = invokeCXF.sendFetchVideo(videoId,name)

        import spray.http._
        import HttpCharsets._
        import MediaTypes._

        import spray.json._
        import MyJsonProtocol._

        val re = Results(m.get("name").asInstanceOf[String]).toJson
        val res = HttpResponse(
        entity = HttpEntity(contentType = ContentType(`application/json`, `UTF-8`),string = re.toString()))
        sender ! res
      case _ => println("should not be here")
    }

}

object UpdateVideoDetails {

  def props(): Props = Props[UpdateVideoDetails]
}
