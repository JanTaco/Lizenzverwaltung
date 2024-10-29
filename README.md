





Dokumentation 







Softwarelizenzverwaltungssystem


Stand 23.10.2024







D2D
Jan Wegner, Luis Bittner
Inhaltsverzeichnis
Verwendungszweck und Vorteile der APP	3
Benutzerdokumentation	3
Allgemeiner Aufbau der Oberfläche	3
Systemdokumentation	5
Aufbau/Funktion Backend	5
Datenbank	6
Spring Boot API	7
Struktur API	7
Security	9
React	9
Struktur React	9
Code Frontend	10
POPUP	12





















Verwendungszweck und Vorteile der APP

Auch wenn Excel eine Vielzahl nützlicher Funktionen bietet, um Daten darzustellen, kann es bei großen Datenmengen schnell unübersichtlich und unpraktisch werden. Die Struktur und Organisation der Daten in Excel sind begrenzt, was dazu führt, dass die Datenverwaltung und -analyse kompliziert und fehleranfällig wird. Aus diesem Grund ist Excel nicht als optimale Datenbank geeignet. Für umfangreiche oder komplexe Datenbestände sind spezialisierte Datenbankmanagementsysteme wie MySQL besser geeignet. So ein System bietet leistungsfähige Funktionen zur Datenorganisation, Abfrage und Verwaltung, die Excel nicht in demselben Maße bereitstellt.

Dieses Projekt wurde entwickelt, um Softwarelizenzen optimal zu verwalten und darzustellen. Durch die übersichtliche und unkomplizierte Web-Oberfläche ist es auch für Nutzer ohne IT-Kenntnisse möglich die Datenbank zu verwalten und abzufragen.

Benutzerdokumentation


Allgemeiner Aufbau der Oberfläche

Die Applikation besteht aus einer Dynamischen-Tabelle die je nachdem, was ausgewählt wurde entweder die einzelnen Lizenzen oder Lizenzvergaben anzeigt.  Die Ansicht kann verändert werden, indem man auf BUTTON[1] klickt.
Für das Erstellen neuer Datensätze klickt man auf BUTTON[2] 
Sobald eine neuer Datensatz erstellt wurde, wird dieser direkt in der Tabelle angezeigt.

Um einen Datensatz zu löschen oder bearbeiten zu können muss man den Bearbeiten Knopf betätigen ( BUTTON[3])


Um nach bestimmten Datensätzen zu suchen, verwendet man die Suchleiste. Hier genügt es nach einem beliebigen wert zu suchen (Es ist egal in welcher Spalte dieser Existiert).In der Anzeige oben rechts wird die Anzahl der freien und vergebenen Lizenzen angezeigt. Zusätzlich sieht man auch die Gesamtanzahl der Lizenzen in der Datenbank. Diese Daten verändern sich nach jedem Löschen oder Erstellen eines Datensatzes.
 


Ansicht Lizenzen:
 
Ansicht Lizenzvergaben:
 




Ansicht Datensatz bearbeiten:
 
Systemdokumentation 

Aufbau/Funktion Backend














 





Datenbank

Für diese Projekt wurde eine MySQL-Datenbank verwendet, die mit einer Java-Spring-Boot-API verwaltet wird. 


Aufbau Datenbank:

 



employee_entity: Hier werden die einzelnen Mitarbeiter gespeichert an die Lizenzen vergeben werden können. 
license_entity: Hier werden die einzelnen Softwarelizenzen mit Informationen wie. 
Licensing: diese Tabelle dient als Hilfstabelle für die n:m Beziehung zwischen license_entity und employee_entity. Hier werden alle lizenzvergeben gespeichert
Users: Die Tabelle „users“ ist ausschließlich für das Einloggen zur Applikation zuständig sie steht in keinem Zusammenhang mit den anderen Tabellen in der Datenbank. Der Username wird als Klartext in der Datenbank gespeichert, das Passwort wird allerdings al Hashwert in der Datenbank gespeichert



Spring Boot API

Struktur API

 




Die Hauptfunktionen der API erden auf 3 Arten von Klassen aufgeteilt.
1.	Controller-Klassse
-	Die Controller-Klasse verarbeitet alle http-Anfragen die die API-erhält und gibt eine Antwort zurück. 
2.	Entity-Klassen
-	Diese Klassen repräsentieren die einzelnen Tabellen in der Datenbank. Jede Instanz einer Entity-Klasse entspricht einer Zeile in der Tabelle.
3.	 Repo-Klassen
-	Diese Klassen bieten Methoden zum Zugriff auf die Datenbank, wie z.B. das Speichern, Finden oder Löschen von Daten. Sie sind für die Datenmanipulation zuständig.

EmployeeEntity & LicenseEntity:

Die Klassen EmployeeEntity und LicenseEntity repräsentieren Entitäten in einer Spring-Anwendung. Beide sind mit der Annotation @Entity versehen, was sie zu persistenten Objekten in unserer Datenbank macht.
Gemeinsamkeiten
Beide Klassen modellieren unterschiedliche Aspekte der Anwendung und nutzen Getter- und Setter-Methoden für den Zugriff auf ihre Felder. Dies unterstützt die Datenkapselung und vereinfacht den Datenbankzugriff. EmployeeEntity verwaltet Mitarbeiterinformationen und LicenseEntity kümmert sich um Lizenzdetails. Die Verknüpfung erfolgt durch das license-Attribut in EmployeeEntity, das auf eine Lizenz verweist.


EmployeeRepo & LicenseRepo:

