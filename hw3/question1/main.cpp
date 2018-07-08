//Ibrahim Yazici 131044011 HW3 Question 1
#include <iostream>
#include <stdlib.h>
#include <time.h>
using namespace std;

#define MAX 30

void display_matrix(int matrix[][MAX], int size);
void fill_the_matrix(int matrix[][MAX]);
void nearest_neighbor_interpolation(int matrix[][MAX], int zoomingFactor);



int main()
{
    int matrix[MAX][MAX] = {0};
    fill_the_matrix(matrix);
    //Testing the functions for question1
    nearest_neighbor_interpolation(matrix, 2);
    nearest_neighbor_interpolation(matrix, 3);

    cout << "I generated a 10x10 matrix with random numbers."
         << "And zoomed in with zoomingFactor 2 and 3 respectively" << endl;
    return 0;
}

/**
 * Prints the matrix on the screen
 * @param matrix matrix to be shown
 */
void display_matrix(int matrix[][MAX], int size)
{
    for(int i = 0; i < 10 * size; ++i)
    {
        for(int j = 0; j < 10 * size; ++j)
        {
            cout << matrix[i][j] << "  ";
        }
        cout << endl;
    }
}
/**
 * It fills the matrix with random number on interval [0,9]
 * @param matrix matrix to be filled
 */
void fill_the_matrix(int matrix[][MAX])
{
    int randomNumber = 0;
    srand((unsigned int) time(NULL));

    for(int i = 0; i < 10; ++i)
        for(int j = 0; j < 10; ++j)
        {
            randomNumber = rand() % 10;
            matrix[i][j] = randomNumber;
        }
}

/**
 * Changes the matrix with zoom factor
 * @param zoomingFactor
 */
void nearest_neighbor_interpolation(int matrix[][MAX], int zoomingFactor)
{
    int result[MAX][MAX] = {0};
    cout << " Our initial 10x10 matrix " << endl;
    display_matrix(matrix, 1);
    for(int i = 0 ; i < 10; ++i)
    {
        for(int j = 0; j < 10; ++j)
            for(int k = 0; k < zoomingFactor; ++k)
                result[i*zoomingFactor][j*zoomingFactor+k] = matrix[i][j];

        for(int m = 0; m < 10 * zoomingFactor; ++m)
            for(int k = 0; k < zoomingFactor; ++k)
                result[i*zoomingFactor+k][m] = result[i*zoomingFactor][m];
    }
    cout << endl << "With zooming factor of " << zoomingFactor << endl << endl;
    display_matrix(result, zoomingFactor);
}