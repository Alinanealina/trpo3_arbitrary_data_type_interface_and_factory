import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class number implements UserType {
    private ArrayList<Integer> n = new ArrayList<Integer>();
    private boolean isprime(int a)
    {
        for (int i = 2; i < Math.sqrt(a); i++)
        {
            if (a % i == 0)
                return false;
        }
        return true;
    }
    private void filln(int a)
    {
        n.clear();
        for (int i = 2; i < a ; i++)
        {
            if (!isprime(i))
                continue;
            for (; a % i == 0; a /= i)
                n.add(i);
        }
        if (a > 1)
            n.add(a);
    }

    number(int a)
    {
        set_num(a);
    }
    number(ArrayList<Integer> a)
    {
        set_arr(a);
    }
    @Override
    public void print()
    {
        System.out.print(get_num());
    }

    int sum(int a)
    {
        int n = get_num() + a;
        if (n > 0)
        {
            filln(n);
            return get_num();
        }
        else
        {
            System.out.println("number < 0");
            return -1;
        }
    }
    int diff(int a)
    {
        int n = get_num() - a;
        if (n > 0)
        {
            filln(n);
            return get_num();
        }
        else
        {
            System.out.println("number < 0");
            return -1;
        }
    }
    int mul(int a)
    {
        int n = get_num() * a;
        if (n > 0)
        {
            filln(n);
            return get_num();
        }
        else
        {
            System.out.println("number < 0");
            return -1;
        }
    }
    int div(int a)
    {
        int n = get_num() / a;
        if (n > 0)
        {
            filln(n);
            return get_num();
        }
        else
        {
            System.out.println("number < 0");
            return -1;
        }
    }

    void set_ind_n(int i, int a)
    {
        if ((i >= n.size()) || (i < 0))
            System.out.println("Индекс выходит за границы массива");
        else if (a < 0)
            System.out.println("number < 0");
        else if (!isprime(a))
            System.out.println("Число не простое");
        else
            n.set(i, a);
    }
    void set_n(int a)
    {
        if (a < 0)
            System.out.println("number < 0");
        else if (!isprime(a))
            System.out.println("Число не простое");
        else
            n.add(a);
    }
    void del_ind_n(int i)
    {
        if ((i >= n.size()) || (i < 0))
            System.out.println("Индекс выходит за границы массива");
        else
            n.remove(i);
    }

    void set_num(int a)
    {
        if (a > 0)
            filln(a);
        else
            System.out.println("number < 0");
    }
    int get_num()
    {
        int a = 1;
        for (int i = 0; i < n.size(); i++)
            a *= n.get(i);
        return a;
    }

    void set_arr(ArrayList<Integer> a)
    {
        n.clear();
        n.addAll(a);
    }
    ArrayList<Integer> get_arr()
    {
        return n;
    }

    void write(String filename)
    {
        try
        {
            PrintWriter f = new PrintWriter(new FileWriter(filename));
            for (int i = 0; i < n.size(); i++)
                f.write(n.get(i) + " ");
            f.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
    void read(String filename)
    {
        try
        {
            Scanner f = new Scanner(new FileReader(filename));
            while (f.hasNextLine())
            {
                n.clear();
                String str = f.nextLine();
                String[] nums = str.split(" ");
                for (String s : nums)
                    n.add(Integer.parseInt(s));
            }
            f.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    void write_bin(String filename)
    {
        try
        {
            ObjectOutputStream f = new ObjectOutputStream(new FileOutputStream(filename));
            for (int i = 0; i < n.size(); i++)
                f.write(n.get(i));
            f.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
    void read_bin(String filename)
    {
        try
        {
            ObjectInputStream f = new ObjectInputStream(new FileInputStream(filename));
            n.clear();
            while (true)
            {
                try
                {
                    int m = f.readInt();
                    n.add(m);
                }
                catch (EOFException e)
                {
                    break;
                }
            }
            f.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String typeName()
    {
        return number.class.getSimpleName();
    }
    @Override
    public Object create()
    {
        return new number(2);
    }
    @Override
    public Object clone()
    {
        return new number(get_num());
    }
    @Override
    public Object readValue(InputStreamReader in)
    {
        try
        {
            BufferedReader f = new BufferedReader(in);
            return parseValue(f.readLine());
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            return -1;
        }
    }
    @Override
    public Object parseValue(String ss)
    {
        String[] splittedStr = ss.split(" ");
        if (splittedStr.length == 1)
            return new number(Integer.parseInt(splittedStr[0]));
        else if (splittedStr.length > 1)
        {
            int a, i;
            for (a = 0, i = 1; i < splittedStr.length; i++)
            {
                int m = Integer.parseInt(splittedStr[i]);
                if (isprime(m))
                    a *= m;
                else
                {
                    System.out.println("Число не простое");
                    return -1;
                }
            }
            return new number(a);
        }
        else
            return new number(2);
    }
    @Override
    public Comparator getTypeComparator()
    {
        return new Comparator()
        {
            public int compare(Object o1, Object o2)
            {
                return ((number)o1).get_num() - ((number)o2).get_num();
            }
        };
    }
}
