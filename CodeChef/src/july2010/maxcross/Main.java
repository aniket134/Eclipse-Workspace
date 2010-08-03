package july2010.maxcross;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static char[][] matrix = new char[5000][];
	
	public static int getMax(int a, int b, int c, int d) {
		if(a > b) {
			if(a > c) {
				if(a > d) {
					return a;
				}
				else {
					return d;
				}
			}
			else {
				if(c > d) {
					return c;
				}
				else {
					return d;
				}
			}
		}
		else {
			if(b > c) {
				if(b > d) {
					return b;
				}
				else {
					return d;
				}
			}
			else {
				if(c > d) {
					return c;
				}
				else {
					return d;
				}
			}
		}
	}

	public static void main(String[] args) {
		int noOfCases = 0;
		int index = 0;
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String inpt = reader.readLine();
			noOfCases = Integer.parseInt(inpt);
			do {
				matrix[index] = new char[noOfCases];
				String s = reader.readLine();
				for (int i = 0; i < s.length(); i++) {
					matrix[index][i] = s.charAt(i);
				}
				index++;
			}
			while(index < noOfCases);
		}
		catch(IOException ioe) {
			System.out.println(ioe);
			ioe.printStackTrace();
		}
		for(int i = 0; i < noOfCases; i++) {
			String out = "";
			for (int j = 0; j < noOfCases; j++) {
				char a = matrix[i][j];
				if(a == '.') {
					out += "0 ";
					continue;
				}
				int countA = 1;
				int countB = 1;
				int countC = 1;
				int countD = 1;
				int countE = 1;
				int countF = 1;
				int countG = 1;
				int countH = 1;
				if(i != 0) {
					if(matrix[i - 1][j] == a) {
						countA++;
						for(int k = i - 2; k >= 0; k--) {
							if(matrix[k][j] == a) {
								countA++;
							}
							else {
								break;
							}
						}
					}
					if(j != noOfCases - 1) {
						if(matrix[i - 1][j + 1] == a) {
							countE++;
							int l = j+2;
							for(int k = i-2; k >= 0 && l<=noOfCases-1; k--,l++) {
								if(matrix[k][l] == a) {
									countE++;
								}
								else {
									break;
								}
							}
						}
					}
				}
				if(i != noOfCases - 1) {
					if(matrix[i + 1][j] == a) {
						countB++;
						for(int k = i + 2; k <= noOfCases - 1; k++) {
							if(matrix[k][j] == a) {
								countB++;
							}
							else {
								break;
							}
						}
					}
					if(j != 0) {
						if(matrix[i + 1][j - 1] == a) {
							countF++;
							int l = j-2;
							for(int k = i+2; l >= 0 && k<=noOfCases-1; l--,k++) {
								if(matrix[k][l] == a) {
									countF++;
								}
								else {
									break;
								}
							}
						}
					}
				}
				if(j != 0) {
					if(matrix[i][j - 1] == a) {
						countC++;
						for(int k = j - 2; k >= 0; k--) {
							if(matrix[i][k] == a) {
								countC++;
							}
							else {
								break;
							}
						}
					}
					if(i != 0) {
						if(matrix[i - 1][j - 1] == a) {
							countG++;
							int l = j-2;
							for(int k = i-2; l >= 0 && k>=0; l--,k--) {
								if(matrix[k][l] == a) {
									countG++;
								}
								else {
									break;
								}
							}
						}
					}
				}
				if(j != noOfCases - 1) {
					if(matrix[i][j + 1] == a) {
						countD++;
						for(int k = j + 2; k <= noOfCases - 1; k++) {
							if(matrix[i][k] == a) {
								countD++;
							}
							else {
								break;
							}
						}
					}
					if(i != noOfCases - 1) {
						if(matrix[i + 1][j + 1] == a) {
							countH++;
							int l = j+2;
							for(int k = i+2; k<=noOfCases-1 && l<=noOfCases-1; k++,l++) {
								if(matrix[k][l] == a) {
									countH++;
								}
								else {
									break;
								}
							}
						}
					}
				}
				int a1 =  getMax(countA+countB-1, countC+countD-1, countE+countF-1, countG+countH-1);
				/*System.out.println("for:i:"+i+":j:"+j+":countA:"+countA+":countB" +
						":"+countB+":countC:"+countC+":countD:"+countD+":countE" +
						":"+countE+":countF:"+countF+":countG:"+countG+":countH" +
						":"+countH+":a1:"+a1);*/
				out += a1 + " ";
			}
			System.out.println(out.substring(0, out.length()-1));
		}
	}

}
