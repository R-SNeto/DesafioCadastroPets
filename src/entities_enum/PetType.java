package entities_enum;

public enum PetType {

    DOG(1),
    CAT(2);

    private final int code;

    PetType(int code) {
        this.code = code;
    }

    public int getCode(){
        return this.code;
    }

    public static PetType valueOf(int code) {
        for (PetType value : PetType.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid pet type code");
    }
}
