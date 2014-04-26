package cn.liuyb.app.common.utils;


public class CommonUtils {

	public static boolean equals(Object object1, Object object2) {
        if (object1 == object2) {
            return true;
        }
        if ((object1 == null) || (object2 == null)) {
            return false;
        }
        return object1.equals(object2);
    }
	
	public static boolean stringEqualsIgnoreNull(Object object1, Object object2) {
		String str1 = (String)object1;
		String str2 = (String)object2;
		
		if (str1 == null) str1 = "";
		if (str2 == null) str2 = "";
		
		return str1.equals(str2);
	}
}
