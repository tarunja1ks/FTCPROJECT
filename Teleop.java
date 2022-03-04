/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * {@link ConceptTelemetry} illustrates various ways in which telemetry can be
 * transmitted from the robot controller to the driver station. The sample illustrates
 * numeric and text data, formatted output, and optimized evaluation of expensive-to-acquire
 * information. The telemetry {@link Telemetry#log() log} is illustrated by scrolling a poem
 * to the driver station.
 *
 * @see Telemetry
 */
@TeleOp(name = "Concept: Telemetry", group = "Concept")

public class Teleopsample extends LinearOpMode  {
    /** keeps track of the line of the poem which is to be emitted next */
    HardwarePushbot robot = new HardwarePushbot();
    public double position=0.0;
    public double lcpos=1.0;
    public boolean lock=false;
    public void runOpMode(){

        robot.init(hardwareMap);
        telemetry.addData("Say", "Running: waiting for controller input");
        telemetry.update();
        

        double left;
        double right;
        double drive;
        double turn;
        double max;
        double scoop_turn = 0.5;


        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        //robot.leftClaw.scaleRange(0.4,0.6);
        // run until the end of the match (driver presses STOP)
        
        while (opModeIsActive()) {
            boolean check=false;
            //if(dpad)
            if(gamepad1.right_bumper){
                robot.leftArm.setPower(0.6);
                
                //sleep(50);
            }
        
            else if(gamepad1.left_bumper){
                robot.leftArm.setPower(-0.6);
                //sleep(50);
            }
            else{
                robot.leftArm.setPower(0.0);
            }
            
            // servo code
            double change=0.05;
            if(gamepad1.y){
                
                
                //robot.leftClaw.setPosition(1.0);
    
                robot.leftClaw.setPosition(Math.min(robot.leftClaw.getPosition()+change,1.0));
                
            }
            else if(gamepad1.a){
                robot.leftClaw.setPosition(Math.max(robot.leftClaw.getPosition()-change,0.0));
                //robot.leftClaw.setPosition(0.0);
                lcpos-=change;
            }
            
            //else{
              //  robot.leftClaw.setPosition(0.5);
            //}
            
            
            boolean left_trig_val = false;
            boolean right_trig_val = false;
            
            
            if(gamepad1.dpad_up){
                lock=true;
            }
            if(gamepad1.dpad_down){
                lock=false;
            }
            
            
            telemetry.addData("scoop", scoop_turn);
            telemetry.addData("left_trigger",left_trig_val);
            telemetry.addData("right_trigger",right_trig_val);
            /*else{
                robot.scoop.setPosition(0.5);
            }*/
            
            
            if(gamepad1.b){
                //robot.rightClaw.setPosition(1.0);
                if(gamepad1.x){
                    robot.rightClaw.setPower(-0.50);
                }
                else{
                    robot.rightClaw.setPower(0.50);
                }
            }
            else{
                robot.rightClaw.setPower(0.0);
            }
            
            
            // Run wheels in POV mode (note: The joystick goes negative when pushed forwards, so negate it)
            // In this mode the Left stick moves the robot fwd and back, the Right stick turns left and right.
            // This way it's also easy to just drive straight, or just turn.
            
            drive=0;
            turn=0;
            if(gamepad1.left_stick_y>0.4){
                drive=-0.5;
                
                
            }
            
            else if(gamepad1.left_stick_y<-0.4){
                drive=0.5;
            }
            if(gamepad1.x){
                drive*=2;
            }
            else if(gamepad1.dpad_down){
                drive*=0.5;
            }
            else{
                drive*=1;
            }
            if(gamepad1.right_stick_x>0.4){
                turn=0.5;
            }
             else if(gamepad1.right_stick_x<-0.4){
                turn=-0.5;
            }
            
            if(gamepad1.right_trigger>=0.2){
                robot.scoop.setPosition(Math.min(robot.scoop.getPosition() + 0.1, 1.0));
            }
            else if(gamepad1.left_trigger>=0.2){
                robot.scoop.setPosition(Math.max(robot.scoop.getPosition() - 0.1, 0.0));
            }
            
            
            
            
            //drive = -gamepad1.left_stick_y;
            //turn  =  gamepad1.right_stick_x;
            
            /*if(gamepad1.dpad_down){
                telemetry.addData("Down:Pressed",gamepad1.dpad_down);
                robot.leftArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.leftArm.setTargetPosition(1100);
                if(robot.leftClaw.getPosition()>1100)
                    robot.leftArm.setPower(-0.3);
                else{
                    robot.leftArm.setPower(0.3);
                }
                
            }
            else{
                robot.leftArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                robot.leftArm.setPower(0.0);
            }
            if(gamepad1.dpad_up){
                //Trigger Tilt Platform to Carry Item Position
            }*/
            
            telemetry.addData("left_stick_y",gamepad1.left_stick_y);
            telemetry.addData("left_stick_x",gamepad1.left_stick_x);
            telemetry.addData("right_stick_y",gamepad1.right_stick_y);
            telemetry.addData("item_platform",robot.leftClaw.getPosition());
            telemetry.addData("Arm",robot.leftArm.getCurrentPosition());
            telemetry.addData("Y",gamepad1.y);
            telemetry.addData("A",gamepad1.a);
            telemetry.addData("Servoposititon",robot.leftClaw.getPosition());
            telemetry.addData("Down",gamepad1.dpad_down);
            telemetry.addData("Left",gamepad1.dpad_left);
            telemetry.addData("Right",gamepad1.dpad_right);
            telemetry.addData("Up",gamepad1.dpad_up);
            robot.touch.setMode(DigitalChannel.Mode.INPUT);
            
            if(robot.touch.getState() == true) {
                telemetry.addData("Digital Touch", "Is Not Pressed");
            } else {
                telemetry.addData("Digital Touch", "Is Pressed");
            }

            telemetry.update();
           
            telemetry.update();
            // Combine drive and turn for blended motion.
            left  = drive + turn;
            right = drive - turn;
            
            // Normalize the values so neither exceed +/- 1.0
            max = Math.max(left,right);
            if (max > 1.0)
            {
                left /= max;
                right /= max;
            }

            // Output the safe vales to the motor drives.
            //if(!gamepad1.right_bumper){
                robot.leftDrive.setPower(left);
                robot.rightDrive.setPower(right);
            //}
            if(gamepad1.right_bumper && gamepad1.left_bumper){
                break;
            }
            sleep(100);
        }
        reset();
    }
    public static double absoluteval(double i){
        if(i<0){
            return -i;
        }
        return i;
    }
    
    public void reset(){
        /*robot.leftClaw.setPosition(1.0);
        while(robot.touch.getState()==true){
            robot.leftArm.setPower(0.4);
        }
        robot.leftArm.setPower(0.0);*/
        
    }
}
