package objects;

import objects.checks.AttackCheck;
import objects.checks.Check;
import objects.checks.CheckType;
import objects.checks.DamageType;

import java.util.ArrayList;
import java.util.Objects;

public class Character {
    String name;
    CharacterClass characterClass;
    int level;

    public Character(String name, CharacterClass characterClass, int strength, int dexterity, int constitution, int intelligence, int wisdom, int charisma) {
        this.name = name;
        this.characterClass = characterClass;
        this.strength = prepocti(strength);
        this.dexterity = prepocti(dexterity);
        this.constitution = prepocti(constitution);
        this.intelligence = prepocti(intelligence);
        this.wisdom = prepocti(wisdom);
        this.charisma = prepocti(charisma);
        this.level = 1;
    }

    public int prepocti(int v) {
        return v / 2 - 5;
    }

    final int strength;
    final int dexterity;
    final int constitution;
    final int intelligence;
    final int wisdom;
    final int charisma;

    public ArrayList<AttackCheck> checks;

    public int getArmorClass() {
        int armor = 0;

        for (Check c : checks) {
            if (c.type == CheckType.Armor) {
                armor += c.value;
            }
        }

        return dexterity + this.getProficiency("dexterity", true) + armor;
    }

    public int getProficiency(String type, boolean isProfficient)  {
        return characterClass.getProficiency(type) && isProfficient ? (int) (Math.ceil(level / 4.0) + 1) : 0;
    }

    public int getAttackStrength(int roll, CheckType checkType)  {
        return roll + strength + this.getProficiency("strength", characterClass.checkTypeIsProficient(checkType));
    }

    public int getAttackDexterity(int roll, CheckType checkType)  {
        return roll + dexterity + this.getProficiency("dexterity", characterClass.checkTypeIsProficient(checkType));
    }

    public int getAttackIntelligence(int roll, CheckType checkType)  {
        return roll + intelligence + this.getProficiency("intelligence", characterClass.checkTypeIsProficient(checkType));
    }

    public int getAttackWisdom(int roll, CheckType checkType)  {
        return roll + wisdom + this.getProficiency("wisdom", characterClass.checkTypeIsProficient(checkType));
    }
    public int getAttackCharisma(int roll, CheckType checkType)  {
        return roll + charisma + this.getProficiency("charisma", characterClass.checkTypeIsProficient(checkType));
    }

    public int getSavingStrength() {
        int roll = Roll.RollD20();
        return roll + strength + this.getProficiency("strength", true);
    }

    public int getSavingDexterity() {
        int roll = Roll.RollD20();
        return roll + dexterity + this.getProficiency("dexterity", true);
    }
    public int getSavingConstitution() {
        int roll = Roll.RollD20();
        return roll + constitution + this.getProficiency("constitution", true);
    }
    public int getSavingIntelligence() {
        int roll = Roll.RollD20();
        return roll + intelligence + this.getProficiency("intelligence", true);
    }
    public int getSavingWisdom() {
        int roll = Roll.RollD20();
        return roll + wisdom + this.getProficiency("wisdom", true);
    }

    public int getSavingCharisma() {
        int roll = Roll.RollD20();
        return roll + charisma + this.getProficiency("charisma", true);
    }

    public Attack getStrength(String name) {
        for (Check c : checks) {
            if (Objects.equals(c.name, name)) {
                int roll = c.roll();
                Attack attack = new Attack();

                switch (c.characterAttribute) {
                    case "strength":
                        attack.attack = c.value + getAttackStrength(roll, c.type);
                        attack.type = CharacterAttribute.STRENGTH;
                        attack.damageType = c.damageType;
                    case "dexterity":
                        attack.attack = c.value + getAttackDexterity(roll, c.type);
                        attack.type = CharacterAttribute.DEXTERITY;
                        attack.damageType = c.damageType;
                    case "intelligence":
                        attack.attack = c.value + getAttackIntelligence(roll, c.type);
                        attack.type = CharacterAttribute.INTELLIGENCE;
                        attack.damageType = c.damageType;
                    case "wisdom":
                        attack.attack = c.value + getAttackWisdom(roll, c.type);
                        attack.type = CharacterAttribute.WISDOM;
                        attack.damageType = c.damageType;
                    case "charisma":
                        attack.attack = c.value + getAttackCharisma(roll, c.type);
                        attack.type = CharacterAttribute.CHARISMA;
                        attack.damageType = c.damageType;
                }

                return attack;
            }
        }

        return unarmed();
    }

    public Attack unarmed() {
        Attack attack = new Attack();
        int hit = 1 + strength;
        attack.attack = hit <= 0 ? 1 : hit;
        attack.unarmed = true;
        attack.damageType = DamageType.BLUDGEONING;
        return attack;
    }
}
