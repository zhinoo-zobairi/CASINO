# JASINO

Willkommen zu Jasino, einem Java-Projekt, das ein einfaches Casinospiel mit mehreren Spielmodi, Benutzerverwaltung und Statistiken demonstriert. Dieses Projekt bietet eine grafische Oberfläche (GUI), in der sich Spieler*innen einloggen, verschiedene Modi wählen, Einsätze tätigen und ihre Fortschritte in einer CSV-Datei speichern können.

Inhalt
	1.	Überblick
	2.	Hauptfunktionen
	3.	Projektstruktur
	4.	Voraussetzungen & Installation
	5.	Spielablauf
	6.	Profile & Statistiken
	7.	Mitwirken (Contributing)
	8.	Lizenz

Überblick
	•	Sprache: Java (Swing für die GUI)
	•	Zweck: Ein einfaches Casinospiel mit mehreren Modi, bei dem Spieler*innen Walzen drehen und Gewinne erzielen können.
	•	Besonderheiten:
	•	Benutzerverwaltung (Login/Registrierung)
	•	CSV-gestützte Speicherung und Verwaltung von Spielerdaten (Guthaben, Spiele, Gewinne etc.)
	•	Verschiedene Spielmodi:
	1.	Früchte-Modus (klassische Symbole)
	2.	Herz-Symbole (Smiley-Gewinnbild als Spezialfall)
	3.	Emoji-Modus

Hauptfunktionen
	1.	Login und Registrierung
	•	Verwaltung von Benutzernamen und Passwörtern in einer CSV-Datei.
	•	Automatisches Anlegen neuer Spielerkonten (inkl. Startguthaben).
	2.	Mehrere Spielmodi
	•	Mode 1 (bzw. 3) und Mode 2 unterscheiden sich im Symbol-Set und in den Gewinnmustern.
	•	Gewinnbedingungen: Reihen & Spalten oder spezielles Smiley-Muster.
	3.	Einsätze und Guthaben
	•	Spieler*innen können den Einsatz erhöhen oder verringern.
	•	Guthaben und Einsätze werden in Echtzeit angezeigt.
	4.	On-a-Roll-Bonus
	•	Wird aktiviert, wenn mehrere Gewinnlinien (Reihe und Spalte) in einer Drehung erzielt werden.
	•	Erhöht die Gewinne durch Multiplikatoren in den Folgerunden.
	5.	Profil & Statistiken
	•	Anzeige persönlicher Daten (Guthaben, Anzahl Spiele, Gewinne, Winrate).
	•	Vergleich mit anderen Spieler*innen (Durchschnittsvermögen, Gesamtspielanzahl, globale Winrate).
	•	Zurücksetzen des eigenen Guthabens auf 10.000.
	6.	Instruktionen
	•	Ein separates Fenster mit Erklärungen zum Spielablauf und zur Bedienung.

Projektstruktur

Die wichtigsten Klassen sind:
	1.	Casino
	•	Hauptfenster des Spiels, enthält das Layout mit Walzen, Menüleisten und Einstelloptionen.
	•	Steuert den ReelPanel, Einsatz-Buttons sowie das „Spin“-Verhalten.
	2.	ReelPanel
	•	Panel zur Darstellung der Walzen (als 5×5 Raster).
	•	Zeichnet Gewinnlinien (rote Linien) oder ein Smiley-Gesicht, abhängig vom aktuellen Modus.
	3.	CasinoLogic
	•	Kern-Logik des Spiels (Geldberechnung, Modusumschaltung, CSV-Aktualisierung).
	•	Zuständig für das Generieren der Zufallssymbole und das Ausrechnen der Gewinne.
	•	Methoden zum Laden und Speichern aus/in data.csv.
	4.	CasinoLoginUI
	•	Separates Fenster für die Benutzer-Anmeldung oder Registrierung.
	•	Validiert Benutzername/Passwort und ruft das Hauptspiel auf.
	5.	Profile
	•	Zeigt ein Profilfenster mit persönlichen Daten (Guthaben, Spiele, Winrate) an.
	•	Bietet zudem eine Vergleichsansicht (Durchschnittswerte aller Spieler*innen) und eine Reset-Funktion.
	6.	Instructions
	•	Enthält ein Fenster mit erklärenden Texten zum Spielablauf, Modi, Regeln usw.
	7.	CasinoMain (nicht komplett im Code gezeigt, aber im Code referenziert)
	•	Vermutlich die Klasse, die Frames öffnet/schließt und zwischen den unterschiedlichen GUIs (Login, Spiel, Profil) vermittelt.

Voraussetzungen & Installation
	1.	Java-Entwicklungsumgebung
	•	Das Projekt ist mit Java 8 oder höher lauffähig.
	•	Es empfiehlt sich, ein IDE wie IntelliJ, Eclipse oder NetBeans zu verwenden.
	2.	Dateistruktur
	•	Das Projekt enthält die Java-Klassen und eine CSV-Datei data.csv, in welcher Benutzerdaten verwaltet werden.
	•	Achte darauf, dass data.csv im richtigen Verzeichnis liegt, damit das Lesen/Schreiben funktioniert (Standard: ./data.csv).
	3.	Kompilierung & Ausführung
	•	Öffne das Projekt in deiner IDE oder kompiliere es über die Kommandozeile (javac *.java).
	•	Starte anschließend die entsprechende Main-Klasse, z.B. CasinoLoginUI, um das Spiel zu testen.

Spielablauf
	1.	Anmeldung
	•	Starte das Programm und registriere dich falls nötig, oder melde dich mit bestehendem Benutzernamen und Passwort an.
	2.	Auswahl des Modus
	•	Im Hauptfenster (Casino) über das Menü „Mode“ lassen sich die Modi 1, 2 oder 3 umschalten.
	3.	Einsatz setzen
	•	Rechts im Fenster kann der Einsatz (BET) über +100, +1000, -100, -1000 oder Set 0 geändert werden.
	•	Beobachte gleichzeitig dein Guthaben (Money).
	4.	Spin
	•	Betätige den „Spin“-Button (oder den entsprechenden Shortcut), um die Walzen zu drehen.
	•	Je nach Modus werden verschiedene Symbole angezeigt (Früchte, Herzen, Emojis).
	5.	Gewinnbewertung
	•	Modus 1 & 3: Gewinne bei einer kompletten Reihe oder Spalte mit gleichen Symbolen (rote Linien).
	•	Modus 2: Spezielles Smiley-Muster aktiviert einen hohen Gewinn.
	6.	Weitermachen oder Profil aufrufen
	•	Ist dein Guthaben aufgebraucht, kannst du eventuell nicht mehr weiter spielen.
	•	Unter „Profile“ findest du deine Statistiken und kannst ggf. dein Geld auf 10.000 zurücksetzen (Startwert).

Profile & Statistiken
	•	Profilfenster (Profile.java):
	•	Zeigt deinen Benutzernamen, dein aktuelles Guthaben und Spiele/Winrate.
	•	Vergleicht globale Daten wie durchschnittliches Guthaben, Gesamtspiele und globale Winrate.
	•	Bietet die Option, dein Guthaben zurückzusetzen.
	•	Instruktionen (Instructions.java):
	•	Erklärt detailliert den Spielablauf, die Bedienung und das Gewinnsystem.
 über eine Nachricht oder einen Issue im Repository.
