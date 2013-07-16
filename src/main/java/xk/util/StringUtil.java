package xk.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StringUtil {
	/**
	 * @see 該方法為將傳入對象中所有為STRING的屬性去空格，返回值為該對象中所有為STRING的屬性的都已去除空格。
	 * @param obj
	 * @return Object
	 */
	public static Object trim(Object obj){
		if(obj!=null){
			if(obj instanceof Map){
				Map tmpMap=(Map)obj;
				if(tmpMap.size()!=0){
					Set tmpSet=tmpMap.entrySet();
					Iterator iterator=tmpSet.iterator();
					while(iterator.hasNext()){
						String key=(String)iterator.next();
						Object tmpobj=tmpMap.get(key);
						tmpMap.remove(key);
						tmpMap.put(key, StringUtil.trimObject(tmpobj));
					}
				}
			}else if(obj instanceof List){
				List tmpList=(List)obj;
				if(tmpList.size()!=0){
					for(int i=0;i<tmpList.size();i++){
						Object tmpobj=tmpList.get(i);
						tmpList.set(i, StringUtil.trimObject(tmpobj));
					}
					
				}
				
			}else if(obj instanceof Set){
				Set tmpSet=(Set)obj;
				if(tmpSet.size()!=0){
					Iterator iterator=tmpSet.iterator();
					while(iterator.hasNext()){
						Object tmpobj=iterator.next();
						tmpSet.remove(tmpobj);
						tmpSet.add(StringUtil.trimObject(tmpobj));
					}
				}
			}else{
				return StringUtil.trimObject(obj);
			}
		}
		return null;
	}
	/**
	 * @author jiangxc
	 * @see 該方法為將傳入對象中所有為STRING的屬性去空格，返回值為該對象中所有為STRING的屬性的都已去除空格。
	 * @param obj 类型为普通object,非list,set,map
	 * @return Object
	 */
	private static Object trimObject(Object obj){
		if(obj!=null){
			if(obj instanceof Map){
				return null;
			}else if(obj instanceof List){
				return null;
			}else if(obj instanceof Set){
				return null;
			}else{
				Class tmpclass=obj.getClass();
				Field[] tmpfield=tmpclass.getDeclaredFields();
				for(int i=0;i<tmpfield.length;i++){
					Field field = tmpfield[i];
		
					String fieldName = field.getName();
					if(fieldName.equals("serialVersionUID"))continue;
					String backName = fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1);
					String getMethodName="get"+backName;
					String setMethodName="set"+backName;
					Method getMethod,setMethod;
					try {
						getMethod = tmpclass.getMethod(getMethodName,new Class[]{});
						Object value = getMethod.invoke(obj, new Object[] {});
						if(value==null)continue;
						if(value instanceof String){
							value=String.valueOf(value).trim();
						}
						
						if("java.lang.String".equals(tmpfield[i].getType().getName())){
							setMethod=tmpclass.getMethod(setMethodName, new Class[]{field.getType()});
							setMethod.invoke(obj, new Object[] {value});
						}
						
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}
				}
				return obj;
			}
		}
		return null;
	}
}
