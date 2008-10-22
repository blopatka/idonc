package org.lopatka.idonc.web.service;

import java.util.List;

import org.lopatka.idonc.web.model.user.IdoncUser;

public interface IdoncService {

	//Us�ugi przeznaczone przede wszystkim dla aplikacji webowej

	//pobiera liste uzytkownikow, wraz i informacjami o nich (duzo danych) :)
	public List<IdoncUser> getUserList(String username, String sessionId);

	//pobiera dane jednego u�ytkownika
	public IdoncUser getUserDetails(String queriedUsername, String username, String sessionId);

	//pobiera liste nazw u�ytkownik�w
	public List<String> getUserNameList(String username, String sessionId);

	//zmienia dane zarejstrowanego u�ytkownika - podajemy nowy zestaw danych,
	//oraz osobno nazwe uzytkownika i numer sesji - UWAGA nie mozna zmieni�
	//nazwy uzytkownika
	public void updateUser(IdoncUser user, String username, String sessionId);

	//odpowiedzialne za logowanie kokretnego uzytkownika -
	//podaje sie nazwe i haslo, usluga zwraca id sesji - nowe
	//jezeli uzytkownik nie jest zalogowany, istniejace w bazie,
	//jezeli id sesji dla tego uzytkownika juz istnieje (nie zosta�
	//od czasu poprzedniego loginu wylogowany)
	public String loginUser(String username, String password);

	//us�uga rejestruj�ca nowego u�ytkownika - na wej�ciu
	//podajemy wszelkie potrzebne do tego dane, us�uga
	//po poprawnym zarejestrowaniu u�ytkownika loguje go
	//do systemu i zwraca idsesji - je�eli co� p�jdzie
	//niepoprawnie, to zwracany jest odpowiedni exception (np jezeli
	//istnieje juz taki uzytkownik w bazie
	public String registerUser(IdoncUser user);

	//odpowiedzialna za wylogowanie u�ytkownika - podajemy dane
	//jednoznacznie i w spos�b bezpieczny identyfikuj�ce u�ytkownika
	//nazwe i id sesji, po czym wylogowujemy - polega to na tym, �e
	//je�eli istnieje takie id sesji w bazie, i odpowiada ono
	//podanej nazwie, to z tabeli zalogowanych uzytkownikow
	//usuwany jest konkretny wpis
	public void logoutUser(String userName, String sessionId);
}
