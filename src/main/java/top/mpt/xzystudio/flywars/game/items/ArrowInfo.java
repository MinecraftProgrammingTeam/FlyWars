package top.mpt.xzystudio.flywars.game.items;

import org.bukkit.Material;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 箭的信息的注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ArrowInfo {
    /**
     * 箭的名字
     */
    String name();

    /**
     * 箭的材料，默认光灵箭
     */
    Material material() default Material.SPECTRAL_ARROW;
}
