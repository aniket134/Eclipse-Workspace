
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) {

		int input = 0;
		int div = 0;
		int count = 0;
		int val = 0;
		int index = 0;
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String inpt = reader.readLine();
			input = Integer.parseInt(inpt.split(" ")[0]);
			div = Integer.parseInt(inpt.split(" ")[1]);
			do {
				val = Integer.parseInt(reader.readLine());
				if(val%div == 0) {
					count++;
				}
				index++;
			}
			while(index < input);
			System.out.println(count);
		}
		catch(IOException ioe) {
		}
	}

}
