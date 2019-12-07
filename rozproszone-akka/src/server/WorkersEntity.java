package server;

import akka.actor.ActorRef;

public class WorkersEntity {
    private final ActorRef firstWorker;
    private final ActorRef secondWorker;
    private ActorRef client;
    private boolean isTaskDone = false;
    private boolean firstActorDone = false;

    public WorkersEntity(ActorRef firstWorker, ActorRef secondWorker) {
        this.firstWorker = firstWorker;
        this.secondWorker = secondWorker;
    }

    public ActorRef getFirstWorker() {
        return firstWorker;
    }

    public ActorRef getSecondWorker() {
        return secondWorker;
    }

    public ActorRef getClient() {
        return client;
    }

    public void setClient(ActorRef client) {
        this.client = client;
    }

    public boolean isTaskDone() {
        return isTaskDone;
    }

    public void setTaskDone(boolean taskDone) {
        isTaskDone = taskDone;
    }

    public boolean isFirstActorDone() {
        return firstActorDone;
    }

    public void setFirstActorDone(boolean firstActorDone) {
        this.firstActorDone = firstActorDone;
    }
}
