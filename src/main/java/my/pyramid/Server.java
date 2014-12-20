package my.pyramid;

import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.bootstrap.ServerBootstrap;

public final class Server {

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private LoggingHandler loggingHandler;
    private ServerInitializer serverInitializer;
    private int port;
    private ServerBootstrap bootstrap;

    public EventLoopGroup getBossGroup() {
        return bossGroup;
    }

    public void setBossGroup(EventLoopGroup bossGroup) {
        this.bossGroup = bossGroup;
    }

    public EventLoopGroup getWorkerGroup() {
        return workerGroup;
    }

    public void setWorkerGroup(EventLoopGroup workerGroup) {
        this.workerGroup = workerGroup;
    }

    public LoggingHandler getLoggingHandler() {
        return loggingHandler;
    }

    public void setLoggingHandler(LoggingHandler loggingHandler) {
        this.loggingHandler = loggingHandler;
    }

    public ServerInitializer getServerInitializer() {
        return serverInitializer;
    }

    public void setServerInitializer(ServerInitializer serverInitializer) {
        this.serverInitializer = serverInitializer;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Server(EventLoopGroup bossGroup, EventLoopGroup workerGroup, LoggingHandler loggingHandler, ServerInitializer serverInitializer) {
        bootstrap = new ServerBootstrap();
        bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(loggingHandler)
                .childHandler(serverInitializer);
    }

    public void start() throws InterruptedException {
        Channel ch = bootstrap.bind(port).sync().channel();
        System.out.println("Server start!");
        ch.closeFuture().sync();
    }

}