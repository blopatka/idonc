/*
 * $Id: PhonebookApplicationForTesting.java 2641 2007-07-09 16:55:36Z kare $
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

import org.apache.wicket.protocol.http.HttpSessionStore;
import org.apache.wicket.session.ISessionStore;
import org.springframework.context.ApplicationContext;

/**
 * @author Kare Nuorteva
 */
public class PhonebookApplicationForTesting extends BasePhonebookApplication {
	public final MockContext context = new MockContext();

	@Override
	public ApplicationContext context() {
		return context;
	}

	@Override
	protected ISessionStore newSessionStore() {
		return new HttpSessionStore(this);
	}
}