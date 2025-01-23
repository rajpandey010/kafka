package dummyRes.dummyRes.controller;

import dummyRes.dummyRes.service.DummyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DummyController {
    int count =0;
    @Autowired
    DummyService dummyService;

    @PostMapping("/dummyapi")
    public StringBuilder checkMessage(@RequestBody String dummyMessage){
        System.out.println("count = "+count); // i want to print this line after every 1 second of interval and i want to print that count
        count++;
        return dummyService.dummyResponse(dummyMessage);
    }

}
