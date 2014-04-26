package cn.liuyb.app.common.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import cn.liuyb.app.common.domain.DomainModel;

/**
 * utility class for dealing with java beans
 */
public class BeanUtils {
    private static Set<String> ignores = new HashSet<String>();
    static {
        try {
            PropertyDescriptor[] properties =  Introspector.getBeanInfo(Object.class).getPropertyDescriptors();
            for (PropertyDescriptor p : properties) {
                ignores.add(p.getName());
            }
            
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
    }
    /**
     * dumps the properties names and values of a bean into a string
     * 
     * @param bean
     *            the JavaBean to be intropected
     * @return String a dump of the property names and values
     */
    public static String toString(Object bean, String indent, int level) {
        if (level < 0) return "<br/>";
        StringBuffer buf = new StringBuffer();
        if (bean != null) {
            try {
                BeanInfo binfo = Introspector.getBeanInfo(bean.getClass());
                PropertyDescriptor[] properties = binfo.getPropertyDescriptors();
                if (properties != null) {
                    for (int i = 0; i < properties.length; i++) {
                        Method readMethod = properties[i].getReadMethod();
                        if (readMethod != null) {
                            if (ignores.contains(properties[i].getName())) {
                                continue;
                            }
                            buf.append(indent + properties[i].getName());
                            buf.append(" = ");
                            Object obj = readMethod.invoke(bean);
                            if (obj != null) {
                                if (obj instanceof DomainModel) {
                                    buf.append(obj);
                                    buf.append("<br/>");
                                    buf.append(toString(obj, indent + "--", level - 1));
                                    buf.append("<br/>");
                                } else {
                                    buf.append(obj.toString());
                                }
                            } else {
                                buf.append("null");
                            }
                            buf.append("<br/>");
                        }
                    }
                }
            } catch (Exception e) {
                // ignore exceptions thrown, this is a development aid
            }
        }
        return buf.toString();
    }
    
    /**
     * @param file input file name
     * @param out output stream
     * @throws IOException
     */
    public static void copyInputFileToOutput(String file, OutputStream out) throws IOException{
		InputStream input = null;
		try {
			input = new FileInputStream(file);
			byte[] b = new byte[1024];  
			int i = 0;  
			  
			while((i = input.read(b)) > 0)  
			{  
				out.write(b, 0, i);  
			}
			out.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (input != null){
				input.close();
			}
		}
	}
    
    /**
     * @param file input file name
     * @param out output stream
     * @throws IOException
     */
    public static void copyInputFileToOutput(File file, OutputStream out) throws IOException{
		InputStream input = null;
		try {
			input = new FileInputStream(file);
			byte[] b = new byte[1024];  
			int i = 0;  
			  
			while((i = input.read(b)) > 0)  
			{  
				out.write(b, 0, i);  
			}
			out.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (input != null){
				input.close();
			}
		}
	}
}