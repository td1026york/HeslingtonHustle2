package io.github.uoyeng1g6.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import io.github.uoyeng1g6.components.AnimationComponent;
import io.github.uoyeng1g6.components.FixtureComponent;
import io.github.uoyeng1g6.components.PlayerComponent;
import io.github.uoyeng1g6.constants.MoveDirection;
import io.github.uoyeng1g6.constants.PlayerConstants;

public class PlayerInputSystem extends EntitySystem {
    private final ComponentMapper<PlayerComponent> pm = ComponentMapper.getFor(PlayerComponent.class);
    private final ComponentMapper<FixtureComponent> fm = ComponentMapper.getFor(FixtureComponent.class);
    private final ComponentMapper<AnimationComponent> am = ComponentMapper.getFor(AnimationComponent.class);

    private final Vector2 velocity = new Vector2();

    private Entity playerEntity;

    public PlayerInputSystem() {}

    @Override
    public void addedToEngine(Engine engine) {
        playerEntity = engine.getEntitiesFor(
                        Family.all(PlayerComponent.class, AnimationComponent.class, FixtureComponent.class)
                                .get())
                .first();
    }

    @Override
    public void update(float deltaTime) {
        velocity.set(0, 0);

        boolean left, right, up, down;
        left = right = up = down = false;

        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
            velocity.x = -PlayerConstants.PLAYER_SPEED;
            left = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
            velocity.x = PlayerConstants.PLAYER_SPEED;
            right = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) {
            velocity.y = PlayerConstants.PLAYER_SPEED;
            up = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) {
            velocity.y = -PlayerConstants.PLAYER_SPEED;
            down = true;
        }

        if ((left && right) || (!left && !right)) {
            velocity.x = 0;
        }
        if ((up && down) || (!up && !down)) {
            velocity.y = 0;
        }

        var fixture = fm.get(playerEntity).fixture;
        fixture.getBody().setLinearVelocity(velocity);

        var ac = am.get(playerEntity);
        if (velocity.x == 0 && velocity.y == 0) {
            ac.currentAnimation = MoveDirection.STATIONARY;
        } else if (velocity.x != 0 && velocity.y == 0) {
            ac.currentAnimation = velocity.x > 0 ? MoveDirection.RIGHT : MoveDirection.LEFT;
        } else {
            ac.currentAnimation = velocity.y > 0 ? MoveDirection.UP : MoveDirection.DOWN;
        }

        pm.get(playerEntity).isInteracting = Gdx.input.isKeyJustPressed(Input.Keys.E);
    }
}