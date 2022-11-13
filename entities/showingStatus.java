package entities;

public enum showingStatus {
    COMING_SOON("COMING SOON"),
    PREVIEW("PREVIEW"),
    NOW_SHOWING("NOW_SHOWING"),
    END_OF_SHOWING("END_OF_SHOWING");

    private final String nameText;

    private showingStatus(String nameText) {
        this.nameText = nameText;
    }

    public boolean isEqual(String nameToCompare) {
        return nameText.equals(nameToCompare);
    }

    public String toString() {
        return this.nameText;
    }
}
