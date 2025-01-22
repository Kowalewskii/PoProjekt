package enums;

public enum GameLabelText {
    NO_GENOTYPE("Brak genotypu do pokazania"),
    NO_SELECTED_ANIMAL("Brak zwierzaka do pokazania");

    private final String communicat;

    GameLabelText(String communicat) {
        this.communicat = communicat;
    }

    public String toString() {
        return communicat;
    }
}