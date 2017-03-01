package jackson.rick;
import robocode.*;
import robocode.util.*;
import robocode.AdvancedRobot;

/**
 * Created by rickjackson on 2/27/17.
 */
public class DodgeShootBot extends AdvancedRobot {
    double previousEnergy = 100;
    int movementDirection = 1;
    int gunDirection = 1;
    
    public void run() {
        setTurnGunRight(99999);
        
        while (true) {
            ahead(100);
            turnGunRight(360);
            back(100);
            turnGunRight(360);
        }
    }
    
    public void onScannedRobot(ScannedRobotEvent e) {
        setTurnRight(e.getBearing() + 90 - 30 * movementDirection);
        double changeInEnergy = previousEnergy - e.getEnergy();
        
        if (changeInEnergy > 0 && changeInEnergy <= 3) {
            movementDirection = -movementDirection;
            setAhead((e.getDistance() / 4 + 25) * movementDirection);
        }
        
        gunDirection = -gunDirection;
        setTurnGunRight(99999 * gunDirection);
        fire(3);
        previousEnergy = e.getEnergy();
    }
    
    public void onHitByBullet(HitByBulletEvent e) {
        back(10);
    }
    
    
    public void onHitWall(HitWallEvent e) {
        movementDirection = -movementDirection;
    }
}
