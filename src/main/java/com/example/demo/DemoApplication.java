package com.example.demo;

import com.example.demo.common.item.GPS;
import com.example.demo.manager.DataManager;
import com.example.demo.tcp_connection.TCP_Connection_Thread;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import java.io.*;
import java.util.ArrayList;

@SpringBootApplication
@EnableScheduling
public class DemoApplication {

    public static void main(String[] args) {
        loadFile();
        SpringApplication.run(DemoApplication.class, args);
        TCP_Connection_Thread tcp_connection = new TCP_Connection_Thread();
        Thread tcp_thread = new Thread(tcp_connection);
        tcp_thread.start();
        try {
            tcp_thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler();
    }

    public static void loadFile() {
        ArrayList<GPS> dataArrayList = new ArrayList<>();
        try {
            File file = new ClassPathResource(".\\static\\gps4.txt").getFile();
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                if (line == "") continue;
                dataArrayList.add(new GPS(line));
            }
            DataManager.getInstance().setGpsdata(dataArrayList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
