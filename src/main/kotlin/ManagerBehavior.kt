import akka.actor.typed.Behavior
import akka.actor.typed.javadsl.AbstractBehavior
import akka.actor.typed.javadsl.ActorContext
import akka.actor.typed.javadsl.Behaviors
import akka.actor.typed.javadsl.Receive

class ManagerBehavior(context: ActorContext<String>) : AbstractBehavior<String>(context) {

    companion object {
        // instantiate this behaviour without calling having to call constructor
        fun create(): Behavior<String> = Behaviors.setup { context ->
            ManagerBehavior(context)
        }
    }

    override fun createReceive(): Receive<String> =
        newReceiveBuilder()
            .onMessageEquals("start") {
                for (i in 1..20) {
                    context.spawn(WorkerBehavior.create(), "child_$i")
                        .tell("start")
                }
                return@onMessageEquals this
            }
            .build()
}