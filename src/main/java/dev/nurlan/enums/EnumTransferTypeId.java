package dev.nurlan.enums;

import java.util.HashMap;
import java.util.Map;

public enum EnumTransferTypeId {

    CARD_TO_CARD(1),

    CARD_TO_NO_ACCOUNT(2);


    private int value;

    private static final Map<Integer, EnumTransferTypeId> VALUES = new HashMap<>();

    static {
        for (EnumTransferTypeId type : EnumTransferTypeId.values()) {
            VALUES.put(type.value, type);
        }
    }

    EnumTransferTypeId(int enumValue) {
        this.value = enumValue;
    }

    public Integer getValue() {
        return value;
    }

    public static EnumTransferTypeId getEnum(Integer value) {
        if (value == null)
            return null;

        return VALUES.get(value.intValue());
    }
}
