package org.xnat.dao.util;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.Test;
import org.xnat.dao.Utils_dao;
import org.xnat.entity.Person;

public class TestBaseDaoUtil {
	
	@Test
	public void testGetAllFields() {
		List<String> list = Utils_dao.getAllFields(Person.class);
		for (String s : list) {
			System.out.println(s);
		}
	}
	
	@Test
	public void testGetIdField() {
		Field f = Utils_dao.getIdField(Person.class);
		System.out.println(f.getName());
	}
	
}
