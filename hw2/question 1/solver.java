import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by legen on 4/7/2017.
 */
public class solver {
    public static void main(String[] argv)
    {

        /*-----------------------*/
        /*JACOBI ELIMINATION PART*/
        /*-----------------------*/
        if(argv[3].equals("JCB"))
        {
            JCB(argv[1]);
        }

        /*----------------------*/
        /*GAUSS ELIMINATION PART*/
        /*----------------------*/
        else if(argv[3].equals("GESP"))
        {
            GESP(argv[1]);
        }
        else
        {
            System.out.println("Invalid ");
            System.out.println("Please enter like below ");
            System.out.println("name -i input.txt - m GESP");
            System.out.println("OR ");
            System.out.println("name -i input.txt -m JCB");
        }

    }
    public static void JCB(String filename)
    {
        try
        {
            BufferedReader input = new BufferedReader(new FileReader(new File(filename)));
            String line = "";
            int numberOFLines = 0;

            while((line = input.readLine()) != null)
            {
                ++numberOFLines;
            }
            input.close();

            double[][] matrix = new double[numberOFLines][];
            for(int i = 0; i < numberOFLines; ++i)
                matrix[i] = new double[numberOFLines+1];

            /*Spliting string into matrix*/
            numberOFLines = 0;
            input = new BufferedReader(new FileReader(new File(filename)));
            while((line = input.readLine()) != null)
            {
                String[] temp = line.split(",");
                for(int i = 0; i < temp.length; ++i)
                {
                    matrix[numberOFLines][i] = Double.parseDouble(temp[i]);
                }
                ++numberOFLines;
            }
            input.close();

            Double[] variables = new Double[numberOFLines];
            for(int i = 0; i < variables.length; ++i)
                variables[i] = 0.0;
            Double[] oldValue = new Double[numberOFLines];
            for(int i = 0; i < oldValue.length; ++i)
                oldValue[i] = 0.0;

            int counter = 0;
            jacobi(variables,numberOFLines,counter);
            counter++;
            while(counter ==1 || criterian(variables, oldValue))
            {
                for(int i = 0; i < numberOFLines; ++i)
                    oldValue[i] = variables[i];

                     /*Resestting for new numbers*/
                for(int i = 0; i < variables.length; ++i)
                    variables[i] = 0.0;

                    /*Formula of the variables*/
                for(int i = 0; i < numberOFLines; ++i)
                {
                    //System.out.println(counter + "-------------------");
                    for(int j = 0; j < numberOFLines; ++j)
                    {
                        if(i != j)
                        {
                            //System.out.println("x" + (i+1) + " = " + oldValue[j] + " * " + matrix[i][j]);
                            variables[i] = variables[i] - matrix[i][j] * oldValue[j];
                        }
                    }
                    //System.out.println(" new x" + (i+1) + " = " + "(" + variables[i] + " + " + matrix[i][numberOFLines] + ") / " +matrix[i][i]);
                    //System.out.println(counter + "-------------------");
                    variables[i] = (variables[i] + matrix[i][numberOFLines]) / matrix[i][i];
                    //System.out.println("= " + variables[i]);

                }
                jacobi(variables,numberOFLines,counter);

                counter++;
                if(counter > 10)
                {
                    System.out.println("After 10 iterations the criterian is not met");
                    break;
                }
            }

        }catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
    public static void GESP(String filename)
    {
        try
        {
            BufferedReader input = new BufferedReader(new FileReader(new File(filename)));
            String line = "";
            int numberOFLines = 0;

            while((line = input.readLine()) != null)
            {
                ++numberOFLines;
            }
            input.close();

            System.out.println(numberOFLines + "x" + numberOFLines + " matrix is created");
            double[][] matrix = new double[numberOFLines][];
            for(int i = 0; i < numberOFLines; ++i)
                matrix[i] = new double[numberOFLines+1];

            /*Spliting string into matrix*/
            numberOFLines = 0;
            input = new BufferedReader(new FileReader(new File(filename)));
            while((line = input.readLine()) != null)
            {
                String[] temp = line.split(",");
                for(int i = 0; i < temp.length; ++i)
                {
                    matrix[numberOFLines][i] = Double.parseDouble(temp[i]);
                }
                ++numberOFLines;
            }
            input.close();
            print(matrix);

            /*Spotting the pivot*/
            System.out.println("Gauss Elimination");

            int changer = 0;
            double temp = matrix[changer][changer];
            double pivot = 0;

            for(int row = 1; row < numberOFLines; ++row)
            {
                temp = matrix[row-1][row-1];
                if(temp != 0.0)
                {
                    //System.out.println("temp is " + temp);
                    for(int i = 0 ; i < matrix.length + 1; ++i)
                        matrix[row-1][i] = matrix[row-1][i] / temp;

                }
                for(int count = 0; count < numberOFLines - row; ++count)
                {
                    pivot = matrix[row+count][row-1];
                    if(pivot != 0.0)
                    {
                        //System.out.println("pivot is " + pivot);
                        for(int i = 0; i < matrix.length + 1; ++i)
                        {
                            //System.out.println("1e esitleme");
                            matrix[row+count][i] = matrix[row+count][i] - matrix[row-1][i]*pivot;
                        }
                    }
                }
                //print(matrix);
                temp = matrix[row][row];
                //System.out.println("temp is " + temp);
                for(int i = 0 ; i < matrix.length + 1; ++i)
                    matrix[row][i] = matrix[row][i] / temp;
                //print(matrix);
            }
            print(matrix);
            double[] root = new double[numberOFLines];
            System.out.println("Roots are ");
            for(int i = numberOFLines; i > 0; --i)
            {
                if(i == numberOFLines)
                    root[i-1] = matrix[i-1][i] / matrix[i-1][i-1];
                else
                {
                    for(int j = 0; j < numberOFLines; ++j)
                    {
                        root[i-1] = root[i-1] + root[j] * matrix[i-1][j];
                    }
                    root[i-1] = - root[i-1] + matrix[i-1][numberOFLines];
                }

                System.out.println("x" + i + " = " + root[i-1]);

            }
        }catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static boolean criterian(Double[] value, Double[] oldValue)
    {
        double result = 0;
        double temp = 0;
        double crit = 0;
        for(int i = 0; i < value.length; ++i)
        {
            if(Math.abs(result) < Math.abs(value[i]))
                result = value[i];
            if(Math.abs(temp) < Math.abs(oldValue[i]))
                temp = oldValue[i];
        }
        crit = (result-temp)/result;
        System.out.println("crit is " + Math.abs(crit));
        if(Math.abs(crit) < 0.001)
            return false;
        else
            return true;
    }
    public static void jacobi(Double[] values, int numberOFLines, int iteration)
    {
        System.out.println("\nITERATION " + iteration);
        for(int i = 0; i < numberOFLines; ++i)
            System.out.print("x" + (i+1) + " = " + values[i] + "   ");
        System.out.println();
    }
    public static boolean gauss(double[][] matrix)
    {
        int checker = 0;
        for(int i = 0 ; i < matrix.length; ++i)
           if(matrix[i][i] != 1.0)
               return false;
        return true;
    }
    public static void print(double[][] matrix)
    {
        System.out.println("Showing the matrix in a pretty way...");
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
