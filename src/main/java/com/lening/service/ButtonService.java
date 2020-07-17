package com.lening.service;

import com.lening.pojo.Button;

import java.util.List;

public interface ButtonService {

	List<Button> findAll();

	int save(Button button);

	int delete(int id);

	Button findById(int id);

	int update(Button button);
}
