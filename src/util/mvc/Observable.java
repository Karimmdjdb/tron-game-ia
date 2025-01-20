package util.mvc;

public interface Observable {
    public void addObserver(Observer o);
    public void removeObserver(Observer o);
    public void fireChangements();
}