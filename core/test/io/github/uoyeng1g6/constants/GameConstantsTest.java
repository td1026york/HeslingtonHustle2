package io.github.uoyeng1g6.constants;

import io.github.uoyeng1g6.GdxTestRunner;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(GdxTestRunner.class)
class GameConstantsTest {
    //check game constants are correct
    @Test
    void checkConstants(){
        assertEquals(GameConstants.WORLD_WIDTH,130);
        assertEquals(GameConstants.WORLD_HEIGHT,108);
        assertEquals(GameConstants.CAMERA_WIDTH,130/3f);
        assertEquals(GameConstants.CAMERA_HEIGHT, 108/3f);
        assertEquals(GameConstants.PIXELS_PER_TILE, 32f);
        assertEquals(GameConstants.FPS, 60);
        assertEquals(GameConstants.FONT_SIZE, 0.1f);
        assertEquals(GameConstants.MAX_ENERGY, 100);
        assertEquals(GameConstants.MAX_HOURS, 16);
        assertEquals(GameConstants.OVERLAY_SECONDS_PER_HOUR, 2);
    }

}