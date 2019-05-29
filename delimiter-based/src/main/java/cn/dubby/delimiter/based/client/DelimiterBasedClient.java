package cn.dubby.delimiter.based.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @author dubby
 * @date 2019/5/29 14:47
 */
public class DelimiterBasedClient {

    public static void main(String[] args) throws InterruptedException {

        EventLoopGroup eventExecutors = new NioEventLoopGroup(1);
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
                            ByteBuf delimiter = Unpooled.copiedBuffer("$_$".getBytes(CharsetUtil.UTF_8));
                            pipeline.addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
                            pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
                            pipeline.addLast(new ClientHandler());
                        }
                    });
            Channel channel = bootstrap.connect("127.0.0.1", 8888).sync().channel();
            channel.closeFuture().sync();
        } finally {
            eventExecutors.shutdownGracefully();
        }
    }

}
