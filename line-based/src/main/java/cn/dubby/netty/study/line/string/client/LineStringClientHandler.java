package cn.dubby.netty.study.line.string.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

public class LineStringClientHandler extends SimpleChannelInboundHandler<String> {

    private String uuid = UUID.randomUUID().toString().replace("-", "");

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(uuid + System.lineSeparator());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println(msg);
        ctx.writeAndFlush(uuid + System.lineSeparator());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println(cause.getMessage());
        ctx.close();
    }
}
