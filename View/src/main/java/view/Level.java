package view;

public enum Level {
    Easy(5),
    Medium(45),
    Hard(60),
    Łatwy(5),
    Średni(45),
    Trudny(60);
    private int number;

    Level(int ile) {
        this.number = ile;
    }

    public int getNumber() {
        return number;
    }
}
