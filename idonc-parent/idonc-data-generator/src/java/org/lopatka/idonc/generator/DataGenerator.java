package org.lopatka.idonc.generator;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.lopatka.idonc.model.data.IdoncLongData;
import org.lopatka.idonc.model.data.IdoncPart;
import org.lopatka.idonc.model.data.IdoncProject;
import org.lopatka.idonc.model.data.PartType;
import org.lopatka.idonc.model.user.Address;
import org.lopatka.idonc.model.user.IdoncAdmin;
import org.lopatka.idonc.model.user.IdoncUser;
import org.lopatka.idonc.model.user.UserCredential;
import org.lopatka.idonc.model.util.PasswordHasher;



/**
 * Hello world!
 *
 */
public class DataGenerator
{
	private static final String[] PROJECTNAMES = {"Proof of Concept", "Proof of Concept II", "Liczba Pi", "Spin"};
	private static final String[] PROJECT_DESCRIPTIONS = {"Projekt pokazujacy ze dziala wymiana informacji miedzy serwerem a klientem - ze sprawdzeniem poprawnosci wyniku", "Projekt pokazujacy ze dziala wymiana informacji miedzy serwerem a klientem - bez potwierdzania poprawnosci wyniku", "Projekt oblicza liczbe Pi", "projekt oblicza Spiny ;-)"};
	private static final String[] PROJECT_WEBSITE = {"www.poc.example.com", "www.poc2.example.com", "www.pi.example.com", "www.spin.example.com"};
	private static final String[] PROJECT_CLASS_NAME = {"org.lopatka.idonc.computation.poc.PocComputation", "org.lopatka.idonc.computation.poc.Poc2Computation", "org.lopatka.idonc.computation.pi.PiComputation", "org.lopatka.idonc.computation.ising.IsingComputation"};

	private static final String[] USERNAMES = {"andy", "gary", "lisa", "bart",
			"homer", "marge", "maggie", "krusty", "opek", "opiszon", "kasia",
			"kacha", "koteczek", "pies", "kot", "sowa", "jaszczurka", "slon",
			"kon", "swiniak", "koza", "krowa", "grab", "dab", "orzech", "jablon",
			"wisnia", "grusza", "modrzew", "sosna"};

	private static final String PASSWORD = "qwe123";

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


    public static void main( String[] args )
    {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("idonc");
    	EntityManager em = emf.createEntityManager();

    	EntityTransaction tx = em.getTransaction();
    	tx.begin();

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

			//idoncService.registerUser(user, element);
			//rejestracja uzytkownika

			//user = userDao.save(user);
			em.persist(user);

			UserCredential cred = new UserCredential();
			cred.setUser(user);
			byte[] salt;
			try {
				salt = PasswordHasher.createSalt();
				byte[] pass = PasswordHasher.getHash(1000, PASSWORD, salt);
				cred.setSalt(PasswordHasher.byteToBase64(salt));
				cred.setPassword(PasswordHasher.byteToBase64(pass));

				//userCredentialDao.save(cred);
				em.persist(cred);
			} catch (NoSuchAlgorithmException e) {
				//return false;
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		}

		//dodanie uzytkownika admin
		IdoncUser forAdmin = em.find(IdoncUser.class, new Long(1));
		IdoncAdmin admin = new IdoncAdmin();
		admin.setUser(forAdmin);
		em.persist(admin);

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
			//project = projectDao.save(project);
			//projectDao.save(project);
			em.persist(project);

//			project.setPartsToProcess(generatePartsForPOCProject(project));
		}
    	//save
    	tx.commit();

    	tx.begin();
    	IdoncProject Poc1 = em.find(IdoncProject.class, new Long(1));
    	Poc1.setParts(generatePartsForPOCProject(Poc1));
    	em.persist(Poc1);

    	IdoncProject Poc2 = em.find(IdoncProject.class, new Long(2));
    	Poc2.setParts(generatePartsForPOCProject(Poc2));
    	em.persist(Poc2);

    	IdoncProject Pi = em.find(IdoncProject.class, new Long(3));
    	Pi.setParts(generatePartsForPiProject(Pi));
    	em.persist(Pi);

    	IdoncProject Ising = em.find(IdoncProject.class, new Long(4));
    	Ising.setParts(generatePartsForIsingProject(Ising));
    	em.persist(Ising);

