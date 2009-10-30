package org.lopatka.idonc.generator;

import org.lopatka.idonc.service.GeneratorService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class DataGenerator
{

	private static ApplicationContext springContext;
	private static GeneratorService generatorService;

    public static void main( String[] args )
    {
    	springContext = new ClassPathXmlApplicationContext("client-applicationContext.xml");
		generatorService = (GeneratorService) springContext.getBean("generatorService");


    }
}
