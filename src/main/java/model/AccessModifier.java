package model;

public enum AccessModifier {
    PUBLIC("public "),
    PROTECTED("protected "),
    PRIVATE("private "),
    DEFAULT(""); // 包私有（默认）权限没有关键字

    private final String keyword;

    AccessModifier(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return keyword;
    }
}