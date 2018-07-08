/****************************************/
/*HW1 ibrahim yazici 131044011			*/
/****************************************/
#include <iostream>
#include <cmath>
#include <iomanip>
#include <string>
#include <cstdlib>
#include <cstdio>
using namespace std;

/* f(x)=x+1-2sinPIx */
double f1(double x);

int main(int argc,char *argv[])
{
	int counter = 0;
	double tempMiddlePoint = 0, middlePoint = 0, lower = 0, upper = 0, e = 0;
	double upperBound=0, lowerBound=0,absoluteError=0,relativeError=0;
	int theorical = 0, ePow = 0;
	string criterion;

	/*Getting input from user for interval and epsilon*/
	sscanf(argv[1],"%lf",&lower);
	sscanf(argv[2],"%lf",&upper);
	criterion = argv[3];
	sscanf(argv[4],"%lf",&e);
	
	if (f1(lower) * f1(upper) >= 0)
	{
		cout << "f(" << lower << ") = " << f1(lower) << endl;
		cout << "f(" << upper << ") = " << f1(upper) << endl;
		cout << "They do not have opposite signs" << endl;
		return 1;
	}
	cout << lower << "," << upper << "," << criterion << "," << setprecision(9) << fixed << e << endl;
	/*Storing the original values*/
	upperBound = upper;
	lowerBound = lower;


	/*Error checking for criterion*/
	if (criterion != "RELATIVE_ERROR" && criterion != "ABSOLUTE_ERROR" && criterion != "DISTANCE_TO_ROOT")
	{
		cerr << "Invalid choice of criterion";
		return 1;
	}

	/*Here we find the first middlePoint*/
	middlePoint = (lower + upper) / 2;
	tempMiddlePoint = middlePoint;
	/*Printing on the screen in a readable way for the user*/
	cout << "ITERATION" << setw(20) << "ABSOLUTE ERROR" << setw(20) << "RELATIVE ERROR" << endl;


	/*SOURCE: THE ALGORITHM USED BELOW IN THIS FOR LOOP*/
	/*Numerical_methods_for_engineers_Chapra_Canale_6th_edition*/
	/*PAGE:124 FIGURE:5.5*/
	for (int i = 0; i < 100; ++i)
	{
		if (f1(lower) * f1(middlePoint) < 0)
			upper = middlePoint;
		else
			lower = middlePoint;

		counter++;

		cout << setw(2) << counter << setw(10)
			<< setw(25) << setiosflags(ios::right) << setprecision(9) << fixed << abs(middlePoint - tempMiddlePoint)
			<< setw(20) << setiosflags(ios::right) << setprecision(9)
			<< setw(25) << fixed << (abs(middlePoint - tempMiddlePoint) / abs(middlePoint)) << endl;
		
		if (criterion == "DISTANCE_TO_ROOT")
		{
			if (abs(f1(middlePoint)) < e)
				break;
		}
		else if (criterion == "ABSOLUTE_ERROR" && i>1)
		{
			absoluteError = abs(middlePoint - tempMiddlePoint);
			if (absoluteError < e)
				break;
		}
		else if (criterion == "RELATIVE_ERROR" && i>1)
		{
			relativeError = abs(middlePoint - tempMiddlePoint) / abs(middlePoint);
			if ( relativeError < e)
				break;
		}

		tempMiddlePoint = middlePoint;
		middlePoint = (lower + upper) / 2.0;
	}
	if (counter > 99)
	{
		cerr << "Number of iterations is 100";
	}
	else
	{
		cout << "Root is " << middlePoint << endl;
		while (e < 1 && e > 0)
		{
			e = e * 10;
			ePow++;
		}
		ePow = -ePow;
		theorical = (int)((log10(upperBound-lowerBound) - ePow) / log10(2)) + 1;
		cout << "Theorical number of iterations is " << theorical << endl;
	}

	return 0;
}

double f1(double x)
{
	/*I found that EulerConstant is better be stored like that*/
	const double PI = 3.14159265358;
	double result;
	result = x + 1 - 2 * sin(PI*x);
	return result;
}
