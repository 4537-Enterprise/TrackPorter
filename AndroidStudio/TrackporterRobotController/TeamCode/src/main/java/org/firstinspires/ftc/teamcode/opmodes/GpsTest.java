package org.firstinspires.ftc.teamcode.opmodes;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;

import androidx.core.content.ContextCompat;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp (name = "GPS Test", group = "Test")
public class GpsTest extends LinearOpMode {

    LocationManager locationManager;
    Location currentLocation;

    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart();

        while (opModeIsActive()) {
            getDeviceLocation();

            telemetry.addData("Latitude: ", currentLocation.getLatitude());
            telemetry.addData("Longitude: ", currentLocation.getLongitude());
            telemetry.addData("Heading", currentLocation.getBearing());
            telemetry.addData("Time", currentLocation.getTime());
            telemetry.addData("Speed", currentLocation.getSpeed());
            telemetry.addData("Accuracy", currentLocation.getAccuracy());
            telemetry.addData("Provider", currentLocation.getProvider());
            telemetry.update();
        }
    }

    @SuppressLint("MissingPermission")
    private void getDeviceLocation() {
        currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }
}