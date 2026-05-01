package entities_enum;

public enum Gender {
    FEMALE(1),
    MALE(2);

    private final int code;

    Gender(int code) {
        this.code = code;
    }

    public int getCode(){
        return this.code;
    }

    public static Gender valueOf(int code) {
        for (Gender value : Gender.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid pet gender code");
    }
}
