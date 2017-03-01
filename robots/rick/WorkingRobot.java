package rick;
import robocode.*;
import robocode.util.*;
import robocode.util.Utils;

/**
 * Created by rickjackson on 2/27/17.
 */
public class WorkingRobot extends AdvancedRobot {
    double previousEnergy = 100;
    int movementDirection = 1;
    int gunDirection = 1;
    
    public void run() {
        setAdjustGunForRobotTurn(true);
        turnRadarRight(Double.POSITIVE_INFINITY);
        setAdjustRadarForGunTurn(true);
        setTurnGunRight(99999);
        
        while (true) {
            ahead(100);
            back(100);
        }
    }
    
    public void onScannedRobot(ScannedRobotEvent e) {
        double absoluteBearing = e.getBearing() + getHeading();
        double laterVelocity = e.getVelocity()
                               * getHeading();
        double gunTurnAmount;
        setTurnLeft(getRadarTurnRemaining());
        
        if (Math.random() > .9) {
            setMaxVelocity((12 * Math.random()) + 12);
        }
        
        if (e.getDistance() > 150) {
            gunTurnAmount =
                    Utils.normalRelativeAngle(absoluteBearing
                                              - getGunHeading()
                                              + laterVelocity / 22);
            setTurnGunRight(gunTurnAmount);
            setTurnRight(
                    Utils.normalRelativeAngle(absoluteBearing
                                              - getHeading()
                                              + laterVelocity / getVelocity()));
            setAhead((e.getDistance() - 140) * movementDirection);
            setFire(2);
        } else {
            gunTurnAmount =
                    Utils.normalRelativeAngle(absoluteBearing
                                              - getGunHeading()
                                              + laterVelocity / 15);
            setTurnGunRight(gunTurnAmount);
            setTurnLeft(-90 - e.getBearing());
            setAhead((e.getDistance() - 140 * movementDirection));
            setFire(3);
        }
    }
    
    public void onHitByBullet(HitByBulletEvent e) {
        back(10);
    }
    
    
    public void onHitWall(HitWallEvent e) {
        movementDirection = -movementDirection;
    }
}
