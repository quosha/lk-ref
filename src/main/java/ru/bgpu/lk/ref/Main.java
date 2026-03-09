
package ru.bgpu.lk.ref;

public class Main {
    
    static {
        AppConfigWorker.init("ru.bgpu.lk.ref", "config.properties");
    }
    
    public static void main(String[] args) {
        A a = new A();
        System.out.println("a = "+a);        
        System.out.println("new A = "+new A());
    }
}
