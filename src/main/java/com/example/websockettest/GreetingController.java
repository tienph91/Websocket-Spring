package com.example.websockettest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller public class GreetingController {

        @Autowired private SimpMessagingTemplate template;

        @MessageMapping("/hello") @SendTo("/topic/greetings") public Greeting greeting(HelloMessage message) {
                return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
        }

        @MessageMapping("/download") @SendTo("/topic/download") public Greeting download(String str) {
                Downloader downloader = new Downloader(this);
                new Thread(downloader).start();
                return new Greeting("Hello, waiting for downloading, pls!!!");
        }

        public void fireDownload() {
                this.template.convertAndSend("/topic/download-finish", new Greeting("/employee.xlsx"));
        }
}

