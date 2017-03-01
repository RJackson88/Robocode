package rick;
import robocode.*;

import robocode.AdvancedRobot;

/**
 * Created by rickjackson on 2/27/17.
 */
public class DodgingBot extends AdvancedRobot {
    double previousEnergy = 100;
    int movementDirection = 1;
    int gunDirection = 1;
    double changeInEnergy;
    
    public void run() {
        setTurnGunRight(99999);
    }
    
    public void onScannedRobot(ScannedRobotEvent e) {
        setTurnRight(e.getBearing() + 90 - 30 * movementDirection);
        
        changeInEnergy = previousEnergy - e.getEnergy();
        
        if (changeInEnergy > 0 && changeInEnergy <= 3) {
            movementDirection = -movementDirection;
            setAhead((e.getDistance() / 4 + 25) * movementDirection);
        }
        
        gunDirection = -gunDirection;
        
        setTurnGunRight(99999 * gunDirection);
        
        fire(2);
        
        previousEnergy = e.getEnergy();
    }
    
    public void onHitByBullet(HitByBulletEvent e) {
        back(10);
    }
    
    
    public void onHitWall(HitWallEvent e) {
        movementDirection = -movementDirection;
    }
}
