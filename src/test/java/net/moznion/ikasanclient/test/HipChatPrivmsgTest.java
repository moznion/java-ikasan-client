package net.moznion.ikasanclient.test;

import me.geso.servlettester.jetty.JettyServletTester;
import net.moznion.ikasanclient.hipchat.HipChatIkasanClient;
import net.moznion.ikasanclient.hipchat.HipChatColor;
import net.moznion.ikasanclient.hipchat.HipChatMessageFormat;
import org.apache.http.HttpResponse;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HipChatPrivmsgTest {
    @Test
    public void testForDefaultPrivmsg() throws Exception {
        JettyServletTester.runServlet((req, resp) -> {
            assertEquals("/privmsg", req.getPathInfo());
            assertEquals("#channel", req.getParameter("channel"));
            assertEquals("msg", req.getParameter("message"));
            assertEquals("ikasan", req.getParameter("nickname"));
            assertEquals(HipChatColor.YELLOW.getValue(), req.getParameter("color"));
            assertEquals(HipChatMessageFormat.TEXT.getValue(), req.getParameter("message_format"));
        }, "/", (baseUri) -> {
            HipChatIkasanClient hipChatIkasanClient = HipChatIkasanClient.builder(baseUri.getHost())
                    .port(baseUri.getPort())
                    .useSSL(false)
                    .build();
            HttpResponse resp = hipChatIkasanClient.privmsg("#channel", "msg").send();
            assertEquals(200, resp.getStatusLine().getStatusCode());
        });
    }

    @Test
    public void testForCustomizedPrivmsg() throws Exception {
        JettyServletTester.runServlet((req, resp) -> {
            assertEquals("/privmsg", req.getPathInfo());
            assertEquals("#channel", req.getParameter("channel"));
            assertEquals("msg", req.getParameter("message"));
            assertEquals("nick", req.getParameter("nickname"));
            assertEquals(HipChatColor.RED.getValue(), req.getParameter("color"));
            assertEquals(HipChatMessageFormat.HTML.getValue(), req.getParameter("message_format"));
        }, "/", (baseUri) -> {
            HipChatIkasanClient hipChatIkasanClient = HipChatIkasanClient.builder(baseUri.getHost())
                    .port(baseUri.getPort())
                    .useSSL(false)
                    .build();
            HttpResponse resp = hipChatIkasanClient.privmsg("#channel", "msg")
                    .nickname("nick")
                    .color(HipChatColor.RED)
                    .messageFormat(HipChatMessageFormat.HTML)
                    .send();
            assertEquals(200, resp.getStatusLine().getStatusCode());
        });
    }

    @Test
    public void testWithPrefix() throws Exception {
        JettyServletTester.runServlet((req, resp) -> {
            assertEquals("/privmsg", req.getPathInfo());
            assertEquals("#channel", req.getParameter("channel"));
            assertEquals("[hello]msg", req.getParameter("message"));
            assertEquals("ikasan", req.getParameter("nickname"));
            assertEquals(HipChatColor.YELLOW.getValue(), req.getParameter("color"));
            assertEquals(HipChatMessageFormat.TEXT.getValue(), req.getParameter("message_format"));
        }, "/", (baseUri) -> {
            HipChatIkasanClient hipChatIkasanClient = HipChatIkasanClient.builder(baseUri.getHost())
                    .port(baseUri.getPort())
                    .useSSL(false)
                    .messagePrefix("[hello]")
                    .build();
            HttpResponse resp = hipChatIkasanClient.privmsg("#channel", "msg").send();
            assertEquals(200, resp.getStatusLine().getStatusCode());
        });
    }
}
