package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


//DECLARE TELEOP
@TeleOp(name="Teleop Driving", group="Linear Opmode")  // @Autonomous(...) is the other common choice
public class BasicTankDrive extends LinearOpMode {


    //TIMEOUT
    int timeout = 0; //Timeout flag, to make sure that inversion does not happen over and over

    //INVERSION
    int inverted = 1; //Inversion flag, to make the motors run inverted on the press of a button

    //TIMER
    private ElapsedTime runtime = new ElapsedTime(); //Timer

    //DRIVING
    DcMotor leftMotorDriving = null; //This is the left driving motor
    DcMotor rightMotorDriving = null; //This is the right driving motor

    //CAPPING
    DcMotor capBallMotor1 = null; //This is one of the cap ball motors
    DcMotor capBallMotor2 = null; //This is one of the cap ball motors
    Servos leftServoCapBall = null; //This is one of the cap ball SERVOS
    Servos rightServoCapBall = null; //This is one of the cap ball SERVOS

    //SHOOTING
    DcMotor ballShooterShooterMotor = null; //This is the ball shooter's shooter motor
    DcMotor ballShooterLiftMotor = null; //This is the ball shooter's lifter motor
    DcMotor ballShooterIntakeMotor = null; //This is the ball shooter's intake box motor

    //BEACONBUTTONPUSHERS
    Servos leftServoBeacon; //This is the left beacon button pusher
    Servos rightServoBeacon; //This is the right beacon button pusher



    @Override
    public void runOpMode() throws InterruptedException {

        //DELARE TELEMETRY
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //DRIVING MOTORS
        //set variables to motors
        leftMotorDriving  = hardwareMap.dcMotor.get("left motor"); //This is the left driving motor
        rightMotorDriving = hardwareMap.dcMotor.get("right motor"); //This is the right driving motor
        //set variables to mode
        leftMotorDriving.setDirection((DcMotor.Direction.FORWARD)); //This is the left motor driving being set to Forwards mode
        rightMotorDriving.setDirection((DcMotor.Direction.FORWARD)); //This is the right driving motor being set to Forwards mode

        //CAPPING MOTORS
        //set up capping motors
        capBallMotor1 = hardwareMap.dcMotor.get("cap ball motor 1"); //This is a capball motor
        capBallMotor2 = hardwareMap.dcMotor.get("cap ball motor 2"); //This is a capball motor
        //set up capping servos
        //TODO: Set the positions to be correct for default
        leftServoCapBall = new Servos("left servo", 0, 150, 0); //This is the left capball servo
        rightServoCapBall = new Servos("right servo", 0, 150, 0); //This is the right capball servo
        //set variables to mode
        capBallMotor1.setDirection((DcMotor.Direction.FORWARD)); //This is the first capball motor being set to forwards mode
        capBallMotor2.setDirection((DcMotor.Direction.FORWARD)); //This is the second capball motor being set to forwards mode

        //BALL SHOOTER MOTORS
        //set ball shooter motors to be dcMotor variables
        ballShooterShooterMotor = hardwareMap.dcMotor.get("ball shooter shooter motor"); //This is the ball shooter's shooter motor
        ballShooterLiftMotor = hardwareMap.dcMotor.get("ball shooter lifter motor"); //Ball shooter lifter motor
        ballShooterIntakeMotor = hardwareMap.dcMotor.get("ball shooter intake motor"); //Ball shooter intake motor
        //set ball shooter motors to be in the forwards mode
        ballShooterShooterMotor.setDirection((DcMotor.Direction.FORWARD)); //This is the ball shooter's SHOOTER motor being set to Forwards mode
        ballShooterLiftMotor.setDirection((DcMotor.Direction.FORWARD)); //This is the ball shooter's LIFT motor being set to Forwards mode
        ballShooterIntakeMotor.setDirection((DcMotor.Direction.FORWARD)); //This is the ball shooter's INTAKE motor being set to Forwards mode

        //BEACONBUTTONPUSHERS
        //set beaconbuttonpushers to be their variables
        //TODO: Set the positions to be correct for default
        leftServoBeacon = new Servos("beacon left servo", 0, 150, 0); //This is the left servo on the beacon button pusher
        rightServoBeacon = new Servos("beacon right servo", 0, 150, 0); //This is the right servo on the beacon button pusher

        //OPMODE CODE
        waitForStart();  //Wait for the game to start
        runtime.reset(); //Reset the timer before it is supposed to start

        //OPMODE RUNNING
        while (opModeIsActive()) { //While opmode is running

            //DRIVING CONTROL CODE
            //This will make the robot move
            leftMotorDriving.setPower(-inverted*gamepad1.left_stick_y);  //Set the left motor's power to be that of the gamepad's left stick
            rightMotorDriving.setPower(inverted*gamepad1.right_stick_y); //Set the right motor's power to be that of the gamepad's right stick

            //Toggle inversion if the invert button is pushed and the timeout is in the area that it needs to be in so that it can not be spammed.
            if (gamepad1.x&&timeout>=75) {         //If the inversion button is pushed
                inverted = -inverted; //make the inverted flag the oppposite of the inversion flag. INVERTS
                timeout=0; //This resets the timeout
            }
            //This line increments the timeout for the inversion mode
            timeout++;


            //BEACONBUTTONPUSHER CONTROL CODE
            //If the left servo toggle (the left trigger) is pushed
            if (gamepad1.left_trigger>0.4) {
              //toggle the servo's position
              leftServoBeacon.togglePosition();
            }

            //If the right servo toggle (the right trigger) is pushed
            if (gamepad1.right_trigger>0.4) {
              //toggle the servo's position
              rightServoBeacon.togglePosition();
            }


            //OPMODE DEFAULT CODE
            idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
            //Datalog
            telemetry.addData("Status", "Run Time: " + runtime.toString()); //Tell the user how long the code has been running
            telemetry.update();                                             //Pushes to terminal

        }
    }
}
