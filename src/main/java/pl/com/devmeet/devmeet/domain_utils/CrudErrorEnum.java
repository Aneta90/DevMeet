package pl.com.devmeet.devmeet.domain_utils;

public enum CrudErrorEnum {

    METHOD_NOT_IMPLEMENTED("Method not implemented");

    private String status;

    CrudErrorEnum(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
