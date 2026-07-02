package day1;

public class Zigzag {

	void zigzag (int x) {
        int counter = 0;
        for(int i = 0; i<x; i++){
            for(int j = 0; j<x; j++){
                if (i % 2 == 0) {
                    counter++;
                    System.out.print( counter + "\t");
                } else {
                System.out.print( counter+ "\t");
                counter--;
                }
            }
        counter += x;
        System.out.println("");
        }
	}
}
