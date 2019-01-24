/*
 * File: BlankKarel.java
 * ---------------------
 * This class is a blank one that you can change to whatever you want!
 */

import stanford.karel.*;

public class AssemblyKarel2 extends Karel {
	final int VAL_1_DIST = 5;
	final int VAL_2_DIST = 3;
	final int VAL_3_DIST = 1;
	final int OP_DIST = 7;
	final int TRACK_DIST = 8;

	//TODO: see about 1-pass ADD, SUB, etc.
	
	public void run() {
		setBeepersInBag(999999);
		while(beepersPresent()) {
			checkADD();
		}
	}
	
	private void checkADD() {
		transferBeeperNorth();
		if (noBeepersPresent()) {
			transferAllBeepersSouth();
			ADD();
		} else {
			checkINC();
		}
	}
	private void checkINC() {
		transferBeeperNorth();
		if (noBeepersPresent()) {
			transferAllBeepersSouth();
			INC();
		} else {
			checkSUB();
		}
	}
	private void checkSUB() {
		transferBeeperNorth();
		if (noBeepersPresent()) {
			transferAllBeepersSouth();
			SUB();
		} else {
			checkDEC();
		}
	}
	private void checkDEC() {
		transferBeeperNorth();
		if (noBeepersPresent()) {
			transferAllBeepersSouth();
			DEC();
		} else {
			checkMOV();
		}
	}
	private void checkMOV() {
		transferBeeperNorth();
		if (noBeepersPresent()) {
			transferAllBeepersSouth();
			MOV();
		} else {
			checkMVT();
		}
	}
	private void checkMVT() {
		transferBeeperNorth();
		if (noBeepersPresent()) {
			transferAllBeepersSouth();
			MVT();
		} else {
			checkMVF();
		}
	}
	private void checkMVF() {
		transferBeeperNorth();
		if (noBeepersPresent()) {
			transferAllBeepersSouth();
			MVF();
		} else {
			checkSTO();
		}
	}
	private void checkSTO() {
		transferBeeperNorth();
		if (noBeepersPresent()) {
			transferAllBeepersSouth();
			STO();
		} else {
			checkGET();
		}
	}
	private void checkGET() {
		transferBeeperNorth();
		if (noBeepersPresent()) {
			transferAllBeepersSouth();
			GET();
		} else {
			checkSET();
		}
	}
	private void checkSET() {
		transferBeeperNorth();
		if (noBeepersPresent()) {
			transferAllBeepersSouth();
			SET();
		} else {
			checkJMP();
		}
	}
	private void checkJMP() {
		transferBeeperNorth();
		if (noBeepersPresent()) {
			transferAllBeepersSouth();
			JMP1();
		} else {
			checkJMZ();
		}
	}
	private void checkJMZ() {
		transferBeeperNorth();
		if (noBeepersPresent()) {
			transferAllBeepersSouth();
			JMZ();
		} else {
			checkJMS();
		}
	}
	private void checkJMS() {
		transferBeeperNorth();
		if (noBeepersPresent()) {
			transferAllBeepersSouth();
			JMS();
		} else {
			checkJZS();
		}
	}
	private void checkJZS() {
		transferBeeperNorth();
		if (noBeepersPresent()) {
			transferAllBeepersSouth();
			JZS();
		} else {
			checkPSH();
		}
	}
	private void checkPSH() {
		transferBeeperNorth();
		if (noBeepersPresent()) {
			transferAllBeepersSouth();
			PSH();
		} else {
			checkPOP();
		}
	}
	private void checkPOP() {
		transferBeeperNorth();
		if (noBeepersPresent()) {
			transferAllBeepersSouth();
			POP();
		} else {
			transferAllBeepersSouth();
			checkPEK();
		}
	}
	private void checkPEK() {
		transferBeeperNorth();
		if (noBeepersPresent()) {
			transferAllBeepersSouth();
			PEK();
		} else {
			transferAllBeepersSouth();
			NUL();
		}
	}
	private void NUL() {
		move();
	}
	private void methodReset() {
		setLayerOP();
		turnToRight();
		move();
	}
	private void SET() {
		mark();
		JMP1();
		setLayerVal2();
		SETRecComp();
		findMark();
		methodReset();
	}
	private void SETRecComp() {
		if (beepersPresent()) {
			transferBeeperNorth();
			SETRecComp();
			putBeeper();
		} else {
			transferAllBeepersSouth();
			findMark();
			mark();
			setLayerVal3();
			SETDecision();
			clearAllBeepers();
		}
	}
	private void SETDecision() {
		if (beepersPresent()) {
			transferBeeperNorth();
			if (beepersPresent()) {
				transferBeeperNorth();
				if (beepersPresent()) {
					transferAllBeepersSouth();
					JMP2();
					JMP2();
					setLayerVal3();
				} else {
					transferAllBeepersSouth();
					JMP2();
					JMP2();
					setLayerVal2();
				}
			} else {
				transferAllBeepersSouth();
				JMP2();
				JMP2();
				setLayerVal1();
			}
		} else {
			JMP2();
			JMP2();
			setLayerOP();
		}
	}
	private void GET() {
		mark();
		setLayerVal3();
		GETDecision();
		findMark();
		methodReset();
	}
	private void GETRecComp() {
		if (beepersPresent()) {
			transferBeeperNorth();
			GETRecComp();
			putBeeper();
		} else {
			transferAllBeepersSouth();
			findMark();
			mark();
			JMP2();
			setLayerVal2();
			clearAllBeepers();
		}
	}
	private void GETDecision() {
		if (beepersPresent()) {
			transferBeeperNorth();
			if (beepersPresent()) {
				transferBeeperNorth();
				if (beepersPresent()) {
					transferAllBeepersSouth();
					JMP1();
					JMP2();
					setLayerVal3();
					GETRecComp();
				} else {
					transferAllBeepersSouth();
					JMP1();
					JMP2();
					setLayerVal2();
					GETRecComp();
				}
			} else {
				transferAllBeepersSouth();
				JMP1();
				JMP2();
				setLayerVal1();
				GETRecComp();
			}
		} else {
			JMP1();
			JMP2();
			setLayerOP();
			GETRecComp();
		}
	}
	private void INC() {
		mark();
		JMP1();
		setLayerVal1();
		if (beepersPresent()) {
			DECS1();
		} else {
			INCS();
		}
		findMark();
		methodReset();
	}
	private void DEC() {
		mark();
		JMP1();
		setLayerVal1();
		if (beepersPresent()) {
			INCS();
		} else {
			DECS2();
		}
		findMark();
		methodReset();
	}
	private void INCS() {		//incrementing positive or decrementing negative
		setLayerVal2();
		putBeeper();
	}
	private void DECS1() {		//incrementing negative
		setLayerVal2();
		handleSubtraction2();
	}
	private void DECS2() {		//decrementing positive
		setLayerVal2();
		handleSubtraction1();
	}
	private void POP() {
		mark();
		goToStackPtr();
		goToStackEnd();
		POPRecComp();
		findMark();
		methodReset();
	}
	private void POPRecComp(){
		if (beepersPresent()) {
			pickBeeper();
			POPRecComp();
			putBeeper();
		} else {
			goToStackPtr();
			pickBeeper();
			findMark();
			mark();
			JMP1();
			setLayerVal2();
			clearAllBeepers();
		}
	}
	private void PSH() {
		mark();
		JMP1();
		setLayerVal2();
		PSHRecComp();
		findMark();
		methodReset();
	}
	private void PEK() {
		mark();
		goToStackPtr();
		goToStackEnd();
		PEKRecComp();
		findMark();
		methodReset();
	}
	private void PEKRecComp(){
		if (beepersPresent()) {
			transferBeeperNorth();
			PEKRecComp();
			putBeeper();
		} else {
			transferAllBeepersSouth();
			findMark();
			mark();
			JMP1();
			setLayerVal2();
			clearAllBeepers();
		}
	}
	private void PSHRecComp() {
		if (beepersPresent()) {
			transferBeeperNorth();
			PSHRecComp();
			putBeeper();
		} else {
			transferAllBeepersSouth();
			goToStackPtr();
			putBeeper();
			goToStackEnd();
			clearAllBeepers();
		}
	}
	
