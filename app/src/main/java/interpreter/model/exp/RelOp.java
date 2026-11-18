package interpreter.model.exp;

// Enumerates the relational operators supported by the language.
public enum RelOp {
    LT("<"),
    LE("<="),
    EQ("=="),
    NE("!="),
    GT(">"),
    GE(">=");

    private final String symbol;

    RelOp(String symbol) {
        this.symbol = symbol;
    }

    @Override
    // Returns a readable representation of this object.
    public String toString() {
        return symbol;
    }
}
