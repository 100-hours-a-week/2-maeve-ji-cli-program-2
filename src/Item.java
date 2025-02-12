package src;

class Item {
    private String name;
    private String effect;
    private int healAmount;

    public Item(String name, String effect, int healAmount) {
        this.name = name;
        this.effect = effect;
        this.healAmount = healAmount;
    }

    public String getName() {
        return name;
    }

    public int getHealAmount() {
        return healAmount;
    }
}
