package fr.guronzan.mediatheque.mappingclasses.domain.types;

public enum VideoType {
    MOVIE("Movie"), CONCERT("Concert"), TV_SHOW("TV Show"), DOCUMENTARY(
            "Documentary");

    private final String value;

    private VideoType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
