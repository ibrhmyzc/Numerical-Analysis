import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by ibrahim yazici on 4/7/2017.
 */
public class Main {
    public static void main(String[] argv) {
		 int howManyCoordinates = 5;
        double[][] coordinates = new double[howManyCoordinates][2];
        //x values
        coordinates[0][0] = 0;
        coordinates[1][0] = 1;
        coordinates[2][0] = 2;
        coordinates[3][0] = 3;
        coordinates[4][0] = 4;
        //y values
        coordinates[0][1] = 2;
        coordinates[1][1] = 2.5;
        coordinates[2][1] = 3;
        coordinates[3][1] = 2.7;
        coordinates[4][1] = 6;

		System.out.println("x values");
		for(int i = 0; i < howManyCoordinates; ++i)
			System.out.print(coordinates[i][0] + ", ");
		System.out.println("\n y values");
		for(int i = 0; i < howManyCoordinates; ++i)
			System.out.print(coordinates[i][1] + ", ");
		System.out.println();
        //Filling hte matrix
        int fitting_m = 2;
        int numberOFLines = 5;
        double[][] matrix = new double[fitting_m + 1][fitting_m + 2];
        double[] x_values = new double[fitting_m*2+1];
        double[] x_coordinates = new double[numberOFLines];
        double[] y_coordinates = new double[numberOFLines];

        //THE FOLLOWING 4 LOOPS ARE TAKEN FROM THE INTERNET. THE REST IS MINE CODE
        //THE MOST OF THE CODE WRITTEN BY ME,ONLY A SMALL PART IS FROM THE WEBSITE BELOW
        //http://www.bragitoff.com/2015/09/c-program-for-polynomial-fit-least-squares/
        //Storing the components
        for(int i = 0; i < numberOFLines; ++i)
        {
            x_coordinates[i] = coordinates[i][0];
            y_coordinates[i] = coordinates[i][1];
        }

        //step 1
        for(int  i =0; i < 2 * fitting_m + 1; ++i)
        {
            x_values[i]=0;
            for(int j = 0; j < numberOFLines; ++j)
                x_values[i]= x_values[i] + Math.pow(x_coordinates[j], i);
        }
        //step2
        for(int i = 0; i <= fitting_m; ++i)
            for(int j = 0; j <= fitting_m; ++j)
                matrix[i][j]= x_values[i+j];

        double[] tempM = new double[fitting_m+1];

        for(int i = 0; i < fitting_m+1 ; ++i)
        {
            tempM[i]=0;
            for (int j=0; j<numberOFLines; ++j)
                tempM[i]=tempM[i]+Math.pow(x_coordinates[j],i) * y_coordinates[j];
        }

        for (int i = 0; i<= fitting_m; ++i)
            matrix[i][fitting_m+1] = tempM[i];

        fitting_m = fitting_m+1;
        numberOFLines = fitting_m;
        print(matrix);

    /*Spotting the pivot*/
        System.out.println("Gauss Elimination");

        int changer = 0;
        double temp = matrix[changer][changer];
        double pivot = 0;

        for (int row = 1; row < numberOFLines; ++row) {
            temp = matrix[row - 1][row - 1];
            if (temp != 0.0) {
                //System.out.println("temp is " + temp);
                for (int i = 0; i < matrix.length + 1; ++i)
                    matrix[row - 1][i] = matrix[row - 1][i] / temp;

            }
            for (int count = 0; count < numberOFLines - row; ++count) {
                pivot = matrix[row + count][row - 1];
                if (pivot != 0.0) {
                    //System.out.println("pivot is " + pivot);
                    for (int i = 0; i < matrix.length + 1; ++i) {
                        //System.out.println("1e esitleme");
                        matrix[row + count][i] = matrix[row + count][i] - matrix[row - 1][i] * pivot;
                    }
                }
            }
            //print(matrix);
            temp = matrix[row][row];
            //System.out.println("temp is " + temp);
            for (int i = 0; i < matrix.length + 1; ++i)
                matrix[row][i] = matrix[row][i] / temp;
            //print(matrix);
        }
        print(matrix);
        double[] root = new double[numberOFLines];
        double[] myResult = new double[numberOFLines];
        System.out.println("Roots are ");
        for (int i = numberOFLines; i > 0; --i) {
            if (i == numberOFLines)
                root[i - 1] = matrix[i - 1][i] / matrix[i - 1][i - 1];
            else {
                for (int j = 0; j < numberOFLines; ++j) {
                    root[i - 1] = root[i - 1] + root[j] * matrix[i - 1][j];
                }
                root[i - 1] = -root[i - 1] + matrix[i - 1][numberOFLines];
            }
            System.out.println("x" + i + " = " + root[i - 1]);
            myResult[i-1] = root[i-1];
        }

        System.out.println("\n***The result***\n");
        for(int i = 0; i < myResult.length; ++i)
        {
            if(myResult[i] < 0)
                System.out.print(String.format("%.3g", myResult[i]) + "x^" + i + "  ");
            else
                System.out.print("+" + String.format("%.3g", myResult[i]) + "x^" + i + "  ");
        }
		System.out.println();
    }


    public static void print(double[][] matrix)
    {
        String format = "__";
        String line = "";
        for(int i = 0 ; i < 8 * matrix.length; ++i)
            format = format + " ";
        format = format + "__";
        System.out.println(format);
        for(int i = 0; i < matrix.length; ++i)
        {
            line = "|";
            for(int j = 0; j < matrix[i].length; ++j)
            {
                line = line + "  " + matrix[i][j];
            }
            line = line + "  |\n";
            System.out.print(line);
            line = "";
        }
        System.out.println(format);
    }
}
