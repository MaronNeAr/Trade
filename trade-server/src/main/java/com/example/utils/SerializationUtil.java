package com.example.utils;

import java.io.*;

public class SerializationUtil {
    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.flush();
        oos.close();
        return bos.toByteArray();
    }

    public static <T> T deserialize(byte[] data, Class<T> clazz) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bis);
        T obj = clazz.cast(ois.readObject());
        ois.close();
        return obj;
    }
}

