package entities;


import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;


public class Camera {

    private  final Vector3f  position = new Vector3f(800, 35, 402);
    private float pitch= 20;
    private float yaw;
    private float roll;

    private Player player ;
    private float playerToCamDist = 22;
    private float playerAngleA =0;

    public Camera(Player player) {
        this.player  = player;
    }

    public void move() {
    calculateZoom();
    calculatePitch();
    calculatePlayerAngleA();
    float hDist = calculateHorizontalDist();
    float vDist = calculateVerticalDist();
    calculateCamPosition(hDist,vDist);
    this.yaw = 180 - (player.getRotY() + playerAngleA);
    }


    public Vector3f getPosition() {
        return position;
    }


    public float getPitch() {
        return pitch;
    }


    public float getYaw() {
        return yaw;
    }


    public float getRoll() {
        return roll;
    }

    private void calculateZoom(){
        float zoomLevel = Mouse.getDWheel()* 0.1f;
        float playerToCamDistOG = playerToCamDist;
        playerToCamDist -= zoomLevel;
        if (playerToCamDistOG>playerToCamDist && playerToCamDist<12){
            playerToCamDist =12;
        }
   //     System.out.println(playerToCamDist);
    }

    private void calculatePitch() {

            if (Mouse.isButtonDown(1)) {
                float pitchChange = Mouse.getDY() * 0.1f;
                float pitchOG = pitch;
                pitch -= pitchChange;
                if (pitchOG>pitch && pitch<2){
                    pitch =2;
                }

            }
    }

    private void calculatePlayerAngleA(){
        if (Mouse.isButtonDown(0)){
            float angleChange = Mouse.getDX() * 0.4f;
            playerAngleA -= angleChange;
        }
    }
    private float calculateHorizontalDist(){
        return (float) (playerToCamDist * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDist(){
        return (float) (playerToCamDist * Math.sin(Math.toRadians(pitch)));
    }
    private void calculateCamPosition(float hDist, float vDist){
        float theta= player.getRotY() + playerAngleA;
        float offsetX =(float) (hDist *Math.sin(Math.toRadians((theta))));
        float offsetZ =(float) (hDist *Math.cos(Math.toRadians((theta))));
        position.x = player.getPosition().x - offsetX;
        position.z = player.getPosition().z - offsetZ;
        position.y = player.getPosition().y+ vDist+10;
     //   System.out.println(position.y);
    }


}

