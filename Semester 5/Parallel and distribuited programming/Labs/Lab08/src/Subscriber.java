import mpi.MPI;

public class Subscriber implements Runnable {
    private final DSMLibrary dsmLibrary;

    public Subscriber(DSMLibrary dsmLibrary) {
        this.dsmLibrary = dsmLibrary;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Rank " + MPI.COMM_WORLD.Rank() + " waiting..");
            Object[] messageObject = new Object[1];

            MPI.COMM_WORLD.Recv(messageObject, 0, 1, MPI.OBJECT, MPI.ANY_SOURCE, MPI.ANY_TAG);
            BaseMessage message = (BaseMessage) messageObject[0];

            if (message instanceof CloseMessage) {
                System.out.println("Rank " + MPI.COMM_WORLD.Rank() + " stopped listening...");
                return;
            } else if (message instanceof SubscribeMessage) {
                SubscribeMessage subscribeMessage = (SubscribeMessage) message;
                System.out.println("Subscribe message received");
                System.out.println("Rank " + MPI.COMM_WORLD.Rank() + " received: rank " + subscribeMessage.rank + " subscribes to " + subscribeMessage.variableName);
                this.dsmLibrary.addNewSubscriptionToVariable(subscribeMessage.variableName, subscribeMessage.rank);
            } else if (message instanceof WriteMessage) {
                WriteMessage writeMessage = (WriteMessage) message;
                System.out.println("Update message received");
                System.out.println("Rank " + MPI.COMM_WORLD.Rank() + " received:" + writeMessage.variableName + "->" + writeMessage.newValue);
                this.dsmLibrary.setVariable(writeMessage.variableName, writeMessage.newValue);
            }

            System.out.println(this.dsmLibrary);
        }
    }
}
