// package org.firstinspires.ftc.teamcode;

// // Odometry
// import com.qualcomm.robotcore.hardware.IMU;
// import java.util.Set;
// import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
// import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
// import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
// import java.util.Locale;

// // Math
// import java.lang.Math;
// import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
// import com.qualcomm.robotcore.util.ElapsedTime;

// // OpMode related imports
// import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
// import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

// // Motor Hardware imports
// import com.qualcomm.robotcore.hardware.DcMotor;
// import com.qualcomm.robotcore.hardware.DcMotorEx;

// // Servo Hardware imports
// import com.qualcomm.robotcore.hardware.CRServo;
// import com.qualcomm.robotcore.hardware.Servo;

// @Autonomous(name = "OverdriveX_Auto_Odo_SampleLowClose")
// public class OverdriveX_Auto_Odo_SampleLowClose extends LinearOpMode {
//     // ==== CLASS ATTRIBUTE DECLARATIONS AND INITIALIZATIONS ====
//     // Declaration of Motor and Servo member variables
//     private DcMotor                 FL                        = null; // front left   motor
//     private DcMotor                 FR                        = null; // front right  motor
//     private DcMotor                 BL                        = null; // back  left   motor
//     private DcMotor                 BR                        = null; // back  right  motor
//     private DcMotor                 arm                       = null; // arm          motor
//     private CRServo                 intake                    = null; // intake       servo
//     // Declaration and initialization of odometry position-tracking member variables
//     private GoBildaPinpointDriver   odo;
//     private static double           curr_x                    = 0;
//     private static double           curr_y                    = 0;
//     private static double           curr_heading              = 0;
//     private static double           start_x                   = 0;
//     private static double           start_y                   = 0;
//     private static double           start_heading             = 0;
//     // Declaration and initialization of arm position constants
//     private static final double     ARM_TICKS_PER_DEGREE      = 28 * 250047.0 / 4913.0 * 100.0 / 20.0 * (1 / 360.0); // ~19.79
//     private static final double     ARM_COLLAPSED             = 0;
//     private static final double     ARM_COLLECT               = 250 * ARM_TICKS_PER_DEGREE;
//     private static final double     ARM_CLEAR_BARRIER         = 230 * ARM_TICKS_PER_DEGREE;
//     private static final double     ARM_SCORE_SPECIMEN        = 160 * ARM_TICKS_PER_DEGREE;
//     private static final double     ARM_SCORE_SAMPLE_IN_LOW   = 140 * ARM_TICKS_PER_DEGREE;
//     private static final double     ARM_ATTACH_HANGING_HOOK   = 120 * ARM_TICKS_PER_DEGREE;
//     private static final double     ARM_WINCH_ROBOT           = 15  * ARM_TICKS_PER_DEGREE;
//     private static final double     ARM_FUDGE_FACTOR          = 15  * ARM_TICKS_PER_DEGREE;
//     // Declaration and initialization of intake-position constants
//     private static final double     INTAKE_COLLECT            = 1.0;
//     private static final double     INTAKE_OFF                = 0.0;
//     private static final double     INTAKE_DEPOSIT            = -1.0;
//     // Declaration and initialization of motor-power-tempering constants
//     private static final double     TEMPER                    = 0.5;
//     private static final double     ARMTEMPER                 = 0.8;
//     private static final double     ODOTIME                   = 150;

//     // ==== CLASS METHOD DECLARATIONS AND DEFINITIONS ====
//     @Override
//     public void runOpMode() {
//       setupMotorsAndServos();
//       waitForStart();         // Wait for driver to press PLAY
      
//       captureLocation();
//       initStartLocation();

      // Odometry based motion
      // if (opModeIsActive()) {
        // turnClockOdo(1.57);
        // moveRightOdo(130, 0.0, 0.0);
        //moveBackwardOdo(730, 0.0, 0.0);
        // turnAntiClockOdo(0.6);
        // raiseArm();
        // outtake();
        // collapseArm();
        // turnClockOdo(1.22);
        // initStartLocation();
        // moveRightOdo(75, 0.0, 0.0);
        // initStartLocation();
        // moveForwardOdo(60, 0.0, 0.0);
        // initStartLocation();
        // moveLeftOdo(15, 0.0, 0.0);
        // ADD CODE HERE
      // }

      // Time based motion
      // if (opModeIsActive()) {
          // go towards the basket
          // moveRight   (310, 0.0, 0.0);
          // moveForward (1200, 0.0, 0.0);
          // turnAntiClock (400);
          // stopDrivetrain();
          // //drop your load
          // raiseArm();
          // outtake();
          // collapseArm();
          // // turn and position for picking up rest of the pieces
          // turnClock   (1040);
          // moveRight   (840, 0.0,  0.0);
          // // push first piece on the floor
          // moveForward (1300, 0.0,  0.0);
          // moveLeft    (400,  0.0,  0.0);
          // moveBackward    (1675, 0.0,  0.1);
          // // push the second piece on the floor
          // moveForward (1675, -0.01,  0.0);
          // moveLeft    (470,  0.0,  0.0);
          // moveBackward    (1650, 0.0,  0.0);
          // // // push the third piece on the floor
          // // turnClock(30);
          // // moveForward (1700, 0.0,  0.0);
          // // moveLeft    (330,  0.0,  0.0);
          // // moveBackward    (1620, 0.0,  -0.1);
          // // go touch the bar
          // moveForward (1475, 0.0, 0.0);
          // turnClock(650);
          // stopDrivetrain();
          // specimenArm();
          // moveForward(650,0.0,0.0);
      // }
      // sleep(20000);
