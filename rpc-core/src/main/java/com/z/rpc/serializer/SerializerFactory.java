package com.z.rpc.serializer;

import com.z.rpc.spi.SpiLoader;

/**
 * 序列化器工厂（用于获取序列化器对象）
 */
public class SerializerFactory {

//    static {
//        SpiLoader.load(Serializer.class);
//    }

    /**
     * 默认序列化器
     */
//    private static final Serializer DEFAULT_SERIALIZER = new JdkSerializer();
    private static Serializer DEFAULT_SERIALIZER = null;

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static Serializer getInstance(String key) {
        if (DEFAULT_SERIALIZER == null) {
            synchronized (SerializerFactory.class) {
                if (DEFAULT_SERIALIZER == null) {
                    //首次加载时加载SPI
                    SpiLoader.load(Serializer.class);

                    DEFAULT_SERIALIZER = new JdkSerializer();
                }
            }
        }
        return SpiLoader.getInstance(Serializer.class, key);
    }

}