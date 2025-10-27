public enum Difficulty {
    EASY(150, "Easy"),
    NORMAL(100, "Normal"),
    HARD(70, "Hard"),
    EXTREME(40, "Extreme");

    private final int delay;
    private final String name;

    Difficulty(int delay, String name) {
        this.delay = delay;
        this.name = name;
    }

    public int getDelay() {
        return delay;
    }

    public String getName() {
        return name;
    }
}