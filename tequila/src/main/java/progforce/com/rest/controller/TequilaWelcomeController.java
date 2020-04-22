package progforce.com.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import progforce.com.util.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class TequilaWelcomeController {
    private final SimpleDateFormat sdf = new SimpleDateFormat(Constants.FULL_DATE_FORMAT);

    @RequestMapping("/")
    public String index() {
        return (String.format("Welcome to Tequila application%n(now: %s)", sdf.format(new Date())));
    }
}
