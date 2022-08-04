package objects.checks;

import objects.Roll;

import java.util.ArrayList;

public class AttackCheck extends Check {

    public ArrayList<Roll> rolls;

    public AttackCheck(String name, CheckType type, String characterAttribute, DamageType damageType, ArrayList<Roll> rolls) {
        super(name, type, 0, characterAttribute, damageType);
        this.rolls = rolls;
    }

    public int roll() {
        int c = 0;
        for (Roll r : rolls) {
            c += r.roll();
        }
        return c;
    }
}