	private void goToStackPtr() {
		setLayerVal2();
		turnToRight();
		while(frontIsClear()) {
			move();
		}
	}

	private void goToStackEnd() {
		if (beepersPresent()) {
			transferBeeperNorth();
			goToStackEnd();
			move();
		} else {
			transferAllBeepersSouth();
			setLayerVal2();
			turnToLeft();
		}
	}

	private void ADD() {
		mark();
		JMP1();
		setLayerVal1();
		if (beepersPresent()) {
			SUBIn();
		} else {
			ADDIn();
		}
		findMark();
		methodReset();
	}

	private void SUB() {
		mark();
		JMP1();
		setLayerVal1();
		if (beepersPresent()) {
			ADDIn();
		} else {
			SUBIn();
		}
		findMark();
		methodReset();
	}

	private void SUBIn() {
		findMark();
		mark();
		JMP2();
		setLayerVal1();
		if (beepersPresent()) {
			findMark();
			mark();
			JMP1();
			setLayerVal2();
			ADDS();
		} else {
			findMark();
			mark();
			JMP1();
			setLayerVal2();
			SUBS1();
		}
	}

	private void ADDIn() {
		findMark();
		mark();
		JMP2();
		setLayerVal1();
		if (beepersPresent()) {
			findMark();
			mark();
			JMP1();
			setLayerVal2();
			SUBS2();
		} else {
			findMark();
			mark();
			JMP1();
			setLayerVal2();
			ADDS();
		}
	}

