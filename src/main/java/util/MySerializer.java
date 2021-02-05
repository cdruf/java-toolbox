package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Helper for serializing and deserializing objects.
 * 
 * @author Christian Ruf
 * 
 */
public class MySerializer {

    /**
     * Serialize an object for the purppose of being stored in a database.
     * 
     * @param obj
     * @param ps
     * @param parameterIndex
     */
    public static void writeObject(Object obj, PreparedStatement ps, int parameterIndex) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oout = new ObjectOutputStream(baos);
            oout.writeObject(obj);
            oout.close();
            ps.setBytes(parameterIndex, baos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieve an object from a database.
     * 
     * @param rs
     * @param column
     * @return
     */
    public static Object read(ResultSet rs, String column) {
        try {
            byte[] buf = rs.getBytes(column);
            if (buf != null) {
                ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
                return objectIn.readObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeObjectToFile(Object obj, String path) {

        ObjectOutput output = null;

        try {
            OutputStream file = new FileOutputStream(path);
            OutputStream buffer = new BufferedOutputStream(file);
            output = new ObjectOutputStream(buffer);
            output.writeObject(obj);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Object readObjectFromFile(File file) {
        return readObjectFromFile(file.getPath());
    }

    public static Object readObjectFromFile(String path) {
        ObjectInput input = null;
        Object obj = null;
        try {
            // use buffering
            InputStream file = new FileInputStream(path);
            InputStream buffer = new BufferedInputStream(file);
            input = new ObjectInputStream(buffer);
            obj = input.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

}
