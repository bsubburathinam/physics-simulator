package com.backfield.simulators;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PhysicsSimulator {


    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/application-context.xml");

        MainWindow mainWindow = (MainWindow) applicationContext.getBean("mainWindow");
        mainWindow.setVisible(true);
    }

}
