# Jasino


Willkommen zu **Jasino**, einem Java-Projekt, das ein einfaches Casinospiel mit mehreren Spielmodi, Benutzerverwaltung und Statistiken demonstriert. Dieses Projekt bietet eine grafische Oberfläche (GUI), in der sich Spieler*innen einloggen, verschiedene Modi wählen, Einsätze tätigen und ihre Fortschritte in einer CSV-Datei speichern können.

---

## Inhaltsverzeichnis

1. [Überblick](#überblick)  
2. [Hauptfunktionen](#hauptfunktionen)  
3. [Projektstruktur](#projektstruktur)  
4. [Spielablauf](#spielablauf)  
5. [Profile & Statistiken](#profile--statistiken)  

---

## Überblick

- **Sprache**: Java (Swing für die GUI)  
- **Zweck**: Ein einfaches Casinospiel mit mehreren Modi, bei dem Spieler*innen Walzen drehen und Gewinne erzielen können.  
- **Besonderheiten**:  
  - Benutzerverwaltung (Login/Registrierung)  
  - CSV-gestützte Speicherung und Verwaltung von Spielerdaten (Guthaben, Spiele, Gewinne etc.)  
  - Verschiedene Spielmodi:  
    1. **Früchte-Modus** (klassische Symbole)  
    2. **Herz-Symbole** (Smiley-Gewinnbild als Spezialfall)  
    3. **Emoji-Modus**  

---

## Hauptfunktionen

1. **Login und Registrierung**  
   - Verwaltung von Benutzernamen und Passwörtern in einer CSV-Datei.  
   - Automatisches Anlegen neuer Spielerkonten (inkl. Startguthaben).

2. **Mehrere Spielmodi**  
   - **Mode 1** (bzw. 3) und **Mode 2** unterscheiden sich im Symbol-Set und in den Gewinnmustern.  
   - Gewinnbedingungen: Reihen & Spalten oder spezielles Smiley-Muster.

3. **Einsätze und Guthaben**  
   - Spieler*innen können den Einsatz erhöhen oder verringern.  
   - Guthaben und Einsätze werden in Echtzeit angezeigt.

4. **On-a-Roll-Bonus**  
   - Wird aktiviert, wenn mehrere Gewinnlinien (Reihe und Spalte) in einer Drehung erzielt werden.  
   - Erhöht die Gewinne durch Multiplikatoren in den Folgerunden.

5. **Profil & Statistiken**  
   - Anzeige persönlicher Daten (Guthaben, Anzahl Spiele, Gewinne, Winrate).  
   - Vergleich mit anderen Spieler*innen (Durchschnittsvermögen, Gesamtspielanzahl, globale Winrate).  
   - Zurücksetzen des eigenen Guthabens auf 10.000.

6. **Instruktionen**  
   - Ein separates Fenster mit Erklärungen zum Spielablauf und zur Bedienung.

---

## Projektstruktur

Die wichtigsten Klassen sind:

- **Casino**  
  - Hauptfenster des Spiels: Layout mit Walzen, Menüleisten und Einstelloptionen  
  - Steuert den `ReelPanel`, Einsatz-Buttons sowie das „Spin“-Verhalten

- **ReelPanel**  
  - Panel zur Darstellung der Walzen (als 5×5 Raster)  
  - Zeichnet Gewinnlinien (rote Linien) oder ein Smiley-Gesicht, abhängig vom aktuellen Modus

- **CasinoLogic**  
  - Kern-Logik des Spiels (Geldberechnung, Modusumschaltung, CSV-Aktualisierung)  
  - Zuständig für das Generieren der Zufallssymbole und das Ausrechnen der Gewinne  
  - Methoden zum Laden und Speichern aus/in `data.csv`

- **CasinoLoginUI**  
  - Separates Fenster für die Benutzer-Anmeldung oder Registrierung  
  - Validiert Benutzername/Passwort und ruft das Hauptspiel auf

- **Profile**  
  - Zeigt ein Profilfenster mit persönlichen Daten (Guthaben, Spiele, Winrate) an  
  - Bietet zudem eine Vergleichsansicht (Durchschnittswerte aller Spieler*innen) und eine Reset-Funktion

- **Instructions**  
  - Fenster mit erklärenden Texten zum Spielablauf, Modi, Regeln usw.

- **CasinoMain** (im Code referenziert, nicht vollständig hier gezeigt)  
  - Vermutlich die Klasse, die Frames öffnet/schließt und zwischen den unterschiedlichen GUIs (Login, Spiel, Profil) vermittelt

---

## Spielablauf

1. **Anmeldung**  
   - Starte das Programm, registriere dich (falls nötig) oder melde dich mit bestehendem Benutzernamen und Passwort an.  

2. **Auswahl des Modus**  
   - Im Hauptfenster (Casino) über das Menü „Mode“ wählbar (1, 2 oder 3).  

3. **Einsatz setzen**  
   - Rechts im Fenster kannst du den Einsatz (BET) mit +100, +1000, -100, -1000 oder Set 0 ändern.  
   - Dein aktuelles Guthaben (Money) wird ständig angezeigt.  

4. **Spin**  
   - Klicke den „Spin“-Button, um die Walzen zu drehen.  
   - Je nach Modus werden verschiedene Symbole (Früchte, Herzen, Emojis) angezeigt.  

5. **Gewinnbewertung**  
   - **Modus 1 & 3**: Gewinne bei einer kompletten Reihe oder Spalte (rote Linien).  
   - **Modus 2**: Spezielles Smiley-Muster für hohen Gewinn.  

6. **Weitermachen oder Profil aufrufen**  
   - Ist dein Guthaben aufgebraucht, kannst du eventuell nicht mehr weiterspielen.  
   - Unter „Profile“ findest du deine Statistiken und kannst dein Geld auf 10.000 zurücksetzen.

---

## Profile & Statistiken

- **Profilfenster (Profile.java)**  
  - Zeigt Benutzernamen, Guthaben, Spieleanzahl und Winrate.  
  - Stellt globale Daten (Durchschnittsguthaben, Gesamtspielanzahl, globale Winrate) gegenüber.  
  - Bietet die Möglichkeit, das Guthaben auf 10.000 zurückzusetzen.

- **Instruktionen (Instructions.java)**  
  - Detaillierte Erklärungen zum Spielablauf, den Regeln und dem On-a-Roll-Bonus.

---


**Viel Spaß beim Spielen!**  
