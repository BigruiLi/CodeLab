package com.rspfamily.service;

import java.io.IOException;
import java.util.List;

import com.rspfamily.pojo.People;

public interface PeopleService {
	/**
	 * 显示全部
	 * @return
	 */
	List<People> show()  throws IOException;
}
