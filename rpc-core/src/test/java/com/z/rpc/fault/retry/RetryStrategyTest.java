package com.z.rpc.fault.retry;

import com.z.rpc.model.RpcResponse;
import org.junit.Test;

/**
 * @Author: Citres
 * @Description:
 * @Date: 2024/5/19 16:49
 */

@SuppressWarnings({"all"})
/**
 * 重试策略测试
 */
public class RetryStrategyTest {

//    RetryStrategy retryStrategy = new NoRetryStrategy();
    RetryStrategy retryStrategy = new FixedIntervalRetryStrategy();

    @Test
    public void doRetry() {
        try {
            RpcResponse rpcResponse = retryStrategy.doRetry(() -> {
                System.out.println("测试重试");
                throw new RuntimeException("模拟重试失败");
            });
            System.out.println(rpcResponse);
        } catch (Exception e) {
            System.out.println("重试多次失败");
            e.printStackTrace();
        }
    }
}