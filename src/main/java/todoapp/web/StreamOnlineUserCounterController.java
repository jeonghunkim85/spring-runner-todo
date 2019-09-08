package todoapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import todoapp.web.support.ConnectedClientCountBroadcaster;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.IntStream;

@Controller
public class StreamOnlineUserCounterController {

    private ConnectedClientCountBroadcaster broadcaster = new ConnectedClientCountBroadcaster();


    @RequestMapping("/stream/online-users-counter")
    public SseEmitter counter(HttpServletResponse response) {
        return broadcaster.subscribe();
        /**
         * 동기 방식의 IO를 사용할 경우, 1 connection 당 1 thread 를 사용하고 있으므로,
         * 아래 코드를 그대로 사용할 경우 실제 product 환경에서는 문제가 있음.
         * @param response
         */
//        response.setContentType("text/event-stream");
//
//        IntStream.range(1, 11).forEach(number -> {
//            try {
//                Thread.sleep(1000);
//
//                ServletOutputStream outputStream = response.getOutputStream();
//                outputStream.write(("data: " + number + "\n\n").getBytes());
//                outputStream.flush();
//            }catch (InterruptedException ignore) {}
//            catch (IOException ignore) {}
//        });
    }
}
