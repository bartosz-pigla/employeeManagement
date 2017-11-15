package avra.hrsystem.employeemanagement.exceptions;

public class WrongFieldException extends RuntimeException{
    private String fieldName;

    public WrongFieldException(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
