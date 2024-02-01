import java.util.*;

public class A51 {
	public static void main(String[] args) {
		//get inputs from user for registers
		Scanner s = new Scanner(System.in);
		System.out.print("Enter x register: ");
		String sx = s.nextLine();
		System.out.print("Enter y register: ");
		String sy = s.nextLine();
		System.out.print("Enter z register: ");
		String sz = s.nextLine();
		System.out.print("Enter number of bits for keystream: ");
		int num = s.nextInt();
		a51(sx, sy, sz, num);
		//a51("1010101010101010101", "1100110011001100110011", "11100001111000011110000");
	}
	
	//step calculator for x register
	static ArrayList<Integer> xstep(ArrayList<Integer> x) {
		int p = x.get(13) ^ x.get(16) ^ x.get(17) ^ x.get(18);		//calculate p for x
		int output = x.get(x.size()-1);								//save output just for fun
		ArrayList<Integer> newX = (ArrayList<Integer>) x.clone();	//initialize values for new array
		for (int i = x.size()-2; i >= 0; i--) {						//shift all bits except for first to the right
			newX.set(i+1, x.get(i));
		}
		newX.set(0, p);												//set first bit to p
		return newX;
	}
	
	//step calculator for y register
	static ArrayList<Integer> ystep(ArrayList<Integer> y) {
		int p = y.get(20) ^ y.get(21);								//calculate p for y
		int output = y.get(y.size()-1);								//save output just for fun
		ArrayList<Integer> newY = (ArrayList<Integer>) y.clone();	//initialize values for new array
		for (int i = y.size()-2; i >= 0; i--) {						//shift all bits except for first to the right
			newY.set(i+1, y.get(i));
		}
		newY.set(0, p);												//set first bit to p
		return newY;
	}
	
	//step calculator for z register
	static ArrayList<Integer> zstep(ArrayList<Integer> z) {
		int p = z.get(7) ^ z.get(20) ^ z.get(21) ^ z.get(22);		//calculate p for z
		int output = z.get(z.size()-1);								//save output just for fun
		ArrayList<Integer> newZ = (ArrayList<Integer>) z.clone();	//initialize values for new array
		for (int i = z.size()-2; i >= 0; i--) {						//shift all bits except for first to the right
			newZ.set(i+1, z.get(i));
		}
		newZ.set(0, p);												//set first bit to p
		return newZ;
	}
	
	
	static void a51(String sx, String sy, String sz, int num) {
		ArrayList<Integer> x = new ArrayList<>();
		ArrayList<Integer> y = new ArrayList<>();
		ArrayList<Integer> z = new ArrayList<>();
		
		//initialize arrays to integers
		//need to convert from ascii value to actual int value
		for (int i = 0; i < sx.length(); i++) {
			x.add((int) sx.charAt(i) - 48);
		}
		for (int i = 0; i < sy.length(); i++) {
			y.add((int) sy.charAt(i) - 48);
		}
		for (int i = 0; i < sz.length(); i++) {
			z.add((int) sz.charAt(i) - 48);
		}
		
		//loop through 32 times to generate the bit stream
		ArrayList<Integer> bitStream = new ArrayList<>();
		for (int i = 0; i < num; i++) {
			//getting majority
			int maj = -1;
			if (x.get(8) == 1 && y.get(10) == 1 || x.get(8) == 1 && z.get(10) == 1 || y.get(10) == 1 && z.get(10) == 1) {
				maj = 1;
			} else {
				maj = 0;
			}
			//end of getting majority
			
			//check if each register needs to step
			if (x.get(8) == maj) {
				x = xstep(x);
			}
			if (y.get(10) == maj) {
				y = ystep(y);
			}
			if (z.get(10) == maj) {
				z = zstep(z);
			}
			//add generated bit to bit stream
			bitStream.add(x.get(18) ^ y.get(21) ^ z.get(22));
		}
		
		//print bit stream
		System.out.print("bit stream: ");
		for (int i = 0; i < bitStream.size(); i++) {
			System.out.print(bitStream.get(i));
		}
		System.out.println();
		
		//print x register
		System.out.print("x register: ");
		for (int i = 0; i < x.size(); i++) {
			System.out.print(x.get(i));
		}
		System.out.println();
		
		//print y register
		System.out.print("y register: ");
		for (int i = 0; i < y.size(); i++) {
			System.out.print(y.get(i));
		}
		System.out.println();
		
		//print z register
		System.out.print("z register: ");
		for (int i = 0; i < z.size(); i++) {
			System.out.print(z.get(i));
		}
	}
	
}
