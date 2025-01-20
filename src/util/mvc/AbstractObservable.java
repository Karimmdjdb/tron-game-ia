package util.mvc;

import java.util.List;
import java.util.ArrayList;

public class AbstractObservable implements Observable {
    private List<Observer> obeservers = new ArrayList<>();

    @Override
    public void addObserver(Observer o) {
        obeservers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        obeservers.remove(o);
    }

    @Override
    public void fireChangements() {
        for(Observer o : obeservers) {
            o.update(this);
        }
    }
}