//     }
//     private void captureLocation(){
//       odo.update();
//       Pose2D pos   = odo.getPosition();
//       curr_x       = pos.getX(DistanceUnit.MM);
//       curr_y       = pos.getY(DistanceUnit.MM);
//       curr_heading = pos.getHeading(AngleUnit.DEGREES);
//     }
//     private void initStartLocation(){
//       start_x       = curr_x;
//       start_y       = curr_y;
//       start_heading = curr_heading;
//     }
//     private void reportLocation(){
//       telemetry.addData("X start: ",      start_x);
//       telemetry.addData("Y start: ",      start_y);
//       telemetry.addData("H start: ",      start_heading);
//       telemetry.addData("X curr: ",       curr_x);
//       telemetry.addData("Y curr: ",       curr_y);
//       telemetry.addData("H curr: ",       curr_heading);
//       telemetry.addData("X diff: ",       curr_x - start_x);
//       telemetry.addData("Y diff: ",       curr_y - start_y);
//       telemetry.addData("Heading diff: ", curr_heading - start_heading);
//       telemetry.update();
//     }
//     // drift
//     // if you are moving in y direction (forward or back)
//     // set drift to how much you want to go in x (right is positive)
//     // if you are moving in y direction (left or right)
//     // set drift to how much you want to go in y (forward is positive)
//     // rotationaldrift
//     // if you notice that the bot is drifting in heading as it moves
//     // you can use the rotationaldrift parameter to correct for it
//     // you might have to play around with it to get a good value

//     //******************** ODOMETRY MOVEMENT ********************
//     private void moveForwardOdo(double distance_units, double drift, double rotationaldrift){
//       while (Math.abs(curr_y - start_y) < distance_units) {
//         moveForward (ODOTIME, drift, rotationaldrift);
//         captureLocation();
//         reportLocation();
//       }
//     }
//     private void moveBackwardOdo(double distance_units, double drift, double rotationaldrift){
//       while (Math.abs(curr_y - start_y) < distance_units) {
//         moveBackward (ODOTIME, drift, rotationaldrift);
//         captureLocation();
//         reportLocation();
//       }
//     }
//     private void moveRightOdo(double distance_units, double drift, double rotationaldrift){
//       telemetry.addLine("moving right");
//       while (Math.abs(curr_x - start_x) < distance_units) {
//         telemetry.addLine("In Loop");
//         telemetry.update();
//         moveRight (ODOTIME, drift, rotationaldrift);
//         captureLocation();
//         reportLocation();
  
//       }
//     }
//     private void moveLeftOdo(double distance_units, double drift, double rotationaldrift){
//       telemetry.addLine("moving left");
//       telemetry.update();
//       while (Math.abs(curr_x - start_x) < distance_units) {
//         moveLeft (ODOTIME, drift, rotationaldrift);
//         captureLocation();
//         reportLocation();
//       }
//     }
//     private void turnClockOdo(double radians){
//       telemetry.addLine("turning");
//       telemetry.update();
//       while (Math.abs(curr_heading - start_heading) < radians) {
//         turnClock(ODOTIME);
//         captureLocation();
//         reportLocation();
//       }
//     }
//     private void turnAntiClockOdo(double radians){
//       while (Math.abs(curr_heading - start_heading) < radians) {
//         turnAntiClock(ODOTIME);
//         captureLocation();
//         reportLocation();
//       }
//     }
//     //********************** TIME-BASED MOVEMENT ********************
//     private void moveForward(double milliseconds, double drift, double rotationaldrift){
//       setMotorPower(drift, 1, rotationaldrift);
//       long naptime = (long)(milliseconds);
//       sleep(naptime);
//       stopDrivetrain();
//     }
    
//     private void moveBackward(double milliseconds, double drift, double rotationaldrift){
//       setMotorPower(drift, -1, rotationaldrift);
//       long naptime = (long)(milliseconds);
//       sleep(naptime);
//       stopDrivetrain();
//     }
    
