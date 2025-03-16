package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@TeleOp(name = "Wrist", group = "Robot")
public class Wrist extends LinearOpMode {
 
    public Servo Wrist;
  
    public void runOpMode() {
     
      Wrist = hardwareMap.get(Servo.class, "Wrist");
      
      waitForStart();
       
        // Main control loop
      while (opModeIsActive()) {
       Wrist.setPosition(0);
      
      }
    }   
    
}
