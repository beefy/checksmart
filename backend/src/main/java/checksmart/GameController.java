package checksmart;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private static final String template = "%s's Checker Game";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/new")
    public Game greeting(@RequestParam(value="name", defaultValue="Anon") String name) {
        return new Game(counter.incrementAndGet(),
                            String.format(template, name));
    }
}
