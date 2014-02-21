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

	public List<Field> fields(Class<?> clazz) {
		List<Field> fields = new ArrayList<>();
		while (!clazz.equals(Object.class)) {
			for (Field field : clazz.getDeclaredFields()) {
				if(!field.getName().equalsIgnoreCase("serialversionuid")){
					fields.add(field);
				}
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
					value = this.convertObject(field, value);
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

	public Object convertObject(Field field, Object value) {
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
	
	public Object setAllValues(Map<Field, Object> fieldAndValue, Class<?> clazz) throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Object object = newInstance(clazz);
		for(Field field: fieldAndValue.keySet()){
			Object value = fieldAndValue.get(field);
			if(value != null){
				value = this.convertObject(field, value);
				field.setAccessible(true);
				field.set(object, value);
			}
		}
		return object;
	}
	
	public Object setAllValuesIgnoringClasses(Map<Field, Object> fieldAndValue, Class<?> clazz, String fk) throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Object object = newInstance(clazz);
		Object recursive = this.newInstanceOfField(clazz, fk);
		for(Field field: fieldAndValue.keySet()){
			if(!field.getName().equalsIgnoreCase("serialversionuid")){
				Object value = fieldAndValue.get(field);
				field.setAccessible(true);
				if(field.getName().equalsIgnoreCase(fk)){
					this.setValue(recursive, value, "id");
					field.set(object, recursive);
				}else if(value != null && !this.hasField(field.getType(), "id")){
					value = this.convertObject(field, value);
					field.set(object, value);
				}
			}
		}
		return object;
	}
	
	public Object newInstanceOfField(Class<?> clazz, String name) throws InstantiationException, IllegalAccessException{
		while (!clazz.equals(Object.class)) {
			for(Field field: clazz.getDeclaredFields()){
				if(field.getName().equalsIgnoreCase(name)){
					return newInstance(field.getType());
				}
			}
			clazz = iteratorClazz(clazz);
		}
		return null;
	}
	
	public boolean hasField(Class<?> clazz, String name){
		while (!clazz.equals(Object.class)) {
			for(Field field: clazz.getDeclaredFields()){
				if(field.getName().equalsIgnoreCase(name)){
					return true;
				}
			}
			clazz = iteratorClazz(clazz);
			if(clazz == null){
				break;
			}
		}
		return false;
	}

	public Object setThisRecursive(Object obj) throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException {
		Class<?> clazz = obj.getClass();
		Integer id = (Integer) this.getValue(obj, "id");
		for(Field field: clazz.getDeclaredFields()){
			field.setAccessible(true);
			Object temp = field.get(obj);
			if(temp != null){
				if(this.hasField(field.getType(), "id")){
					Class<?> iterator = temp.getClass();
					while(!iterator.equals(Object.class)){
						for(Field f: iterator.getDeclaredFields()){
							if(this.hasField(f.getType(),"id")){
								f.setAccessible(true);
								Object value = f.getType().cast(f.get(temp));
								if(f.getType().equals(obj.getClass()) && value != null){
									Integer fk = (Integer) this.getValue(value, "id");
									if(id.equals(fk)){
										f.set(temp, obj.getClass().cast(this.copyObject(obj, value)));
									}
								}
							}
						}
						iterator = iteratorClazz(iterator);
					}
				}
			}
		}
		return obj;
	}
	
	private Object copyObject(Object obj, Object value) throws IllegalArgumentException, IllegalAccessException {
		for(Field f1: obj.getClass().getDeclaredFields()){
			f1.setAccessible(true);
			Object copy = f1.get(obj);
			for(Field f2: obj.getClass().getDeclaredFields()){
				f2.setAccessible(true);
				if(f2.getType().equals(f1.getType()) && f2.getName().equalsIgnoreCase(f1.getName()) && !f1.getName().equalsIgnoreCase("serialversionuid")){
					f2.set(value, copy);
				}
			}
		}
		return value;
	}

	public Object insertObject(Object object, Object toInsert) throws IllegalArgumentException, IllegalAccessException, InstantiationException{
		Class<?> clazz = object.getClass();
		while(!clazz.equals(Object.class)){
			for(Field field: clazz.getDeclaredFields()){
				field.setAccessible(true);
				if(this.hasField(field.getType(), "id") && field.get(object) == null && field.getType().equals(toInsert.getClass())){
					field.set(object, toInsert);
				}
			}
			clazz = ObjectReflectionUtil.iteratorClazz(clazz);
		}
		return object;
	}
}
