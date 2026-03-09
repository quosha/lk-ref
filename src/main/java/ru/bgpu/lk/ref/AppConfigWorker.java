
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

public class AppConfigWorker {
    
    public static void init(String packageName, String propFile) {
        try {
            Reflections reflections = new Reflections(new ConfigurationBuilder()
                    .setUrls(ClasspathHelper.forPackage(packageName))
                    .setScanners(Scanners.FieldsAnnotated)
            );
            
            Properties properties = new Properties();
            properties.load(new FileInputStream(new File(propFile)));
            
            reflections.getFieldsAnnotatedWith(AppConfig.class).forEach( field -> {
               
                String value = properties.getProperty(
                        field.getName(),
                        field.getAnnotation(AppConfig.class).value()
                );
                try {
                    field.setAccessible(true);
                    field.set(null, value);
                    field.setAccessible(false);
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    Logger.getLogger(AppConfigWorker.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (IllegalArgumentException |IOException ex) {
            Logger.getLogger(AppConfigWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
    
}
