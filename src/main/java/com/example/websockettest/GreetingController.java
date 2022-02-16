package com.example.websockettest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

        @Autowired
        Environment environment;

        @GetMapping("/sayHello")
        public String sayHello() {
                String port = environment.getProperty("server.port");
                System.out.println("Hello world!!! from "+port);
                return "";
        }

        @Autowired private SimpMessagingTemplate template;

        @MessageMapping("/hello") @SendTo("/topic/greetings") public Greeting greeting(HelloMessage message) {
                return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
        }

        @MessageMapping("/download") @SendTo("/topic/download") public Greeting download(String str) {
                Downloader downloader = new Downloader(this);
                new Thread(downloader).start();
                String port = environment.getProperty("server.port");
                return new Greeting("Hello, waiting for downloading, pls!!! from: " + port);
        }

        public void fireDownload() {
                String port = environment.getProperty("server.port");
                System.out.println("Downloaded!!! from "+port);
                this.template.convertAndSend("/topic/download-finish", new Greeting("/employee.xlsx"));
        }
}

