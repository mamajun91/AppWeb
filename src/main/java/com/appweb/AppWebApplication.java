package com.appweb;

import com.appweb.custom.CustomProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class AppWebApplication implements CommandLineRunner {


    @Autowired
    private CustomProperties props;

    public static void main(String[] args) {SpringApplication.run(AppWebApplication.class, args);}

    @Override
    public void run(String... args) throws Exception {System.out.println("URL de l'API: " + props.getApilnr());}
}
