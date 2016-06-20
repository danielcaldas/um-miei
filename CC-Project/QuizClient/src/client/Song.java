package client;

import jaco.mp3.player.MP3Player;
import java.io.File;

/**
 * Classe que trata objetos que representam uma canção.
 * @author jdc
 * @version 2015.04.07
 */

public class Song {

    private String file;
    private MP3Player player;
    
    public Song(String filename) {
        player = new MP3Player();
        player.addToPlayList(new File(filename));        
    }
    
    // gets & sets
    public String getSongFile() { return this.file; }
    
    public void play() {
        player.play();                        
    }
    public void pause() { player.pause(); }
    public void stop() { player.stop(); }      
}
