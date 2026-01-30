# 🐍 Snake Game

## Charakterystyka projektu

**Snake Game** to klasyczna gra zręcznościowa zaimplementowana w języku Java z wykorzystaniem biblioteki Swing.

### Opis gry
Gracz steruje wężem, który porusza się po planszy 20x20 komórek. Celem jest zbieranie jedzenia (żółte kółka), które powoduje wydłużenie węża i zwiększenie wyniku. Gra kończy się, gdy wąż uderzy w ścianę lub we własne ciało.

### Pełna lista funkcjonalności

#### 🎮 Rozgrywka
| Funkcjonalność | Opis |
|----------------|------|
| Sterowanie wężem | Ruch w 4 kierunkach (góra, dół, lewo, prawo) |
| Zbieranie jedzenia | Żółte kółka pojawiające się losowo na planszy |
| Wzrost węża | Każde zjedzone jedzenie wydłuża węża o 1 segment |
| Punktacja | +1 punkt za każde zebrane jedzenie |
| Wykrywanie kolizji | Kolizja ze ścianą lub własnym ciałem kończy grę |
| Plansza 20x20 | 400 komórek o rozmiarze 25x25 pikseli |

#### 🎯 Poziomy trudności
| Poziom | Opóźnienie | Opis |
|--------|------------|------|
| Easy | 150ms | Najwolniejszy, dla początkujących |
| Normal | 100ms | Standardowa prędkość |
| Hard | 70ms | Szybki ruch węża |
| Extreme | 40ms | Maksymalna prędkość, dla ekspertów |

#### ⌨️ Sterowanie
| Klawisz | Akcja |
|---------|-------|
| ↑ / W | Ruch w górę |
| ↓ / S | Ruch w dół |
| ← / A | Ruch w lewo |
| → / D | Ruch w prawo |
| SPACE | Restart gry po Game Over |
| ESC | Powrót do menu głównego |

#### 🏆 System rekordów
| Funkcjonalność | Opis |
|----------------|------|
| Automatyczny zapis | Rekord zapisywany do pliku `highscore.properties` |
| Wyświetlanie rekordu | Rekord widoczny w menu i podczas gry |
| Powiadomienie | Komunikat "NEW HIGH SCORE!" przy pobiciu rekordu |
| Trwałość danych | Rekord zachowany po zamknięciu gry |

#### 🎵 Muzyka i dźwięk
| Funkcjonalność | Opis |
|----------------|------|
| Muzyka w tle | Automatyczne odtwarzanie po uruchomieniu |
| Losowy wybór | Losowy utwór z folderu `/assets/music/` |
| Ciągłe odtwarzanie | Muzyka gra w pętli bez przerwy |
| Format WAV | Obsługa plików audio .wav |

#### 🎨 Interfejs graficzny
| Element | Opis |
|---------|------|
| Menu startowe | Wybór trudności, przycisk START, rekord |
| Kolorowy wąż | Segmenty w kolorach tęczy (7 kolorów) |
| Żółte jedzenie | Wyraźnie widoczne kółko z obramowaniem |
| Różowe tło | Charakterystyczny kolor planszy |
| Niestandardowa czcionka | ElmsSans-SemiBold dla lepszej estetyki |
| Ekran Game Over | Wynik, rekord, instrukcje restartu |

#### 🔧 Funkcje techniczne
| Funkcjonalność | Opis |
|----------------|------|
| Responsywne menu | Przyciski zmieniające kolor po zaznaczeniu |
| Zapobieganie cofaniu | Wąż nie może zawrócić o 180° |
| Płynna animacja | Timer synchronizujący ruch i rendering |
| Cross-platform LookAndFeel | Jednolity wygląd na różnych systemach |

### Technologie, Frameworki i Biblioteki

#### Język programowania
| Technologia | Wersja | Zastosowanie |
|-------------|--------|--------------|
| **Java** | 17+ | Główny język implementacji |

