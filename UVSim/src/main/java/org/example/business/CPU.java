package main.java.org.example.business;

import main.java.org.example.controller.UVSimGUI;
import org.example.data.Memory;
import org.example.business.ArithmeticOperations
import org.example.business.BranchOperations;
import java.util.Scanner;

public class CPU {
    public int programCounter;
    public Memory memory;
    private Scanner inputScanner = new Scanner(System.in);
    public int accumulator;
    private  ArithmeticOperations arithmeticOperations;
    private BranchOperations branchOperations;
    UVSimGUI uvSimGUI;

    public CPU (Memory mem, UVSimGUI uvSimGUI){
        this.memory = mem;
        this.arithmeticOperations = new ArithmeticOperations(this);
        this.branchOperations = new BranchOperations(this);
        this.uvSimGUI = uvSimGUI;
    }

    public void execute() {
        Memory memory = this.memory;
        int instruction;
        // Execution loop
        while (programCounter < 100 && programCounter >= 0) { // FC: Loop condition updated to use program counter
            instruction = memory.getData(programCounter);
            int operation = instruction / 100;
            int operand = instruction % 100;

            switch (operation) {
                case 10:
                    read(operand);
                    break;
                case 11:
                    write(operand);
                    break;
                case 20:
                    accumulator = memory.getData(operand);
                    break;
                case 21:
                    memory.setData(operand, accumulator);
                    break;
                case 43: // FC: HALT operation
                    System.out.println("Program execution halted.");
                    programCounter = -1;
                    break;
                default:
                    System.out.println("Invalid instruction: " + instruction);
            }

            boolean isMathOperation = (operation == 30 || operation == 31 || operation == 32 || operation == 33);
            if (isMathOperation) {
                switch(operation) {
                    case 30:
                        ArithmeticOperations.add(operand);
                        break;
                    case 31:
                        ArithmeticOperations.subtract(operand);
                        break;
                    case 32:
                        ArithmeticOperations.divide(operand);
                        break;
                    case 33:
                        ArithmeticOperations.multiply(operand);
                        break;
                }
            }

            boolean isBranchOperation = (operation == 40 || operation == 41 || operation == 42 || operation == 43);
            boolean shouldIncrementPC = true;

            if (isBranchOperation) {
                if (operation == 40) {
                    shouldIncrementPC = false;
                } else if (operation == 41 && accumulator < 0) {
                    shouldIncrementPC = false;
                } else if (operation == 42 && accumulator == 0) {
                    shouldIncrementPC = false;
                } else if (operation == 43) {
                    shouldIncrementPC = false;
                }
            }

            if (shouldIncrementPC) {
                programCounter++;
            }
        }
    }

    public void read(int index) {
        uvSimGUI.appendOutput("Enter 4 digit number: ");
        int digit = uvSimGUI.getInputField();
        memory.setData(index, digit);
    }

    public void write(int index) {
        uvSimGUI.appendOutput(String.valueOf(memory.getData(index)));
    }

    public void add(int operand) {
        accumulator += memory.getData(operand);
    }
    public void subtract(int operand) {
        accumulator -= memory.getData(operand);
    }
    public void divide(int operand) {
        if (memory.getData(operand) != 0) {
            accumulator /= memory.getData(operand);
        } else {
            throw new ArithmeticException("Division by zero");
        }
    }
    public void multiply(int operand) {
        accumulator *= memory.getData(operand);
    }

    //public void add(int operand) {
    //    accumulator += memory.getData(operand);
    //}
    //public void subtract(int operand) {
    //    accumulator -= memory.getData(operand);
    //}
    //public void divide(int operand) {
    //    if (memory.getData(operand) != 0) {
    //        accumulator /= memory.getData(operand);
    //    } else {
    //        throw new ArithmeticException("Division by zero");
    //    }
    //}
    //public void multiply(int operand) {
    //    accumulator *= memory.getData(operand);
    //}
}

//int instruction;
//// Execution loop
//        while (uvSim.programCounter < 100) { // FC: Loop condition updated to use program counter
//instruction = uvSim.memory[uvSim.programCounter];
//int operation = instruction / 100;
//int operand = instruction % 100;
//
//            switch (operation) {
//        case 10:
//        uvSim.read(operand);
//                    break;
//                            case 11:
//                            uvSim.write(operand);
//                    break;
//                            case 20:
//                            uvSim.load(operand);
//                    break;
//                            case 21:
//                            uvSim.store(operand);
//                    break;
//                            case 30:
//                            uvSim.add(operand);
//                    break;
//                            case 31:
//                            uvSim.subtract(operand);
//                    break;
//                            case 32:
//                            uvSim.divide(operand);
//                    break;
//                            case 33:
//                            uvSim.multiply(operand);
//                    break;
//                            case 40: // FC: BRANCH operation
//                            uvSim.branch(operand);
//                    break;
//                            case 41: // FC: BRANCHNEG operation
//                            uvSim.branchNeg(operand);
//                    break;
//                            case 42: // FC: BRANCHZERO operation
//                            uvSim.branchZero(operand);
//                    break;
//                            case 43: // FC: HALT operation
//                            uvSim.halt();
//                    break;
//default:
//        System.out.println("Invalid instruction: " + instruction);
//            }
//
//boolean isBranchOperation = (operation == 40 || operation == 41 || operation == 42 || operation == 43);
//boolean shouldIncrementPC = true;
//
//            if (isBranchOperation) {
//        if (operation == 40) {
//shouldIncrementPC = false;
//        } else if (operation == 41 && uvSim.accumulator < 0) {
//shouldIncrementPC = false;
//        } else if (operation == 42 && uvSim.accumulator == 0) {
//shouldIncrementPC = false;
//        } else if (operation == 43) {
//shouldIncrementPC = false;
//        }
//        }
//
//        if (shouldIncrementPC) {
//uvSim.programCounter++;
//        }
//
//        // FC: If halted, break the loop
//        if (uvSim.programCounter == -1) {
//        break;
//        }
//        }