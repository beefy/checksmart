package checksmart;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MoveController {

    @MessageMapping("/sendmove")
    @SendTo("/receivemove")
    public NetworkMessage move(String msg) throws Exception {
        return new NetworkMessage(msg);
    }

}
