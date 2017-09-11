package com.example.androidthings.myproject;


import com.google.android.things.pio.Gpio;

/**
 * Created by ksk on 9/7/17.
 */

public class SelectTypeKeyboard extends SimplePicoPro {
    float f0,f1,f2,f3;
    int currentRow = 0;
    char[][] rowLetters = {
            {'a', 'b', 'c', 'd'},
            {'e', 'f', 'g', 'h'},
            {'i', 'j', 'k', 'l'},
            {'m', 'n', 'o', 'p'},
            {'q', 'r', 's', 't'},
            {'u', 'v', 'w', 'x'},
            {'y', 'z', ' ', '^'}
    };


    @Override
    public void setup() {
        // analog
        analogInit();

        //set two GPIOs to input
        pinMode(GPIO_10,Gpio.DIRECTION_IN);
        setEdgeTrigger(GPIO_10,Gpio.EDGE_BOTH);

        pinMode(GPIO_32,Gpio.DIRECTION_IN);
        setEdgeTrigger(GPIO_32,Gpio.EDGE_BOTH);

        pinMode(GPIO_33,Gpio.DIRECTION_IN);
        setEdgeTrigger(GPIO_33,Gpio.EDGE_BOTH);

        pinMode(GPIO_34,Gpio.DIRECTION_IN);
        setEdgeTrigger(GPIO_34,Gpio.EDGE_BOTH);
        changeRow(0);
        changeRow(1);
        changeRow(2);

    }

    @Override
    public void loop() {

        f1 = analogRead(A1); // space
        f0=analogRead(A0); // up down

        if (f1 <= 0.05) {
            printCharacterToScreen(' ');
            println("Space: ");
        } else {
            if (f0 < 0.4) {
                println("moveup");

                currentRow = (currentRow - 1); // go down
                if (currentRow < 0) {
                    currentRow += 7;
                }
                println("------");
                println("Keys in row: ");
                for (char c: rowLetters[currentRow]) {
                    print(String.valueOf(c));
                    print(" ");
                }
                println("------");

                // Change the row
                changeRow(currentRow);
            } else if (f0 > 3) {
                println("movedown");
                currentRow = (currentRow + 1) % 7; // go up, mod the number of rows

                println("------");
                println("Keys in row: ");
                for (char c: rowLetters[currentRow]) {
                    print(String.valueOf(c));
                    print(" ");
                }
                println("------");

                // Change the row
                changeRow(currentRow);

            }

        }

        delay(100);

    }

    @Override
    void digitalEdgeEvent(Gpio pin, boolean value) {

        println("event");
        // first key
        if(pin==GPIO_10 && value==HIGH) {

            printCharacterToScreen(rowLetters[currentRow][3]);
            println("Printing: " + String.valueOf(rowLetters[currentRow][3]));
            //printCharacterToScreen('a');
        }
        // second key
        if(pin==GPIO_32 && value==HIGH) {
            printCharacterToScreen(rowLetters[currentRow][2]);
            println("Printing: " + String.valueOf(rowLetters[currentRow][2]));

            //printCharacterToScreen('b');
        }

        // third key
        if(pin==GPIO_33 && value==HIGH) {
            printCharacterToScreen(rowLetters[currentRow][1]);
            println("Printing: " + String.valueOf(rowLetters[currentRow][1]));

            //printCharacterToScreen('c');
        }

        // fourth key
        if(pin==GPIO_34 && value==HIGH) {
            printCharacterToScreen(rowLetters[currentRow][0]);
            println("Printing: " + String.valueOf(rowLetters[currentRow][0]));

            //printCharacterToScreen('d');
        }


    }
}
