package br.com.ppo.persistence.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ObjectReflectionUtil {

	public static Object newInstance(Class<?> clazz)
			throws InstantiationException, IllegalAccessException {
		return clazz.newInstance();
	}

	public List<String> fields(Class<?> clazz) {
		List<String> fields = new ArrayList<>();
		while (!clazz.equals(Object.class)) {
			for (Field field : clazz.getDeclaredFields()) {
				fields.add(field.getName());
			}
			clazz = iteratorClazz(clazz);
		}
		return fields;
	}

	public Object setValue(Object obj, Object value, String nameField)
			throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		Class<?> clazz = obj.getClass();
		while (!clazz.equals(Object.class)) {
			for (Field field : clazz.getDeclaredFields()) {
				if (field.getName().equalsIgnoreCase(nameField)) {
					field.setAccessible(true);
					if(field.getName().equalsIgnoreCase("id")){
						value = this.convertId(field, value);
					}
					field.set(obj, value);
					return obj;
				}
			}
			clazz = iteratorClazz(clazz);
		}
		return obj;
	}

	private static Class<?> iteratorClazz(Class<?> clazz) {
		return clazz.getSuperclass();
	}

	public Object convertId(Field field, Object value) {
		if(!field.getType().equals(value.getClass())){
			if(value instanceof Integer && field.getType().equals(Long.class)){
				return Long.parseLong(String.valueOf(value));
			}else if(value instanceof Long && field.getType().equals(Integer.class)){
				return Integer.parseInt(String.valueOf(value));
			}
		}
		return value;
	}
	
	public Object getValue(Object obj, String nameField)
			throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		Class<?> clazz = obj.getClass();
		while (!clazz.equals(Object.class)) {
			for (Field field : clazz.getDeclaredFields()) {
				if (field.getName().equalsIgnoreCase(nameField)) {
					field.setAccessible(true);
					return field.get(obj);
				}
			}
			clazz = iteratorClazz(clazz);
		}
		return null;
	}
	
	public Object setAllValues(Map<String, Object> fieldAndValue, Class<?> clazz) throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Object object = newInstance(clazz);
		for(String name: fieldAndValue.keySet()){
			Object value = fieldAndValue.get(name);
			if(value != null){		
				this.setValue(object, value, name);
			}
		}
		return object;
	}
}
