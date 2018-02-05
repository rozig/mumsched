package mumsched.entity;

public enum UserRoles {
    STUDENT("STUDENT"), FACULTY("FACULTY"), ADMIN("ADMIN");

    String value;

    UserRoles(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
