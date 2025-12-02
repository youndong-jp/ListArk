package com.example.ListArk.mapper;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class NullSafe {

    private NullSafe() {
        // 인스턴스화 방지
        throw new AssertionError("Utility class should not be instantiated");
    }

    /** 리스트 null-safe 처리 */
    public static <T> List<T> list(List<T> list) {
        return (list == null) ? Collections.emptyList() : list;
    }

    /** 객체 null-safe 처리 - NPE 발생 시 null 반환 */
    public static <T> T get(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (NullPointerException e) {
            return null;
        }
    }

    /** 객체 null-safe 처리 - 기본값 지정 */
    public static <T> T get(Supplier<T> supplier, T defaultValue) {
        try {
            T value = supplier.get();
            return (value != null) ? value : defaultValue;
        } catch (NullPointerException e) {
            return defaultValue;
        }
    }

    /** NPE가 날 가능성이 있는 supplier 안전하게 실행 (레거시 호환용) */
    @Deprecated
    public static <T> T tryGet(Supplier<T> supplier, T defaultValue) {
        return get(supplier, defaultValue);
    }
}