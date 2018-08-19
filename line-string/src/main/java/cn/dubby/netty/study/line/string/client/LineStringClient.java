package cn.dubby.netty.study.line.string.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class LineStringClient {

    public static void main(String[] args) throws Exception {
        EventLoopGroup loopGroup = new NioEventLoopGroup(1);
        try {
            Bootstrap b = new Bootstrap();
            b.group(loopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new LineStringInitializer());

            Channel ch = b.connect("127.0.0.1", 8080).sync().channel();

            ch.closeFuture().sync();
        } finally {
            loopGroup.shutdownGracefully();
        }
    }

}
