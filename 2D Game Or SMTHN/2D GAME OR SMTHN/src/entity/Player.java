package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;

public class Player {

    public int x, y;
    public int speed;

    private boolean isJumping;
    private boolean isOnGround;
    private double verticalVelocity;

    // Constants for jumping and gravity
    private static final int JUMP_STRENGTH = 10;
    private static final double GRAVITY = 0.5;
    private static final int TILE_SIZE = 48;
    private static final double dashStrength = 10;

    protected GamePanel gp;
    protected KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
    }

    public void setDefaultValues() {
        x = 100;
        y = 564;
        speed = 4;
        isJumping = false;
        isOnGround = true;
        verticalVelocity = 0;
    }

    public void Dash()
    {
        if (KeyH.shiftPressed && KeyH.leftPressed)
        {
            x -= dashStrength;
        }
    }

    public void update() {

        Dash();
        
        // Horizontal movement
        if (keyH.leftPressed) {
            x -= speed;
        }
        if (keyH.rightPressed) {
            x += speed;
        }

        // Jumping
        if (keyH.spacePressed && isOnGround) {
            verticalVelocity = -JUMP_STRENGTH; // Set initial jump velocity
            isJumping = true;
            isOnGround = false;
        }

        // Update vertical position
        if (isJumping) {
            y += verticalVelocity;
            verticalVelocity += GRAVITY; // Apply gravity

            // Check for landing
            if (y >= gp.getHeight() - TILE_SIZE) {
                y = gp.getHeight() - TILE_SIZE;
                isJumping = false;
                verticalVelocity = 0;
                isOnGround = true;
            }
        } else {
            // Ensure the player stays on the ground
            if (y >= gp.getHeight() - TILE_SIZE) {
                y = gp.getHeight() - TILE_SIZE;
                isOnGround = true;
            } else {
                isOnGround = false;
            }
        }

        // Boundary checking
        if (x < 0) x = 0;
        if (x > gp.getWidth() - TILE_SIZE) x = gp.getWidth() - TILE_SIZE;
        if (y < 0) y = 0; // Prevent going above the screen
        if (y > gp.getHeight() - TILE_SIZE) y = gp.getHeight() - TILE_SIZE;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.fillRect(x, y, TILE_SIZE, TILE_SIZE);
    }
}