#### Biblioteki standardowe Java (JDK)

| Pakiet | Klasy | Zastosowanie |
|--------|-------|--------------|
| **javax.swing** | `JFrame`, `JPanel`, `JButton`, `JLabel`, `Timer`, `SwingUtilities` | Tworzenie interfejsu graficznego, obsługa zdarzeń, animacje |
| **java.awt** | `Graphics`, `Color`, `Font`, `Point`, `Dimension`, `FontMetrics`, `GraphicsEnvironment` | Renderowanie grafiki, kolory, czcionki, geometria |
| **java.awt.event** | `ActionListener`, `KeyAdapter`, `KeyEvent` | Obsługa zdarzeń klawiatury i timera |
| **javax.sound.sampled** | `Clip`, `AudioSystem`, `AudioInputStream` | Odtwarzanie muzyki w formacie WAV |
| **java.io** | `File`, `FileInputStream`, `FileOutputStream` | Operacje na plikach (zapis rekordów) |
| **java.util** | `ArrayList`, `Random`, `Properties` | Kolekcje, generowanie losowości, konfiguracja |
| **java.net** | `URL` | Ładowanie zasobów z classpath |

#### Narzędzia budowania

| Narzędzie | Plik konfiguracyjny | Zastosowanie |
|-----------|---------------------|--------------|
| **Gradle** | `build.gradle.kts` | Automatyzacja budowy, zarządzanie zależnościami |
| **Gradle Wrapper** | `gradlew`, `gradlew.bat` | Uruchamianie bez instalacji Gradle |

#### Zależności testowe

| Biblioteka | Wersja | Zastosowanie |
|------------|--------|--------------|
| **JUnit 5** | 5.10.0 | Testy jednostkowe |
| **JUnit Jupiter** | 5.10.0 | Silnik testów JUnit 5 |

#### Zasoby

| Typ | Format | Lokalizacja |
|-----|--------|-------------|
| **Czcionki** | TTF | `/assets/fonts/ElmsSans-SemiBold.ttf` |
| **Muzyka** | WAV | `/assets/music/*.wav` |

#### Architektura
- **Wzorzec**: MVC (Model-View-Controller)
  - **Model**: `Difficulty`, stan gry w `SnakeGame`
  - **View**: `SnakeGame.paintComponent()`, `StartMenu`
  - **Controller**: `GameManager`, obsługa klawiszy
---

## 📁 Struktura kodu

### Przegląd klas

```
┌──────────────────────────────────────────────────────────────┐
│                         Main                                 │
│                    (punkt wejścia)                          │
└──────────────────────┬───────────────────────────────────────┘
                       │ tworzy
                       ▼
┌──────────────────────────────────────────────────────────────┐
│                     GameManager                              │
│           (zarządzanie stanami i przepływem)                │
├──────────────────────┬───────────────────────────────────────┤
│                      │ zarządza                             │
│    ┌─────────────────┼─────────────────┐                    │
│    ▼                 ▼                 ▼                    │
│ StartMenu       SnakeGame        MusicPlayer               │
│  (menu)          (gra)           (muzyka)                  │
└──────────────────────────────────────────────────────────────┘
                       │
                       ▼
              ┌────────────────┐
              │   Difficulty   │
              │    (enum)      │
              └────────────────┘
```

---

### 🔷 Klasa: `Main`

**Przeznaczenie**: Punkt wejścia do aplikacji. Inicjalizuje środowisko graficzne Swing i uruchamia grę.

| Metoda | Przeznaczenie | Parametry | Zwracana wartość |
|--------|---------------|-----------|------------------|
| `main(String[] args)` | Uruchamia aplikację, tworzy okno JFrame i inicjalizuje GameManager | `args` - argumenty wiersza poleceń (nieużywane) | `void` |

