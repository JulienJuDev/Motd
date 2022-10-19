import akka.actor.typed.ActorSystem

fun main() {
    val system: ActorSystem<String> = ActorSystem.create(ManagerBehavior.create(), "Manager")

    system.tell("start")
}