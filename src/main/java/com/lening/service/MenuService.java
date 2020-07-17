package com.lening.service;

import java.util.List;

import com.lening.pojo.Menu;

public interface MenuService {

	List<Menu> findAll();

	int save(Menu menu);

	int delete(int id);

	Menu findById(int id);

	int update(Menu menu);
}
