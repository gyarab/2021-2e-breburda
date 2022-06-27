package objects;

import objects.checks.AttackCheck;
import objects.checks.Check;
import objects.checks.DamageType;

import java.util.ArrayList;

public class Enemy {
    String name;
    DamageType[] weaknesses;
    DamageType[] resistances;
    DamageType[] immunities;

    public Enemy(String name, DamageType[] weaknesses, DamageType[] resistances, DamageType[] immunities) {
        this.name = name;
        this.weaknesses = weaknesses;
        this.resistances = resistances;
        this.immunities = immunities;
    }

    public int damage(Attack a) {
        for (DamageType t : weaknesses) {
            if(t == a.damageType) return a.attack * 2;
        }
        for (DamageType t : resistances) {
            if(t == a.damageType) return a.attack / 2;
        }
        for (DamageType t : immunities) {
            if(t == a.damageType) return 0;
        }

        return a.attack;
    }

    public ArrayList<Attack> suggestAttackType(Character character) {
        ArrayList<Attack> attacks = new ArrayList<>();

        Attack max = character.unarmed();
        max.attack = damage(max);

        for (Check check : character.checks) {
            if (check instanceof AttackCheck) {
                Attack a = character.getStrength(check.name);
                a.attack = damage(a);
                if (a.attack > max.attack) {
                    attacks = new ArrayList<>();
                    attacks.add(a);
                    max = a;
                } else if (a.attack == max.attack)
                    attacks.add(a);
            }
        }

        return attacks;
    }

    public static void main(String[] args) {
        ArrayList<Attack> x = new ArrayList<>();
          x.add(new Attack(CharacterAttribute.CHARISMA, DamageType.ACID, 10, false));
          x.add(new Attack(CharacterAttribute.STRENGTH, DamageType.BLUDGEONING, 12, false));
          x.add(new Attack(CharacterAttribute.DEXTERITY, DamageType.PIERCING, 6, false));
          x.add(new Attack(CharacterAttribute.CHARISMA, DamageType.ACID, 10, false));
          x.add(new Attack(CharacterAttribute.CHARISMA, DamageType.ACID, 10, true));
//        printAttacks(x);
    }

    public String chars(int x, String a) {
        String s = "";

        for (int i = 0; i < x; i++)
            s += a;

        return s;
    }

    public void printAttacks(ArrayList<Attack> attacks) {
        System.out.println(name);

        ArrayList<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"Damage Type", "Average Damage", "Character Attribute"});

        int c1 = 11, c2 = 14, c3 = 19;

        for (Attack a : attacks) {
            String s1 = a.damageType + "";
            c1 = c1 < s1.length() ? s1.length() : c1;
            String s2 = a.attack + (a.unarmed ? " Unarmed strike" : "");
            c2 = c2 < s2.length() ? s2.length() : c2;
            String s3 = a.type + "";
            c3 = c3 < s3.length() ? s3.length() : c3;
            rows.add(new String[]{s1, s2, s3});
        }

        String divider = chars(c1 + c2 + c3 + 10, "-");
        System.out.println(divider);

        for (String[] r : rows) {
            String s1 = r[0], s2 = r[1], s3 = r[2];

            System.out.println("| " + s1 + chars(c1 - s1.length(), " ") +
                " | " + s2 + chars(c2 - s2.length(), " ") +
                " | " + s3 + chars(c3 - s3.length(), " ") + " |");
            System.out.println(divider);
        }
    }


}
