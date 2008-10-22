/*
 * $Id: org.eclipse.jdt.ui.prefs 367 2005-10-11 16:06:41 -0700 (Tue, 11 Oct 2005) ivaynberg $
 * $Revision: 367 $
 * $Date: 2005-10-11 16:06:41 -0700 (Tue, 11 Oct 2005) $
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
package org.lopatka.idonc.web;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class IdoncApplication extends BaseIdoncApplication {

	@Override
	public ApplicationContext context() {
		return WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
	}

}
