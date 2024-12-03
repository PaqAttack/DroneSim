package mapItems;

public interface CommInterface {
	public void transmit(CommInterface reciever, Message msg);
	public void recieve(CommInterface transmitter, Message msg);
}