	private void SUBS1() {
		if (beepersPresent()) {
			transferBeeperNorth();
			SUBS1();
			handleSubtraction1();
		} else {
			transferAllBeepersSouth();
			findMark();
			mark();
			JMP2();
			setLayerVal2();
		}
	}

	private void SUBS2() {
		if (beepersPresent()) {
			transferBeeperNorth();
			SUBS2();
			handleSubtraction2();
		} else {
			transferAllBeepersSouth();
			findMark();
			mark();
			JMP2();
			setLayerVal2();
		}
	}

	private void ADDS() {
		if (beepersPresent()) {
			transferBeeperNorth();
			ADDS();
			putBeeper();
		} else {
			transferAllBeepersSouth();
			findMark();
			mark();
			JMP2();
			setLayerVal2();
		}
	}

	private void handleSubtraction1() {		//negative + positive
		if(beepersPresent()) {
			setLayerVal1();
			if (beepersPresent()) {
				setLayerVal2();
				putBeeper();
			} else {
				setLayerVal2();
				pickBeeper();
			}
		} else {
			setLayerVal1();
			putBeeper();
			setLayerVal2();
			putBeeper();
		}
	}

	private void handleSubtraction2() {		//positive + negative
		if(beepersPresent()) {
			setLayerVal1();
			if (noBeepersPresent()) {
				setLayerVal2();
				putBeeper();
			} else {
				setLayerVal2();
				pickBeeper();
			}
		} else {
			setLayerVal1();
			pickBeeper();
			setLayerVal2();
			putBeeper();
		}
	}

	private void STO() {		//can use STO to negate via STO 1 ? 1
		mark();
		setLayerVal1();
		STORecursiveComponent();
		findMark();
		methodReset();
	}

	private void STORecursiveComponent() {
		if (beepersPresent()) {
			transferBeeperNorth();
			STORecursiveComponent();
			putBeeper();
		} else {
			transferAllBeepersSouth();
			setLayerVal3();
			if (beepersPresent()) {
				pickBeeper();
				if (beepersPresent()) {
					pickBeeper();
					if (beepersPresent()) {
						putBeeper();
						putBeeper();
						JMP2();
						setLayerVal3();
					} else {
						putBeeper();
						putBeeper();
						JMP2();
						setLayerVal2();
					}
				} else {
					putBeeper();
					JMP2();
					setLayerVal1();
				}
			} else {
				JMP2();
				setLayerOP();
			}
			clearAllBeepers();
		}
	}

	private void MVF() {
		mark();
		setLayerVal3();
		MVFDecision();
		MVFRecComp();
		findMark();
		methodReset();
	}
	private void MVFRecComp() {
		if (beepersPresent()) {
			transferBeeperNorth();
			MVFRecComp();
			putBeeper();
		} else {
			transferAllBeepersSouth();
			findMark();
			mark();
			JMP2();
			setLayerVal2();
			clearAllBeepers();
		}
	}
	private void MVFDecision() {
		if (beepersPresent()) {
			transferBeeperNorth();
			if (beepersPresent()) {
				transferBeeperNorth();
				if (beepersPresent()) {
					transferAllBeepersSouth();
					JMP1();
					setLayerVal3();
				} else {
					transferAllBeepersSouth();
					JMP1();
					setLayerVal2();
				}
			} else {
				transferAllBeepersSouth();
				JMP1();
				setLayerVal1();
			}
		} else {
			JMP1();
			setLayerOP();
		}
	}
	private void MVT() {
		mark();
		JMP1();
		setLayerVal2();
		MVTRecComp();
		findMark();
		methodReset();
	}
	private void MVTRecComp() {
		if(beepersPresent()) {
			transferBeeperNorth();
			MVTRecComp();
			putBeeper();
		} else {
			transferAllBeepersSouth();
			findMark();
			mark();
			setLayerVal3();
			MVTDecision();
			clearAllBeepers();
		}
	}
	private void MVTDecision() {
		if (beepersPresent()) {
			transferBeeperNorth();
			if (beepersPresent()) {
				transferBeeperNorth();
				if (beepersPresent()) {
					transferAllBeepersSouth();
					JMP2();
					setLayerVal3();
				} else {
					transferAllBeepersSouth();
					JMP2();
					setLayerVal2();
				}
			} else {
				transferAllBeepersSouth();
				JMP2();
				setLayerVal1();
			}
		} else {
			JMP2();
			setLayerOP();
		} 
	}
	private void MOV() {
		MOV1();
		MOV2();
		MOV3();
		methodReset();
	}

