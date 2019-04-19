package com.wangff.learning.designpatterns.observer.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ColorEnum {

    RED(1),
    GREEN(2),
    YELLOW(3)
    ;
    private int mode;

    public static ColorEnum modeOf(int mode) {
        for (ColorEnum colorEnum : values()) {
            if (colorEnum.mode==mode) {
                return colorEnum;
            }
        }
        return RED;
    }
}
