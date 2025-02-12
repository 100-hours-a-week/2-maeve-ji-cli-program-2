public class Player extends Character {
    private String job;
    private final int MAX_HP = 100;  // 플레이어 최대 HP

    public Player(String name, String job) {
        super(name, 100, 10);
        this.job = job;
    }

    public void showStats() {
        System.out.println("\n========================================");
        System.out.println("[Info] 플레이어 상태");
        System.out.println("이름  : " + name);
        System.out.println("직업  : " + job);
        System.out.println("레벨  : " + level);
        System.out.println("HP    : " + hp + "/" + MAX_HP);
        System.out.println("공격력: " + attack);
        System.out.println("========================================\n");
    }

    public void heal(int amount) {
        hp += amount;
        if (hp > MAX_HP) {
            hp = MAX_HP;
            System.out.println("[System] " + name + "이(가) HP를 최대치(" + MAX_HP + ")로 회복하였습니다!");
        } else {
            System.out.println("[System] " + name + "이(가) HP를 " + amount + " 회복했습니다. (현재 HP: " + hp + ")");
        }
    }
}
