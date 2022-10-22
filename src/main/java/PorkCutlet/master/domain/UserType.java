package PorkCutlet.master.domain;

public enum UserType {
    COMMON(0), ADMIN(1);
    int value =0;

    UserType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean isAdmin() {
        if(getValue() == 0) return false;
        return true;
    }
}
