package pl.com.devmeet.devmeet.user.domain;

public enum DefaultUserLoginTypeEnum {

    PHONE(1),
    EMAIL(2);

    private final int defaultLogin;
    private String badLoginTypeMessage = "Bad login type";

    DefaultUserLoginTypeEnum(int defaultLogin) {
        this.defaultLogin = defaultLogin;
    }

    @Override
    public String toString() {
        if (defaultLogin == 1)
            return "phone";
        if (defaultLogin == 2)
            return "email";
        else
            throw new IllegalArgumentException(badLoginTypeMessage);
    }
}
