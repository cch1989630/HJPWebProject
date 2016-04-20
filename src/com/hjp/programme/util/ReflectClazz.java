package com.hjp.programme.util;

import java.lang.reflect.Field;

public class ReflectClazz {  
    /** 
     * 利用反射获取指定对象的指定属性 
     * @param obj 目标对象 
     * @param fieldName 目标属性 
     * @return 目标属性的值 
     */  
    public static Object getFieldValue(Object obj, String fieldName) {  
        Object result = null;  
        Field field = ReflectUtil.getField(obj, fieldName);  
        if (field != null) {  
           field.setAccessible(true);  
           try {  
               result = field.get(obj);  
           } catch (IllegalArgumentException e) {  
               e.printStackTrace();  
           } catch (IllegalAccessException e) {  
               e.printStackTrace();  
           }  
        }  
        return result;  
    }  
     
    /** 
     * 利用反射设置指定对象的指定属性为指定的值 
     * @param obj 目标对象 
     * @param fieldName 目标属性 
      * @param fieldValue 目标值 
     */  
    public static void setFieldValue(Object obj, String fieldName,  
           String fieldValue) {  
        Field field = ReflectUtil.getField(obj, fieldName);  
        if (field != null) {  
           try {  
               field.setAccessible(true);  
               field.set(obj, fieldValue);  
           } catch (IllegalArgumentException e) {  
               e.printStackTrace();  
           } catch (IllegalAccessException e) {  
               e.printStackTrace();  
           }  
        }  
     }  
 }  