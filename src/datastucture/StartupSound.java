package datastucture;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class StartupSound {

    public void setURL(URL BTS) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(BTS);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            
        } catch (Exception e) {

        }
    }

}
