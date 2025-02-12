public class Entity {
    protected String name;
    protected int hp, attack;

    public Entity(String name, int hp, int attack) {
        this.name = name;
        this.hp = hp;
        this.attack = attack;
    }

    public void takeDamage(int damage) {
        hp -= damage;
        System.out.println("[System] " + name + "이(가) " + damage + "의 피해를 입었습니다. 남은 HP: " + hp);
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public String getName(){
        return name;
    }
}
