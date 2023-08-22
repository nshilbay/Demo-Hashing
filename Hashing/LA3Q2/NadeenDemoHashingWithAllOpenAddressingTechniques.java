package LA3Q2;
import LA3Q1.*;
import static LA3Q1.NadeenDemoHashingWithLinearProbing.*;
public class NadeenDemoHashingWithAllOpenAddressingTechniques {

    public static void main(String [] args){
        

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

        //create an Integer type array
        Integer [] myArr = {-11, 22, -33, -44, 55};

        //output the integer array
        System.out.print("The given dataset is: ");
        printArray(myArr);
        System.out.println();
        System.out.println("Let's enter each data item from the above to the hash table:");

        // add each key to the hashTable using the linear probing technique
        System.out.println("\nAdding data - linear probing resolves collision");
        for(int i = 0; i < myArr.length; i++)
        {
            addValueLinearProbe(myArr[i]);
        }
        printHashTable(); //print hashTable
        emptyHashTable(); //empty hashTable for next hashing technique
        System.out.println();

        // add each key to the hashTable using the quadratic probing technique
        System.out.println("\nAdding data - quadratic probing resolves collision");
        if(If<=0.5) //if the load factor is 0.5 or less
        {
            for(int i = 0; i < myArr.length; i++)
            {
                addValueQuadraticProbe(myArr[i]);
            }
        }
        else//if the load factor is greater than 0.5
        {
            System.out.println("Probing failed! Use a load factor of 0.5 or less. Goodbye!");
            for(int i = 0; i <= tableCapacity/2; i++)
            {
                addValueQuadraticProbe(myArr[i]);
            }
        }
        printHashTable();//print hashTable
        emptyHashTable();//empty hashTable for next hashing technique
        System.out.println();

        // add each key to the hashTable using the double hashing technique
        System.out.println("\nAdding data - double hashing resolves collision");
        for(int i = 0; i < myArr.length; i++)
        {
            addValueDoubleHashing(myArr[i]);
        }
        System.out.println("The q value for double hashing is : " + thePrimeNumberForSecondHashFunction(tableCapacity));
        printHashTable();//print hashTable


    }


    public static int thePrimeNumberForSecondHashFunction(int n)
    {
        int hold = n;
        int m = n/2; //just need to check half of the n factors
        for(int i = 3; i <=m; i++){
            if (n%i == 0) //if n is not a prime number
            {
                i = 2;
                n--;
                m = n/2; //just need to check half of the n factors
            }
        }

        if(hold ==n){
            return thePrimeNumberForSecondHashFunction(n-1);
        }
        return n;
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


    //a method that adds a value to the hashTable using quadratic probing
    public static void addValueQuadraticProbe(Integer key)
    {
        //calculate the hash index using the equation key%tableCapacity
        int hIndex = key%tableCapacity;

        //if the hash index is negative, add the table capacity so it becomes positive
        if(hIndex < 0)
        {
            hIndex += tableCapacity;
        }

        int counter = 0;//initialize the counter to 0

        while (hashTable[hIndex].getKey() != -1 && hashTable[hIndex].getKey() != -111) //while the key isn't -1 and -111 (null and available)
        {
            //calculate the hash index using the formula for quadratic probing
            hIndex = (key + (counter*counter))%tableCapacity;

            //if the hash index reaches table capacity, then start back at index 0
            if(hIndex>=tableCapacity)
            {
                hIndex = 0;
            }

            //if the hash index is negative, add the table capacity so it becomes positive
            if(hIndex < 0)
            {
                hIndex += tableCapacity;
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

    //a method that adds a value to the hashTable using double hashing
    public static void addValueDoubleHashing(Integer key)
    {
        //calculate the first hash index using the equation key%tableCapacity
        int hIndex1 = key%tableCapacity;

        //if the hash index is negative, add the table capacity so it becomes positive
        if(hIndex1<0)
        {
            hIndex1 +=tableCapacity;
        }

        int hIndex2;
        int j =1; //the multiplier for the second hash index
        int probe = 0;
        int q = thePrimeNumberForSecondHashFunction(tableCapacity); //the prime number for the second hash function

        if(hashTable[hIndex1].getKey()!= -1 && hashTable[hIndex1].getKey()!= -111)//while the key isn't -1 and -111 (null and available)
        {
            //calculate the second hash index
            hIndex2 = q - (key%q);

            //calculate the probe using hash index 1, hash index 2, and the multiplier j
            probe = hIndex1 + (j*hIndex2)%tableCapacity;

            while(hashTable[probe].getKey()!=-1 && hashTable[probe].getKey()!=-111)//while the key isn't -1 and -111 (null and available)
            {
                //calculate the probe using hash index 1, hash index 2, and the multiplier j
                probe = hIndex1+(j*hIndex2);
                j++; //increment the multiplier by 1

                //when the probe is greater than or equal to the table capacity, decrement by the table capacity
                while(probe >= tableCapacity)
                {
                    probe -= tableCapacity;
                }

                //if the multiplier is equivalent to the table capacity, break out of the loop
                if(j==tableCapacity)
                {
                    break;
                }
            }
            //set the key value for the given hash index
            hashTable[probe].setKey(key);
        }
        else
        {
            //set the key value for the given hash index
            hashTable[hIndex1].setKey(key);
        }
    }

    //method to empty the hash table
    public static void emptyHashTable()
    {
        for(int i = 0; i < tableCapacity; i++)
        {
            hashTable[i] = new NadeenValueEntry();
        }
    }

    //print array method
    public static void printArray(Integer [] arr)
    {
        System.out.print("[");
        for(int i = 0; i < arr.length; i++) //traverse through array
        {
            System.out.print(arr[i] + ", ");
        }
        System.out.print("\b\b]");
    }

}
