package ru.neoflex.springloom.controller;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@OpenAPIDefinition(info = @Info(title = "Controller emulation long running tasks", version = "1"))
public class SleepController {

    @GetMapping("/sleep/{time}")
    @ResponseBody
    @Operation(description = "Run long task", method = "GET")
    @ApiResponse(responseCode = "200", description = "Information of execution thread")
    public String sleep(@Parameter(required = true, description = "Time of long operation", example = "1000")
                        @PathVariable Long time) throws InterruptedException {
        Thread.sleep(time);
        return Thread.currentThread().toString();
    }

}
