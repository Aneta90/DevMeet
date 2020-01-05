package pl.com.devmeet.devmeetcore.domain_utils;

public enum CrudErrorEnum {

    METHOD_NOT_IMPLEMENTED("Method not implemented"),
    INCORRECT_VALUES("Incorrect values");

    private String status;

    CrudErrorEnum(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
