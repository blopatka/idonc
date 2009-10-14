package org.lopatka.idonc.client;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.lopatka.idonc.Celsius;
import org.lopatka.idonc.Farenheit;
import org.lopatka.idonc.model.user.LoggedUser;
import org.lopatka.idonc.service.ConverterService;
import org.lopatka.idonc.service.IdoncService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private JLabel label;
	private JLabel label2;
	
	public MainPanel() {
		initComponents();
	}
	
	private void initComponents() {
		label = new JLabel();
		label.setHorizontalAlignment(JLabel.CENTER);
		//label.setFont(label.getFont().deriveFont(Font.PLAIN));
		
		add(label);
		
		String text = null;
		
		ApplicationContext springContext = new ClassPathXmlApplicationContext("client-applicationContext.xml");
		
		IdoncService idoncService = (IdoncService) springContext.getBean("idoncService");
		Celsius cel = new Celsius();
		cel.setTemperatur(10);
		ConverterService convService = (ConverterService) springContext.getBean("converterService");
		Farenheit far = (Farenheit) convService.celsiusToFarenheit(cel);
		text = "celsius " + cel.getTemperature() + " is farenheit " + far.getTemperature();
		
		label.setText(text);
		
		LoggedUser lU = idoncService.loginUser("opek", "opek");
		
		label2 = new JLabel();
		label2.setHorizontalAlignment(JLabel.CENTER);
		add(label2);
		
		label2.setText(lU.getSessionId() + " " + lU.getUser().getUserName());
		//jak zrobi się logowanie, to trzeba tez zrobic wylogowanie - ale to podczas zamykania okienka,
		//czyli w evencie zamykajacym aplikację (na X), oraz podczas zalogowania, jeżeli jakieś info
		//o zalogowanym użytkowniku jest w aplikacji :-)
	}
}
