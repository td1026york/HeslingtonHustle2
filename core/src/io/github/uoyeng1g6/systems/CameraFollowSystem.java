package io.github.uoyeng1g6.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.uoyeng1g6.components.*;
import io.github.uoyeng1g6.constants.GameConstants;
import io.github.uoyeng1g6.constants.PlayerConstants;
import io.github.uoyeng1g6.models.GameState;

public class CameraFollowSystem extends EntitySystem {

    private final ComponentMapper<FixtureComponent> fm = ComponentMapper.getFor(FixtureComponent.class);
    private final GameState gameState;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private Entity playerEntity;
    private Table[] tables;

    public CameraFollowSystem(GameState gameState, OrthographicCamera camera, Viewport viewport) {
        this.gameState = gameState;
        this.camera = camera;
        this.viewport = viewport;
        this.playerEntity = null;
    }

    public void addPlayer(Entity playerEntity) {
        this.playerEntity = playerEntity;
    }

    public void addTables(Table[] tables) {
        this.tables = tables;
    }

    public void update(float deltaTime) {

        if (gameState.interactionOverlay != null) {
            return;
        }

        // camera.position.set(PlayerConstants.START_POSITION.x, PlayerConstants.START_POSITION.y, 0);
        var fixture = fm.get(playerEntity).fixture;
        var playerPosition = fixture.getBody().getPosition();

        float x = playerPosition.x;
        float y = playerPosition.y;

        boolean CameraFollowX =
                x + GameConstants.CAMERA_WIDTH / 2 + PlayerConstants.HITBOX_RADIUS <= GameConstants.WORLD_WIDTH
                        && x - GameConstants.CAMERA_WIDTH / 2 > 0;
        boolean CameraFollowY =
                y + GameConstants.CAMERA_HEIGHT / 2 + PlayerConstants.HITBOX_RADIUS <= GameConstants.WORLD_HEIGHT
                        && y - GameConstants.CAMERA_HEIGHT / 2 > 0;

        viewport.apply();

        if (CameraFollowX) {
            camera.position.set(x + PlayerConstants.HITBOX_RADIUS, camera.position.y, 0);
        }
        if (CameraFollowY) {
            camera.position.set(camera.position.x, y + PlayerConstants.HITBOX_RADIUS, 0);
        }

        camera.update();

        for (Table t : this.tables) {
            t.setPosition(
                    camera.position.x - GameConstants.CAMERA_WIDTH / 2,
                    camera.position.y - GameConstants.CAMERA_HEIGHT / 2);
        }
    }

    public Vector2 getCameraPosition() {
        return new Vector2(camera.position.x, camera.position.y);
    }
}
