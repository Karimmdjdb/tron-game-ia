package game.model.platform;

import java.util.ArrayList;
import java.util.List;

import game.model.entities.Bike;

public class Team {
    private List<Bike> members;

    public Team() {
        members = new ArrayList<>();
    }

    public void addMember(Bike new_member) {
        members.add(new_member);
    }

    public void removeMember(Bike new_member) {
        members.remove(new_member);
    }

    public List<Bike> getMembers() {
        return members;
    }
}
