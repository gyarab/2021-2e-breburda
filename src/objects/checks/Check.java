package objects.checks;

import objects.Roll;

public class Check {

    public String name;
    public CheckType type;
    public int value;
    public String characterAttribute;
    public DamageType damageType;

    public Check(String name, CheckType type, int value, String characterAttribute, DamageType damageType) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.characterAttribute = characterAttribute;
        this.damageType = damageType;
    }

    public int roll() {
        return Roll.RollD20();
    }
}