//     private void moveRight(double milliseconds, double drift, double rotationaldrift){
//       setMotorPower(1, drift, rotationaldrift);
//       long naptime = (long)(milliseconds);
//       sleep(naptime);
//       stopDrivetrain();
//     }
    
//     private void moveLeft(double milliseconds, double drift, double rotationaldrift){
//       setMotorPower(-1, drift, rotationaldrift);
//       long naptime = (long)(milliseconds);
//       sleep(naptime);
//       stopDrivetrain();
//     }
    
//     private void turnClock(double milliseconds){
//       setMotorPower(0, 0, 1.0);
//       long naptime = (long)(milliseconds);
//       sleep(naptime);
//       stopDrivetrain();
//     }
    
//     private void turnAntiClock(double milliseconds){
//       setMotorPower(0, 0, -1.0);
//       long naptime = (long)(milliseconds);
//       sleep(naptime);
//       stopDrivetrain();
//     }
    
//     private void collapseArm(){
//       arm.setTargetPosition((int) (ARM_COLLAPSED));
//       ((DcMotorEx) arm).setVelocity(1000 * ARMTEMPER);
//       arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//       arm.setPower(ARMTEMPER);
//       while(arm.isBusy()){
//         sleep(100);
//       }
//     }
    
//     private void raiseArm(){
//       arm.setTargetPosition((int) (ARM_SCORE_SAMPLE_IN_LOW));
//       ((DcMotorEx) arm).setVelocity(1000 * ARMTEMPER);
//       arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//       arm.setPower(ARMTEMPER);
//       while(arm.isBusy()){
//         sleep(100);
//       }
//     }
    
//     private void specimenArm(){
//       arm.setTargetPosition((int) (ARM_SCORE_SPECIMEN));
//       ((DcMotorEx) arm).setVelocity(1000 * ARMTEMPER);
//       arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//       arm.setPower(1);
//       while(arm.isBusy()){
//         sleep(100);
//       }
//     }
    
//     private void outtake(){
//       intake.setPower(INTAKE_DEPOSIT);
//       sleep(1000);
//       intake.setPower(INTAKE_OFF);
//     }
    
//     private void setMotorPower(double X, double Y, double R) {
//       // Calculate motor powers using above 3 sensor values
//       double FLPower =  X + Y + R;
//       double FRPower = -X + Y - R;
//       double BLPower = -X + Y + R;
//       double BRPower =  X + Y - R;
//       // Normalize motor powers
//       double maxPower = Math.max(Math.abs(FLPower), Math.max(Math.abs(FRPower), Math.max(Math.abs(BLPower), Math.abs(BRPower))));
//       if (maxPower > 1.0) {
//           FLPower /= maxPower;
//           FRPower /= maxPower;
//           BLPower /= maxPower;
//           BRPower /= maxPower;
//       }
//       // Apply motor powers
//       FL.setPower( FLPower * TEMPER);
//       FR.setPower( FRPower * TEMPER);
//       BL.setPower( BLPower * TEMPER);
//       BR.setPower( BRPower * TEMPER);
//     }
    
//     private void stopDrivetrain(){
//       FL.setPower(0);
//       FR.setPower(0);
//       BL.setPower(0);
//       BR.setPower(0);
//     }
    
//     private void setupMotorsAndServos() {
//         // assign motors, servos and sensors
//         FL       = hardwareMap.get(DcMotor.class, "FL");
//         FR       = hardwareMap.get(DcMotor.class, "FR");
//         BL       = hardwareMap.get(DcMotor.class, "BL");
//         BR       = hardwareMap.get(DcMotor.class, "BR");
//         arm      = hardwareMap.get(DcMotor.class, "arm");
//         intake   = hardwareMap.get(CRServo.class, "intake");
//         odo      = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
 
//         // set odo
//         odo.setOffsets(-58.0, -108.0);
//         odo.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
//         odo.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.REVERSED, GoBildaPinpointDriver.EncoderDirection.REVERSED);
//         odo.resetPosAndIMU();
//         odo.recalibrateIMU();

//         // set defualt direction of rotation when powered
//         FL.setDirection(DcMotor.Direction.FORWARD);
//         FR.setDirection(DcMotor.Direction.REVERSE);
//         BL.setDirection(DcMotor.Direction.FORWARD);
//         BR.setDirection(DcMotor.Direction.FORWARD);
//         arm.setDirection(DcMotor.Direction.FORWARD);

//         // set defualt behavior when no power applied
//         FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//         FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//         BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//         BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//         arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

//         // set arm servo behaviors
//         ((DcMotorEx)arm).setCurrentAlert(5, CurrentUnit.AMPS);
//         arm.setTargetPosition(0);
//         arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

//         // set intake servo behaviors
//         intake.setPower(INTAKE_OFF);

//         // log readiness on driver station terminal
//         telemetry.addLine("motors and servos set up; robot ready");
//         telemetry.update();
//     }
// }

