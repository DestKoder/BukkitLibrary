package ru.dest.library.nms;

import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;

public enum NMS {
    v1_12_R1("ru.dest.library.nms.v1_12_R1.TagUtils", "SKULL:1:3"),
    v1_16_R3("ru.dest.library.nms.v1_16_R3.TagUtils", "PLAYER_HEAD")
    ;

    private final String tagUtilsClass;
    private final String headMaterial;

    NMS(String tagUtilsClass, String headMaterial) {
        this.tagUtilsClass = tagUtilsClass;
        this.headMaterial = headMaterial;
    }

    public String getHeadMaterial() {
        return headMaterial;
    }

    public @Nullable TagUtils createTagUtils() {
        try {
            return (TagUtils) Class.forName(tagUtilsClass).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                 ClassNotFoundException e) {
            return null;
        }
    }
}
