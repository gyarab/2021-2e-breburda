import objects.*;
import objects.Character;
import objects.checks.AttackCheck;
import objects.checks.CheckType;
import objects.checks.DamageType;

import java.util.ArrayList;
import java.util.Objects;

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
        MyScanner sc = new MyScanner();
        ArrayList<AttackCheck> checks = new ArrayList<>();

        while (true) {
            String name = sc.next("Add weapon: (When you want to end adding checks just type end after name: )", "\"ShortSword\"");

            if (name.equals("end")) {
                return checks;
            }
            String line = sc.nextLine("Rolls: ", "\" 2 d6 \"");
            String[] rollsAr = line.split(", ");

            ArrayList<Roll> rolls = new ArrayList<>();
            for (String s : rollsAr) rolls.add(getRoll(s));

            DamageType dmgType = damageType(sc.next("Damage type: ", "\" slashing \""));

            String x = sc.nextLine("Check type: ", "\" Simple Melee Weapon \"");
            CheckType checkType = getCheckType(x);

            String characterAttr = getCharacterAttribute(sc.next("Character attribute: ", "\" dexterity \""));


            checks.add(
                new AttackCheck(
                    name,
                    checkType,
                    characterAttr,
                    dmgType,
                    rolls
                )
            );
        }
    }

    public static Roll getRoll(String roll) throws Exception {
        DieType type = switch (roll.split(" ")[1].toUpperCase()) {
            case "D4" -> DieType.D4;
            case "D6" -> DieType.D6;
            case "D8" -> DieType.D8;
            case "D10" -> DieType.D10;
            case "D12" -> DieType.D12;
            case "D20" -> DieType.D20;
            default -> throw new Exception("Invalid value");
        };

        return new Roll(
                type,
                Integer.parseInt(roll.split(" ")[0])
        );
    }

    public static String getCharacterAttribute(String attr) throws Exception {
        return switch (attr.toUpperCase()) {
            case "STRENGTH" -> "strength";
            case "DEXTERITY" -> "dexterity";
            case "INTELLIGENCE" -> "intelligence";
            case "WISDOM" -> "wisdom";
            case "CHARISMA" -> "charisma";
            default -> throw new Exception("Invalid character attribute");
        };
    }

    public static CheckType getCheckType(String ch) throws Exception {
        return switch (ch.toUpperCase()) {
            case "SIMPLE MELEE WEAPON" -> CheckType.SimpleMeleeWeapons;
            case "SIMPLE RANGED WEAPON" -> CheckType.SimpleRangedWeapons;
            case "MARTIAL MELEE WEAPON" -> CheckType.MartialMeleeWeapons;
            case "MARTIAL RANGED WEAPON" -> CheckType.MartialRangedWeapons;
            case "SPELL" -> CheckType.Spell;
            case "ARMOR" -> CheckType.Armor;
            default -> throw new Exception("Invalid value");
        };
    }

    public static Character load() {
        MyScanner sc = new MyScanner();

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
            String name = names[idx];
            stats[idx] = sc.nextInt(name + ": ", " 1 - 20");
            idx++;
        }

        String name = sc.next("Name: ", "\" Arnold \"");
        String className = sc.next("Class: ", "\" Barbarian \"");
        CharacterClass characterClass = switch (className.toUpperCase()) {
            case "BARBARIAN" -> CharacterClass.BARBARIAN;
            case "BARD" -> CharacterClass.BARD;
            case "CLERIC" -> CharacterClass.CLERIC;
            case "DRUID" -> CharacterClass.DRUID;
            case "FIGHTER" -> CharacterClass.FIGHTER;
            case "MONK" -> CharacterClass.MONK;
            case "SORCERER" -> CharacterClass.SORCERER;
            case "ROGUE" -> CharacterClass.ROGUE;
            default -> null;
        };

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

    public static DamageType[] parseSet(MyScanner sc, String title, String info) {
        try {
            String[] weaknessesAr = sc.nextLine(title, info).split(", ");

            if (Objects.equals(weaknessesAr[0], "none")) {
                return new DamageType[0];
            }

            DamageType[] attrSet = new DamageType[weaknessesAr.length];
            for (int i = 0; i < attrSet.length; i++) attrSet[i] = damageType(weaknessesAr[i]);
            return attrSet;
        } catch (Exception ex) {
            System.out.println("Zadané hodnoty nejsou platné");
            return parseSet(sc, title, info);
        }
    }

    public static Enemy parseEnemy() {
        MyScanner sc = new MyScanner();
        String name = sc.next("Enemy name: ", "\" Goblin \"");

        DamageType[] weaknesses = parseSet(sc, "Weaknesses: (split single damage types by comma and gap)", "\" Fire \"");
        DamageType[] resistances = parseSet(sc, "Resistances: (split single damage types by comma and gap)", "\" Fire \"");
        DamageType[] immunities = parseSet(sc, "Immunities: (split single damage types by comma and gap)", "\" Fire \"");
        return new Enemy(
                name,
                weaknesses,
                resistances,
                immunities
        );
    }

    public static DamageType damageType(String d) throws Exception {
        switch (d.toUpperCase()) {
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
                throw new Exception("Error");
        }
    }
}
