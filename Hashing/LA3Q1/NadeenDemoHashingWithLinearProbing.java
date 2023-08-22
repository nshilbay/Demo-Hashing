package LA3Q1;
import java.util.Scanner;

public class NadeenDemoHashingWithLinearProbing {

    //public static variables
    public static int items; //hold the value of the total number of data items to be added
    public static Scanner input = new Scanner (System.in);
    public static double If; //load factor
    public static int tableCapacity; //store the value of the table capacity
    public static NadeenValueEntry [] hashTable; //NadeenValueEntry type array for the hashTable
    public static NadeenValueEntry [] workingHashTable;//NadeenValueEntry type array for the workingHashTable

    //main method
    public static void main(String[] args) {


        System.out.println("Let's decide on the initial table capacity based on the load factor and dataset size");

        //user input for number of items
        System.out.println("How many data items: ");
        items = input.nextInt();

        //user input for the load factor
        System.out.println("What is the load factor (Recommended: <= 0.5):");
        If = input.nextDouble();

        //calculate the table capacity using the Math.round() and checkPrime() methods
        tableCapacity = (int)Math.round(items/If);
        tableCapacity = checkPrime(tableCapacity);
        System.out.println("The minimum required table capacity is " + tableCapacity);

        //instantiate the hashTable and the workingHashTable to the size of the tableCapacity
        hashTable = new NadeenValueEntry[tableCapacity];
        workingHashTable = new NadeenValueEntry[tableCapacity];

        //for loop to instantiate the table contents with no-argument constructor
        for (int i = 0; i < tableCapacity;i++)
        {
            hashTable[i] = new NadeenValueEntry();
        }

        // add each key to the hashTable using the linear probing technique
        for(int i = 0; i<items; i++)
        {
            System.out.printf("Enter item %d: ", (i+1));
            addValueLinearProbe(input.nextInt());
        }

        //print the hashTable
        System.out.print("The Current Hash-Table: ");
        printHashTable();
        System.out.println();

        //remove two values from the hashTable using the removeValueLinearProbe() method
        System.out.println("\nLet’s remove two values from the table and then add one…\n");
        System.out.println("Enter a value you want to remove:");
        removeValueLinearProbe(input.nextInt());
        System.out.print("The Current Hash-Table: ");
        printHashTable();//print hashTable
        System.out.println();

        System.out.println("Enter a value you want to remove:");
        removeValueLinearProbe(input.nextInt());
        System.out.print("The Current Hash-Table: ");
        printHashTable();
        System.out.println();

        //add a value to the hashTable using the addValueLinearProbe() method
        System.out.println("Enter a value to add to the table: ");
        addValueLinearProbe(input.nextInt());
        System.out.print("The Current Hash-Table: ");
        printHashTable();//print hashTable
        System.out.println();

        //rehash the hashTable by using the rehashingWithLinearProbe() method
        System.out.println("\nRehashing the table...");
        System.out.println("The rehashed table capacity is: " + checkPrime(tableCapacity*2));
        rehashingWithLinearProbe();
        System.out.print("The Current Hash-Table: ");
        printHashTable();//print hashTable


    }

    //a method that adds a value to the hashTable using linear probing
    public static void addValueLinearProbe(Integer key)
    {
        //calculate the hash index using the equation key%tableCapacity
        int hIndex = key%tableCapacity;

        //if the hash index is negative, add the table capacity so it becomes positive
        if (hIndex < 0)
        {
            hIndex += tableCapacity;
        }

        int counter = 0;//initialize the counter to 0

        while(hashTable[hIndex].getKey() != -1 && hashTable[hIndex].getKey() != -111)//while the key isn't -1 and -111 (null and available)
        {
            //increment the hash index by one
            hIndex++;

            //if the hash index reaches table capacity, then start back at index 0
            if(hIndex>=tableCapacity)
            {
                hIndex = 0;
            }

            //if the counter is equal to table capacity (gone through the table fully), exit the loop
            if(counter==tableCapacity)
            {
                break;
            }

            //increment the counter by one
            counter++;
        }

        //set the key value for the given hash index
        hashTable[hIndex].setKey(key);
    }


    //check if prime method
    public static int checkPrime(int n)
    {
        int m = n/2; //just need to check half of the n factors
        for(int i = 3; i <=m; i++)
        {
            if (n%i == 0) //if n is not a prime number
            {
                i = 2; //reset i to 2 so that it is incremented to 3 in the for-header
                n++;
                m = n/2; //just need to check half of the n factors
            }
        }
        return n;
    }

    //remove value from hash table method
    public static void removeValueLinearProbe(Integer key)
    {

        //initialize the boolean variable to false
        boolean valueIsFound = false;

        //traverse through table from index 0
        for(int i = 0; i <tableCapacity; i++)
        {
            //if the value is found, set the key to -111 (which represents an available spot) at the given hash index
            if(hashTable[i].getKey()==key)
            {
                hashTable[i].setKey(-111);
                valueIsFound = true;
                System.out.println(key + " is Found and removed!");
            }
        }

        if(!valueIsFound)//if the value is not found
        {
            System.out.println("The key value does not exist");
        }
    }

    //rehashing with linear probe method
    public static void rehashingWithLinearProbe()
    {
        //copy elements from the hashTable to the workingHashTable
        workingHashTable = hashTable;

        //find the closest prime number for the new table capacity
        tableCapacity = checkPrime(tableCapacity*2);

        //resets hash table
        hashTable = new NadeenValueEntry[tableCapacity];

        for(int i = 0; i < tableCapacity; i++)
        {
            hashTable[i] = new NadeenValueEntry();
        }

        //adds values back into the larger table
        for(int i = 0; i < workingHashTable.length; i++)
        {
            if(workingHashTable[i].getKey()!=-1 && workingHashTable[i].getKey()!=-111)
            {
                addValueLinearProbe(workingHashTable[i].getKey());
            }
        }
    }

    //print hash table method
    public static void printHashTable()
    {
        System.out.print("[");
        for(int i = 0; i< tableCapacity;i++)
        {
            if(hashTable[i].getKey()==-1)//-1 signifies a null spot
            {
                System.out.print("null, ");
            }
            else if(hashTable[i].getKey()== -111)//-111 signifies an available spot
            {
                System.out.print("available, ");
            }
            else
            {
                System.out.print(hashTable[i].getKey() + ", ");
            }
        }
        System.out.print("\b\b]");

    }

}
