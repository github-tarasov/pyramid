package my.pyramid;

import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    private ChannelInboundHandlerAdapter serverHandler;

    public ChannelInboundHandlerAdapter getServerHandler() {
        return serverHandler;
    }

    public void setServerHandler(ChannelInboundHandlerAdapter serverHandler) {
        this.serverHandler = serverHandler;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new HttpServerCodec());
        p.addLast(this.serverHandler);
    }
}