package action;

import model.Farm;
import model.Player;

public interface Action {
    void execute(Player player, Farm farm, String args);
    boolean isExecutable(Player player);
}