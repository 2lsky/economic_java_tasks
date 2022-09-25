public class Dog {
    private String firstName;
    private int age;
    private double weight;
    private boolean vaccinated;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean isVaccinated() {
        return vaccinated;
    }

    public void setVaccinated(boolean vaccinated) {
        this.vaccinated = vaccinated;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "firstName='" + firstName + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", vaccinated=" + vaccinated +
                '}';
    }
}
