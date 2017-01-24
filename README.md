<img src="./assets/imgs/steamworks_logo.png" width="480"/>


# Prototype 2017
[![](https://img.shields.io/pypi/status/Django.svg)]()
![](./assets/imgs/team.svg)

This repository stores the code for our testbed/testbench, the system used to test prototypes and autonomous for Poolesville High School's _FIRST&reg;_ Robotics Competition team, Falcons FIRST (FRC 4099).

## Code Structure
* `Looper`, `MultiLooper`
  * Allows for multithreaded application of controllers

## cRIO Information
* Team Number for Practice Board: `9001`
* Driver Station IP Address: `10.90.1.5`
* Driver Station Subnet Mask: `255.0.0.0`
* Driver Station Default Gateway: `10.90.1.1`

## Joysticks
* Testing curvature drive, based off of Team 254's drive code.

## Troubleshooting
* If the joysticks are not being recognized, reboot the Driver Station.
* If the joysticks are recognized but no output is produced, unplug and replug.
* When imaging the cRIO, change the Driver Station's IP address to `10.XX.YY.5`, with subnet mask `255.0.0.0`


