package EngineMenu;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class Menu {
    Map<Integer, Function<Object,Object>> menuMap = new HashMap<>();
    Set<Function<Object,Object>> menuSet = new HashSet<>();
    public Menu(){

    }
}
