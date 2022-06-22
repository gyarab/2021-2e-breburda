import objects.*;
import objects.Character;
import objects.checks.AttackCheck;
import objects.checks.Check;
import objects.checks.CheckType;
import objects.checks.DamageType;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

public class DND {

    public static void main(String[] args) throws Exception {
        Character ch = load();
        Enemy e = parseEnemy();
        ch.checks = parseCheck();

        System.out.println("Insert check name: ");
        ArrayList<Attack> attacks = e.suggestAttackType(ch);
        e.printAttacks(attacks);
    }

    public static ArrayList<AttackCheck> parseCheck() throws Exception {
        Scanner sc = new Scanner(System.in);
        ArrayList<AttackCheck> checks = new ArrayList<AttackCheck>();

        while (true) {
            System.out.print("Name: (When you want to end adding checks just type end after name: )");
            String name = sc.next();

            if (name.equals("end")) {
                return checks;
            }

            System.out.print("Attack damage: ");
            int value = sc.nextInt();

            System.out.println("Damage type: ");
            DamageType dmgType = damageType(sc.next());

            System.out.println("Check type: ");
            String x = sc.nextLine();

            while (x.equals("")) {
                x = sc.nextLine();
            }

            CheckType checkType = getCheckType(x);

            System.out.println("Character attribute: ");
            String characterAttr = getCharacterAttribute(sc.next());

            System.out.println("Rolls: ");
            String line = sc.nextLine();

            while (line.equals("")) {
                line = sc.nextLine();
            }

            String[] rollsAr = line.split(", ");


            ArrayList<Roll> rolls = new ArrayList<>();
            for (String s : rollsAr) rolls.add(getRoll(s));

            checks.add(
                new AttackCheck(
                    name,
                    checkType,
                    value,
                    characterAttr,
                    dmgType,
                    rolls
                )
            );
        }
    }

    public static Roll getRoll(String roll) {
        DieType type = null;

        switch (roll.split(" ")[0]) {
            case "D4":
                type = DieType.D4;
                break;
            case "D6":
                type = DieType.D6;
                break;
            case "D8":
                type = DieType.D8;
                break;
            case "D10":
                type = DieType.D10;
                break;
            case "D12":
                type = DieType.D12;
                break;
            case "D20":
                type = DieType.D20;
                break;
            default:
                type = DieType.D4;
        }

        return new Roll(
            type,
            Integer.parseInt(roll.split(" ")[1])
        );
    }

    public static String getCharacterAttribute(String attr) throws Exception {
        switch (attr) {
            case "strength":
                return "strength";
            case "dexterity":
                return "dexterity";
            case "intelligence":
                return "intelligence";
            case "wisdom":
                return "wisdom";
            case "charisma":
                return "charisma";
            default:
                throw new Exception("Invalid character attribute");
        }
    }

    public static CheckType getCheckType(String ch) {
        switch (ch) {
            case "Simple Melee Weapon":
                return CheckType.SimpleMeleeWeapons;
            case "Simple Ranged Weapon":
                return CheckType.SimpleRangedWeapons;
            case "Martial Melee Weapon":
                return CheckType.MartialMeleeWeapons;
            case "Martial Ranged Weapon":
                return CheckType.MartialRangedWeapons;
            case "Spell":
                return CheckType.Spell;
            case "Armor":
                return CheckType.Armor;
            default:
                return CheckType.Spell;
        }
    }

    public static Character load() {
        Scanner sc = new Scanner(System.in);

        String[] names = new String[]{
            "strenght",
            "dexterity",
            "constitution",
            "intelligence",
            "wisdom",
            "charisma",
        };
        int[] stats = new int[6];
        int idx = 0;

        while (idx < 6) {
            System.out.print(names[idx] + ": ");
            stats[idx] = sc.nextInt();
            idx++;
        }

        System.out.println("Name: ");
        String name = sc.next();
        System.out.println("Class: ");
        String className = sc.next();
        CharacterClass characterClass = null;

        switch (className) {
            case "BARBARIAN":
                characterClass = CharacterClass.BARBARIAN;
                break;
            case "BARD":
                characterClass = CharacterClass.BARD;
                break;
            case "CLERIC":
                characterClass = CharacterClass.CLERIC;
                break;
            case "DRUID":
                characterClass = CharacterClass.DRUID;
                break;
            case "FIGHTER":
                characterClass = CharacterClass.FIGHTER;
                break;
            case "MONK":
                characterClass = CharacterClass.MONK;
                break;
            case "SORCERER":
                characterClass = CharacterClass.SORCERER;
                break;
            case "ROGUE":
                characterClass = CharacterClass.ROGUE;
                break;
        }

        return new Character(
            name,
            characterClass,
            stats[0],
            stats[1],
            stats[2],
            stats[3],
            stats[4],
            stats[5]
        );
    }

    public static Enemy parseEnemy() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Name: ");
        String name = sc.next();

        System.out.println("Weaknesses: (split single damage types by comma and gap)");
        String[] weaknessesAr = sc.nextLine().split(", ");
        DamageType[] weaknesses = new DamageType[weaknessesAr.length];

        for (int i = 0; i < weaknesses.length; i++) weaknesses[i] = damageType(weaknessesAr[i]);

        System.out.println("Resistances: (split single damage types by comma and gap)");
        String[] resistancesAr = sc.nextLine().split(", ");
        DamageType[] resistances = new DamageType[resistancesAr.length];

        for (int i = 0; i < resistances.length; i++) resistances[i] = damageType(resistancesAr[i]);

        System.out.println("Immunities: (split single damage types by comma and gap)");
        String[] immunitiesAr = sc.nextLine().split(", ");
        DamageType[] immunities = new DamageType[immunitiesAr.length];

        for (int i = 0; i < immunities.length; i++) immunities[i] = damageType(immunitiesAr[i]);

        return new Enemy(
            name,
            weaknesses,
            resistances,
            immunities
        );
    }

    public static DamageType damageType(String d) {
        switch (d) {
            case "ACID":
                return DamageType.ACID;
            case "BLUDGEONING":
                return DamageType.BLUDGEONING;
            case "COLD":
                return DamageType.COLD;
            case "FIRE":
                return DamageType.FIRE;
            case "FORCE":
                return DamageType.FORCE;
            case "LIGHTNING":
                return DamageType.LIGHTNING;
            case "NECROTIC":
                return DamageType.NECROTIC;
            case "PIERCING":
                return DamageType.PIERCING;
            case "POISON":
                return DamageType.POISON;
            case "PSYCHIC":
                return DamageType.PSYCHIC;
            case "RADIANT":
                return DamageType.RADIANT;
            case "SLASHING":
                return DamageType.SLASHING;
            case "THUNDER":
                return DamageType.THUNDER;
            default:
                return DamageType.LIGHTNING;
        }
    }
}
