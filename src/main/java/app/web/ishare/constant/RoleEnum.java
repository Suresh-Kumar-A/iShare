package app.web.ishare.constant;

import lombok.Getter;

@Getter
public enum RoleEnum {
    GUEST(1), USER(2), CREATOR(3), ADMIN(4);

    private int value = 1;

    private RoleEnum(int value) {
        this.value = value;
    }

    private RoleEnum(String text) {
        switch (text.toUpperCase()) {
            case "USER":
                value = 2;
                break;
            case "CREATOR":
                value = 3;
                break;
            case "ADMIN":
                value = 4;
                break;
            default:
                value = 1;
                break;
        }
    }
}
