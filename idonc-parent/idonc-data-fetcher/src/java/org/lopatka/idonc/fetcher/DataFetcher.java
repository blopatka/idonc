package org.lopatka.idonc.fetcher;

import java.awt.Component;
import java.util.EventObject;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.ResourceManager;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;

/**
 * Hello world!
 *
 */
public class DataFetcher extends SingleFrameApplication
{
    public static void main( String[] args )
    {
    	Application.launch(DataFetcher.class, args);
    }

	private ApplicationContext ctx;
	private ResourceMap resource;

	@Override
	protected void startup() {
		FrameView frameView = new MainViewFrame(this);
		frameView.setFrame(new MainFrame());
		show(frameView);

		addExitListener(new ExitListener() {

			public boolean canExit(EventObject event) {
				Object[] options = { resource.getString("label.yes"),
						resource.getString("label.no") };
				Object source = (event != null) ? event.getSource() : null;

				Component owner = (source instanceof Component) ? (Component) source
						: null;

				boolean mayExit = JOptionPane
						.showOptionDialog(owner, resource
								.getString("label.exit"), resource
								.getString("Application.title"),
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[1]) == JOptionPane.YES_OPTION;
				return mayExit;
			}

			public void willExit(EventObject event) {
			}

		});
	}

	@Override
	protected void initialize(String[] args) {
		System.out.println("Init application...");
		this.ctx = getContext();
		ResourceManager mgr = ctx.getResourceManager();
		resource = mgr.getResourceMap(DataFetcher.class);
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("idonc");
    	EntityManager em = emf.createEntityManager();
    	ApplicationSession session = ApplicationSession.getInstance();
    	session.setEmf(emf);
    	session.setEm(em);
	}

	@Override
	protected void shutdown() {
		ApplicationSession session = ApplicationSession.getInstance();
		session.getEm().close();
		session.getEmf().close();
	}
	
	
}
