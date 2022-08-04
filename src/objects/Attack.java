package objects;

import objects.checks.DamageType;

public class Attack {

    CharacterAttribute type;
    DamageType damageType;
    int attack;
    boolean unarmed;

    public Attack() {}

    public Attack(CharacterAttribute type, DamageType damageType, int attack, boolean unarmed) {
        this.type = type;
        this.damageType = damageType;
        this.attack = attack;
        this.unarmed = unarmed;
    }
}
