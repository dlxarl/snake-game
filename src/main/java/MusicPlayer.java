import javax.sound.sampled.*;
import java.io.File;
import java.net.URL;
import java.util.Random;

/**
 * Klasa do odtwarzania muzyki w tle w grze Snake.
 * <p>
 * Zapewnia możliwość odtwarzania losowych plików audio w formacie WAV
 * z określonego folderu. Obsługuje ciągłe odtwarzanie w pętli.
 * </p>
 *
 * @author Team 6 (vosadcha, abondarchuk)
 * @version 1.0
 */
public class MusicPlayer {

    /** Obiekt do odtwarzania audio */
    private Clip clip;

    /**
     * Odtwarza losową piosenkę z określonego folderu.
     * <p>
     * Metoda wyszukuje wszystkie pliki z rozszerzeniem .wav w określonym folderze,
     * wybiera jeden losowy plik i rozpoczyna jego ciągłe odtwarzanie.
     * Jeśli już gra inna piosenka, zostanie zatrzymana.
     * </p>
     *
     * @param folderPath ścieżka do folderu z plikami muzycznymi (względem
     *                   classpath)
     * @throws RuntimeException jeśli wystąpi błąd podczas ładowania lub odtwarzania
     *                          audio
     */
    public void playRandomSong(String folderPath) {
        try {
            URL url = getClass().getClassLoader().getResource(folderPath);
            if (url == null) {
                System.out.println("Music folder not found: " + folderPath);
                return;
            }

            File folder = new File(url.toURI());
            File[] files = folder.listFiles((dir, name) -> name.endsWith(".wav"));

            if (files == null || files.length == 0) {
                System.out.println("No available tracks in " + folderPath);
                return;
            }

            File randomFile = files[new Random().nextInt(files.length)];
            System.out.println("Playing: " + randomFile.getName());

            if (clip != null && clip.isRunning()) {
                clip.stop();
                clip.close();
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(randomFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Zatrzymuje odtwarzanie muzyki.
     * <p>
     * Jeśli muzyka jest odtwarzana, metoda ją zatrzymuje i zwalnia zasoby.
     * Bezpieczne do wywołania, nawet jeśli muzyka nie jest odtwarzana.
     * </p>
     */
    public void stop() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }
}