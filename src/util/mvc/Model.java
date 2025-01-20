package util.mvc;

public interface Model {
    public void addObserver(Observer o);
    public void removeObserver(Observer o);
    public void fireChangements();
}