Die Interfaces LicenseRepo und EmployeeRepo sind Spring Data JPA-Repositorys für den Datenbankzugriff. Beide erweitern CrudRepository und bieten grundlegende CRUD-Operationen sowie benutzerdefinierte SQL-Abfragen mit der Annotation @Query.

Beide Repository-Interfaces verwenden native SQL-Abfragen für komplexe Datenbankoperationen, um spezifische Datenmanipulationen durchzuführen und auf gespeicherte Informationen zuzugreifen.
Während sich LicenseRepo auf die Verwaltung von Lizenzdaten konzentriert, kümmert sich EmployeeRepo um die Verknüpfung von Mitarbeitern mit ihren Lizenzen. Zusammen ermöglichen sie den Zugriff auf verknüpfte Datenbanktabellen und erleichtern so die Verwaltung von Mitarbeitern und Lizenzen in der Anwendung.


MainController:
Die Klasse MainController ist ein Spring MVC-Controller, der HTTP-Anfragen bearbeitet und als Vermittler zwischen der Webanwendung und den Datenbank-Repositorys fungiert. Sie ist mit der Annotation @Controller gekennzeichnet und integriert mit den Repositories EmployeeRepo und LicenseRepo über @Autowired.
 Die Klasse definiert Endpunkte für CRUD-Operationen und Datenabfragen zu Mitarbeitern und Lizenzen
Die Methoden geben JSON-Antworten zurück, um die Interaktion mit Frontend-Anwendungen zu erleichtern.

SpringSwlzfwApplication:
Die Klasse SpringSwlzfwApplication ist der zentrale Einstiegspunkt für die Spring Boot-Anwendung. Ihre main-Methode initiiert den Start der Anwendung und stellt sicher, dass alles reibungslos Funktion.

Application.properties:

Applicatiojn.properties  gibt die Datenbankverbindung, den Benutzer, das Passwort und den JDBC-Treiber an und sorgt dafür, dass die Datenbankstruktur automatisch aktualisiert, wird

Security

Hier die Security Funktion erklären
In dieser Applikation wird mit einem JWT-Token gearbeitet. Dieser Token ermöglicht dem Benutzer das einmalige Einloggen in die Applikation. Bei jedem API-Abruf wird eine Authentifizieruung benötigt, diese kann über zwei Wege geschehen 1. Durch das eingeben des Benutzers und des Benutzerpassworts 2. Durch das mitsenden eines JWT-Tokens. Im normalfall wird beim ersten log-in ein JWT-Token mit einer Gültigkeit von 8h erstellt…..
(Bei jedem betreten und Aktualisieren der Seite kontrolliert der Server die Gültigkeit des Tokens, sol)
React 

Struktur React

 



Code Frontend

App.css:
Die Klasse App.css ist eine Standartklasse von React und sorgt dafür, dass die Standartwebseite von React ein Design hat.

App.js: 
Die Klasse App.js ist die hauptklasse von React. Sie verwaltet den Aufbau der Benutzeroberfläche.

App.test.js: 
App.test.js ist eine Klasse, in der Funktionen getestet werden bevor sie in App.js verwendet werden.

CustomTable.js:
Diese Klasse erschafft eine Tabelle in der die Angaben zu den Lizenzen eingetragen bearbeitet und gelöscht werden können. 

e2e.test.js:
Die Klasse e2e.test.js dient dazu, die Funktionalität einer Webanwendung automatisch zu testen, indem es typische Benutzerinteraktionen nachahmt und sicherstellt, dass die Anwendung korrekt reagiert.

EditModal.js:
Mit Hilfe dieser Klasse kann man die einzelnen Einträge der Tabelle bearbeiten.

EditOrAddEntityFields.js:
Die EditOrAddEntityFields.js erzeugt jeweils einen Button, um Datensätze hinzuzufügen oder sie zu ändern.

Index.css:
Die Index.css ist für das Design der Webanwendung zuständig, hier werden die Farben und Größen der Tabelle und der Buttons bestimmt.



Index.js: 
Diese Klasse stellt sicher, dass die Hauptkomponente der Anwendung (App) in das DOM gerendert wird, und bietet die Möglichkeit, Performance-Daten zu sammeln. Sie ist der Startpunkt, an dem die gesamte React-Anwendung beginnt und sorgt dafür, dass alle notwendigen Styles und Skripte geladen werden, bevor die Anwendung gestartet wird.

Logo.svg:
Logo.svg ist eine Standardklasse von React die das Reactlogo enthält.

reportWebVitals.js:
Die Datei reportWebVitals.js liefert die Leistungsdaten der Webanwendung.

setupTests.js:
setupTests.js ist eine Datei, die angelegt wurde, um die Einstellungen der Webanwendung zu testen.

Funktionsweise der Tabelle:
Die Tabelle wird in zwei Klassen unterteilt (Tableemploye.js und TableLicense.js), welche der beiden Tabellen angezeigt wird, wird durch die mode variable im local-Storage entschieden. Der wert der variable wird durch den „Show License“/„Show Employee“-Button verändert dann wird bei jedem neuen Render der Zustand der Variable kontrolliert. 

Die Tablerow.js Datei ist nur für das Einfügen der Daten in die Tabelle zuständig. Diese Klasse verwendet ebenfalls die Variable mode aus dem local-Storage um zu entscheiden wie und wo die einzelnen Datensätze eingefügt werden
 
POPUP

Das Popup zur Bearbeitung kann von TableLicense und TableEmployee aufgerufen werden. Beim Aufruf werden die Daten der Zeile und der mode der Tabelle übergeben. Anhand dieser Daten passen sich die Informationen in dem Popup an
