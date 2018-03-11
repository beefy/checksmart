package hello;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MoveController {

    @MessageMapping("/sendmove")
    @SendTo("/receivemove")
    public Move move(NetworkMessage message) throws Exception {
        return new Move("Hello, " + message.getName() + "!");
    }

}