**Szczegóły działania**:
- Ustawia cross-platform Look and Feel
- Tworzy okno JFrame z tytułem "Snake"
- Konfiguruje okno jako niezmienne rozmiarem
- Centruje okno na ekranie

---

### 🔷 Klasa: `GameManager`

**Przeznaczenie**: Centralny kontroler aplikacji zarządzający przepływem między ekranami, zapisem rekordów i muzyką w tle.

#### Pola klasy

| Pole | Typ | Przeznaczenie |
|------|-----|---------------|
| `frame` | `JFrame` | Główne okno aplikacji |
| `startMenu` | `StartMenu` | Referencja do menu startowego |
| `snakeGame` | `SnakeGame` | Referencja do aktualnej gry |
| `highScore` | `int` | Aktualny najlepszy wynik |
| `musicPlayer` | `MusicPlayer` | Odtwarzacz muzyki w tle |
| `HIGH_SCORE_FILE` | `String` | Nazwa pliku z rekordem (stała) |

#### Metody

| Metoda | Przeznaczenie | Parametry | Zwracana wartość |
|--------|---------------|-----------|------------------|
| `GameManager(JFrame frame)` | Konstruktor - inicjalizuje menedżer, ładuje rekord, pokazuje menu, uruchamia muzykę | `frame` - główne okno aplikacji | - |
| `loadHighScore()` | Wczytuje rekord z pliku properties | brak | `void` |
| `saveHighScore(int score)` | Zapisuje nowy rekord jeśli przekracza poprzedni | `score` - wynik do zapisania | `void` |
| `showStartMenu()` | Wyświetla menu startowe, usuwa poprzednią grę | brak | `void` |
| `startGame(Difficulty difficulty)` | Uruchamia nową grę z wybraną trudnością | `difficulty` - poziom trudności | `void` |
| `gameOver(int score)` | Obsługuje koniec gry, zapisuje rekord | `score` - końcowy wynik gracza | `void` |
| `getHighScore()` | Zwraca aktualny rekord | brak | `int` - najlepszy wynik |
| `getMusicPlayer()` | Zwraca odtwarzacz muzyki | brak | `MusicPlayer` |

---

### 🔷 Klasa: `SnakeGame`

**Przeznaczenie**: Główny komponent gry implementujący całą logikę rozgrywki. Rozszerza `JPanel` i implementuje `ActionListener`.

#### Stałe

| Stała | Typ | Wartość | Przeznaczenie |
|-------|-----|---------|---------------|
| `TILE_SIZE` | `int` | 25 | Rozmiar komórki w pikselach |
| `BOARD_WIDTH` | `int` | 20 | Szerokość planszy (komórki) |
| `BOARD_HEIGHT` | `int` | 20 | Wysokość planszy (komórki) |

#### Pola klasy

| Pole | Typ | Przeznaczenie |
|------|-----|---------------|
| `gameManager` | `GameManager` | Referencja do menedżera |
| `difficulty` | `Difficulty` | Aktualny poziom trudności |
| `snake` | `ArrayList<Point>` | Lista segmentów węża |
| `food` | `Point` | Pozycja jedzenia |
| `direction` | `String` | Kierunek ruchu ("UP"/"DOWN"/"LEFT"/"RIGHT") |
| `running` | `boolean` | Czy gra jest aktywna |
| `timer` | `Timer` | Timer do animacji |
| `random` | `Random` | Generator liczb losowych |
| `score` | `int` | Aktualny wynik |
| `customFont` | `Font` | Niestandardowa czcionka |

#### Konstruktory

| Konstruktor | Przeznaczenie | Parametry |
|-------------|---------------|-----------|
| `SnakeGame(GameManager, Difficulty)` | Tworzy grę z domyślnym generatorem losowym | `gameManager`, `difficulty` |
| `SnakeGame(GameManager, Difficulty, Random)` | Tworzy grę z własnym generatorem (do testów) | `gameManager`, `difficulty`, `random` |

