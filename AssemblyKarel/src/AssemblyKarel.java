/*
 * File: BlankKarel.java
 * ---------------------
 * This class is a blank one that you can change to whatever you want!
 */

import stanford.karel.*;

public class AssemblyKarel extends Karel {
	final int VAL_1_DIST = 3;
	final int VAL_2_DIST = 1;
	final int OP_DIST = 5;
	final int TRACK_DIST = 6;
	
	//TODO: increased efficiency for certain operations: (e.g. setLayerOP, setLayerTrack, JMP) by going to the end/bottom of the screen
	//TODO: representation of negative and floating-point numbers
	
	public void run() {
		setBeepersInBag(999999);
		while(beepersPresent()) {
			checkADD();
		}
	}
	
	private void checkADD() {
		transferBeeperNorth();
		if (!beepersPresent()) {
			transferAllBeepersSouth();
			ADD();
		} else {
			checkSUB();
		}
	}
	
	private void checkSUB() {
		transferBeeperNorth();
		if (!beepersPresent()) {
			transferAllBeepersSouth();
			SUB();
		} else {
			checkMOV();
		}
	}
	
	private void checkMOV() {
		transferBeeperNorth();
		if (!beepersPresent()) {
			transferAllBeepersSouth();
			MOV();
		} else {
			checkSTO();
		}
	}
	
	private void checkSTO() {
		transferBeeperNorth();
		if (!beepersPresent()) {
			transferAllBeepersSouth();
			STO();
		} else {
			checkJMP();
		}
	}
	
	private void checkJMP() {
		transferBeeperNorth();
		if (!beepersPresent()) {
			transferAllBeepersSouth();
			JMP1();
		} else {
			checkJMZ();
		}
	}
	
	private void checkJMZ() {
		transferBeeperNorth();
		if (!beepersPresent()) {
			transferAllBeepersSouth();
			JMZ();
		} else {
			checkJNZ();
		}
	}
	
	private void checkJNZ() {
		transferBeeperNorth();
		if (!beepersPresent()) {
			transferAllBeepersSouth();
			JNZ();
		} else {
			NUL();
		}
		
	}
	
	private void NUL() {
		move();
	}
	
	private void SUB() {
		mark();
		JMP1();
		setLayerVal1();
		SUBRecursiveComponent();
		findMark();
		setLayerOP();
		turnToRight();
		move();
	}
	
	private void SUBRecursiveComponent() {
		if (beepersPresent()) {
			transferBeeperNorth();
			SUBRecursiveComponent();
			if (beepersPresent()) {
				pickBeeper();
			}
		} else {
			transferAllBeepersSouth();
			findMark();
			mark();
			JMP2();
			setLayerVal1();
		}
	}
	
	private void ADD() {
		mark();
		JMP1();
		setLayerVal1();
		ADDRecursiveComponent();
		findMark();
		setLayerOP();
		turnToRight();
		move();
	}
	
	private void ADDRecursiveComponent() {
		if (beepersPresent()) {
			transferBeeperNorth();
			ADDRecursiveComponent();
			putBeeper();
		} else {
			transferAllBeepersSouth();
			findMark();
			mark();
			JMP2();
			setLayerVal1();
		}
	}
	
	private void MOV() {
		mark();
		JMP1();
		setLayerVal1();
		MOVRecursiveComponent();
		findMark();
		setLayerOP();
		turnToRight();
		move();
	}
	
	private void MOVRecursiveComponent() {
		if (beepersPresent()) {
			transferBeeperNorth();
			MOVRecursiveComponent();
			putBeeper();
		} else {
			transferAllBeepersSouth();
			findMark();
			mark();
			JMP2();
			setLayerVal1();
			clearAllBeepers();
		}
	}
	
	private void STO() {
		mark();
		setLayerVal1();
		STORecursiveComponent();
		findMark();
		setLayerOP();
		turnToRight();
		move();
	}
	
	private void STORecursiveComponent() {
		if (beepersPresent()) {
			transferBeeperNorth();
			STORecursiveComponent();
			putBeeper();
		} else {
			transferAllBeepersSouth();
			JMP2();
			setLayerVal1();
			clearAllBeepers();
		}
	}
	
	//ends facing right in OP
	private void JMZ() {		//target of zero-determining should be in data, ideally
		mark();
		JMP1();
		setLayerVal1();
		if (!beepersPresent()) {
			findMark();
			JMP2();
		} else {
			findMark();
			move();
		}
		setLayerOP();
		turnToRight();
	}
	
	//ends facing right in OP
	private void JNZ() {
		mark();
		JMP1();
		setLayerVal1();
		if (beepersPresent()) {
			findMark();
			JMP2();
		} else {
			findMark();
			move();
		}
		setLayerOP();
		turnToRight();
	}
	
	//ends facing right in op layer
	private void JMP1() {
		setLayerVal1();
		JMPRecursiveComponent();
	}
	
	//ends facing right in op layer
	private void JMP2() {		//for internal use only
		setLayerVal2();
		JMPRecursiveComponent();
	}
	
	//ends facing right in OP layer
	private void JMPRecursiveComponent() {
		if (beepersPresent()) {
			transferBeeperNorth();
			JMPRecursiveComponent();
			move();
		} else {
			transferAllBeepersSouth();
			setLayerOP();
			turnToLeft();
			moveExtreme();
			turnToRight();
		}
	}
	
	//ends facing north one square above start
	private void transferAllBeepersSouth() {
		turnToNorth();
		move();
		while(beepersPresent()) {
			pickBeeper();
			turnToSouth();
			move();
			putBeeper();
			turnToNorth();
			move();
		}
	}
	
	//ends facing south on same layer as start
	private void transferBeeperNorth() {
		pickBeeper();
		turnToNorth();
		move();
		putBeeper();
		turnToSouth();
		move();
	
	}
	
	//ends facing right in track layer
	private void findMark() {
		setLayerTrack();
		turnToLeft();
		moveExtreme();
		turnToRight();
		while(!beepersPresent()) {
			move();
		}
		pickBeeper();
	}
	
	//ends facing south in track layer
	private void mark() {
		setLayerTrack();
		putBeeper();
	}
	
	//ends facing south
	private void setLayerVal1() {
		setLayerInit();
		for (int i = 0; i < VAL_1_DIST; i++) {
			move();
		}
	}
	
	//ends facing south
	private void setLayerVal2() {
		setLayerInit();
		for (int i = 0; i < VAL_2_DIST; i++) {
			move();
		}
	}
	
	//ends facing south
	private void setLayerOP() {
		setLayerInit();
		for (int i = 0; i < OP_DIST; i++) {
			move();
		}
	}
	
	//ends facing south
	private void setLayerTrack() {
		setLayerInit();
		for (int i = 0; i < TRACK_DIST; i++) {
			move();
		}
	}
	
	//ends on far north facing south
	private void setLayerInit() {
		turnToNorth();
		moveExtreme();
		turnToSouth();
	}
	
	//ends facing direction moved to
	private void moveExtreme() {
		while(frontIsClear()) {
			move();
		}
	}
	
	private void clearAllBeepers() {
		while(beepersPresent()) {
			pickBeeper();
		}
	}
	
	private void turnToLeft() {
		while(!facingWest()) {
			turnLeft();
		}
	}
	
	private void turnToRight() {
		while(!facingEast()) {
			turnLeft();
		}
	}
	
	private void turnToNorth() {
		while(!facingNorth()) {
			turnLeft();
		}
	}
	
	private void turnToSouth() {
		while(!facingSouth()) {
			turnLeft();
		}
	}
}