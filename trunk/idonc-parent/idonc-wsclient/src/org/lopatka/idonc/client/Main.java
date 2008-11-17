package org.lopatka.idonc.client;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import org.lopatka.idonc.Celsius;
import org.lopatka.idonc.Farenheit;
import org.lopatka.idonc.service.ConverterService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
  /**
   * Create the GUI and show it. For thread safety, this method should be
   * invoked from the event-dispatching thread.
   */
  public static void createAndShowGUI()
	{
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("idonc webstart client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(label.getFont().deriveFont(Font.PLAIN));
        frame.getContentPane().add(label);

        String text = null;

		ApplicationContext context = new ClassPathXmlApplicationContext ( "client-applicationContext.xml" );
		//TimeService timeService = ( TimeService ) context.getBean ( "timeService" );
		ConverterService service = (ConverterService) context.getBean("converterService");
        Celsius cel = new Celsius();
        cel.setTemperatur(10);
        Farenheit far = (Farenheit) service.celsiusToFarenheit(cel);
        text = "celsius "+cel.getTemperature()+"is farenheit"+far.getTemperature();

        label.setText(text);

        frame.pack();
        frame.setVisible(true);
	}

  public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
  }
}
