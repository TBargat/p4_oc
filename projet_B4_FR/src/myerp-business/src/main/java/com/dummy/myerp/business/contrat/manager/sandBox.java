package com.dummy.myerp.business.contrat.manager;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dummy.myerp.business.impl.manager.ComptabiliteManagerImpl;
import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;

public class sandBox {

	public static void main(String[] args) {
		System.out.println("coucou");
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		BeanFactory factory = context;
		ComptabiliteDaoImpl test = (ComptabiliteDaoImpl) factory.getBean("myDao");
		ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();
		String myList = manager.getListCompteComptable().toString();

		System.out.println(myList);

	}
}
