package felix.shot;

import felix.entities.Entity;
import felix.entities.Player;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import static java.lang.Math.sqrt;

public class Shot {
    Player player;
    Entity enemyE;
    int numshot;
    long time_last_shot;
    long time_last_shot_avalible_in;
    public Shot(Player player, Entity enemyE) {
        this.player  = player;
        this.enemyE = enemyE;
        numshot =0;
    }
     public void isThereAShoot(){
         if (Mouse.isButtonDown(0)&& Sys.getTime()/1000>time_last_shot_avalible_in) {
             time_last_shot=Sys.getTime()/1000;
             time_last_shot_avalible_in=time_last_shot+1;
             numshot++;
            // System.out.println("nigga"+numshot+"   \\   "+player.getPosition().x+"              \\     "+enemyE.getPosition().x);
             double dist = distanceToEnemy();
             Vector2f vector = createVectorFromAngle(player.getRotY());
             //double xna2 = Math.sqrt(dist*dist* vector.x);
             //double yna2 = Math.sqrt(dist*dist* vector.y);

             double xvec =player.getPosition().x+ (dist*vector.y);
             double yvec = player.getPosition().z+(dist*vector.x);
             System.out.println("Position of enemy must be :   "+xvec+"   "+yvec);

             System.out.println("Position of enemy is : X:  "+enemyE.getPosition().x+"  Y : "+enemyE.getPosition().z);
             System.out.println(checkIfHitt((float) xvec,(float) yvec,enemyE.getPosition().x,enemyE.getPosition().z));



             System.out.println(vector);
         }
     }
     private double distanceToEnemy(){
       float difX = difference(player.getPosition().x,enemyE.getPosition().x);
       float difZ = difference(player.getPosition().z,enemyE.getPosition().z);
        System.out.println(sqrt((difX*difX)+(difZ*difZ)));
        return  Math.sqrt((difX*difX)+(difZ*difZ));
     }
     private float difference(float a,float b){
        float dif = a-b;

       /* if(dif<0){
            dif=dif*(-1);



        }*/
        return  dif;
     }
    private static Vector2f createVectorFromAngle(float angleDegrees) {
        // Convert the angle from degrees to radians
        float angleRadians = (float) Math.toRadians(angleDegrees);

        // Compute the vector components and return as Vector2f
        return new Vector2f((float) Math.cos(angleRadians), (float) Math.sin(angleRadians));
    }
    private boolean checkIfHitt(float expectedPositionX ,float expectedPositionY ,float realPostionX,float realPositionY){
        boolean hitt = false;

       float difX = difference(expectedPositionX,realPostionX);
       float difY = difference(expectedPositionY,realPositionY);
       if (difX<0){
           difX=difX*-1;
       }
        if (difY<0){
            difY=difY*-1;
        }
        if (difX<=3&&difY<=3){
            hitt=true;
        }
        System.out.println(hitt);
        return hitt;
    }
}
