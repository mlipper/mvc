package gov.nyc.doitt.geoclient.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

// See https://www.baeldung.com/spring-mvc-content-negotiation-json-xml

@RestController
public class PetController {

    static final Logger logger = LoggerFactory.getLogger(PetController.class);

    private static ConcurrentMap<String, Animal> DB = new ConcurrentHashMap<>();
    static {
        DB.put("dog:Otis", new Dog("Otis", 13));
        DB.put("dog:Makenna", new Dog("Makenna", 8));
        DB.put("dog:Emma", new Dog("Emma", 6));
        DB.put("seaotter:Fred", new SeaOtter("Fred", 2));
    }

    @RequestMapping(value = "/pets/{type}", produces = { "application/json", "application/xml" }, method = RequestMethod.GET)
    public @ResponseBody Animal animal(@PathVariable String type, @RequestParam String name) {
        return byName(type, name);
    }

    private Animal byName(String type, String name) {
        logger.info("Searching for type='{}', name='{}'.", type, name);
        Animal animal = null;
        if(type.startsWith("dog")) {
            logger.info("It's a dog!");
            animal = PetController.DB.getOrDefault(String.format("dog:%s", name), new Dog("Default Dog", 1));
        } else {
            logger.info("It's a sea otter!");
            animal = PetController.DB.getOrDefault(String.format("seaotter:%s", name), new SeaOtter("Default Sea Otter", 12));
        }
        logger.info("Found a {} named {}.", animal.getClass().getSimpleName(), animal);
        return animal;
    }
}
