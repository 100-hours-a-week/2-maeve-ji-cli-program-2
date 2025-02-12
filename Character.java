import java.util.ArrayList;
import java.util.List;

public class Character extends Entity {
    protected int level, exp;
    protected List<Item> inventory;

    public Character(String name, int hp, int attack) {
        super(name, hp, attack);
        this.level = 1;
        this.exp = 0;
        this.inventory = new ArrayList<>();
    }

    public void attack(Character target) {
        System.out.println("[Action] " + name + "이(가) " + target.name + "을(를) 공격!");
        target.takeDamage(attack);
    }

    public void gainExp(int amount) {
        exp += amount;
        System.out.println("\n[System] " + name + "이(가) " + amount + " 경험치를 얻었습니다!");
        if (exp >= level * 10) {
            levelUp();
        }
    }

    private void levelUp() {
        level++;
        exp = 0;
        attack += 2;
        hp += 10;
        System.out.println("=== 레벨 업! ===\n[System] " + name + " → 현재 레벨: " + level
                + " (HP +10, 공격력 +2 증가)\n");
    }
}
