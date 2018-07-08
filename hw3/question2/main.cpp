//Ibrahim Yazici 131044011 HW3 Question2
#include <iostream>

using namespace std;

#define LENGHT 5

//it calculates question 2
double div_diff_poly(const double fx[][LENGHT]);
//helper function for the function above
double calculate(double a, double b, double c, double d);
//shows steps until we find the result
void printArray(const double arr[], int index);

int main() {
    //Initial coordinates for question 1
    double fx[LENGHT][LENGHT] =
            { {1.0, 0.7651977},
              {1.3, 0.6200860},
              {1.6, 0.4554022},
              {1.9, 0.2818186},
              {2.2, 0.1103623}
            };
    double result = div_diff_poly(fx);
    cout << "The result is: " << result << endl;
    return 0;
}
/**
 * Question2
 * @param fx out initial coordinates stored in a matrix
 * @return the result
 */
double div_diff_poly( const double fx[][LENGHT])
{
    double y[LENGHT] = {0};
    double x[LENGHT] = {0};
    int index = LENGHT, counter = 0;

    //Storing x and y values
    for(int i = 0; i < LENGHT; ++i)
    {
        x[i] = fx[i][0];
        y[i] = fx[i][1];
    }

    for(int  i = 0; i < LENGHT - 1; ++i)
    {
        for( int j = 0; j < LENGHT - 1 - i; ++j)
        {
            y[j] =  calculate(y[j+1], y[j], x[j+1+counter], x[j]);
        }
        cout << "After " << i+1 << ". iteration" << endl;
        printArray(y, --index);
        counter++;
        cout << "   ***" << endl;
    }
    return y[0];
}
/**
 * The formula
 */
double calculate(double a, double b, double c, double d)
{
    return (a-b)/(c-d);
}
/**
 * Prints the x and y on the console exreen
 * @param arr our array of results to be interpolated
 * @param index number of results
 */
void printArray(const double arr[], int index)
{
    cout << "[";
    for(int i = 0; i < index; ++i)
        if(i < index - 1)
            cout << arr[i] << ", ";
        else
            cout << arr[i] << "]" << endl;
}

