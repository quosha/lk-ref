
package ru.bgpu.lk.ref;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация @AppConfig используется для пометки полей,
 * значения которых должны быть загружены из конфигурационного файла.
 * 
 * Аннотация применяется только к полям (@Target(ElementType.FIELD))
 * и сохраняется во время выполнения (@Retention(RetentionPolicy.RUNTIME)),
 * что позволяет использовать Reflection API для доступа к ней.
 * 
 * Параметр value() задает значение по умолчанию, которое будет использоваться,
 * если в конфигурационном файле отсутствует соответствующее свойство.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AppConfig {
    String value() default "";
}