	private void MOV1() {
		mark();
		JMP1();
		setLayerVal1();
		MOVRecursiveComponent1();
		findMark();
	}

	private void MOVRecursiveComponent1() {
		if (beepersPresent()) {
			transferBeeperNorth();
			MOVRecursiveComponent1();
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

	private void MOV2() {
		mark();
		JMP1();
		setLayerVal2();
		MOVRecursiveComponent2();
		findMark();
	}

	private void MOVRecursiveComponent2() {
		if (beepersPresent()) {
			transferBeeperNorth();
			MOVRecursiveComponent2();
			putBeeper();
		} else {
			transferAllBeepersSouth();
			findMark();
			mark();
			JMP2();
			setLayerVal2();
			clearAllBeepers();
		}
	}

	private void MOV3() {
		mark();
		JMP1();
		setLayerVal3();
		MOVRecursiveComponent3();
		findMark();
	}

	private void MOVRecursiveComponent3() {
		if (beepersPresent()) {
			transferBeeperNorth();
			MOVRecursiveComponent3();
			putBeeper();
		} else {
			transferAllBeepersSouth();
			findMark();
			mark();
			JMP2();
			setLayerVal3();
			clearAllBeepers();
		}
	}

	private void JMS() {
		goToStackPtr();
		goToStackEnd();
		JMP2();
		mark();
		goToStackPtr();
		goToStackEnd();
		clearAllBeepers();
		goToStackPtr();
		pickBeeper();
		findMark();
		setLayerOP();
		turnToRight();
	}
	
	private void JZS() {
		mark();
		JMP1();
		setLayerVal2();
		if (noBeepersPresent()) {
			findMark();
			JMS();
		} else {
			findMark();
			move();
		}
		setLayerOP();
		turnToRight();
	}
	
	//ends facing right in OP
	private void JMZ() {		//target of zero-determining should be in data, ideally
		mark();
		JMP1();
		setLayerVal2();
		if (noBeepersPresent()) {
			findMark();
			JMP2();
		} else {
			findMark();
			move();
		}
		setLayerOP();
		turnToRight();
	}

	//NOTE: JNZ is deprecated in KAS2

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

	//ends facing right in op layer
	private void JMP3() {	//for internal use only
		setLayerVal3();
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
		while(noBeepersPresent()) {
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
	private void setLayerVal3() {
		setLayerInit();
		for (int i = 0; i < VAL_3_DIST; i++) {
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
		if (facingNorth()) {
			turnLeft();
		}
		if (facingEast()) {
			turnLeft();
			turnLeft();
		}
		if (facingSouth()) {
			turnLeft();
			turnLeft();
			turnLeft();
		}
	}

	private void turnToRight() {
		if (facingSouth()) {
			turnLeft();
		}
		if (facingWest()) {
			turnLeft();
			turnLeft();
		}
		if (facingNorth()) {
			turnLeft();
			turnLeft();
			turnLeft();
		}
	}

	private void turnToNorth() {
		if (facingEast()) {
			turnLeft();
		}
		if (facingSouth()) {
			turnLeft();
			turnLeft();
		}
		if (facingWest()) {
			turnLeft();
			turnLeft();
			turnLeft();
		}
	}

	private void turnToSouth() {
		if (facingWest()) {
			turnLeft();
		}
		if (facingNorth()) {
			turnLeft();
			turnLeft();
		}
		if (facingEast()) {
			turnLeft();
			turnLeft();
			turnLeft();
		}
	}
}

