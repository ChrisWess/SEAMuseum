package com.seamuseum.auswahlelement.guestbook;

import java.util.Date;

/**
 * Created by bashimon on 01.06.17.
 */

public class User implements Comparable<User>
{
    private final String _name;
    private final Date _date;
    private final long _time;
    private final String _message;

    public User(String name, String message)
    {
        _name = name;
        _message = message;
        _time = System.currentTimeMillis();
        _date = new Date(_time);
    }

    public User(String name, String message, long time)
    {
        _name = name;
        _message = message;
        _time = time;
        _date = new Date(_time);
    }

    public Date get_date()
    {
        return _date;
    }

    public long get_time()
    {
        return _time;
    }

    public String get_message()
    {
        return _message;
    }

    public String get_name()
    {
        return _name;
    }

    @Override
    public String toString()
    {
        return _name + " @ " + _date;
    }

    public int compareTo(User user)
    {
        return (int) (user.get_time() - get_time());
    }
}
