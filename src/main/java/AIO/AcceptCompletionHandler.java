package AIO;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;


/**
 *
 <V> – The result type of the I/O operation
 <A> – The type of the object attached to the I/O operation
 */
//接受完成后返回一个SocketChannel<V>, 绑定到ServerHandler上<A>
public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel,
        AsyncTimeServerHandler> {

    @Override
    public void completed(AsynchronousSocketChannel result, AsyncTimeServerHandler attachment) {
        attachment.asynchronousServerSocketChannel.accept(attachment, this);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        result.read(buffer, buffer, new ReadCompletionHandler(result)); //绑定accept后的read方法
    }

    //处理错误
    @Override
    public void failed(Throwable exc, AsyncTimeServerHandler attachment) {
        exc.printStackTrace();
        attachment.latch.countDown();
    }
}
