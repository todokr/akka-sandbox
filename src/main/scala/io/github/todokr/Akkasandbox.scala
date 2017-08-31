package io.github.todokr

import akka.actor._
import akka.actor.OneForOneStrategy
import akka.actor.SupervisorStrategy._
import scala.concurrent.duration._
import akka.routing.BalancingPool
import java.util.concurrent.TimeoutException

object Akkasandbox extends App {

  val actorSystem = ActorSystem("akkaSandbox")
  val parentActor = actorSystem.actorOf(Props[ParentActor])

  (0 to 10) foreach { no ⇒
    Thread.sleep(100)
    parentActor ! Msg(no)
  }

  Thread.sleep(1000 * 10)
  actorSystem.terminate()
}

class ParentActor extends Actor {
  val childActorsPool = context.actorOf(BalancingPool(4).props(Props[ChildActor]))

  override val supervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = -1, withinTimeRange = Duration.Inf, loggingEnabled = false) { // 無制限にリトライし続ける
      case _: TimeoutException ⇒ Restart
      case _: Exception        ⇒ Escalate
    }

  def receive = {
    case Msg(no) ⇒ childActorsPool ! Msg(no)
  }
}

class ChildActor extends Actor {

  override def preRestart(reason: Throwable, message: Option[Any]) = {
    super.preRestart(reason, message)
    message.map(_.asInstanceOf[Msg]).foreach(x ⇒ println(s"restart: ${x.no}"))
    message.foreach(self ! _) // Restartされたら例外発生時のメッセージを再度自分に投げる
  }

  def receive = {
    case Msg(no) =>
      if (scala.util.Random.nextBoolean) {
        println(s"success: $no, ${Thread.currentThread().getName}")
      } else {
        println(s"failed : $no, ${Thread.currentThread().getName}")
        throw new TimeoutException(s"${Thread.currentThread().getName}: $no failed...")
      }
  }
}

case class Msg(no: Int)