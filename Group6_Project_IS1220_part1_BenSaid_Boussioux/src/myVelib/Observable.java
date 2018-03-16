package myVelib;

public interface Observable {
	public void setChanged();
	public void notifyObservers();
	public void addObserver(User user);
	public void deleteObserver(User user);
}
