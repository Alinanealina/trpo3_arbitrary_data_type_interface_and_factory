import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {
    public static void main(String[] args) throws Exception
    {
        list l = new list(new number(30));
        l.add(new number(6));
        l.add(new number(14));
        l.print();

        l.add_ind(new number(8), 1);
        l.print();

        l.sort();
        l.print();

        l.del();
        l.print();

        l.add_ind(new String2("gg"), 0);
        l.print();
    }
}
interface Comparator {
    public int compare(Object o1, Object o2);
}
interface UserType {
    public void print();
    public String typeName();
    public Object create();
    public Object clone();
    public Object readValue(InputStreamReader in);
    public Object parseValue(String ss);
    public Comparator getTypeComparator();
}

class String2 implements UserType {
    private String str;
    String2(String str)
    {
        set_str(str);
    }
    void set_str(String str)
    {
        this.str = str;
    }
    String get_str()
    {
        return str;
    }

    @Override
    public void print()
    {
        System.err.print(str);
    }
    @Override
    public String typeName()
    {
        return String.class.getSimpleName();
    }
    @Override
    public Object create()
    {
        return "";
    }
    @Override
    public Object clone()
    {
        return str;
    }
    @Override
    public Object readValue(InputStreamReader in)
    {
        try
        {
            BufferedReader f = new BufferedReader(in);
            return f.readLine();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            return "";
        }
    }
    @Override
    public Object parseValue(String ss)
    {
        return ss;
    }
    @Override
    public Comparator getTypeComparator()
    {
        return new Comparator()
        {
            public int compare(Object o1, Object o2)
            {
                return ((String)o1).compareTo((String)o2);
            }
        };
    }
}