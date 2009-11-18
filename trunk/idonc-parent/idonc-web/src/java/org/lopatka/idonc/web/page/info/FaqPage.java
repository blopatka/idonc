package org.lopatka.idonc.web.page.info;

import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.lopatka.idonc.web.page.component.BasePage;

/**
 * @author Bartek
 */
@AuthorizeInstantiation("ADMIN")
public class FaqPage extends BasePage {

    private static final long serialVersionUID = 1L;

	public FaqPage() {
		initLayout();
	}

	public void initLayout() {
	}

}

