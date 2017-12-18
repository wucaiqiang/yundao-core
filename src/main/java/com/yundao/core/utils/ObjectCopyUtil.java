package com.yundao.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;




/**
 * 
 * @author gjl 对象copy工具类
 */
public class ObjectCopyUtil
{
    
    @SuppressWarnings("serial")
    private final static List<Class<?>> PrimitiveClasses = new ArrayList<Class<?>>()
    {
        {
            add(Long.class);
            add(Double.class);
            add(Integer.class);
            add(String.class);
            add(Boolean.class);
            add(java.util.Date.class);
            add(java.sql.Date.class);
        }
    };
    
    private final static boolean _IsPrimitive(Class<?> cls)
    {
        return cls.isPrimitive() || PrimitiveClasses.contains(cls);
    }
    
    /**
     * copy pojo 支持mapping注解名称转换。
     * 
     * @param fromObj
     * @param toObjClazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T copyObject(Object fromObj, Class<T> toObjClazz)
    {
        try
        {
            
            Class<?> fromObjClazz = fromObj.getClass();
            // 普通类型直接返回
            if (_IsPrimitive(toObjClazz))
                return (T)fromObj;
            
            T toObj = toObjClazz.newInstance();
            
            Field[] fields = toObjClazz.getDeclaredFields();
            
            for (Field toF : fields)
            {
                try
                {
                    
                    int mod = toF.getModifiers();
                    // 静态成员及常量成员不copy
                    if (Modifier.isFinal(mod) || Modifier.isStatic(mod))
                        continue;
                    
                    String toFieldName = toF.getName();
                    String fromFieldName;
                    Mapping mapping = toF.getAnnotation(Mapping.class);
                    
                    if (mapping == null || mapping.name() == null || mapping.name().trim().equals(""))
                        fromFieldName = toFieldName;
                    else
                        fromFieldName = mapping.name();
                    
                    toF.setAccessible(true);
                    Field fromF = fromObjClazz.getDeclaredField(fromFieldName);
                    fromF.setAccessible(true);
                    // System.out.println("aaaaa"+fromF.get(fromObj));
                    toF.set(toObj, fromF.get(fromObj));
                    // System.out.println(toF.get(toObj));
                }
                catch (Exception e)
                {
                    if (e instanceof IllegalArgumentException)
                        e.printStackTrace();
                    // e.printStackTrace();
                }
            }
            return toObj;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
        
    }
    
    /**
     * 如果目标对象属性为空，复制源对象到目标对象
     * @param obj
     * @param toObj
     */
    @SuppressWarnings("rawtypes")
	public static void copyObjectByNull(Object toObj,Object obj)
    {
    	Class cla = toObj.getClass();
    	Field[] fields = cla.getDeclaredFields();
        
        for (Field toF : fields)
        {
            try
            {
            	toF.setAccessible(true);
            	Object fileToObj = toF.get(toObj);
            	Object fileObj = toF.get(obj);
            	if((fileToObj == null || BooleanUtils.isEmpty(String.valueOf(fileToObj))) && fileObj != null && !BooleanUtils.isEmpty(String.valueOf(fileObj)))
            	{
            		toF.set(toObj, toF.get(obj));
            	}
            }
            catch (Exception e)
            {
//            	e.printStackTrace();
            	//吃掉这个异常。
            }
        }
    }
    
    /**
     * 如果对象中有属性，copy过去.相当于覆盖已有的属性
     * @param obj
     * @param toObj
     */
    @SuppressWarnings("rawtypes")
	public static void copyObject(Object toObj,Object obj)
    {
    	Class cla = toObj.getClass();
    	Field[] fields = cla.getDeclaredFields();
        
        for (Field toF : fields)
        {
            try
            {
            	toF.setAccessible(true);
            	Object fileObj = toF.get(obj);
            	if(fileObj != null && !BooleanUtils.isEmpty(String.valueOf(fileObj)))
            	{
            		toF.set(toObj, toF.get(obj));
            	}
            }
            catch (Exception e)
            {
//            	e.printStackTrace();
            	//吃掉这个异常。
            }
        }
    }
    
    /**
     * copy list对象
     * 
     * @param fromObjList
     * @param toObjClazz
     * @return
     */
    public static <T> List<T> copyList(List<?> fromObjList, Class<T> toObjClazz)
    {
        List<T> toObjList = new ArrayList<T>(fromObjList.size());
        
        for (int i = 0; i < fromObjList.size(); i++)
        {
            toObjList.add(copyObject(fromObjList.get(i), toObjClazz));
        }
        return toObjList;
    }
    
    /**
     * copy map 对象
     * 
     * @param fromObjMap
     * @param toObjClazz
     * @return
     */
    public static <T> Map<String, T> copyMap(Map<String, ?> fromObjMap, Class<T> toObjClazz)
    {
        Map<String, T> toObjMap = new HashMap<String, T>(fromObjMap.size());
        Iterator<String> iter = fromObjMap.keySet().iterator();
        while (iter.hasNext())
        {
            String key = iter.next();
            Object fromObj = fromObjMap.get(key);
            
            toObjMap.put(key, copyObject(fromObj, toObjClazz));
        }
        return toObjMap;
    }
    
    /**
     * List<map<key,value>> to List<T>
     * @param mapList
     * @param toObjClass
     * @return
     */
    public static <T> List<T> copyListMap(List<Map<String, ?>> mapList, Class<T> toObjClass)
    {
        List<T> toObjList = new ArrayList<T>(mapList.size());
        for (Map<String, ?> map : mapList)
        {
            toObjList.add(copyMapToBean(map, toObjClass));
        }
        return toObjList;
    }
    
    /**
     * map to bean
     * @param map
     * @param toObjClass
     * @return
     */
    public static <T> T copyMapToBean(Map<String, ?> map, Class<T> toObjClass)
    {
        try
        {
            Set<String> set = map.keySet();
            T objT = toObjClass.newInstance();
            for (String key : set)
            {
                try
                {
                    Object value = map.get(key);
                    Field toF = toObjClass.getDeclaredField(key);
                    toF.setAccessible(true);
                    toF.set(objT, value);
                }
                catch(Exception e)
                {
                    //吃掉这个异常
                }
            }
            return objT;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
	
	/**
	 * bean TO map
	 * @param t
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, String> beanToMap(Object t){
		Class cla = t.getClass();
		Map<String, String> result = new HashMap<String, String>();
		Field[] fields = cla.getDeclaredFields();
		for (Field field : fields) {
			try{
				if("serialVersionUID".equals(field.getName()))
					continue;
				field.setAccessible(true);
				if (field.get(t) instanceof Date) {
					result.put(field.getName(), DateUtils.format((Date)field.get(t), DateUtils.YYYY_MM_DD_HH_MM_SS));
				}
				else {
					result.put(field.getName(), field.get(t).toString());
				}
			}catch(Exception e){
				//吃掉这个异常
			}
		}
		
		Class superClass = cla.getSuperclass();
		if (superClass != null) {
			Field[] superClassfields = superClass.getDeclaredFields();
			if (superClassfields!= null) {
				for (Field field : superClassfields) {
					try{
						if("serialVersionUID".equals(field.getName()))
							continue;
						field.setAccessible(true);
						if (field.get(t) instanceof Date) {
							result.put(field.getName(), 
									DateUtils.format((Date)field.get(t), DateUtils.YYYY_MM_DD_HH_MM_SS));
						}
						else {
							result.put(field.getName(), field.get(t).toString());
						}
					}catch(Exception e){
						//吃掉这个异常
					}
				}
			}
		}
		return result;
	}
}
