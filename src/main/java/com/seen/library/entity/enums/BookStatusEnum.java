package com.seen.library.entity.enums;

/**
 * 理解枚举类
 */
public enum  BookStatusEnum {
    NORMAL(0),
    DELETE(1),
    RECOMMENDED(2),;

    private int value;

    BookStatusEnum(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
