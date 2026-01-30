/**
 * Wyliczenie poziomów trudności gry Snake.
 * <p>
 * Każdy poziom trudności określa prędkość ruchu węża poprzez opóźnienie timera.
 * Mniejsze opóźnienie oznacza wyższy poziom trudności gry.
 * </p>
 *
 * @author Team 6 (vosadcha, abondarchuk)
 * @version 1.0
 */
public enum Difficulty {
    /** Łatwy poziom z opóźnieniem 150ms między ruchami */
    EASY(150, "Easy"),

    /** Normalny poziom z opóźnieniem 100ms między ruchami */
    NORMAL(100, "Normal"),

    /** Trudny poziom z opóźnieniem 70ms między ruchami */
    HARD(70, "Hard"),

    /** Ekstremalny poziom z opóźnieniem 40ms między ruchami */
    EXTREME(40, "Extreme");

    /** Opóźnienie między ruchami węża w milisekundach */
    private final int delay;

    /** Nazwa poziomu trudności do wyświetlania w interfejsie */
    private final String name;

    /**
     * Konstruktor poziomu trudności.
     *
     * @param delay opóźnienie między ruchami w milisekundach
     * @param name  nazwa poziomu do wyświetlania
     */
    Difficulty(int delay, String name) {
        this.delay = delay;
        this.name = name;
    }

    /**
     * Zwraca opóźnienie między ruchami węża.
     *
     * @return opóźnienie w milisekundach
     */
    public int getDelay() {
        return delay;
    }

    /**
     * Zwraca nazwę poziomu trudności do wyświetlania w interfejsie.
     *
     * @return nazwa poziomu trudności
     */
    public String getName() {
        return name;
    }
}