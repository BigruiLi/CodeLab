package com.rspfamily.service.impl;

import java.util.List;

import com.rspfamily.dao.FlowerDao;
import com.rspfamily.dao.impl.FlowerDaoImpl;
import com.rspfamily.pojo.Flower;
import com.rspfamily.service.FlowerService;

public class FlowerServiceImpl implements FlowerService {
	private FlowerDao flowerDao = new FlowerDaoImpl();
	@Override
	public List<Flower> show() {
		return flowerDao.selAll();
	}
	@Override
	public int add(Flower flower) {
		return flowerDao.insFlower(flower);
	}

}
