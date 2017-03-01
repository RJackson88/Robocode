package jackson.rick;
import robocode.*;
import robocode.util.*;
import static robocode.util.Utils.normalRelativeAngleDegrees;
import static robocode.util.Utils.normalRelativeAngle;

/**
 * Created by rickjackson on 2/27/17.
 */
public class ThePhenomenalBot extends AdvancedRobot {
    double previousEnergy = 100;
    int movementDirection = 1;
    int gunDirection = 1;
    double changeInEnergy;
    double absoluteBearing;
    double laterVelocity;
    double gunTurnAmount;
    
    public void run() {
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForRobotTurn(true);
        turnRadarRightRadians(Double.POSITIVE_INFINITY);
        // setTurnGunRight(99999);
    }
    
    public void onScannedRobot(ScannedRobotEvent e) {
        setAbsoluteBearing(e);
        setLaterVelocity(e);
        setTurnRadarLeftRadians(getRadarTurnRemainingRadians());
        
        if (Math.random() > .9) {
            setMaxVelocity((12 * Math.random()) + 12);
        }
        
        setTurnRight(e.getBearingRadians() + (Math.PI / 3) * movementDirection);
        
        setChangeInEnergy(e);
        
        if (changeInEnergy > 0 && changeInEnergy <= 3) {
            movementDirection = -movementDirection;
            setAhead((e.getDistance() / 4 + 25) * movementDirection);
        }
        
        setGunTurnAmount(e);
        
        gunDirection = -gunDirection;
        
        // setTurnGunRight(99999 * gunDirection);
        //
        // fire(2);
        
        previousEnergy = e.getEnergy();
    }
    
    public void onHitByBullet(HitByBulletEvent e) {
        back(10);
    }
    
    
    public void onHitWall(HitWallEvent e) {
        movementDirection = -movementDirection;
    }
    
    public void setTurn(ScannedRobotEvent e) {
        setTurnRight(e.getBearing() + 90 - 30 * movementDirection);
    }
    
    public void setPreviousEnergy(ScannedRobotEvent e) {
        previousEnergy = e.getEnergy();
    }
    
    public void setChangeInEnergy(ScannedRobotEvent e) {
        changeInEnergy = previousEnergy - e.getEnergy();
    }
    
    public void setAbsoluteBearing(ScannedRobotEvent e) {
        absoluteBearing = e.getBearingRadians() + getHeadingRadians();
    }
    
    public void setLaterVelocity(ScannedRobotEvent e) {
        laterVelocity = e.getVelocity()
                        * Math.sin(e.getHeadingRadians() - absoluteBearing);
    }
    
    public void setGunTurnAmount(ScannedRobotEvent e) {
        if (e.getDistance() > 150) {
            gunTurnAmount = normalRelativeAngle(absoluteBearing -
                                                getGunHeadingRadians() +
                                                laterVelocity / 22);
            setTurnGunRightRadians(gunTurnAmount);
            setTurnRightRadians(normalRelativeAngle(absoluteBearing -
                                                    getHeadingRadians() +
                                                    laterVelocity /
                                                    getVelocity()));
            // setAhead((e.getDistance() - 140) * movementDirection);
            setFire(3);
        } else {
            gunTurnAmount = normalRelativeAngle(absoluteBearing -
                                                getGunHeadingRadians() +
                                                laterVelocity / 15);
            setTurnGunRightRadians(gunTurnAmount);
            setTurnLeft(-90 - e.getBearing());
            // setAhead((e.getDistance() - 140) * movementDirection);
            setFire(3);
        }
    }
}
