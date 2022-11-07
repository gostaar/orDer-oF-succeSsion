import java.util.*;
import java.util.stream.Stream;

class Solution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        Map<String, Person> family = new HashMap<>();
        Person familyHead = null;
        for (int i = 0; i < n; i++) {
            Person p = new Person(in);
            family.put(p.getName(), p);
            if (p.getParent().equals("-")) {
                familyHead = p;
            } else {
                family.get(p.getParent()).addChildren(p);
            }
        }

        familyHead.stream().filter(Person::canBeShown).map(Person::getName).forEach(System.out::println);
    }
}

class Person {
    private final String name;
    private final String parent;
    private final int birth;
    private final boolean alive;
    private final String religion;
    private final String gender;
    private final List<Person> children;

    public Person(Scanner in) {
        this.name = in.next();
        this.parent = in.next();
        this.birth = in.nextInt();
        this.alive = in.next().equals("-");
        this.religion = in.next();
        this.gender = in.next();
        this.children = new ArrayList<>();
    }

    public void addChildren(Person child) {
        children.add(child);
        children.sort(Comparator.comparingInt(Person::genderValue).thenComparingInt(Person::getBirth));
    }

    public List<Person> getChildren() {
        return children;
    }

    public String getName() {
        return name;
    }

    public String getParent() {
        return parent;
    }

    public int getBirth() {
        return birth;
    }

    public int genderValue() {
        return "M".equals(gender) ? 0 : 1;
    }

    public boolean isAnglican() {
        return "Anglican".equals(religion);
    }

    public boolean canBeShown() {
        return alive && isAnglican();
    }

    public Stream<Person> stream() {
        return Stream.concat(Stream.of(this), getChildren().stream().flatMap(Person::stream));
    }
}
