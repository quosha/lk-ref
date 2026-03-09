
package ru.bgpu.lk.ref;


public class A {
    
    @AppConfig(value = "hi!")
    private static String name;
    
    @AppConfig
    static String host;
    
    @Deprecated
    void test() {
        
    }

    @Override
    public String toString() {
        return super.toString()+" {name = "+name+", host = "+host+"}";
    }
    
}