#### Metody publiczne

| Metoda | Przeznaczenie | Parametry | Zwracana wartość |
|--------|---------------|-----------|------------------|
| `getScore()` | Zwraca aktualny wynik | brak | `int` |
| `isRunning()` | Sprawdza czy gra trwa | brak | `boolean` |
| `getDirection()` | Zwraca kierunek ruchu | brak | `String` |
| `getSnake()` | Zwraca kopię listy segmentów | brak | `ArrayList<Point>` |
| `getFood()` | Zwraca pozycję jedzenia | brak | `Point` |
| `getBoardWidth()` | Zwraca szerokość planszy | brak | `int` (static) |
| `getBoardHeight()` | Zwraca wysokość planszy | brak | `int` (static) |
| `setDirection(String)` | Ustawia kierunek ruchu | `direction` - nowy kierunek | `void` |
| `setFood(Point)` | Ustawia pozycję jedzenia | `food` - nowa pozycja | `void` |
| `actionPerformed(ActionEvent)` | Obsługa zdarzenia timera | `e` - zdarzenie | `void` |
| `paintComponent(Graphics)` | Rysuje grę na ekranie | `g` - kontekst graficzny | `void` |

#### Metody prywatne

| Metoda | Przeznaczenie | Parametry | Zwracana wartość |
|--------|---------------|-----------|------------------|
| `startGame()` | Inicjalizuje nową grę | brak | `void` |
| `spawnFood()` | Generuje jedzenie w losowym miejscu | brak | `void` |
| `handleKeyPress(int)` | Obsługuje naciśnięcia klawiszy | `keyCode` - kod klawisza | `void` |
| `move()` | Wykonuje jeden krok ruchu węża | brak | `void` |
| `gameOver()` | Kończy grę, zatrzymuje timer | brak | `void` |

---

### 🔷 Klasa: `StartMenu`

**Przeznaczenie**: Panel menu startowego z wyborem trudności i przyciskiem startu. Rozszerza `JPanel`.

#### Pola klasy

| Pole | Typ | Przeznaczenie |
|------|-----|---------------|
| `gameManager` | `GameManager` | Referencja do menedżera |
| `highScore` | `int` | Rekord do wyświetlenia |
| `selectedDifficulty` | `Difficulty` | Wybrany poziom trudności |
| `difficultyButtons` | `JButton[]` | Tablica przycisków trudności |

#### Metody

| Metoda | Przeznaczenie | Parametry | Zwracana wartość |
|--------|---------------|-----------|------------------|
| `StartMenu(GameManager, int)` | Konstruktor - tworzy menu | `gameManager`, `highScore` | - |
| `createComponents()` | Tworzy wszystkie elementy UI | brak | `void` (private) |
| `selectDifficulty(Difficulty)` | Zaznacza wybrany poziom | `difficulty` - wybrany poziom | `void` (private) |
| `updateDifficultyButtons()` | Aktualizuje wygląd przycisków | brak | `void` (private) |

**Elementy UI tworzone przez `createComponents()`**:
- Tytuł "SNAKE" (JLabel)
- Etykieta rekordu (JLabel)
- 4 przyciski trudności (JButton[])
- Przycisk "START GAME" (JButton)
- Etykieta instrukcji (JLabel)

---

### 🔷 Klasa: `MusicPlayer`

**Przeznaczenie**: Odtwarzacz muzyki w tle z obsługą losowego wyboru utworów.

#### Pola klasy

| Pole | Typ | Przeznaczenie |
|------|-----|---------------|
| `clip` | `Clip` | Aktualnie odtwarzany klip audio |

#### Metody

| Metoda | Przeznaczenie | Parametry | Zwracana wartość |
|--------|---------------|-----------|------------------|
| `playRandomSong(String)` | Odtwarza losowy plik .wav z folderu w pętli | `folderPath` - ścieżka do folderu z muzyką | `void` |
| `stop()` | Zatrzymuje odtwarzanie i zwalnia zasoby | brak | `void` |

