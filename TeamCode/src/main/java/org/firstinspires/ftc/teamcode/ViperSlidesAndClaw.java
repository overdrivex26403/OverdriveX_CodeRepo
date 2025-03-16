package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@TeleOp(name = "ViperSlidesAndClaw", group = "Robot")
public class ViperSlidesAndClaw extends LinearOpMode {

    private DcMotor Viperslide1  = null;
    private DcMotor Viperslide2  = null;
    public Servo LeftArm;
    public Servo RightArm;
    public Servo Wrist;
    public Servo Claw;
    
    
    // private static final double MAX_VIPERSLIDE1_LENGTH = -100;
    // private static final double MAX_VIPERSLIDE2_LENGTH = 100;
    private static final double VIPERSLIDES_FULLY_COLLAPSED = 0;
    private double viperslidePosition = 0;
    private double wristPosition = 0;
    private double OPEN_CLAW = 0.8;
    private double CLOSED_CLAW = 1;
    private double armPosition = 0;
  
    

    public void moveServoGradually(Servo servoToMove, double finalPos) {
      double initPos = 0.2;
      servoToMove.setPosition(initPos);
      telemetry.addData("Initial pos", initPos);
      telemetry.update();
      double currentPos = initPos;
      double deltaToMove = finalPos - currentPos;
      
      int stepsToMove = 15;
      int sleepTime = 100;
      int counter = 0;
      while (counter < stepsToMove)
      {
        telemetry.addData("Initial pos", initPos);
        telemetry.addData("current pos", currentPos);
        telemetry.update();
        currentPos += deltaToMove/stepsToMove;
        servoToMove.setPosition(currentPos);
        counter += 1;
        sleep(sleepTime);
      }
      
    }
    
    
    public void runOpMode() {
      Viperslide1 = hardwareMap.get(DcMotor.class, "Viperslide1");
      Viperslide2 = hardwareMap.get(DcMotor.class, "Viperslide2");
      LeftArm = hardwareMap.get(Servo.class, "LeftArm");
      Wrist = hardwareMap.get(Servo.class, "Wrist");
      Claw = hardwareMap.get(Servo.class, "Claw");
     
      moveServoGradually(LeftArm, 0);
      moveServoGradually(Wrist, 0);
      Wrist.setPosition(0);
      waitForStart();
      
      
       
        // Main control loop
      while (opModeIsActive()) {
        // if (viperslidePosition > MAX_VIPERSLIDES_LENGTH){
        //   viperslidePositions = MAX_VIPERSLIDES_LENGTH;
        // }
        
        
        
        // Move arm forward and backward.
        if (gamepad1.right_stick_y < 0){
          armPosition += 0.05;
          LeftArm.setPosition(armPosition);
          telemetry.addData("armPosition",armPosition);
          telemetry.update();
        }
         if (gamepad1.right_stick_y > 0){
          armPosition -= 0.05;
          LeftArm.setPosition(armPosition);
        }
        
        
       if (gamepad1.a) {
       Claw.setPosition(CLOSED_CLAW);
       }
       
       if (gamepad1.b) {
       Claw.setPosition(OPEN_CLAW);
       }
       
        
        
        // Move wrist segment via servo
        if (gamepad1.left_stick_y < 0) {
          wristPosition +=  0.1;
          Wrist.setPosition(wristPosition);
          telemetry.addData("wristposition",wristPosition);
          telemetry.update();
        }
        if (gamepad1.left_stick_y > 0) {
          wristPosition -=  0.1;
          Wrist.setPosition(wristPosition);
          telemetry.addData("wristposition",wristPosition);
          telemetry.update();
        }
      
    
        // Move Viper slides - use a small amount of power to maintain position 
        if (gamepad1.right_trigger > 0){
          if (viperslidePosition >= 130){
            Viperslide1.setPower(-0.1);
            Viperslide2.setPower(0.1);
          } else {
            viperslidePosition += 10;
            Viperslide1.setPower(-1);
            Viperslide2.setPower(1);
          }
        } else if (gamepad1.left_trigger > 0)  {
          if (viperslidePosition > 0) {
            viperslidePosition -= 10;
            Viperslide1.setPower(1);
            Viperslide2.setPower(-1);
          } else {
            Viperslide1.setPower(-0.05);
            Viperslide2.setPower(0.05);
            viperslidePosition = 0;
          }
        } else {
          Viperslide1.setPower(-0.05);
          Viperslide2.setPower(0.05);
        }
        
        telemetry.addData("arm position",viperslidePosition);
        telemetry.update();
        sleep(50);
      }
       
    }
}
