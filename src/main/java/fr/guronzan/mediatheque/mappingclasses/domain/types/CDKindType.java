package fr.guronzan.mediatheque.mappingclasses.domain.types;

public enum CDKindType {
    ROCK("Rock"), VARIETE_FRANCAISE("Vari�t� Fran�aise"), POP("Pop");

    private final String value;

    private CDKindType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
