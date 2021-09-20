package ru.neoflex.springloom.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SleepController {

    @GetMapping("/sleep/{time}")
    @ResponseBody
    public String sleep(@PathVariable Long time) throws InterruptedException {
        Thread.sleep(time);
        return Thread.currentThread().toString();
    }

}
