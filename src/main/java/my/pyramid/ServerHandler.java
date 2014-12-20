package my.pyramid;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpHeaders.Values;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.*;

@ChannelHandler.Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private WeightStrategy strategy;

    public WeightStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(WeightStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof HttpRequest) {
            HttpRequest req = (HttpRequest) msg;
            if (HttpHeaders.is100ContinueExpected(req)) {
                ctx.write(new DefaultFullHttpResponse(HTTP_1_1, CONTINUE));
            }
            boolean keepAlive = HttpHeaders.isKeepAlive(req);
            QueryStringDecoder decoder = new QueryStringDecoder(req.getUri());

            FullHttpResponse response;
            Integer level = null;
            Integer index = null;

            try {
                // /weight?level={level}&index={index}
                // /weight/{level}/{index}
                if (decoder.path().equals("/weight") && decoder.parameters().containsKey("level") && decoder.parameters().containsKey("index")) {
                    level = Integer.parseInt(decoder.parameters().get("level").get(0));
                    index = Integer.parseInt(decoder.parameters().get("index").get(0));
                } else {
                    Pattern p = Pattern.compile("/weight/(\\d+)/(\\d+)");
                    Matcher m = p.matcher(decoder.path());
                    if (m.find()) {
                        level = Integer.parseInt(m.group(1));
                        index = Integer.parseInt(m.group(2));
                    }
                }
                if (level != null && index != null) {
                    strategy.validate(level, index);
                    Double result = strategy.getHumanEdgeWeight(level, index);
                    response = new DefaultFullHttpResponse(
                            HTTP_1_1,
                            OK,
                            Unpooled.wrappedBuffer(
                                    new DecimalFormat("###.###").format(result).toString().getBytes()
                            )
                    );
                } else {
                    throw new Exception("Operation not found or illegal parameters");
                }
            } catch (Exception e) {
                response = new DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND);
            }
            response.headers().set(CONTENT_TYPE, "text/plain");
            response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
            if (!keepAlive) {
                ctx.write(response).addListener(ChannelFutureListener.CLOSE);
            } else {
                response.headers().set(CONNECTION, Values.KEEP_ALIVE);
                ctx.write(response);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}