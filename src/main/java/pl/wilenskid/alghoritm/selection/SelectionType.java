package pl.wilenskid.alghoritm.selection;

public enum SelectionType {
    RANKING_METHOD("Ranking"),
    TOURNAMENT_METHOD("Tournament"),
    TOURNAMENT_NO_RETURN_METHOD("Tournament (no return)"),
    TOURNAMENT_3_METHOD("Tournament (three subjects group)"),
    ROULETTE_METHOD("Roulette");

    private final String label;

    SelectionType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
