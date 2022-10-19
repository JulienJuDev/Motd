import akka.actor.typed.Behavior
import akka.actor.typed.javadsl.AbstractBehavior
import akka.actor.typed.javadsl.ActorContext
import akka.actor.typed.javadsl.Behaviors
import akka.actor.typed.javadsl.Receive
import java.math.BigInteger
import java.util.*

class WorkerBehavior(context: ActorContext<String>) : AbstractBehavior<String>(context) {

    companion object {
        // instantiate this behaviour without calling having to call constructor
        fun create(): Behavior<String> = Behaviors.setup { context ->
            WorkerBehavior(context)
        }
    }

    override fun createReceive(): Receive<String> =
        newReceiveBuilder()
            .onMessageEquals("start") {
                val bigInteger = BigInteger(2000, Random())
                println("I'm ${context.self.path().name()} and I find that number ${bigInteger.nextProbablePrime()}")
                return@onMessageEquals this
            }
            .build()
}