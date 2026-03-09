
package ru.bgpu.lk.ref;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

/**
 * Класс AppConfigWorker отвечает за инициализацию полей, помеченных аннотацией @AppConfig,
 * значениями из конфигурационного файла с использованием Reflection API.
 * 
 * Основной метод init выполняет следующие шаги:
 * 1. Сканирует указанный пакет для поиска полей с аннотацией @AppConfig.
 * 2. Загружает свойства из конфигурационного файла.
 * 3. Для каждого найденного поля устанавливает значение из свойств или значение по умолчанию из аннотации.
 * 4. Использует Reflection API для доступа к приватным статическим полям и изменения их значений.
 */
public class AppConfigWorker {
    
    /**
     * Инициализирует все поля с аннотацией @AppConfig в указанном пакете.
     * 
     * @param packageName имя пакета для сканирования (например, "ru.bgpu.lk.ref")
     * @param propFile путь к файлу свойств (например, "config.properties")
     */
    public static void init(String packageName, String propFile) {
        try {
            // Создаем конфигурацию для сканирования классов в указанном пакете
            Reflections reflections = new Reflections(new ConfigurationBuilder()
                    .setUrls(ClasspathHelper.forPackage(packageName))
                    .setScanners(Scanners.FieldsAnnotated)
            );
            
            // Загружаем свойства из конфигурационного файла
            Properties properties = new Properties();
            properties.load(new FileInputStream(new File(propFile)));
            
            // Получаем все поля с аннотацией @AppConfig и обрабатываем каждое
            reflections.getFieldsAnnotatedWith(AppConfig.class).forEach( field -> {
               
                // Получаем значение из свойств или используем значение по умолчанию из аннотации
                String value = properties.getProperty(
                        field.getName(),
                        field.getAnnotation(AppConfig.class).value()
                );
                try {
                    // Разрешаем доступ к полю (даже если оно private)
                    field.setAccessible(true);
                    // Устанавливаем значение поля (для статических полей первый параметр null)
                    field.set(null, value);
                    // Возвращаем исходный уровень доступа
                    field.setAccessible(false);
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    Logger.getLogger(AppConfigWorker.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (IllegalArgumentException | IOException ex) {
            Logger.getLogger(AppConfigWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
    
}
