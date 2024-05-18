package io.github.uoyeng1g6.components;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimationComponentTest {
    @Test
    void testAnimationComponent() {
        AnimationComponent myAnimationComponent = new AnimationComponent(5);
        assertEquals(5, myAnimationComponent.spriteScale);
    }
}