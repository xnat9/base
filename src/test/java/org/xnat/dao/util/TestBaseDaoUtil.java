package org.xnat.dao.util;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.Test;
import org.xnat.entity.Person;

public class TestBaseDaoUtil {
	
	@Test
	public void testGetAllFields() {
		BaseDaoUtil_v3 b = new BaseDaoUtil_v3();
		List<String> list = b.getAllFields(Person.class);
		for (String s : list) {
			System.out.println(s);
		}
	}
	
	@Test
	public void testGetIdField() {
		BaseDaoUtil_v3 b = new BaseDaoUtil_v3();
		Field f = b.getIdField(Person.class);
		System.out.println(f.getName());
	}
	
}
