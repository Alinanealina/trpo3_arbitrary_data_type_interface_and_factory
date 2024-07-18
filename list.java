import java.util.ArrayList;

class elem {
    UserType a;
    elem next;
    elem(UserType a)
    {
        this.a = a;
        next = null;
    }
}
interface listint
{
    UserType toDo(UserType a);
}

public class list {
    private elem head;
    list()
    {
        head = null;
    }
    list(UserType a)
    {
        add(a);
    }
    void print()
    {
        for (elem p = head; p != null; p = p.next)
        {
            p.a.print();
            System.out.print(" ");
        }
        System.out.println();
    }
    int length()
    {
        int i;
        elem p;
        for (p = head, i = 0; p != null; p = p.next, i++);
        return i;
    }
    ArrayList<UserType> get_asAL()
    {
        ArrayList<UserType> al = new ArrayList<UserType>();
        for (elem p = head; p != null; p = p.next)
            al.add(p.a);
        return al;
    }

    void add(UserType a)
    {
        if (head == null)
            head = new elem(a);
        else
        {
            elem p;
            for (p = head; p.next != null; p = p.next);
            p.next = new elem(a);
        }
    }
    void del() throws Exception
    {
        elem p;
        if (head == null)
            throw new Exception("Список пустой");
        if (length() == 1)
            head = null;
        else
        {
            for (p = head; p.next.next != null; p = p.next);
            p.next = null;
        }
    }

    void add_ind(UserType a, int ind) throws Exception
    {
        int i;
        elem p, q;
        if ((ind < 0) || (ind >= length()))
            throw new Exception("Индекс выходит за границы списка");
        if (ind == 0)
        {
            q = head;
            head = new elem(a);
            head.next = q;
        }
        else
        {
            for (p = head, i = 0; i < ind - 1; p = p.next, i++);
            q = p.next;
            p.next = new elem(a);
            p.next.next = q;
        }
    }
    void del_ind(int ind) throws Exception
    {
        int i;
        elem p;
        if ((ind < 0) || (ind >= length()))
            throw new Exception("Индекс выходит за границы списка");
        if (ind == 0)
            head = head.next;
        else
        {
            for (p = head, i = 0; i < ind - 1; p = p.next, i++);
            p.next = p.next.next;
        }
    }

    void forEach(listint a)
    {
        todo(a, head);
    }
    private void todo(listint a, elem p)
    {
        if (p == null)
            return;
        p.a = a.toDo(p.a);
        todo(a, p.next);
    }

    list sort()
    {
        UserType[] buf = new UserType[length()];
        elem p;
        int i;
        for (p = head, i = 0; p != null; p = p.next, i++)
            buf[i] = p.a;
        UserType[] res = mergeSort(buf, new UserType[length()], 0, length());
        list sort = new list(res[0]);
        for (i = 1; i < res.length; i++)
            sort.add(res[i]);
        head = sort.head;
        return sort;
    }
    private UserType[] mergeSort(UserType[] buf, UserType[] buf2, int L, int R)
    {
        if (L >= R - 1)
            return buf;
        Comparator c = buf[0].getTypeComparator();
        int m = L + (R - L) / 2, i = L, j = m, k = L;
        UserType[] sort = mergeSort(buf, buf2, L, m), sort2 = mergeSort(buf, buf2, m, R), res;
        if (sort == buf)
            res = buf2;
        else
            res = buf;
        for (; (i < m) && (j < R); k++)
        {
            if (c.compare(sort[i], sort2[j]) < 0)
                res[k] = sort[i++];
            else
                res[k] = sort[j++];
        }
        for (; i < m; i++, k++)
            res[k] = sort[i];
        for (; j < R; j++, k++)
            res[k] = sort2[j];
        return res;
    }
}
