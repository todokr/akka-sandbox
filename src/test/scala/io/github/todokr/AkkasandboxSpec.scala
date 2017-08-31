package io.github.todokr

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import org.scalatest.{FlatSpec, Matchers}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.model.StatusCodes

class AkkasandboxSpec extends FlatSpec with Matchers with ScalatestRouteTest {

    val service = new AkkasandboxService {
      override implicit val system: ActorSystem = ActorSystem("test-actor-system")
      override implicit val materializer:ActorMaterializer = ActorMaterializer()
    }

    "AkkasandboxService" should "return OK for GET requests to status path" in {
      Get("/status") ~> service.routes ~> check {
        status === StatusCodes.OK
      }
    }

}