    	tx.commit();

    	em.close();
    	emf.close();
    }

    private static String randomZipCode() {
		return new StringBuffer().append(rint(0,9)).append(rint(0,9)).append("-").
			append(rint(0,9)).append(rint(0, 9)).append(rint(0, 9)).toString();
	}

	private static String randomString(String[] choices) {
		return choices[rint(0, choices.length)];
	}

	private static int rint(int min, int max) {
		return (int) (Math.random() * (max - min) + min);
	}

	private static List<IdoncPart> generatePartsForPOCProject(IdoncProject project) {
		List<IdoncPart> list = new ArrayList<IdoncPart>();
		for(int i = 0; i < 1000; i++) {
			long timeToWait = getRandomTimeToWait();

			IdoncPart part = new IdoncPart();
			part.setName("wait " + timeToWait + "msec");
			part.setNumber(new Long(i));
			part.setPartType(PartType.NEW);
			part.setProject(project);

			List<IdoncLongData> dataList = new ArrayList<IdoncLongData>();
			IdoncLongData data = new IdoncLongData();
			data.setValue(Long.toString(timeToWait));
			dataList.add(data);
			part.setLongDataList(dataList);
			list.add(part);
		}
		return list;
	}

	private static List<IdoncPart> generatePartsForPiProject(IdoncProject project) {
		List<IdoncPart> list = new ArrayList<IdoncPart>();
		for(int i = 0; i < 1000; i++) {
			long steps = getRandomStepsForPiProcessing();

			IdoncPart part = new IdoncPart();
			part.setName("calculate Pi in "+steps+" steps");
			part.setNumber(new Long(i));
			part.setPartType(PartType.NEW);
			part.setProject(project);

			List<IdoncLongData> dataList = new ArrayList<IdoncLongData>();
			IdoncLongData data = new IdoncLongData();
			data.setValue(Long.toString(steps));
			dataList.add(data);
			part.setLongDataList(dataList);
			list.add(part);
		}
		return list;
	}

	private static long getRandomStepsForPiProcessing() {
		//wylosuj ilos krokow (od 200 do 3000)
		Random rand = new Random();
		int s = rand.nextInt(2800);
		return new Long((s+200) * 10);
	}

	private static List<IdoncPart> generatePartsForIsingProject(IdoncProject project) {
		List<IdoncPart> list = new ArrayList<IdoncPart>();
		for(int i = 0; i < 1000; i++) {
			//tutaj utworzyc dane do przeliczania
			Integer steps = getRandomStepsForIsingProcessing();
			int size = getRandomIsingLatticeSize();

			List<String> array = getRandomIsingLattice(size);

			List<IdoncLongData> data = new ArrayList<IdoncLongData>();
			IdoncLongData stepsData = new IdoncLongData();
			stepsData.setValue(steps.toString());
			data.add(stepsData);

			for(String row : array) {
				IdoncLongData rowData = new IdoncLongData();
				rowData.setValue(row);
				data.add(rowData);
			}

			IdoncPart part = new IdoncPart();
			part.setName("calculate Ising 2d array of size "+size);
			part.setNumber(new Long(i));
			part.setPartType(PartType.NEW);
			part.setProject(project);
			part.setLongDataList(data);
			list.add(part);
		}
		return list;
	}

	private static List<String> getRandomIsingLattice(int size) {
		List<String> ret = new ArrayList<String>();
		Random rand = new Random();
		for (int x = 0; x < size; x++) {
			StringBuffer buf = new StringBuffer();
			for (int y = 0; y < size; y++) {
				if(rand.nextBoolean()) {
					buf.append('1');
				} else {
					buf.append('0');
				}
			}
			ret.add(buf.toString());
		}
		return ret;
	}

	private static int getRandomStepsForIsingProcessing() {
		Random rand = new Random();
		int s = rand.nextInt(900);
		return new Integer(s+2000);
	}

	private static int getRandomIsingLatticeSize() {
		Random rand = new Random();
		int s = rand.nextInt(50);
		return new Integer(s+20);
	}

	private static long getRandomTimeToWait() {
		Random rand = new Random();
		int t = rand.nextInt(26);
		return new Long((t + 5) * 1000);
	}
}
