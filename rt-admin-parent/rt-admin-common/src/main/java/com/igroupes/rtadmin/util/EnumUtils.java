package com.igroupes.rtadmin.util;

import com.igroupes.rtadmin.enums.CodeEnum;

public class EnumUtils {

    /**
     * 返回指定编码的'枚举'
     *
     * @param code
     * @return SharedObjTypeEnum
     * @throws
     */
    public static <E, T extends CodeEnum<E>> T getEnumByCode(Class<T> clazz, E code) {
        for (T _enum : clazz.getEnumConstants()){
            if (code == _enum.getCode()){
                return _enum;
            }
        }
        return null;
    }
}
