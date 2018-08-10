package com.abbiya.lists;

import java.util.ArrayList;
import java.util.List;

public class Mocked {

    public static List<Item> ITEMS = new ArrayList<Item>() {{
        add(new Item(){{
            setContent("first");
        }});
        add(new Item(){{
            setContent("second");
        }});
        add(new Item(){{
            setContent("third");
        }});
        add(new Item(){{
            setContent("fourth");
        }});
        add(new Item() {{
            setContent("fifth");
        }});
    }};
}
