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
package org.lopatka.idonc.service.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lopatka.idonc.dao.ProjectDao;
import org.lopatka.idonc.model.data.IdoncLongData;
import org.lopatka.idonc.model.data.IdoncPart;
import org.lopatka.idonc.model.data.IdoncProject;
import org.lopatka.idonc.model.user.Address;
import org.lopatka.idonc.model.user.IdoncUser;
import org.lopatka.idonc.service.IdoncService;
import org.springframework.beans.factory.InitializingBean;

public class DataGenerator implements InitializingBean {

	private static final String[] PROJECTNAMES = {"Proof of Concept", "Liczba Pi", "Spin"};
	private static final String[] PROJECT_DESCRIPTIONS = {"Projekt pokazujacy ze dziala wymiana informacji miedzy serwerem a klientem", "Projekt oblicza liczbe Pi", "projekt oblicza Spiny ;-)"};
	private static final String[] PROJECT_WEBSITE = {"www.poc.example.com", "www.pi.example.com", "www.spin.example.com"};
	private static final String[] PROJECT_CLASS_NAME = {"org.lopatka.idonc.computation.poc.PocComputation", "org.lopatka.idonc.computation.pi.PiComputation", "org.lopatka.idonc.computation.spin.SpinComputation"};

	private static final String[] USERNAMES = {"andy", "gary", "lisa", "bart",
			"homer", "marge", "maggie", "krusty", "opek", "opiszon", "kasia",
			"kacha", "koteczek", "pies", "kot", "sowa", "jaszczurka", "slon",
			"kon", "swinia", "koza", "krowa", "grab", "dab", "orzech", "jablon",
			"wisnia", "grusza", "modrzew", "sosna"};
	private static final String[] FIRSTNAMES = { "Jacob", "Emily", "Michael",
			"Sarah", "Matthew", "Brianna", "Nicholas", "Samantha",
			"Christopher", "Hailey", "Abner", "Abby", "Joshua", "Douglas",
			"Jack", "Keith", "Gerald", "Samuel", "Willie", "Larry", "Jose",
			"Timothy", "Sandra", "Kathleen", "Pamela", "Virginia", "Debra",
			"Maria", "Linda" };
	private static final String[] LASTNAMES = { "Smiith", "Johnson",
			"Williams", "Jones", "Brown", "Donahue", "Bailey", "Rose", "Allen",
			"Black", "Davis", "Clark", "Hall", "Lee", "Baker", "Gonzalez",
			"Nelson", "Moore", "Wilson", "Graham", "Fisher", "Cruz", "Ortiz",
			"Gomez", "Murray" };
	private static final String[] DOMAINS = {"com", "pl", "de", "co.uk", "org", "edu"};
	private static final String[] CITIES = {"Warszawa", "Poznan", "Krakow", "Gdansk",
			"Katowice", "Wroclaw", "Szczecin", "Radom", "Rzeszow", "Lublin", "Witnica"};
	private static final String[] COUNTRIES = {"Polska", "Niemcy", "Irlandia", "Szkocja"};
	private static final String[] STREETS = {"Warszawska", "Zamenhoffa", "Slowianska",
			"Lechicka", "Wolnosci", "Wschodnia", "Zachodnia", "Polnocna", "Poludniowa",
			"Poranna", "Wieczorna", "Bajkowa", "Filmowa", "Kocia"};

	private IdoncService idoncService;

    private ProjectDao projectDao;


	public void setIdoncService(IdoncService idoncService) {
		this.idoncService = idoncService;
	}

    public void setProjectDao(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

	public void afterPropertiesSet() throws Exception {
		for (String element : USERNAMES) {
			IdoncUser user = new IdoncUser();
			user.setUserName(element);
			user.setFirstName(randomString(FIRSTNAMES));
			user.setLastName(randomString(LASTNAMES));
			Address address = new Address();

			String email = user.getFirstName() + "@" + user.getLastName()+ ".com";
			email = email.toLowerCase();

			address.setEmail(email);
			address.setCity(randomString(CITIES));
			address.setCountry(randomString(COUNTRIES));

			boolean isFlatNumber = rint(0,1) == 0 ? true : false;
			String houseNumber = "";
			if (isFlatNumber) {
				houseNumber = new StringBuffer().append(rint(0, 100)).toString();
			} else {
				houseNumber = new StringBuffer().append(rint(1, 100)).append("/").
					append(rint(1, 15)).toString();
			}
			address.setHouseNumber(houseNumber);

			address.setStreet(randomString(STREETS));

			String website = new StringBuilder().append("www.").append(user.getFirstName()).
				append(".").append(user.getLastName()).append(randomString(DOMAINS)).toString();
			address.setWebsite(website);
			address.setZipCode(randomZipCode());
			user.setAddress(address);

			idoncService.registerUser(user, element);

		}

		for (int i = 0; i < PROJECTNAMES.length; i++) {
			String projectName = PROJECTNAMES[i];
			String projectDescription = PROJECT_DESCRIPTIONS[i];
			String website = PROJECT_WEBSITE[i];
			String className = PROJECT_CLASS_NAME[i];
			IdoncProject project = new IdoncProject();
			project.setName(projectName);
			project.setDescription(projectDescription);
			project.setWebsite(website);
			project.setComputationClassName(className);
			project = projectDao.save(project);
			project.setPartsToProcess(generatePartsForPOCProject(project));
			projectDao.save(project);
		}

//		List<IdoncProject> project = projectDao.get();
//		List<IdoncPart> parts = projectDao.getParts(project.get(0));
//		System.out.println(parts.get(0).getName());
	}

	private String randomZipCode() {
		return new StringBuffer().append(rint(0,9)).append(rint(0,9)).append("-").
			append(rint(0,9)).append(rint(0, 9)).append(rint(0, 9)).toString();
	}

	private String randomString(String[] choices) {
		return choices[rint(0, choices.length)];
	}

	private int rint(int min, int max) {
		return (int) (Math.random() * (max - min) + min);
	}

	private List<IdoncPart> generatePartsForPOCProject(IdoncProject project) {
		List<IdoncPart> list = new ArrayList<IdoncPart>();
		for(int i = 0; i < 1000; i++) {
			long timeToWait = getRandomTimeToWait();

			IdoncPart part = new IdoncPart();
			part.setName("wait " + timeToWait + "msec");
			part.setNumber(new Long(i));
			List<IdoncLongData> dataList = new ArrayList<IdoncLongData>();
			IdoncLongData data = new IdoncLongData();
			data.setValue(timeToWait);
			dataList.add(data);
			part.setLongDataList(dataList);
			part.setProject(project);
			list.add(part);
		}
		return list;
	}

	private long getRandomTimeToWait() {
		Random rand = new Random();
		int t = rand.nextInt(56);
		return new Long((t + 5) * 1000);
	}

}
