package es.ujaen.dae.eventosconsolamail.Observer;

public interface Observable {
	
    public void registerObserver(Observer observer);
    public void removeObserver(Observer observer);
    public void notifyObservers();
    public void cancelEvent();

}
