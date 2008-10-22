package org.lopatka.idonc.web.service;

import java.util.List;

import org.lopatka.idonc.web.model.user.IdoncUser;

public interface IdoncService {

	//Us³ugi przeznaczone przede wszystkim dla aplikacji webowej

	//pobiera liste uzytkownikow, wraz i informacjami o nich (duzo danych) :)
	public List<IdoncUser> getUserList(String username, String sessionId);

	//pobiera dane jednego u¿ytkownika
	public IdoncUser getUserDetails(String queriedUsername, String username, String sessionId);

	//pobiera liste nazw u¿ytkowników
	public List<String> getUserNameList(String username, String sessionId);

	//zmienia dane zarejstrowanego u¿ytkownika - podajemy nowy zestaw danych,
	//oraz osobno nazwe uzytkownika i numer sesji - UWAGA nie mozna zmieniæ
	//nazwy uzytkownika
	public void updateUser(IdoncUser user, String username, String sessionId);

	//odpowiedzialne za logowanie kokretnego uzytkownika -
	//podaje sie nazwe i haslo, usluga zwraca id sesji - nowe
	//jezeli uzytkownik nie jest zalogowany, istniejace w bazie,
	//jezeli id sesji dla tego uzytkownika juz istnieje (nie zosta³
	//od czasu poprzedniego loginu wylogowany)
	public String loginUser(String username, String password);

	//us³uga rejestruj¹ca nowego u¿ytkownika - na wejœciu
	//podajemy wszelkie potrzebne do tego dane, us³uga
	//po poprawnym zarejestrowaniu u¿ytkownika loguje go
	//do systemu i zwraca idsesji - je¿eli coœ pójdzie
	//niepoprawnie, to zwracany jest odpowiedni exception (np jezeli
	//istnieje juz taki uzytkownik w bazie
	public String registerUser(IdoncUser user);

	//odpowiedzialna za wylogowanie u¿ytkownika - podajemy dane
	//jednoznacznie i w sposób bezpieczny identyfikuj¹ce u¿ytkownika
	//nazwe i id sesji, po czym wylogowujemy - polega to na tym, ¿e
	//je¿eli istnieje takie id sesji w bazie, i odpowiada ono
	//podanej nazwie, to z tabeli zalogowanych uzytkownikow
	//usuwany jest konkretny wpis
	public void logoutUser(String userName, String sessionId);
}
