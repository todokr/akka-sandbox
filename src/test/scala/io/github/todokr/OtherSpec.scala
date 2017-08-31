package io.github.todokr

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FlatSpec, Matchers}
import akka.stream.scaladsl.{Sink, Source}
import org.scalatest.time._

class OtherSpec extends FlatSpec with Matchers with ScalaFutures {
    implicit val system = ActorSystem("test-actor-system")
    implicit val mat = ActorMaterializer()

    override implicit def patienceConfig: PatienceConfig = PatienceConfig(timeout = Span(5, Seconds))

    it should "work well" in {

      val input = Source(1 to 10)
      val sink = Sink.headOption[Int]

      val result = input.runWith(sink)
      result.futureValue shouldBe Some(1)

    }
}
