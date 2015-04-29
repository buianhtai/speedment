package com.speedment.orm.function;


import com.speedment.orm.field.test.Hare;
import com.speedment.orm.field.test.HareManager;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import static com.speedment.orm.function.Predicates.eq;
import java.util.List;
// import static com.speedment.orm.function.Predicates.greterThan;

/**
 *
 * @author pemi
 */
@Deprecated
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //List<Hare> hareList = Stream.of(new Hare()).filter(h- > equals("harry",Hare::getName)).collect(toList());
        // Supplier<String> nameSupplier = Hare h -> h.get;
        List<Hare> hareList = Stream.of(new HareHarry())
                .filter(h -> "harry".equals(h.getName()))
                .filter(h -> h.getAge() > 1)
                .filter(h -> h.getAge() < 9)
                .collect(toList());

        System.out.println(hareList);

        List<Hare> hareList2 = Stream.of(new HareHarry())
                .filter(eq("harry", Hare::getName))
                .filter(Predicates.greaterThan(Hare::getAge, 1))
                .filter(Predicates.lessThan(Hare::getAge, 9))
                .collect(toList());

        System.out.println(hareList2);

        HareManager hm = new HareManager();

        List<Hare> hareList3 = hm.stream()
                .filter(eq("harry", Hare::getName))
                .filter(Predicates.greaterThan(Hare::getAge, 1))
                .filter(Predicates.lessThan(Hare::getAge, 9))
                .collect(toList());

        System.out.println(hareList3);

        List<Hare> hareList4 = hm.stream()
                .filter(HareField.name().equal("harry"))
                .filter(HareField.age().greaterThan(1))
                .filter(HareField.age().lessThan(9))
                .collect(toList());
        System.out.println(hareList4);

        List<Hare> hareList5 = hm.stream()
                .filter(HareField.name().equal("harry"))
                .filter(HareField.age().greaterThan(1))
                .filter(HareField.age().lessThan(9))
                .collect(toList());
        System.out.println(hareList5);

        Hare hare = hareList5.stream().findAny().get();

        String name = HareField.name().from(hare);

    }

}
