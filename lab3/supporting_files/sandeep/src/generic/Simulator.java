package generic;

import java.io.FileInputStream;

import generic.ParsedProgram;
import generic.Operand.OperandType;
//added libraries
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.io.*;

public class Simulator {
		
	static FileInputStream inputcodeStream = null;
	static String outfile = null;
	public static void setupSimulation(String assemblyProgramFile, String objectProgramFile)
	{	
		int firstCodeAddress = ParsedProgram.parseDataSection(assemblyProgramFile);
		ParsedProgram.parseCodeSection(assemblyProgramFile, firstCodeAddress);
		outfile = objectProgramFile;
		ParsedProgram.printState();
	}
	
	public static void assemble() throws IOException
	{
		//TODO your assembler code
		Instruction cur = new Instruction();
		File yourFile = new File(outfile);
		yourFile.createNewFile();
		FileOutputStream file = new FileOutputStream(outfile);  
        DataOutputStream data = new DataOutputStream(file);  

		//writing first instruction address
		data.writeInt(ParsedProgram.mainFunctionAddress);

		//writing the static data
		for(int i=0;i<ParsedProgram.data.size();i++){
			data.writeInt(ParsedProgram.data.get(i));
		}

		for(int i=0;i<ParsedProgram.code.size();i++) {
			cur = ParsedProgram.code.get(i);
			int isa_code = cur.getOperationType().ordinal();
			isa_code = isa_code<<5;
			switch(cur.getOperationType())
			{
				//R3I type
				case add : 
				case sub : 
				case mul : 
				case div : 
				case and : 
				case or : 
				case xor : 
				case slt : 
				case sll : 
				case srl : 
				case sra :	{
								isa_code = isa_code + cur.getSourceOperand1().getValue();
								isa_code = isa_code<<5;
								isa_code = isa_code + cur.getSourceOperand2().getValue();
								isa_code = isa_code<<5;
								isa_code = isa_code + cur.getDestinationOperand().getValue();
								isa_code = isa_code<<12;
								break;
							} 
				
				//R2I type
				case addi :
				case subi :
				case muli :
				case divi : 
				case andi : 
				case ori : 
				case xori : 
				case slti : 
				case slli : 
				case srli : 
				case srai :
				case load :
				case store :	{
									isa_code = isa_code + cur.getSourceOperand1().getValue();
									isa_code = isa_code<<5;
									isa_code = isa_code + cur.getDestinationOperand().getValue();
									isa_code = isa_code<<17;
									isa_code = isa_code + cur.getSourceOperand2().getValue();
									break;
								} 
				
				case beq : 
				case bne : 
				case blt : 
				case bgt : 	{	
								isa_code = isa_code + cur.getSourceOperand1().getValue();
								isa_code = isa_code<<5;
								isa_code = isa_code + cur.getSourceOperand2().getValue();
								isa_code = isa_code<<17;
								int tmp=0;
								if(cur.getDestinationOperand().getOperandType()==OperandType.Immediate){
									tmp = cur.getDestinationOperand().getValue()-cur.getProgramCounter();
								}
								else if(cur.getDestinationOperand().getOperandType()==OperandType.Label){
									tmp = ParsedProgram.symtab.get(cur.getDestinationOperand().getLabelValue())-cur.getProgramCounter();
								}
								//System.out.println(Integer.toBinaryString(tmp));
								//System.out.println(Integer.toBinaryString(tmp&((1<<17)-1)));
								isa_code = isa_code + (tmp&((1<<17)-1));
								break;
							}
				
				//RI type :
				case jmp :		{
								int tmp=0;
								isa_code = isa_code<<22;
								if(cur.getDestinationOperand().getOperandType()==OperandType.Immediate||cur.getDestinationOperand().getOperandType()==OperandType.Register){
									tmp = cur.getDestinationOperand().getValue()-cur.getProgramCounter();
								}
								if(cur.getDestinationOperand().getOperandType()==OperandType.Label){
									tmp = ParsedProgram.symtab.get(cur.getDestinationOperand().getLabelValue())-cur.getProgramCounter();
								}
								isa_code=isa_code+(tmp&((1<<22)-1));
								break;
								}
				
				case end :	{
								isa_code = isa_code<<22;
								break;
							}
					
				default: Misc.printErrorAndExit("unknown instruction!!");
			}
			data.writeInt(isa_code);
		}
		data.flush();  
        data.close(); 
		file.close();
	}
}
	
