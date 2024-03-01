import mpi.MPI;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DSMLibrary {
    private final Map<String, Set<Integer>> subscribers;
    private static final Lock lock = new ReentrantLock();
    private final Map<String, Integer> variables;

    public DSMLibrary() {
        this.subscribers = new ConcurrentHashMap<>();
        this.variables = new HashMap<>();
        this.variables.put("a", 1);
        this.variables.put("b", 2);
        this.variables.put("c", 3);
        this.variables.put("d", 4);
        this.variables.keySet().forEach(variable -> this.subscribers.put(variable, new HashSet<>()));
    }

    public void setVariable(String variableName, int newValue) {
        this.variables.put(variableName, newValue);
    }

    public void writeVariable(String variableName, int newValue) {
        lock.lock();
        this.setVariable(variableName, newValue);
        this.sendMessageToSubscribers(variableName, new WriteMessage(variableName, newValue));
        lock.unlock();
    }

    public void compareAndExchange(String variableName, int oldValue, int newValue) {
        if (this.variables.get(variableName) == oldValue) {
            this.writeVariable(variableName, newValue);
        }
    }

    public void addNewSubscriptionToVariable(String variableName, int processRank) {
        this.subscribers.get(variableName).add(processRank);
    }

    public void subscribeCurrentProcessToVariable(String variableName) {
        this.subscribers.get(variableName).add(MPI.COMM_WORLD.Rank());
        this.sendMessageToAllProcesses(new SubscribeMessage(variableName, MPI.COMM_WORLD.Rank()));
    }

    public void sendMessageToSubscribers(String variableName, BaseMessage message) {
        this.subscribers.get(variableName).forEach(subscriberRank -> MPI.COMM_WORLD.Send(new Object[]{message}, 0, 1, MPI.OBJECT, subscriberRank, 0));
    }

    private void sendMessageToAllProcesses(BaseMessage message) {
        for (int i = 0; i < MPI.COMM_WORLD.Size(); i++) {
            if (MPI.COMM_WORLD.Rank() == i && !(message instanceof CloseMessage))
                continue;
            MPI.COMM_WORLD.Send(new Object[]{message}, 0, 1, MPI.OBJECT, i, 0);
        }
    }

    public void close() {
        this.sendMessageToAllProcesses(new CloseMessage());
    }

    @Override
    public String toString() {
        return "DSMLibrary{" +
                "subscribers=" + this.subscribers +
                ", variables=" + this.variables +
                '}';
    }
}
