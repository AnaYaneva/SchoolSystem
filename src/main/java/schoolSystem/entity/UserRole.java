package schoolSystem.entity;

public enum UserRole {
    USER (Constants.USER),
    TEACHER (Constants.TEACHER),
    ADMIN (Constants.ADMIN);

    private final String name;

    UserRole(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return name.equals(otherName);
    }

}