**Szczegóły `playRandomSong()`**:
1. Wyszukuje folder w classpath
2. Filtruje pliki z rozszerzeniem .wav
3. Wybiera losowy plik
4. Zatrzymuje poprzednią muzykę (jeśli gra)
5. Otwiera strumień audio
6. Uruchamia odtwarzanie w pętli

---

### 🔷 Enum: `Difficulty`

**Przeznaczenie**: Definiuje poziomy trudności gry z odpowiednimi opóźnieniami timera.

#### Wartości enum

| Wartość | Opóźnienie (ms) | Nazwa wyświetlana |
|---------|-----------------|-------------------|
| `EASY` | 150 | "Easy" |
| `NORMAL` | 100 | "Normal" |
| `HARD` | 70 | "Hard" |
| `EXTREME` | 40 | "Extreme" |

#### Pola

| Pole | Typ | Przeznaczenie |
|------|-----|---------------|
| `delay` | `int` | Opóźnienie między ruchami (ms) |
| `name` | `String` | Nazwa do wyświetlania |

#### Metody

| Metoda | Przeznaczenie | Parametry | Zwracana wartość |
|--------|---------------|-----------|------------------|
| `Difficulty(int, String)` | Konstruktor enum | `delay`, `name` | - |
| `getDelay()` | Zwraca opóźnienie timera | brak | `int` - opóźnienie w ms |
| `getName()` | Zwraca nazwę poziomu | brak | `String` - nazwa |

---

### Diagram przepływu sterowania

```
┌─────────────┐     ESC      ┌─────────────┐
│  StartMenu  │◄────────────│  SnakeGame  │
│             │              │             │
│ [wybór      │   START      │ [rozgrywka] │
│  trudności] │─────────────►│             │
└─────────────┘              └──────┬──────┘
                                    │
                              kolizja│
                                    ▼
                             ┌─────────────┐
                             │  Game Over  │
                             │             │
                             │ SPACE: restart
                             │ ESC: menu   │
                             └─────────────┘
```

### Autorzy
**Team 6**: vosadcha, abondarchuk

---

## 🚀 Instrukcja uruchomienia

### Wymagania systemowe
| Wymaganie | Minimalna wersja |
|-----------|------------------|
| Java JDK | 17 lub nowsza |
| System operacyjny | Windows / macOS / Linux |
| RAM | 512 MB |
| Miejsce na dysku | 50 MB |

### Sposób 1: Uruchomienie przez Gradle (zalecane)

#### Windows (PowerShell / CMD)
```bash
# Przejdź do folderu projektu
cd projekt-snake

# Uruchom grę
./gradlew run
```

#### Linux / macOS
```bash
# Nadaj uprawnienia do uruchomienia
chmod +x gradlew

# Uruchom grę
./gradlew run
```

### Sposób 2: Uruchomienie przez IDE

#### IntelliJ IDEA
1. **File** → **Open** → wybierz folder projektu
2. Poczekaj na zaimportowanie projektu Gradle
3. Otwórz plik `src/main/java/Main.java`
4. Kliknij zieloną strzałkę ▶️ obok `public static void main`

#### Eclipse
1. **File** → **Import** → **Gradle** → **Existing Gradle Project**
2. Wybierz folder projektu
3. Kliknij prawym przyciskiem na `Main.java` → **Run As** → **Java Application**

#### VS Code
1. Otwórz folder projektu
2. Zainstaluj rozszerzenie "Extension Pack for Java"
3. Otwórz `Main.java` i kliknij **Run** nad metodą `main`

### Sposób 3: Kompilacja i uruchomienie ręczne
```bash
# Kompilacja
javac -d bin src/main/java/*.java

# Uruchomienie
java -cp bin Main
```

### Rozwiązywanie problemów

