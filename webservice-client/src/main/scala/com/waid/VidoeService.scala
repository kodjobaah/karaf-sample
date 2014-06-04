package com.waid

import spray.json.DefaultJsonProtocol
import spray.httpx.unmarshalling._
import spray.httpx.marshalling._
import spray.http._
import HttpCharsets._
import MediaTypes._
import spray.http._
import spray.http.HttpRequest
import spray.http.HttpResponse
import spray.http.Timedout
import com.waid.model.Value.UpdateDetails
import HttpMethods._
import MediaTypes._
import akka.actor._
import akka.util.Timeout
import com.waid.model.Value.UpdateDetails
import java.util.UUID
import scala.concurrent.duration._
import spray.can.Http
import spray.http._

class VideoService extends Actor with ActorLogging {

  case class Video(videoId: String,name: String)

  object MyJsonProtocol extends DefaultJsonProtocol {
    implicit val videoFormat = jsonFormat2(Video)
  }


  implicit val timeout: Timeout = 1.second // for the actor 'asks'

  import context.dispatcher

  // ExecutionContext for the futures and scheduler
  implicit val system = ActorSystem()

  def receive = {
    // when a new connection comes in we register ourselves as the connection handler
    case _: Http.Connected => sender ! Http.Register(self)

    case HttpRequest(GET, Uri.Path("/"),_,_,_) =>
      sender ! index
    case HttpRequest(POST, Uri.Path("/video/setname"),headers, entity: HttpEntity , protocol) =>
      import MyJsonProtocol._
      import spray.httpx.SprayJsonSupport._
      import spray.util._


      val video = entity.as[Video].right
      val rand = UUID.randomUUID().toString
      val ud = system.actorOf(UpdateVideoDetails.props(), name = "updateService-"+rand)
      val message = UpdateDetails(video.get.videoId,video.get.name,sender)
      ud ! message

    case HttpRequest(GET, Uri.Path("/stop"), _, _, _) =>
      sender ! HttpResponse(entity = "Shutting down in 1 second ...")
      sender ! Http.Close
      context.system.scheduler.scheduleOnce(1.second) { context.system.shutdown() }

    case _: HttpRequest => sender ! HttpResponse(status = 404, entity = "Unknown resource!")

    case Timedout(HttpRequest(_, Uri.Path("/timeout/timeout"), _, _, _)) =>
      log.info("Dropping Timeout message")

    case Timedout(HttpRequest(method, uri, _, _, _)) =>
      sender ! HttpResponse(
        status = 500,
        entity = "The " + method + " request to '" + uri + "' has timed out..."
      )
  }

  ////////////// helpers //////////////

  lazy val index = HttpResponse(
    entity = HttpEntity(`text/html`,
      """<!DOCTYPE html>
      <html lang="en">
        <head>
          <script type="text/javascript" src="http://192.168.1.6/jquery-1.11.1.min.js"></script>
          <!-- Latest compiled and minified CSS -->
          <link rel="stylesheet" href="//192.168.1.6/bootstrap/css/bootstrap.min.css">
          <!-- Optional theme -->
          <link rel="stylesheet" href="//192.168.1.6/bootstrap/css/bootstrap-theme.min.css">

          <!-- Latest compiled and minified JavaScript -->
          <script src="//192.168.1.6/bootstrap/js/bootstrap.min.js"></script>
        </head>
        <body>
          <script>


            $( document ).ready(function() {
                console.log( "document loaded" );
                  $( "#target" ).submit(function( event ) {
                      alert( "Handler for .submit() called." );
                      event.preventDefault();
                         var videoId =    $("#target").find("#videoid").val();
                         var name = $("#target").find("#name").val();

                         var d = "{ \"videoId\": \""+videoId+"\",\"name\" :\""+ name+"\"}";

                         $.ajax(
                                     {
                                    dataType: "json",
                                    contentType: "application/json",
                                    type: "POST",
                                    url: "http://localhost:8080/video/setname",
                                    data: d
                                }
                         )
                        .done(function(data) {

                            var items = [];
                            $.each( data, function( key, val ) {
                                items.push( "<li id='" + key + "'>" + val + "</li>" );
                           });

                           $( "<ul/>", {
                              "class": "my-new-list",
                              html: items.join( "" )
                          }).appendTo( "#results" );
                               alert( "success:"+res );
                          })
                        .fail(function() {
                          alert( "error" );
                        })
                         .always(function() {
                          alert( "complete" );
                        });

                  });



            });
          </script>
          <div class="container">

        <form id="target" class="navbar-form navbar-left" role="search">
          <div class="form-group">

            <dl class="dl-horizontal">
              <dt>Video Id </dt>
              <dd><input type="text" id="videoid" class="form-control" placeholder="video id"></dd>
              <dt>Viewer</dt>
              <dd><input type="text" id="name" class="form-control" placeholder="viewer"></dd>

        </dl>
        </div>
          <div class="form-group">
          <button type="submit" class="btn btn-default">Submit</button>
          </div>
        </form>
         </div>
         <div>
            <div id="results">&nbsp;</div>
         </div>
        </body>
      </html>"""
    )
  )

}
