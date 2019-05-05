import java.util.ArrayList;


public class Command {
	private String name;
	private Input inputs[];
	private int paramLength;
	
	Command(String name, int paramLength, Input[] inputs)
	{
		this.name = name;
		this.inputs = inputs;
		this.paramLength = paramLength;
	}

	Command(String name)
	{
		this.name = name;
		this.inputs = null;
		this.paramLength = 0;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Input[] getInputs() {
		return inputs;
	}

	public void setInputs(Input[] inputs) {
		this.inputs = inputs;
	}

	public int getParamLength() {
		return paramLength;
	}

	public void setParamLength(int paramLength) {
		this.paramLength = paramLength;
	}

	
}
