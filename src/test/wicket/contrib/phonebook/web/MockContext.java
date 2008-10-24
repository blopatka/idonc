/*
 * $Id: MockContext.java 2641 2007-07-09 16:55:36Z kare $
 * $Revision: 2641 $
 * $Date: 2007-07-09 18:55:36 +0200 (Pn, 09 lip 2007) $
 *
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.phonebook.web;

import org.apache.wicket.injection.web.InjectorHolder;
import org.apache.wicket.spring.ISpringContextLocator;
import org.apache.wicket.spring.injection.annot.AnnotSpringInjector;
import org.apache.wicket.spring.injection.annot.test.AnnotApplicationContextMock;
import org.springframework.context.ApplicationContext;

/**
 * @author Kare Nuorteva
 */
public class MockContext extends AnnotApplicationContextMock {
	public MockContext() {
		InjectorHolder.setInjector(new AnnotSpringInjector(
				new ISpringContextLocator() {
					public ApplicationContext getSpringContext() {
						return MockContext.this;
					}
				}));
	}
}