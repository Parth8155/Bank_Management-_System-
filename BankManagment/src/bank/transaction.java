package bank;

public class transaction {
    public int transactionID;
    public int sender;
    public int receiver;
    public double amount;
    public String message;
    public String date;

    public transaction(int transactionID, int sender, int receiver, double amount, String message, String date) {
        this.transactionID = transactionID;
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.message = message;
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Sender: %s, Receiver: %s, Amount: %.2f, Date: %s, Message: %s",
                transactionID, sender, receiver, amount, date.toString(), message);
    }
}
