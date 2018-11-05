import com.wj.transation.TxRecv;
import com.wj.transation.TxSend;

/**
 *@author  wj
 *@description 用事务模式，进行消息确认机制
 * 缺点：使用事务机制的话会降低RabbitMQ的性能，降低了吞吐量，增加了channel的请求数，耗时
 */
public class Main {

    public static void main(String[] args) throws Exception {
        TxRecv.recv();
        TxSend.send();
    }
}
