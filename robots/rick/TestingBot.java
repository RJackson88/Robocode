package rick;
import robocode.*;
import robocode.util.*;
import robocode.util.Utils;

/**
 * Created by rickjackson on 2/27/17.
 */
public class TestingBot extends AdvancedRobot {
    double previousEnergy = 100;
    int movementDirection = 1;
    int gunDirection = 1;
    
    public void run() {
        setAdjustGunForRobotTurn(true);
        turnRadarRightRadians(Double.POSITIVE_INFINITY);
        setAdjustRadarForGunTurn(true);
        setTurnGunRight(99999);
    
    }
    
    public void onScannedRobot(ScannedRobotEvent e) {
        double absoluteBearing = e.getBearingRadians() + getHeadingRadians();
        double laterVelocity = e.getVelocity()
                               * Math.sin(e.getHeadingRadians());
        double gunTurnAmount;
        
        setTurnRadarLeftRadians(getRadarTurnRemainingRadians());
        
        if (Math.random() > .9) {
            setMaxVelocity((12 * Math.random()) + 12);
        }
        
        if (e.getDistance() > 150) {
            gunTurnAmount =
                    Utils.normalRelativeAngle(absoluteBearing
                                              - getGunHeadingRadians()
                                              + laterVelocity / 22);
            setTurnGunRightRadians(gunTurnAmount);
            setTurnRightRadians(
                    Utils.normalRelativeAngle(absoluteBearing
                                              - getHeadingRadians()
                                              + laterVelocity / getVelocity()));
            setAhead((e.getDistance() - 140) * movementDirection);
            setFire(3);
        } else {
            gunTurnAmount =
                    Utils.normalRelativeAngle(absoluteBearing
                                              - getGunHeadingRadians()
                                              + laterVelocity / 15);
            setTurnGunRightRadians(gunTurnAmount);
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
