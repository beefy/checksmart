package checksmart;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController{

    private static final String template = "%s's Checker Game";
    private final AtomicLong game_id = new AtomicLong();
    private final AtomicLong move_id = new AtomicLong();

    @RequestMapping("/new")
    public Game startgame(@RequestParam(value="name", defaultValue="Anon") String name) {
        return new Game(game_id.incrementAndGet(),
                            String.format(template, name));
    }

    @RequestMapping("/move")
    public Move makemove(@RequestParam(value="move") String move, @RequestParam(value="player") String player) {
        Move ret = new Move(move_id.incrementAndGet(), move, player);
        if(ret.isvalid()) {
            return ret;
        } else {
            // throw exception
            // TODO: implement exception handling
            //throw new Exception();
            return ret;
        }
    }

    @RequestMapping("/resign")
    public String resigngame(@RequestParam(value="gameid") AtomicLong gameid, @RequestParam(value="player") String player) {
        // TODO: search for game by id, error if not found
        return player;
    }
}
