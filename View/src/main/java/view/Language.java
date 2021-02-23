package view;

public enum Language {
    Polish("pl"),
    English("en"),
    Polski("pl"),
    Angielski("en");
    private String value;

    Language(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
