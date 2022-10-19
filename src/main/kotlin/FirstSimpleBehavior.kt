import akka.actor.typed.Behavior
import akka.actor.typed.javadsl.AbstractBehavior
import akka.actor.typed.javadsl.ActorContext
import akka.actor.typed.javadsl.Behaviors
import akka.actor.typed.javadsl.Receive

class FirstSimpleBehavior(context: ActorContext<String>) : AbstractBehavior<String>(context) {

    companion object {
        // instantiate a FirstSimpleBehavior without calling having to call constructor
        fun create(): Behavior<String> = Behaviors.setup { context ->
            FirstSimpleBehavior(context)
        }
    }

    override fun createReceive(): Receive<String>? {
        return newReceiveBuilder()
            .onMessageEquals("say hello") {
                println("Hola !")
                return@onMessageEquals this
            }
            .onMessageEquals("who are you") {
                println("My path is : ${context.self.path()}")
                return@onMessageEquals this
            }
            .onMessageEquals("create a child") {
                val firstChildActor = context.spawn(FirstSimpleBehavior.create(), "firstSytemChildActor")
                firstChildActor.tell("who are you")
                return@onMessageEquals this
            }
            .onAnyMessage { message ->
                println("hola amigo, dat is ya message : $message")
                return@onAnyMessage this
            }
            .build()
    }
}