| Problem | Rozwiązanie |
|---------|-------------|
| `java: command not found` | Zainstaluj JDK 17+ i dodaj do PATH |
| `Could not find or load main class` | Uruchom z głównego folderu projektu |
| Brak dźwięku | Sprawdź czy folder `assets/music/` zawiera pliki .wav |
| Okno się nie otwiera | Upewnij się, że masz środowisko graficzne |

---

## 📖 Instrukcja obsługi aplikacji

### Uruchomienie gry

```
┌─────────────────────────────────────┐
│           MENU STARTOWE             │
│                                     │
│             🐍 SNAKE                │
│          High Score: 15             │
│                                     │
│        Select Difficulty:           │
│         ┌─────────────┐             │
│         │    Easy     │             │
│         └─────────────┘             │
│         ┌─────────────┐             │
│         │   Normal    │  ← domyślne │
│         └─────────────┘             │
│         ┌─────────────┐             │
│         │    Hard     │             │
│         └─────────────┘             │
│         ┌─────────────┐             │
│         │   Extreme   │             │
│         └─────────────┘             │
│                                     │
│         ┌─────────────┐             │
│         │ START GAME  │             │
│         └─────────────┘             │
└─────────────────────────────────────┘
```

### Krok po kroku

#### 1️⃣ Wybór poziomu trudności
- Kliknij na jeden z przycisków: **Easy**, **Normal**, **Hard** lub **Extreme**
- Wybrany poziom zostanie podświetlony na zielono
- Domyślnie zaznaczony jest poziom **Normal**

#### 2️⃣ Rozpoczęcie gry
- Kliknij przycisk **START GAME**
- Gra rozpocznie się natychmiast
- Wąż zaczyna w centrum planszy, poruszając się w prawo

#### 3️⃣ Sterowanie wężem
```
        ↑ (W)
         │
  ← (A) ─┼─ → (D)
         │
        ↓ (S)
```
- Użyj **strzałek** lub klawiszy **WASD** do zmiany kierunku
- Wąż nie może zawrócić o 180° (np. z "w prawo" na "w lewo")

#### 4️⃣ Zbieranie jedzenia
- Kieruj węża na **żółte kółka** (jedzenie)
- Każde zebrane jedzenie:
  - Wydłuża węża o 1 segment
  - Dodaje +1 do wyniku
  - Generuje nowe jedzenie w losowym miejscu

#### 5️⃣ Unikanie kolizji
Gra kończy się, gdy wąż:
- ❌ Uderzy w **ścianę** (krawędź planszy)
- ❌ Uderzy we **własne ciało**

#### 6️⃣ Ekran Game Over
```
┌─────────────────────────────────────┐
│                                     │
│           Game Over!                │
│            Score: 12                │
│        NEW HIGH SCORE!              │
│                                     │
│  Press SPACE to restart             │
│  ESC for menu                       │
│                                     │
└─────────────────────────────────────┘
```
- Naciśnij **SPACE** aby zagrać ponownie
- Naciśnij **ESC** aby wrócić do menu

#### 7️⃣ Powrót do menu
- W dowolnym momencie gry naciśnij **ESC**
- Zostaniesz przeniesiony do menu startowego
- Aktualny postęp zostanie utracony

### Wskazówki dla graczy

| Wskazówka | Opis |
|-----------|------|
| 🎯 Planuj ruchy | Myśl kilka ruchów do przodu |
| 🔄 Unikaj rogów | Łatwo utknąć w rogu planszy |
| 📏 Używaj krawędzi | Poruszaj się wzdłuż ścian gdy wąż jest długi |
| 🐢 Zacznij od Easy | Naucz się mechaniki na łatwym poziomie |
| 🏆 Bij rekordy | Rekord jest zapisywany automatycznie |

---

### Dokumentacja
Pełna dokumentacja Javadoc dostępna w folderze `docs/index.html`